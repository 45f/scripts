package omniapi.webwalker.web.loader;

import java.io.InputStream;

public interface ILoader<K, V> {

	K load();

	void store();

	InputStream getFileStream();

	V[] parseLines(String[] set);

	String[] getStrings();

	V parseLine(String line);

	String parseObject(V object);

}
