package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class AddIngredientPage extends JPanel {
	
	UI ui;
	public final static String CARD = "AddIngredientPage";
	DefaultListModel<String> list;
	
	public AddIngredientPage(UI ui) {
		this.ui = ui;
		
		setName("Add Ingredient");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Add Ingredient");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        JPanel contentsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets i = new Insets(8, 0, 8, 0);
        gbc.insets = i;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        
        contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        JTextField ingName = new JTextField("Enter Ingredient Name");
	    ingName.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            ingName.setText(null);
	        }
	
	        @Override
	        public void focusLost(FocusEvent e) {
	            if (ingName.getText().equals(null)) {
	                ingName.setText("Enter Ingredient Name");
	            }
	        }
	    });
	    contentsPanel.add(ingName, gbc);

	    gbc.gridx = 1;
        gbc.gridy = 1;
	    JPanel typePanel = new JPanel(new BorderLayout());
	    JLabel typeText = new JLabel("Select Ingredient Type");
	    typePanel.add(typeText, BorderLayout.NORTH);
		List<String> ingTypesList = ui.om.getIngredientTypeNames();		
        String[] ingTypesArray = new String[ingTypesList.size()];
        ingTypesArray = ingTypesList.toArray(ingTypesArray);
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesArray);
        typePanel.add(ingTypes, BorderLayout.CENTER);
        contentsPanel.add(typePanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JPanel allergenPanel = new JPanel(new BorderLayout());
        JLabel allergenText = new JLabel("Select allergens the ingredient contains:");
        allergenPanel.add(allergenText, BorderLayout.NORTH);
	    List<String> allAllergens = ui.om.getAllAllergenNames();
	    list = new DefaultListModel<String>();
	    for(String allergen: allAllergens) {
	    	list.addElement(allergen);
	    }
	    JScrollPane scroll = new JScrollPane();
	    JList<String> allergens = new JList<String>(list);
	    scroll.setViewportView(allergens);
	    allergenPanel.add(scroll, BorderLayout.CENTER);
	    contentsPanel.add(allergenPanel, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    JPanel caloriePanel = new JPanel(new BorderLayout());
	    JLabel calorieText = new JLabel("Calories:");
	    caloriePanel.add(calorieText, BorderLayout.NORTH);
	    SpinnerModel model = new SpinnerNumberModel(0, 0, 1000, 1);
	    JSpinner calories = new JSpinner(model);
	    caloriePanel.add(calories);
	    contentsPanel.add(caloriePanel, gbc);

	    gbc.gridx = 1;
        gbc.gridy = 4;
	    JButton addButton = new JButton("Add Ingredient");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String[] selected = new String[allergens.getSelectedValuesList().size()];
	        	selected = allergens.getSelectedValuesList().toArray(selected);
	        	
	            ui.om.addIngredient(ingName.getText(),ingTypes.getSelectedItem().toString(), selected, (int) calories.getValue());
	            ui.updateIngredients();
	            JOptionPane.showMessageDialog(null, "Added " + ingName.getText() + " to the ontology.");
	            ui.SwitchToFrame(MainPage.CARD);
	            ingName.setText("Enter Ingredient Name");
	        }
	    });
	    contentsPanel.add(addButton, gbc);

	    gbc.gridx = 1;
        gbc.gridy = 5;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(AddPage.CARD);
	            ingName.setText("Enter Ingredient Name");
	        }
	    });
	    contentsPanel.add(backButton, gbc);
	    
	    add(contentsPanel, BorderLayout.CENTER);
	}
	
	void reload() {
		list.removeAllElements();
		List<String> allAllergens = ui.om.getAllAllergenNames();
	    for(String allergen: allAllergens) {
	    	list.addElement(allergen);
	    }
	}
}
