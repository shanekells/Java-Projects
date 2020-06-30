package GUI;
import java.util.EventObject;
				//event class that stores the information from the event created with getters and setters for each field.
public class FormEvent extends EventObject{
	private String name;
	private String occupation;
	private int ageCategory;
	private String empCat;
	private String taxId;
	private boolean irishCitizen;
	private String gender;
	
	
	public FormEvent(Object source) {
		super(source);
	}
	
	public FormEvent(Object source, String name, String occupation, int ageCat, String empCat, String taxId, boolean irishCitizen, String gender) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCat;
		this.empCat = empCat;
		this.taxId = taxId;
		this.irishCitizen = irishCitizen;
		this.gender = gender;
	}
	
	
	public String getGender() {
		return gender;
	}

	public String getTaxId() {
		return taxId;
	}

	public boolean isIrishCitizen() {
		return irishCitizen;
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
	
	public int getAgeCategory() {
		return ageCategory;
	}
	
	public String getEmploymentCategory() {
		return empCat;
	}
}
