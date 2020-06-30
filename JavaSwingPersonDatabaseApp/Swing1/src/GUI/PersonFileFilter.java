package GUI;
import java.io.File;

import javax.swing.filechooser.FileFilter;
							//class that sets the extensions that 
							//the file chooser searches for.
public class PersonFileFilter extends FileFilter {

	public boolean accept(File file) {
		String name = file.getName();
		
		String extension = Utils.getFileExtension(name);
		
		if(file.isDirectory()) {
			return true;
		}
		
		if(extension == null) {
			return false;
		}
		
		if(extension.equals("per")) {
			return true;
		}
		
		return false;
	}

	public String getDescription() {
		return "Person database files (*.per)";
	}

}
