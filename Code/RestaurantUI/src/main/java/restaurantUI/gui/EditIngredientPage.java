package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
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

public class EditIngredientPage extends JPanel {
	
	UI ui;
	public final static String CARD = "EditIngredientPage";
	DefaultListModel<String> list;
	
	public EditIngredientPage(UI ui, String ingredient) {
		this.ui = ui;
		
		setName("Edit Ingredient");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Edit " + ingredient + " Ingredient");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        JPanel contentsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Insets i = new Insets(0, 0, 16, 0);
        gbc.insets = i;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        
        contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
        JPanel editNamePanel = new JPanel(new BorderLayout());
        JLabel editNameText = new JLabel("Edit Name:");
        JTextField ingName = new JTextField(ingredient);
        editNamePanel.add(editNameText, BorderLayout.NORTH);
        editNamePanel.add(ingName, BorderLayout.CENTER);
	    contentsPanel.add(editNamePanel, gbc);

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
	    allergens.setSelectionModel(new DefaultListSelectionModel() {
	        @Override
	        public void setSelectionInterval(int index0, int index1) {
	            if(super.isSelectedIndex(index0)) {
	                super.removeSelectionInterval(index0, index1);
	            }
	            else {
	                super.addSelectionInterval(index0, index1);
	            }
	        }
	    });
	    List<String> allergensInIngredient = ui.om.getAllergensInIngredient(ingredient);
	    for (String allergen: allergensInIngredient) {
	    	allergens.setSelectedIndex(list.indexOf(allergen));
	    }
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
	    calories.setValue(ui.om.getCaloriesInIngredients(Collections.singletonList(ingredient)));
	    caloriePanel.add(calories);
	    contentsPanel.add(caloriePanel, gbc);

	    gbc.gridx = 1;
        gbc.gridy = 4;
	    JButton addButton = new JButton("Save");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String[] selected = new String[allergens.getSelectedValuesList().size()];
	        	selected = allergens.getSelectedValuesList().toArray(selected);
	        	
	            ui.om.updateIngredient(ingredient, ingName.getText(), selected, (int) calories.getValue());
	            ui.updateIngredients();
	            JOptionPane.showMessageDialog(null, "Updated " + ingName.getText() + ".");
	            ui.SwitchToFrame(QueryIngredientPage.CARD);
	        }
	    });
	    contentsPanel.add(addButton, gbc);

	    gbc.gridx = 1;
        gbc.gridy = 5;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(QueryIngredientPage.CARD);
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
