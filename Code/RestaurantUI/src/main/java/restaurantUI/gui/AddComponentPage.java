package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddComponentPage extends JPanel {
	
	UI ui;
	final static String CARD = "AddComponentPage";
	DefaultListModel<String> list = new DefaultListModel<String>();
	
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
    
    for(String ing:allIngs) {
    	list.addElement(ing);
    }
    
    JList<String> ingredients = new JList<String>(list);
    
    add(ingredients);
    
    
    JButton addButton = new JButton("Add Component");
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String[] selected = new String[ingredients.getSelectedValuesList().size()];
        	selected = ingredients.getSelectedValuesList().toArray(selected);
        	
            ui.om.addComponent(compName.getText(),selected);
            ui.updateComponents();
            ui.SwitchToFrame(MainPage.CARD);
        }
    });
    add(addButton, BorderLayout.SOUTH);

	}
	
	void reload() {
		list.removeAllElements();
		List<String> allIngs = ui.om.getAllIngredientNames();
	    for(String ing: allIngs) {
	    	list.addElement(ing);
	    }
	}
}
