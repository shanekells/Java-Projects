package GUI;
							//enables the employment category to be editable in the display form
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import model.EmploymentCategory;

public class EmploymentCategoryEditor extends AbstractCellEditor implements TableCellEditor{

	private JComboBox combo; 	//uses combo box to display options 
	
	public EmploymentCategoryEditor() {
		combo = new JComboBox(EmploymentCategory.values());	//passes values from Employment Category enumeration 
	}
	
	public Object getCellEditorValue() {
		return combo.getSelectedItem();
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		combo.setSelectedItem(value);
		
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();		//tells table cell to stop using editor and start using renderer to save the change.
			}
		});
		
		return combo;
	}

	public boolean isCellEditable(EventObject e) {
		return true;
	}
	
}
