package controller;
					//Controller is an interface that allows the frontend(GUI) to manipulate the 
import java.io.File;	//the data in the database without directly referencing the database.
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import GUI.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;	//imports
import model.Gender;
import model.Person;

public class Controller {
	Database db = new Database();	//create db object
									//db methods
	public List<Person> getPeople(){
		return db.getPeople();
	}
	
	public void removePerson(int index) {
		db.removePerson(index);
	}
	
	public void save() throws SQLException {
		db.save();
	}
	
	public void disconnect() {
		db.disconnect();
	}
	
	public void load() throws SQLException {
		db.load();
	}
									
	public void configure(int port, String user, String password) throws Exception {
		db.configure(port, user, password);
	}
	
	public void connect() throws Exception {
		db.connect();
	}
								//addPerson method that gets the person details from the event passed
	public void addPerson(FormEvent ev) {	//to it, creates the person object and then adds it to the database.
		String name = ev.getName();
		String occupation = ev.getOccupation();
		int ageCatId = ev.getAgeCategory();
		String empCat = ev.getEmploymentCategory();
		boolean isIrish = ev.isIrishCitizen();
		String taxId = ev.getTaxId();
		String gender = ev.getGender();
		
		AgeCategory ageCategory = null;
		switch(ageCatId) {
		case 0:
			ageCategory = AgeCategory.child;
			break;
		case 1:
			ageCategory = AgeCategory.adult;
			break;
		case 2:
			ageCategory = AgeCategory.senior;
			break;
		}
		
		EmploymentCategory empCategory;
		
		if(empCat.equals("employed")) {
			empCategory = EmploymentCategory.employed;
		}
		else if(empCat.equals("self-employed")) {
			empCategory = EmploymentCategory.selfEmployed;
		}
		else if(empCat.equals("unemployed")) {
			empCategory = EmploymentCategory.unemployed;
		}
		else {
			empCategory = EmploymentCategory.other;
			System.err.println();
			
		}
		
		Gender genderCat;
		if(gender.contentEquals("male")) {
			genderCat = Gender.male;
		}
		else {
			genderCat = Gender.female;
		}
		
		Person person = new Person(name, occupation, ageCategory, empCategory, 
				taxId, isIrish, genderCat);
		db.addPerson(person);
	}
						//saves db information to a file(export menu bar option)
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}
						//loads person information from a file(import menu bar option)
	public void loadFromFile(File file) throws IOException{
		db.loadFromFile(file);
	}
}
