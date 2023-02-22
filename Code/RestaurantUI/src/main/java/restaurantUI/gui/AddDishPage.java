package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddDishPage extends JPanel {

	UI ui;
	final static String CARD = "AddDishPage";
	DefaultListModel<String> list;
	
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
	    list = new DefaultListModel<String>();
	    for(String comp: allComps) {
	    	list.addElement(comp);
	    }
	    
	    JList<String> components = new JList<String>(list);
	    
	    add(components);
	    
	    JCheckBox halal = new JCheckBox("Is this dish halal?");
	    JCheckBox kosher = new JCheckBox("Is this dish kosher?");
	    
	    add(halal);
	    add(kosher);
	    
	    
	    JButton addButton = new JButton("Add Dish");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String[] selected = new String[components.getSelectedValuesList().size()];
	        	selected = components.getSelectedValuesList().toArray(selected);
	        	
	            ui.om.addDish(dishName.getText(),
	            			  selected,
	            			  new String[0],
	            			  halal.isSelected(),
	            			  kosher.isSelected());
	            ui.updateDishes();
	            ui.SwitchToFrame(MainPage.CARD);
	        }
	    });
	    add(addButton, BorderLayout.SOUTH);

	}
	
	void reload() {
		list.removeAllElements();
		List<String> allComps = ui.om.getAllComponentNames();
	    for(String comp: allComps) {
	    	list.addElement(comp);
	    }
	}

	
}
