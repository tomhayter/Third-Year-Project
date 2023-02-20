package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddAllergenPage extends JPanel {

	UI ui;
	final static String CARD = "AddAllergenPage";
	
	public AddAllergenPage(UI ui) {


		this.ui = ui;
		
		setName("Add Allergen");

	    JTextField allergenName = new JTextField("Enter allergen Name");
	    allergenName.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            allergenName.setText(null);
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (allergenName.getText().equals(null)) {
	                allergenName.setText("Enter Allergen Name");
	            }
	        }
	    });
	    add(allergenName);
	    
	    
	    JButton addButton = new JButton("Add Allergen");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.om.addAllergen(allergenName.getText());
	            ui.updateAllergens();
	            ui.SwitchToFrame(MainPage.CARD);
	            allergenName.setText("Enter Allergen Name");
	        }
	    });
	    add(addButton, BorderLayout.SOUTH);

	}

	
}
