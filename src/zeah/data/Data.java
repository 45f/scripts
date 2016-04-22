/*
*
* Created Mar 7, 2016, 8:23:26 PM. 
* @author Stress <steelgseries@hotmail.com>
* Copyright (C) 2016  Stress

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
*
*/
package zeah.data;

public class Data {

	public enum Messages {
		MESSAGE_1("We've received reports of a gang meeting in the Hosidius District, upstairs in a shepherd's house west of the bank.", 0, 0, 0),
		MESSAGE_2("We've received reports of a gang infiltrating our own camp! They're meeting in a storage tent north-east of the bank.", 0, 0, 0),
		MESSAGE_3("We've received reports of a gang infiltrating our own camp! They're meeting in the south-west corner of the graveyard.", 0, 0,0),
		MESSAGE_4("We've received reports of a gang meeting in the Hosidius District, in the house south of the food servery.", 0, 0, 0),
		MESSAGE_5("We've received reports of a gang meeting in the Hosidius District, upstairs in the farm shop.", 0, 0, 0),
		MESSAGE_6("We've received reports of a gang meeting in the Piscarilius District, in the bar on the pier.", 0, 0, 0),
		MESSAGE_7("We've received reports of a gang meeting in the Piscarilius District, upstairs in a bunkhouse south-west of the bank.", 0, 0, 0),
		MESSAGE_8("We've received reports of a gang meeting at the west side of the Piscarilius District, in one of their cabins on the pier.", 0, 0, 0),
		MESSAGE_9("We've received reports of gang meeting in the Arceuus District, in a house south-east of the bank.", 0, 0, 0),
		MESSAGE_10("We've received reports of gang meeting in the Hosidius District, in the southern pub.", 0, 0, 0),
		MESSAGE_11("We've received reports of a gang meeting in the Arceuus District, in a house west of the bank.", 0, 0, 0),
		MESSAGE_12("We've received reports of a gang meeting in the Arceuus district, upstairs in the bar.", 0, 0, 0),
		MESSAGE_13("We've received reports of a gang meeting at the statue of King Rada in the centre of Kourend.", 0, 0, 0),
		MESSAGE_14("We've received reports of a gang meeting in the Lovakengj District, in the pub.", 0, 0, 0),
		MESSAGE_15("We've received reports of a gang meeting in a house at the south-eastern corner of the Lovakengj District.", 0, 0, 0),
		MESSAGE_16("We've received reports of a gang meeting in the Piscarilius District, upstairs in a house due south of the bank.", 0, 0, 0),
		MESSAGE_17("We've received reports of a gang meeting at the north-west corner of the Piscarilius district, in a shack by the mountains.", 0, 0, 0),
		MESSAGE_18("We've received reports of a gang meeting in the Arceuus district, in a house south-west of the Library.", 0, 0, 0),
		MESSAGE_19("We've received reports of a gang meeting in the Hosidius district, upstairs in the eastern house with ploughs outside.", 0, 0, 0);
		
		private String message;
		private int x;
		private int y;
		private int z;
		
		private Messages(String message, int x, int y, int z) {
			this.setMessage(message);
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public String getMessage() { return message; }

		public void setMessage(String message) { this.message = message; }

		public int getX() { return x; }

		public void setX(int x) { this.x = x; }

		public int getY() { return y; }

		public void setY(int y) { this.y = y; }

		public int getZ() { return z; }

		public void setZ(int z) { this.z = z; }

		
		
	}
}
