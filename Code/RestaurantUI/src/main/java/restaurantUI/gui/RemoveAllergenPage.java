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

public class RemoveAllergenPage extends JPanel {
	UI ui;
	final static String CARD = "RemoveAllergenPage";
	JComboBox<String> allergenName;
	
	public RemoveAllergenPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Allergen");
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Remove Allergen");
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
	    JPanel comboPanel = new JPanel(new BorderLayout());
	    JLabel comboText = new JLabel("Select a Allergen to remove:");
	    comboPanel.add(comboText, BorderLayout.NORTH);
		List<String> allergenNames = ui.om.getAllAllergenNames();
		allergenName = new JComboBox<String>();
		for(String name: allergenNames) {
			allergenName.addItem(name);
		}
		comboPanel.add(allergenName, BorderLayout.CENTER);
		contentsPanel.add(comboPanel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
        JButton removeButton = new JButton("Remove Allergen");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Deleting " + allergenName.getSelectedItem().toString());
        		ui.om.removeAllergen(allergenName.getSelectedItem().toString());
        		JOptionPane.showMessageDialog(null, "Deleted " + allergenName.getSelectedItem().toString() + " from the ontology.");
        		ui.updateAllergens();
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
		List<String> allergenNames = ui.om.getAllAllergenNames();
		
		allergenName.removeAllItems();
		for(String name: allergenNames) {
			allergenName.addItem(name);
		}
	}
}
