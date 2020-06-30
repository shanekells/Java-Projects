package GUI;		//necessary imports

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Controller;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Toolbar toolbar;
	private FormPanel formPanel;
	private JFileChooser fileChooser;
	private Controller controller;
	private TablePanel tablePanel;		//components
	private PrefsDialog prefsDialog;
	private Preferences prefs;
	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private MessagePanel messagePanel;

	public MainFrame() {
		super("New App");  //calling the super class constructor

		setLayout(new BorderLayout());	//set layout

		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();		//create components
		prefsDialog = new PrefsDialog(this);
		tabPane = new JTabbedPane();
		messagePanel = new MessagePanel(this);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);	//creating the structure

		splitPane.setOneTouchExpandable(true);

		tabPane.addTab("Person Database", tablePanel);	//adding to the tabPane
		tabPane.addTab("Messages", messagePanel);

		prefs = Preferences.userRoot().node("db");

		controller = new Controller();		//controller constructor

		tablePanel.setData(controller.getPeople());		//retrieving the information for the tablePanel

		tablePanel.setPersonTableListener(new PersonTableListener() {	//intercepting the rowDeleted method and calling removePerson 
			public void rowDeleted(int row) {							// to delete the perosn
				controller.removePerson(row);
			}
		});

		tabPane.addChangeListener(new ChangeListener() { // adding listener to the tab to get selected tab
			public void stateChanged(ChangeEvent e) {
				int tabIndex = tabPane.getSelectedIndex();
				if (tabIndex == 1) {
					messagePanel.refresh();
				}
			}
		});

		prefsDialog.setPrefsListener(new PrefsListener() {
			public void preferencesSet(String user, String password, int port) {
				prefs.put("user", user);
				prefs.put("password", password); // sets preferences
				prefs.putInt("port", port);

				try { // reconfigures the connection to the database
					controller.configure(port, user, password);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to re-connect to the database");
				}
			}

		});
				//assign login details to variables.
		String user = prefs.get("user", "");
		String password = prefs.get("password", "");
		Integer port = prefs.getInt("port", 3306);

		prefsDialog.setDefaults(user, password, port);

		try { // configures the database connection
			controller.configure(port, user, password);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Unable to connect to the database");
		}

		fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new PersonFileFilter());

		setJMenuBar(createMenuBar());

		toolbar.setToolbarListener(new ToolbarListener() {	//set the listener passing the anonymous class
			public void saveEventOccured() {	//intercepts the saveEventOccured method

				try {
					controller.connect();	//calls the connect method
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					controller.save();		//calls the save method
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.",
							"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
				}
			}

			public void refreshEventOccured() {		//intercepts the refreshEventOccured
				refresh();							//
			}
		});

		formPanel.setFormListener(new FormListener() {		//intercepting the formEventOccured method
			public void formEventOccurred(FormEvent e) {	//adding the persons details and refreshing the panel.
				controller.addPerson(e);
				tablePanel.refresh();
			}
		});

		addWindowListener(new WindowAdapter() {			//adding window listener to close down app correctly
			public void windowClosing(WindowEvent e) {	//by disconnecting from db, closing the window and 
				controller.disconnect();				//running the garbage collector
				dispose();
				System.gc();
			}
		});

		add(toolbar, BorderLayout.PAGE_START);	//add toolbar to frame
		add(splitPane, BorderLayout.CENTER);

		refresh();

		setSize(600, 500);		//setting size of mainframe
		setMinimumSize(new Dimension(600, 500));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);

	}

	private JMenuBar createMenuBar() {				//creates menu bar at the top of the app
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data...");
		JMenuItem importDataItem = new JMenuItem("Import Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.add(exitItem);

		JMenu windowMenu = new JMenu("Window");
		JMenu showMenu = new JMenu("Show");
		JMenuItem prefsItem = new JMenuItem("Preferences...");

		JMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
		showFormItem.setSelected(true);
		showMenu.add(showFormItem);
		windowMenu.add(showMenu);
		windowMenu.add(prefsItem);

		prefsItem.addActionListener(new ActionListener() {	//add action listener to listen for an event
			public void actionPerformed(ActionEvent e) {	//passing an anonymous class
				prefsDialog.setVisible(true);				//sets prefsDialog to visible
			}
		});

		showFormItem.addActionListener(new ActionListener() {	//add action listener to the show person option that  
																//hides or shows the add person form 
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
				if (menuItem.isSelected()) {
					splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
				}
				formPanel.setVisible(menuItem.isSelected());
			}

		});

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);		//sets key strokes for short keys
		exitItem.setMnemonic(KeyEvent.VK_X);

		prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {		//import data option that gives the ability to select a file from the pc
			public void actionPerformed(ActionEvent e) {			//and import the details
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.loadFromFile(fileChooser.getSelectedFile());
						tablePanel.refresh();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {		//export data option that gives the ability to export the data and save to
			public void actionPerformed(ActionEvent e) {			//a file on the computer system
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.saveToFile(fileChooser.getSelectedFile());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {	//exit code to close application
			public void actionPerformed(ActionEvent e) {

				int action = JOptionPane.showConfirmDialog(MainFrame.this,		//brings up a window to get the user to confirm if they want to
						"Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);	//exit

				if (action == JOptionPane.OK_OPTION) {
					WindowListener[] listeners = getWindowListeners();
					for (WindowListener listener : listeners) {
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}
				}
			}
		});
		return menuBar;
	}

	private void refresh() {		//refresh method that connects to the database and loads the information on the panel.
		try {
			controller.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Database Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}

		try {
			controller.load();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database.",
					"Database Connection Problem", JOptionPane.ERROR_MESSAGE);
		}
		tablePanel.refresh();
	}
}
