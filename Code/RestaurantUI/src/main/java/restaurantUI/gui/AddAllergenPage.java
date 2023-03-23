package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddAllergenPage extends JPanel {

	UI ui;
	final static String CARD = "AddAllergenPage";
	
	public AddAllergenPage(UI ui) {


		this.ui = ui;
		
		setName("Add Allergen");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Add Allergen");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        JPanel contentsPanel = new JPanel(new GridLayout(7, 3, 0, 32));
        contentsPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        
        contentsPanel.add(new JLabel(""));
	    JTextField allergenName = new JTextField("Enter Allergen Name");
	    allergenName.addFocusListener(new FocusListener() {
	        @Override
	        public void focusGained(FocusEvent e) {
	            allergenName.setText(null);
	        }

	        @Override
	        public void focusLost(FocusEvent e) {
	            if (allergenName.getText().equals(null)) {
	                allergenName.setText("Enter Allergen Name");
	            }
	        }
	    });
	    contentsPanel.add(allergenName);
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    JButton addButton = new JButton("Add Allergen");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	if (allergenName.getText().replace("Enter Allergen Name", "").length() == 0) {
					JOptionPane.showMessageDialog(null, "You must name your allergen!", "Error Adding Allergen", JOptionPane.WARNING_MESSAGE);
	        		return;
				}
	            ui.om.addAllergen(allergenName.getText());
	            JOptionPane.showMessageDialog(null, "Added " + allergenName.getText() + " to the ontology.");
	            ui.updateAllergens();
	            ui.SwitchToFrame(MainPage.CARD);
	            allergenName.setText("Enter Allergen Name");
	        }
	    });
	    contentsPanel.add(addButton);
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(AddPage.CARD);
	            allergenName.setText("Enter Allergen Name");
	        }
	    });
	    contentsPanel.add(backButton);
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    contentsPanel.add(new JLabel(""));
	    
	    add(contentsPanel, BorderLayout.CENTER);
	}

	
}
