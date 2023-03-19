package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RemoveIngredientPage extends JPanel {
	
	UI ui;
	final static String CARD = "RemoveIngredientPage";
	JComboBox<String> ingName;
	
	public RemoveIngredientPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Ingredient");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Remove Ingredient");
	    title.setFont(new Font("Calibri", Font.BOLD, 24));
	    JPanel titlePanel = new JPanel();
	    titlePanel.add(title, BorderLayout.CENTER);
	    add(titlePanel, BorderLayout.NORTH);
	    
	    JPanel contentsPanel = new JPanel(new GridBagLayout());
	    contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
	    GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(16, 0, 16, 0);
	    gbc.insets = i;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
		List<String> ingTypesList = ui.om.getIngredientTypeNames();
		ingTypesList.add(0, "All");
		
        String[] ingTypesArray = new String[ingTypesList.size()];
        ingTypesArray = ingTypesList.toArray(ingTypesArray);
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesArray);
        ingTypes.setSelectedItem("All");
        contentsPanel.add(ingTypes, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        ingName = new JComboBox<String>();
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
        			ings = ui.om.getIngredientNamesOfType(selection);
        		}
        		ingName.removeAllItems();
        		for(String item: ings) {
        			ingName.addItem(item);
        		}
        	}
        });
        contentsPanel.add(ingName, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton removeButton = new JButton("Remove Ingredient");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Deleting " + ingName.getSelectedItem().toString());
        		ui.om.removeIngredient(ingName.getSelectedItem().toString());
        		JOptionPane.showMessageDialog(null, "Deleted " + ingName.getSelectedItem().toString() + " from the ontology.");
                ui.updateIngredients();
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        contentsPanel.add(removeButton, gbc);
        
        gbc.gridx = 1;
	    gbc.gridy = 3;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(RemovePage.CARD);
	        }
	    });
	    contentsPanel.add(backButton, gbc);
	    
	    add(contentsPanel, BorderLayout.CENTER);
	}

	
	protected void reload() {
        ingName.removeAllItems();
		
		List<String> allIngs = ui.om.getAllIngredientNames();
        for (String ing: allIngs) {
        	ingName.addItem(ing);
        }
	}
}
