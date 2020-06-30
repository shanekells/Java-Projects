package GUI;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressDialog extends JDialog { // model for the progress bar

	private JButton cancelButton;
	private JProgressBar progressBar;
	private ProgressDialogListener listener;

	public ProgressDialog(Window parent, String title) {
		super(parent, title, ModalityType.APPLICATION_MODAL);
		
		cancelButton = new JButton("Cancel");
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);		//setting the progress bar text enabled
		
		progressBar.setMaximum(10);
		
		progressBar.setString("Retrieving messages.....");		//adding text to the progress bar

		setLayout(new FlowLayout());

		Dimension size = cancelButton.getPreferredSize();
		size.width = 400;
		progressBar.setPreferredSize(size);

		add(progressBar);
		add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listener!= null) {
					listener.progressDialogCancelled();
				}
			}
		});
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);		//do nothing when the X is clicked on the dialog
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {		//listen for window closing and  
				if(listener!= null) {						//call progressDialogCancelled method
					listener.progressDialogCancelled();
				}			
			}
		});
		
		pack(); // pack shrink wraps the dialog around the countrols so its the min size.

		setLocationRelativeTo(parent); // locates the dialog relative to parent
	}
	
	public void setListener(ProgressDialogListener listener) {		//listening to the progress dialog
		this.listener = listener;
	}

	public void setMax(int value) { // sets the numerical value of the progress bar
		progressBar.setMaximum(value);
	}

	public void setValue(int value) { // sets the current value of the progress bar
		int progress = 100*value/progressBar.getMaximum();		//getting the progress percentage 
		
		progressBar.setString(String.format("%d%% complete", progress));		//adding a percentage text to the progress bar
		
		progressBar.setValue(value);
	}

	public void setVisible(final boolean visible) {

		SwingUtilities.invokeLater(new Runnable() { // using threading to set visibility
			public void run() {

				if (visible == false) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					progressBar.setValue(0);
				}
				
				if(visible) {		//setting the cursor to change when dialog is visible
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		//set to wait cursor
				}else {
					setCursor(Cursor.getDefaultCursor());		//set back to default
				}
				ProgressDialog.super.setVisible(visible); // invoke the super class setVisible
			}
		});
	}
}
