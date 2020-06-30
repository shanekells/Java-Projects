import javax.swing.SwingUtilities;

import GUI.MainFrame;

/*
*Swing app created that add persons details to it's data model and add to an SQL database on my laptop.
*The app also loads the person information from the database and displays in a table. I've also created a message 
*display area that display server messages on the screen depending on what server you select. The app also allows  
*you to save the person information to a file on my laptop and it can also load information from a file to the app and 
*save to the database.
*
* @author Shane Kelly
* @version 1.0
*/


public class App {

	public static void main(String[] args) {
												//running app in thread
		SwingUtilities.invokeLater(new Runnable() { //using static method invokeLater
			public void run() {
				new MainFrame();
			}
		});
	}
}
