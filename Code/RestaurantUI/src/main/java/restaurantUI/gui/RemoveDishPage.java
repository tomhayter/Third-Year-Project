package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RemoveDishPage extends JPanel {

	UI ui;
	final static String CARD = "RemoveDishPage";
	JComboBox<String> dishName;
	
	public RemoveDishPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Dish");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Remove Dish");
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
		List<String> dishNames = ui.om.getAllDishNames();
		dishName = new JComboBox<String>();
		for(String name: dishNames) {
			dishName.addItem(name);
		}
		contentsPanel.add(dishName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
        JButton removeButton = new JButton("Remove Dish");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Deleting " + dishName.getSelectedItem().toString());
        		ui.om.removeDish(dishName.getSelectedItem().toString());
                ui.updateDishes();
                JOptionPane.showMessageDialog(null, "Deleted " + dishName.getSelectedItem().toString() + " from the ontology.");
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        contentsPanel.add(removeButton, gbc);
        
        gbc.gridx = 1;
	    gbc.gridy = 2;
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
	
	
	void reload() {
		List<String> dishNames = ui.om.getAllDishNames();
		
		dishName.removeAllItems();
		for(String name: dishNames) {
			dishName.addItem(name);
		}
	}
}
