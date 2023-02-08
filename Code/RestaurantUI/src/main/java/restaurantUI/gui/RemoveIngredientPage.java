package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import restaurantUI.IngredientType;

public class RemoveIngredientPage extends JPanel {
	
	UI ui;
	
	public RemoveIngredientPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Ingredient");
		JLabel title = new JLabel("Select an ingredient to remove");
		add(title, BorderLayout.NORTH);
		
		List<String> ingTypesList = ui.om.getIngredientTypeNames();
		ingTypesList.add("All");
		
        String[] ingTypesArray = new String[ingTypesList.size()];
        ingTypesArray = ingTypesList.toArray(ingTypesArray);
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesArray);
        ingTypes.setSelectedItem("All");
        add(ingTypes);
        
        JComboBox<String>ingName = new JComboBox<String>();
        
        List<String> allIngs = ui.om.getAllIngredientNames();
        for (String ing: allIngs) {
        	ingName.addItem(ing);
        }
        
        ingTypes.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selection = ingTypes.getSelectedItem().toString();
        		List<String> ings = new ArrayList<String>();
        		if (selection.equals("All")) {
        			ings = ui.om.getAllIngredientNames();
        		}
        		else {
        			ings = ui.om.getIngredientNamesOfType(IngredientType.valueOf(selection));
        		}
        		ingName.removeAllItems();
        		for(String item: ings) {
        			ingName.addItem(item);
        		}
        	}
        });
        add(ingName, BorderLayout.CENTER);
        
        JButton removeButton = new JButton("Remove Ingredient");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (ingTypes.getSelectedItem().toString().equals("All")) {
            		
            	} else {
            		ui.om.removeIngredient(ingName.getSelectedItem().toString());
            	}
                
            	System.out.println("Deleting " + ingName.getSelectedItem().toString());
                ui.SwitchToFrame(UI.MAINPAGE);
            }
        });
        add(removeButton, BorderLayout.SOUTH);
	}

}
