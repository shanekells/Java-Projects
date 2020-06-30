package model;
						//class for the database and its model
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
	private List<Person> people;
	private Connection con;
	
	private int port;
	private String user;		//variables for database login details
	private String password;

	public Database() {
		people = new LinkedList<Person>();
	}
	
	public void configure(int port, String user, String password) throws Exception {
		this.port = port;
		this.user = user;
		this.password = password;
		
		if(con != null) { 		//disconnects if already connected then reconnects.
			disconnect();
			connect();
		}
	}

	public void connect() throws Exception {
		if (con != null)
			return;
		try {					//database driver
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
						//url for connecting to database
		String url = "jdbc:mysql://localhost:3306/swingtest";
		con = DriverManager.getConnection(url, "root", "");	//

	}

	public void disconnect() {		//disconnecting from the database
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can't close connection");
			}
		}
	}

	public void save() throws SQLException {		//saving person details to the database

		String checkSql = "select count(*) as count from people where id=?";	//statement to check if id is already in database

		PreparedStatement checkStmt = con.prepareStatement(checkSql);		
							//SQL statement to insert person into database
		String insertSql = "insert into people (id,name,age,employment_status,tax_id, irish_citizen, gender, occupation) values(?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
							//SQL statement to update data in the database
		String updateSql = "update people set name=?,age=?,employment_status=?,tax_id=?, irish_citizen=?, gender=?, occupation=? where id=?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);

		for (Person person : people) {		//loop through each person and get the details
			int id = person.getId();
			String name = person.getName();
			AgeCategory age = person.getAgeCategory();
			String occupation = person.getOccupation();
			EmploymentCategory emp = person.getEmpCat();
			String tax = person.getTaxId();
			boolean isIrish = person.isIrishCitizen();
			Gender gender = person.getGender();

			checkStmt.setInt(1, id);	//check if the person is in the database, if not then add to the database

			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();

			int count = checkResult.getInt(1);

			if (count == 0) {		//add person if not in the database
				System.out.println("Inserting person with ID " + id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, age.name());
				insertStatement.setString(col++, emp.name());
				insertStatement.setString(col++, tax);
				insertStatement.setBoolean(col++, isIrish);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);

				insertStatement.executeUpdate();

			} else {				//update person in the database
				System.out.println("Updating person with ID " + id);
				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setString(col++, age.name());
				updateStatement.setString(col++, emp.name());
				updateStatement.setString(col++, tax);
				updateStatement.setBoolean(col++, isIrish);
				updateStatement.setString(col++, gender.name());
				updateStatement.setString(col++, occupation);
				updateStatement.setInt(col++, id);

				updateStatement.executeUpdate();
			}
		}

		insertStatement.close();
		checkStmt.close();
		updateStatement.close();
	}

	public void load() throws SQLException {		//load data from database to the person model
		people.clear();

		String sql = "select id,name,age,employment_status,tax_id, irish_citizen, gender, occupation from people order by name";
		Statement selectStatement = con.createStatement();

		ResultSet results = selectStatement.executeQuery(sql);

		while (results.next()) {

			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String taxId = results.getString("tax_id");
			boolean isIrish = results.getBoolean("irish_citizen");
			String gender = results.getString("gender");
			String occ = results.getString("occupation");

			Person person = new Person(id, name, occ, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), taxId,
					isIrish, Gender.valueOf(gender));

			people.add(person);

		}
		results.close();
		selectStatement.close();
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public void removePerson(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {		//save person data to file
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);

		oos.writeObject(persons);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {		//load person data from file.
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
	}
}
