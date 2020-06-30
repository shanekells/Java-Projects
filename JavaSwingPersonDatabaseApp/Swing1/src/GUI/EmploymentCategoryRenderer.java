package GUI;		

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import model.EmploymentCategory;			//renders a cell as a comboBox
					//Renderer class that enables a combo box for EmploymentCategory
public class EmploymentCategoryRenderer implements TableCellRenderer{

	private JComboBox combo;
	
	public EmploymentCategoryRenderer() {
		combo = new JComboBox(EmploymentCategory.values());
	}
			//get selected value in the combo box and return it.
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		combo.setSelectedItem(value);
		
		return combo;
	}

}
