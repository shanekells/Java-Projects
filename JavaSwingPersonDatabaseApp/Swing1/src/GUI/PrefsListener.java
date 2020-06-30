package GUI;
			//interface that allows main frame to intercept preferenceSet method
public interface PrefsListener {
	public void preferencesSet(String user, String passowrd, int port);
}
