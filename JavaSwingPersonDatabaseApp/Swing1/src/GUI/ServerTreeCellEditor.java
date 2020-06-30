package GUI;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
							//class that enables the user to select a checkbox
public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

	private ServerTreeCellRenderer renderer;
	private JCheckBox checkBox;
	private ServerInfo info;
	
	public ServerTreeCellEditor() {
		renderer = new ServerTreeCellRenderer();
	}
	
	public Object getCellEditorValue() {
		info.setChecked(checkBox.isSelected()); //update the info object
		return info;
	}

	//method to get the editor for the selected branch
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);
		
		if(leaf) {
			
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
			info = (ServerInfo)treeNode.getUserObject();
			checkBox = (JCheckBox) component;
			
			ItemListener itemListener = new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					fireEditingStopped();  //tell the tree to stop using the editor and use the renderer when a checkbox has been ticked
					checkBox.removeItemListener(this);
				}
			};
			
			checkBox.addItemListener(itemListener);
		}else {
			
		}
		
		return component;
	}
		//method that checks what branch of the tree the user has clicked on//
	public boolean isCellEditable(EventObject event) {
		if(!(event instanceof MouseEvent)) return false;
		
		MouseEvent mouseEvent = (MouseEvent) event;
		JTree tree = (JTree) event.getSource();
		
		TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());	//get path of branches the user has clicked on
		
		if(path == null) return false;
		
		Object lastComponent = path.getLastPathComponent(); //get last branch clicked
		
		if(lastComponent == null) return false;
	
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent;
		
		return treeNode.isLeaf();			
	}

}
