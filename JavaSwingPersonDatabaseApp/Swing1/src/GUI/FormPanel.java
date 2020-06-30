package GUI;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
			//FormPanel class that contains a form with person detail fields.
public class FormPanel extends JPanel{
	
	private JLabel nameLabel;
	private JLabel occupationLabel;
	private JTextField nameField;
	private JTextField occupationField;
	private JButton okBtn;
	private FormListener formListener;
	private JList ageList;
	private JComboBox empCombo;		//relevant components
	private JCheckBox citizenCheck;
	private JTextField taxField;
	private JLabel taxLabel;
	private JRadioButton maleRadio;
	private JRadioButton femaleRadio;
	private ButtonGroup genderGroup;
	
	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);
		
		nameLabel = new JLabel("Name: ");
		occupationLabel = new JLabel("Occupation: ");
		nameField = new JTextField(10);
		occupationField = new JTextField(10);
		ageList = new JList();
		empCombo = new JComboBox();
		citizenCheck = new JCheckBox();
		taxField = new JTextField(10);
		taxLabel = new JLabel("Tax ID: ");
		okBtn = new JButton("OK");
		
		//Set up Mnemonics, ALT+O for ok button
		okBtn.setMnemonic(KeyEvent.VK_O);
		
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);	//ALT+N to get focus on name field
		nameLabel.setLabelFor(nameField);
		
		maleRadio = new JRadioButton("male");
		femaleRadio = new JRadioButton("female");
		maleRadio.setActionCommand("male");			//create radio buttons
		femaleRadio.setActionCommand("female");
		genderGroup = new ButtonGroup();
		
		maleRadio.setSelected(true);		//have male radio button selected
		
		//Set up gender radios
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
				
		
		//set up tax ID
		taxLabel.setEnabled(false);
		taxField.setEnabled(false);
		
		citizenCheck.addActionListener(new ActionListener() {		//add action listener to citizen check box
			public void actionPerformed(ActionEvent e) {
				boolean isTicked = citizenCheck.isSelected();
				taxLabel.setEnabled(isTicked);			//if checkbox is selected then set tax field to enabled.
				taxField.setEnabled(isTicked);
			}
		});
		
		//Set up list box for age groups
		DefaultListModel ageModel = new DefaultListModel();
		ageModel.addElement(new AgeCategory(0,"Under 18"));		//use the ageCategory utility class to add the element
		ageModel.addElement(new AgeCategory(1,"18 to 65"));
		ageModel.addElement(new AgeCategory(2,"65 or over"));
		ageList.setModel(ageModel);
		
		//setting size of the ageList component
		ageList.setPreferredSize(new Dimension(110, 66));
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setSelectedIndex(1);
		
		//Set up combo box
		DefaultComboBoxModel empModel = new DefaultComboBoxModel();
		empModel.addElement("employed");
		empModel.addElement("self-employed");
		empModel.addElement("unemployed");
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);
		empCombo.setEditable(true);
		
		
		okBtn.addActionListener(new ActionListener() {	//add action listener to listen to button click
			public void actionPerformed(ActionEvent e) {  //take the details from the form.
				String name = nameField.getText();
				String occupation = occupationField.getText();
				AgeCategory ageCat = (AgeCategory)ageList.getSelectedValue();
				String empCat = (String)empCombo.getSelectedItem();
				String taxId = taxField.getText();
				boolean irishCitizen = citizenCheck.isSelected();
				
				String gender = genderGroup.getSelection().getActionCommand();
							//create FormEvent object 
				FormEvent ev = new FormEvent(this, name, occupation, ageCat.getId(), empCat, taxId, irishCitizen, gender);
				
				if(formListener != null) {
					formListener.formEventOccurred(ev);	//tells mainframe that an event has occurred.
				}
			}
		});
		
		//setting the border of the add person form
		Border innerBorder = BorderFactory.createTitledBorder("Add Person");
		Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		layoutComponents();
	}
	
	public void layoutComponents() {		//method for layout
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridy = 0;
		
		//first row//
		
		gc.weightx = 1;
		gc.weighty = 0.1;
				
		gc.gridx = 0;
		
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(nameLabel, gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(nameField, gc);
		
		//next row//
		
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(occupationLabel, gc);

		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(occupationField, gc);
		

		//next row//

		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Age: "), gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(ageList, gc);

		//next row//

		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Employment: "), gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(empCombo, gc);

		//next row//

		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Irish Citizen: "), gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(citizenCheck, gc);

		//next row//

		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(taxLabel, gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(taxField, gc);

		//next row//

		gc.weightx = 1;
		gc.weighty = 0.05;
		
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Gender: "), gc);
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(maleRadio, gc);
		
		//next row//

		gc.weightx = 1;
		gc.weighty = 0.2;
		
		gc.gridy++;
		
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(femaleRadio, gc);
		
		//next row//

		gc.weightx = 1;
		gc.weighty = 2.0;
		
		gc.gridy++;
		gc.gridx = 1;
		gc.insets = new Insets(0,0,0,0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(okBtn, gc);
	}
			//sets the listener 
	public void setFormListener(FormListener listener) {
		this.formListener = listener;
	}
}

class AgeCategory{		//age category utility class for matching up ids and strings.
	private int id;
	private String text;
	
	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public String toString() {
		return text;
	}
	
	public int getId() {
		return id;
	}
}
