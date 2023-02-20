package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import restaurantUI.IngredientType;

public class RemoveDishPage extends JPanel {

	UI ui;
	final static String CARD = "RemoveDishPage";
	JComboBox<String> dishName;
	
	public RemoveDishPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Dish");
		JLabel title = new JLabel("Select a dish to remove");
		add(title, BorderLayout.NORTH);
		
		List<String> dishNames = ui.om.getAllDishNames();
		
		dishName = new JComboBox<String>();
		for(String name: dishNames) {
			dishName.addItem(name);
		}
		add(dishName);
		

        JButton removeButton = new JButton("Remove Dish");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		ui.om.removeDish(dishName.getSelectedItem().toString());
                ui.updateDishes();
            	System.out.println("Deleting " + dishName.getSelectedItem().toString());
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        add(removeButton);
	}
	
	
	void reload() {
		List<String> dishNames = ui.om.getAllDishNames();
		
		dishName.removeAllItems();
		for(String name: dishNames) {
			dishName.addItem(name);
		}
	}
}
