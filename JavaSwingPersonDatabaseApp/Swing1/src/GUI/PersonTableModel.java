package GUI;
					//class for wrapper for the data that presents 
					//the data flor the table in the correct way
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.EmploymentCategory;
import model.Person;

public class PersonTableModel extends AbstractTableModel {

	private List<Person> db;
	
	private String[] colNames = {"ID", "Name", "Occupation", "Age Category","Employment Category","Irish Citizen","Tax ID"};
	
	public PersonTableModel() {
	}
	
	public String getColumnName(int column) {
		return colNames[column];
	}



	public boolean isCellEditable(int row, int col) { // overriding method to make name column editable
		switch (col) {
		case 1:
			return true;
		case 4:
			return true;
		case 5:
			return true;
		default:
			return false;
		}
	}

	public void setValueAt(Object value, int row, int column) { // overriding method to edit values of the table
																// (example here is name and irishCitizen)
		if (db == null)
			return;

		Person person = db.get(row);

		switch (column) {
		case 1:
			person.setName((String) value);
			break;
		case 4:
			person.setEmpCat((EmploymentCategory)value);		//set method to actually set the value of the data to the changed value.
			break;
		case 5:
			person.setIrishCitizen((Boolean)value);
		default:
			return;

		}
	}

	public Class<?> getColumnClass(int col) { // used to put checkbox in a cell in the table
		switch (col) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;		//returns object of type class
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return EmploymentCategory.class;
		case 5:
			return Boolean.class;
		case 6:
			return String.class;
		}
		return null;
	}

	public void setData(List<Person> db) {
		this.db = db;
	}

	public int getRowCount() {
		return db.size();
	}

	public int getColumnCount() {
		return 7;
	}

	public Object getValueAt(int row, int col) {
		Person person = db.get(row);

		switch (col) {
		case 0:
			return person.getId();
		case 1:
			return person.getName();
		case 2:
			return person.getOccupation();
		case 3:
			return person.getAgeCategory();
		case 4:
			return person.getEmpCat();
		case 5:
			return person.isIrishCitizen();
		case 6:
			return person.getTaxId();
		}

		return null;
	}
}
