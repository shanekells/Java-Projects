package GUI;
				//interface for the person table to allow
				//main frame to intercept method rowDeleted
public interface PersonTableListener {
	public void rowDeleted(int row);
	
}
