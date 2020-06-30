package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import model.Message; //class to set the display of the list

public class MessageListRenderer implements ListCellRenderer {

	private JPanel panel;
	private JLabel label;
	private Color selectedColor;
	private Color normalColor;

	public MessageListRenderer() {
		panel = new JPanel();
		label = new JLabel(); // create label and panel. Add icon to label and add label to panel

		label.setFont(createFont("/fonts/CrimewaveBB.ttf").deriveFont(Font.PLAIN, 18));

		selectedColor = new Color(210, 210, 255);
		normalColor = Color.white;

		label.setIcon(createIcon("/images/Information24.gif"));

		panel.setLayout(new FlowLayout(FlowLayout.LEFT));

		panel.add(label);
	}

	private ImageIcon createIcon(String path) {

		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

	private Font createFont(String path) { // createFont method

		URL url = getClass().getResource(path); // gets URL using path of file

		if (url == null) {
			System.err.println("Unable to load font: " + path);
		}

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			System.err.println("Bad format or unable to read file : " + path);
		}

		return font;
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		Message message = (Message) value;

		label.setText(message.getTitle()); // setting title to component

		panel.setBackground(cellHasFocus ? selectedColor : normalColor); // setting background if selected.

		return panel;
	}
}
