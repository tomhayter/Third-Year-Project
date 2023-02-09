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

public class AddDishPage extends JPanel {

	UI ui;
	final static String CARD = "AddDishPage";
	
	public AddDishPage(UI ui) {


		this.ui = ui;
		
		setName("Add Dish");

	    JTextField dishName = new JTextField("Enter Dish Name");
	    dishName.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            dishName.setText(null);
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (dishName.getText().equals(null)) {
	                dishName.setText("Enter Dish Name");
	            }
	        }
	    });
	    add(dishName);
	    
	    
	    
	    
	    List<String> allComps = ui.om.getAllComponentNames();
	    String[] compsList = new String[allComps.size()];
	    compsList = allComps.toArray(compsList);
	    
	    JList<String> ingredients = new JList<String>(compsList);
	    
	    add(ingredients);
	    
	    
	    JButton addButton = new JButton("Add Dish");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String[] selected = new String[ingredients.getSelectedValuesList().size()];
	        	selected = ingredients.getSelectedValuesList().toArray(selected);
	        	
	            ui.om.addDish(dishName.getText(),selected, new String[0]);
	            ui.newDish();
	            ui.SwitchToFrame(MainPage.CARD);
	        }
	    });
	    add(addButton, BorderLayout.SOUTH);

	}

	
}
