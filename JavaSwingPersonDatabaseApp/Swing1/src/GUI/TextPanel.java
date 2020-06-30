package GUI;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
									//creating a text panel for the main frame
public class TextPanel extends JPanel {
	
	private JTextArea textArea;
	
	public TextPanel() {
		textArea = new JTextArea();
										//setting dimensions of the textArea
		textArea.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		
		textArea.setFont(new Font("Serif",Font.PLAIN,20));		//setting font of text
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);	//wrapping the text area in a scroll pane
		
	}
	
	public void appendText(String text) {	//method to add text to the text area
		textArea.append(text);
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}
}
