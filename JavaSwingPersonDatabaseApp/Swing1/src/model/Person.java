package model;

import java.io.Serializable;
			//class for person data model
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private static int count = 1;
	private int id;
	private String name;
	private String occupation;		//declare variables
	private AgeCategory ageCategory;
	private EmploymentCategory empCat;
	private String taxId;
	private boolean irishCitizen;
	private Gender gender;
									//contructor for Person object where id is assigned from the count id
	public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat, String taxId,
			boolean irishCitizen, Gender gender) {
		this.name = name;
		this.occupation = occupation;
		this.empCat = empCat;
		this.taxId = taxId;
		this.irishCitizen = irishCitizen;
		this.gender = gender;
		this.ageCategory = ageCategory;

		this.id = count;
		count++;
	}
							//contructor for Person object where id is assigned in the parameters
	public Person(int id, String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat,
			String taxId, boolean irishCitizen, Gender gender) {

		this(name, occupation, ageCategory, empCat, taxId, irishCitizen, gender);

		this.id = id;

		count++;
	}
						//Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory(AgeCategory ageCategory) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmpCat() {
		return empCat;
	}

	public void setEmpCat(EmploymentCategory empCat) {
		this.empCat = empCat;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public boolean isIrishCitizen() {
		return irishCitizen;
	}

	public void setIrishCitizen(boolean irishCitizen) {
		this.irishCitizen = irishCitizen;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String toString() {
		return id + ": " + name;
	}

}
