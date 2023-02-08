package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddIngredientPage extends JPanel {
	
	UI ui;
	
	public AddIngredientPage(UI ui) {
		this.ui = ui;
		
		setName("Add Ingredient");
		List<String> ingTypesList = ui.om.getIngredientTypeNames();		
        String[] ingTypesArray = new String[ingTypesList.size()];
        ingTypesArray = ingTypesList.toArray(ingTypesArray);
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesArray);
        add(ingTypes);
        
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
	    add(ingName, BorderLayout.CENTER);
	    JButton addButton = new JButton("Add Ingredient");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.om.addIngredient(ingName.getText(),ingTypes.getSelectedItem().toString());
	            ui.SwitchToFrame(UI.MAINPAGE);
	        }
	    });
	    add(addButton, BorderLayout.SOUTH);
	}
}
