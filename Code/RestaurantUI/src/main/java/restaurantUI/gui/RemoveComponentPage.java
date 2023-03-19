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

public class RemoveComponentPage extends JPanel {

	UI ui;
	final static String CARD = "RemoveComponentPage";
	JComboBox<String> compName;
	
	public RemoveComponentPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Component");
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Remove Component");
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
		List<String> compNames = ui.om.getAllComponentNames();
		compName = new JComboBox<String>();
		for(String name: compNames) {
			compName.addItem(name);
		}
		contentsPanel.add(compName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
        JButton removeButton = new JButton("Remove Component");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Deleting " + compName.getSelectedItem().toString());
        		ui.om.removeComponent(compName.getSelectedItem().toString());
        		JOptionPane.showMessageDialog(null, "Deleted " + compName.getSelectedItem().toString() + " from the ontology.");
        		ui.updateComponents();
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
		List<String> compNames = ui.om.getAllComponentNames();
		
		compName.removeAllItems();
		for(String name: compNames) {
			compName.addItem(name);
		}
	}
}
