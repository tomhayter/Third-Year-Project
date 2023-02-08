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

public class AddComponentPage extends JPanel {
	
	UI ui;
	
	public AddComponentPage(UI ui) {

	this.ui = ui;
	
	setName("Add Component");

    JTextField compName = new JTextField("Enter Component Name");
    compName.addFocusListener(new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            compName.setText(null);
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (compName.getText().equals(null)) {
                compName.setText("Enter Component Name");
            }
        }
    });
    add(compName);
    
    
    
    
    List<String> allIngs = ui.om.getAllIngredientNames();
    String[] ingredientsList = new String[allIngs.size()];
    ingredientsList = allIngs.toArray(ingredientsList);
    
    JList<String> ingredients = new JList<String>(ingredientsList);
    
    add(ingredients);
    
    
    JButton addButton = new JButton("Add Component");
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String[] selected = new String[ingredients.getSelectedValuesList().size()];
        	selected = ingredients.getSelectedValuesList().toArray(selected);
        	
            ui.om.addComponent(compName.getText(),selected);
            ui.SwitchToFrame(UI.MAINPAGE);
        }
    });
    add(addButton, BorderLayout.SOUTH);

	}
}
