package omniapi.webwalker.web.loader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.webwalker.web.Web;
import omniapi.webwalker.web.WebNode;
import omniapi.webwalker.web.loader.util.LoaderThread;

public abstract class AbstractWebLoader extends OmniScriptEmulator<OmniScript> implements ILoader<Web, WebNode> {

	private OmniScript context;
	private Web web;
	private InputStream fileStream;

	protected final int processors = 1;

	protected AbstractWebLoader(OmniScript context, Web web) {
		super(context);
		this.context = context;
		this.web = web;
		try {
			this.fileStream = new URL("https://raw.githubusercontent.com/Bobrocket/WebWalkerResource/master/web.dat")
					.openStream();
			// log("Loaded!");
		} catch (IOException e) {
			// warn("Loading failed!");
			e.printStackTrace();
			getScript().stop(false);
		}
		// log("IF IT'S NOT LOADING, PM CZAR");
	}

	public abstract WebNode parseLine(String line);

	public abstract String parseObject(WebNode node);

	public abstract Web load();

	public final ArrayList<WebNode> getNodes() {
		parseLines(readNodes());
		return web.getNodes();
	}

	public final WebNode[] parseLines(String[] set) {
		try {
			int size = set.length - 1;
			// log("Reading web on " + processors + " thread" + (processors > 1
			// ? "s" : "") + "...");
			// log("[DEBUG] " + size + " lines per thread");
			ExecutorService es = Executors.newCachedThreadPool();
			int start = 0;
			int end = size;
			LoaderThread readerThread = new LoaderThread(this, set, web, start, end);
			es.execute(readerThread);
			es.shutdown();
			web.setLoaded(es.awaitTermination(10, TimeUnit.MINUTES));
			if (!web.isLoaded()) {
				log("Failed to load web! Please restart the script.");
				getScript().stop(false);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final void store() {
		try {
			long startTime = System.currentTimeMillis();
			log("Storing web file...");
			int written = 0;
			File outFile = new File(LoaderSettings.WEB);
			FileOutputStream out = new FileOutputStream(outFile);
			for (String s : getStrings()) {
				written++;
				out.write(s.getBytes());
			}
			out.close();
			log("Successfully wrote " + written + "nodes");
			log("Wrote web file written to " + outFile.getPath() + " in " + (System.currentTimeMillis() - startTime)
					+ "ms");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log("Inputstreams cant melt steel beams");
	}

	public final String[] getStrings() {
		ArrayList<String> nodeStrings = new ArrayList<>();
		for (WebNode node : getWeb().getNodes()) {
			nodeStrings.add(parseObject(node));
		}
		return nodeStrings.toArray(new String[nodeStrings.size()]);
	}

	private String[] readNodes() {
		String[] rawSet;
		try {
			List<String> strings = new ArrayList<>();
			BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream()));
			String line;
			while ((line = br.readLine()) != null) {
				strings.add(line);
			}

			rawSet = strings.toArray(new String[strings.size()]);
			// log("Loaded " + strings.size() + " lines");
			return rawSet;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isLoaded() {
		return web.isLoaded();
	}

	public final Web getWeb() {
		return web;
	}

	public final InputStream getFileStream() {
		return fileStream;
	}
}
