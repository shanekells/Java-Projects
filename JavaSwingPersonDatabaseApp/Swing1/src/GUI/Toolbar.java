package GUI;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
									//creates a new toolBar custom component
public class Toolbar extends JPanel implements ActionListener{
	
	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener textListener;
	
	public Toolbar() {
								//set layout
		setBorder(BorderFactory.createEtchedBorder());
		
		saveButton = new JButton();		// add save button
		saveButton.setIcon(createIcon("/images/Save16.gif"));
		saveButton.setToolTipText("Save");
		
		refreshButton = new JButton();		//add refresh button
		refreshButton.setIcon(createIcon("/images/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");
		
		saveButton.addActionListener(this);		//add action listeners to buttons
		refreshButton.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(saveButton);		//add buttons to toolbar
		add(refreshButton);
	}
													//method to create icon for button
	private ImageIcon createIcon(String path) {
		
		URL url = getClass().getResource(path);
		if(url == null) {
			System.err.println("Unable to load image: " + path);
		}
		ImageIcon icon = new ImageIcon(url);
		return icon;
	}
													//set the listener
	public void setToolbarListener(ToolbarListener listener) {
		this.textListener = listener;
	}

	public void actionPerformed(ActionEvent e) { 	//code that is implemented when an action is received
		
		JButton clicked = (JButton)e.getSource();	//get the clicked item
		
		if(clicked == saveButton) {		//if save button then implement saveEventOccured method
			if(textListener != null) {
				textListener.saveEventOccured();
			}							//if refresh button then implement refreshEventOccured method
		}else if(clicked == refreshButton){
			if(textListener != null) {
				textListener.refreshEventOccured();
			}
		}
	}
}
