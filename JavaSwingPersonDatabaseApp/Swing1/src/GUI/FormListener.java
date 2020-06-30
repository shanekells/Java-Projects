package GUI;
import java.util.EventListener;
				//FormListener is the interface between formPanel and MainFrame 
				//to allow MainFrame to intercept FormEventOccured method.
public interface FormListener extends EventListener{
	public void formEventOccurred(FormEvent e);
}
