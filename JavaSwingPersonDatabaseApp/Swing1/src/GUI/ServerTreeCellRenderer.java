package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
			//class to set up renderers for each node
public class ServerTreeCellRenderer implements TreeCellRenderer {

	private JCheckBox leafRenderer;
	private DefaultTreeCellRenderer nonLeafRenderer;

	private Color textForeground;
	private Color textBackground;
	private Color selectionForeground;
	private Color selectionBackground;

	public ServerTreeCellRenderer() {	//sets components
		leafRenderer = new JCheckBox();
		nonLeafRenderer = new DefaultTreeCellRenderer();

		nonLeafRenderer.setLeafIcon(createIcon("/images/Server16.gif"));
		nonLeafRenderer.setOpenIcon(createIcon("/images/WebComponent16.gif"));
		nonLeafRenderer.setClosedIcon(createIcon("/images/WebComponentAdd16.gif"));

		textForeground = UIManager.getColor("Tree.textForeground");
		textBackground = UIManager.getColor("Tree.textBackground");
		selectionForeground = UIManager.getColor("Tree.selectionForeground");
		selectionBackground = UIManager.getColor("Tree.selectionBackground");

	}

	private ImageIcon createIcon(String path) {		//creates icons

		URL url = getClass().getResource(path);
		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

	private Font createFont(String path) {		//createFont method

		URL url = getClass().getResource(path);  //gets URL using path of file
		
		if (url == null) {			
			System.err.println("Unable to load font: " + path);
		}

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			System.err.println("Bad format or unable to read file : "+path);
		}
		
		return font;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		if (leaf) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			ServerInfo nodeInfo = (ServerInfo) node.getUserObject();

			if (selected) {
				leafRenderer.setForeground(selectionForeground);
				leafRenderer.setBackground(selectionBackground);
			} else {
				leafRenderer.setForeground(textForeground);
				leafRenderer.setBackground(textBackground);
			}

			leafRenderer.setText(nodeInfo.toString());
			leafRenderer.setSelected(nodeInfo.isChecked());

			return leafRenderer;
		} else {
			return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	}

}
