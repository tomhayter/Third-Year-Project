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
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddDishPage extends JPanel {

	UI ui;
	final static String CARD = "AddDishPage";
	DefaultListModel<String> list;
	
	public AddDishPage(UI ui) {


		this.ui = ui;
		
		setName("Add Dish");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Add Dish");
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
	    contentsPanel.add(dishName, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    JPanel componentsPanel = new JPanel(new BorderLayout());
	    JLabel componentsText = new JLabel("Select components in dish:");
	    componentsPanel.add(componentsText, BorderLayout.NORTH);
	    List<String> allComps = ui.om.getAllComponentNames();	    
	    list = new DefaultListModel<String>();
	    for(String comp: allComps) {
	    	list.addElement(comp);
	    }
	    JList<String> components = new JList<String>(list);
	    components.setSelectionModel(new DefaultListSelectionModel() {
	        @Override
	        public void setSelectionInterval(int index0, int index1) {
	            if(super.isSelectedIndex(index0)) {
	                super.removeSelectionInterval(index0, index1);
	            }
	            else {
	                super.addSelectionInterval(index0, index1);
	            }
	        }
	    });
	    JScrollPane scroll = new JScrollPane();
	    scroll.setViewportView(components);
	    componentsPanel.add(scroll, BorderLayout.CENTER);
	    contentsPanel.add(componentsPanel, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    JCheckBox halal = new JCheckBox("Is this dish halal?");
	    contentsPanel.add(halal, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    JCheckBox kosher = new JCheckBox("Is this dish kosher?");
	    contentsPanel.add(kosher, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 4;
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
	            JOptionPane.showMessageDialog(null, "Added " + dishName.getText() + " to the ontology.");
	            ui.SwitchToFrame(MainPage.CARD);
	            dishName.setText("Enter Dish Name");
	            components.setSelectedIndices(new int[0]);
	            halal.setSelected(false);
	            kosher.setSelected(false);
	        }
	    });
	    contentsPanel.add(addButton, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 5;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(AddPage.CARD);
	            dishName.setText("Enter Dish Name");
	        }
	    });
	    contentsPanel.add(backButton, gbc);
	    
	    add(contentsPanel, BorderLayout.CENTER);
	}
	
	void reload() {
		list.removeAllElements();
		List<String> allComps = ui.om.getAllComponentNames();
	    for(String comp: allComps) {
	    	list.addElement(comp);
	    }
	}

	
}
