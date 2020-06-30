package GUI;

import java.net.URL;

import javax.swing.ImageIcon;
						//Utils class that checks the filename and gets extension
public class Utils {
	public static String getFileExtension(String name) {
		int pointIndex = name.lastIndexOf(".");

		if (pointIndex == -1) {
			return null;
		}

		if (pointIndex == name.length() - 1) {
			return null;
		}

		return name.substring(pointIndex + 1, name.length());
	}
}
