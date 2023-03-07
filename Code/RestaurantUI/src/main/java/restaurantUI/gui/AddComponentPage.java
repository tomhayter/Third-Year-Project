package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddComponentPage extends JPanel {
	
	UI ui;
	final static String CARD = "AddComponentPage";
	DefaultListModel<String> list = new DefaultListModel<String>();
	
	public AddComponentPage(UI ui) {

	this.ui = ui;
	
	setName("Add Component");
	setLayout(new BorderLayout());
	
	JLabel title = new JLabel("Add Component");
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
    contentsPanel.add(compName, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 1;
    JPanel ingredientsPanel = new JPanel(new BorderLayout());
    JLabel ingredientsText = new JLabel("Select ingredients in component:");
    ingredientsPanel.add(ingredientsText, BorderLayout.NORTH);
    List<String> allIngs = ui.om.getAllIngredientNames();
    for(String ing:allIngs) {
    	list.addElement(ing);
    }
    JScrollPane scroll = new JScrollPane();
    
    JList<String> ingredients = new JList<String>(list);
    scroll.setViewportView(ingredients);
    ingredientsPanel.add(scroll, BorderLayout.CENTER);
    contentsPanel.add(ingredientsPanel, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 2;
    JButton addButton = new JButton("Add Component");
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	String[] selected = new String[ingredients.getSelectedValuesList().size()];
        	selected = ingredients.getSelectedValuesList().toArray(selected);
        	
            ui.om.addComponent(compName.getText(),selected);
            JOptionPane.showMessageDialog(null, "Added " + compName.getText() + " to the ontology.");
            ui.updateComponents();
            ui.SwitchToFrame(MainPage.CARD);
            compName.setText("Enter Component Name");
        }
    });
    contentsPanel.add(addButton, gbc);
    
    
    gbc.gridx = 1;
    gbc.gridy = 3;
    JButton backButton = new JButton("Back");
    backButton.addActionListener(new ActionListener() {
    	@Override
        public void actionPerformed(ActionEvent e) {
        	ui.SwitchToFrame(AddPage.CARD);
        	compName.setText("Enter Component Name");
        }
    });
    contentsPanel.add(backButton, gbc);
    add(contentsPanel, BorderLayout.CENTER);
	}
	
	void reload() {
		list.removeAllElements();
		List<String> allIngs = ui.om.getAllIngredientNames();
	    for(String ing: allIngs) {
	    	list.addElement(ing);
	    }
	}
}
