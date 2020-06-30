package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import model.EmploymentCategory;
import model.Person;
			//class to add the panel to hold the table
public class TablePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private PersonTableModel tableModel;		//declare components
	private JPopupMenu popup;
	private PersonTableListener personTableListener;

	public TablePanel() {
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		popup = new JPopupMenu();
										//sets renderer for the table
		table.setDefaultRenderer(EmploymentCategory.class, new EmploymentCategoryRenderer());
		table.setDefaultEditor(EmploymentCategory.class, new EmploymentCategoryEditor());
		table.setRowHeight(25);
		
		JMenuItem removeItem = new JMenuItem("Delete row");
		popup.add(removeItem);

		table.addMouseListener(new MouseAdapter() {		//adds listener to table
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());

				table.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}
		});

		removeItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

				if (personTableListener != null) {
					personTableListener.rowDeleted(row);
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});

		setLayout(new BorderLayout());
		add(table, BorderLayout.CENTER);
	}

	public void setData(List<Person> db) {
		tableModel.setData(db);
	}

	public void refresh() {
		tableModel.fireTableDataChanged();
	}

	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
	}
}
