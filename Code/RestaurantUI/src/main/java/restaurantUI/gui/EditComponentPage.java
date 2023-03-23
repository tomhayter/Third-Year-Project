package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class EditComponentPage extends JPanel {
	
	UI ui;
	final static String CARD = "EditComponentPage";
	DefaultListModel<String> allList = new DefaultListModel<String>();
	
	DefaultListModel<String> list = new DefaultListModel<String>();
	
	public EditComponentPage(UI ui, String component) {

		this.ui = ui;
		
		setName("Edit Component");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Edit " + component + " Component");
	    title.setFont(new Font("Calibri", Font.BOLD, 24));
	    JPanel titlePanel = new JPanel();
	    titlePanel.add(title, BorderLayout.CENTER);
	    add(titlePanel, BorderLayout.NORTH);
	    
	    JPanel contentsPanel = new JPanel(new GridBagLayout());
	    contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
	    GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 0, 16, 0);
	    gbc.insets = i;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    JPanel editNamePanel = new JPanel(new BorderLayout());
	    JLabel editNameText = new JLabel("Edit Name:");
	    JTextField compName = new JTextField(component);
	    editNamePanel.add(editNameText, BorderLayout.NORTH);
	    editNamePanel.add(compName, BorderLayout.CENTER);
	    contentsPanel.add(editNamePanel, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    JPanel allIngredientsPanel = new JPanel(new BorderLayout());
	    JLabel allIngredientsText = new JLabel("Select ingredients in component:");
	    allIngredientsPanel.add(allIngredientsText, BorderLayout.NORTH);
	    List<String> allIngs = ui.om.getAllIngredientNames();
	    for(String ing:allIngs) {
	    	allList.addElement(ing);
	    }
	    JScrollPane allScroll = new JScrollPane();
	    JList<String> allIngredients = new JList<String>(allList);
	    allIngredients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    allIngredients.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent evt) {
	    		if (list.contains(allIngredients.getSelectedValue())) {
	    			return;
	    		}
	    		list.addElement(allIngredients.getSelectedValue());
	    	}
	    });
	    allScroll.setViewportView(allIngredients);
	    allIngredientsPanel.add(allScroll, BorderLayout.CENTER);
	    contentsPanel.add(allIngredientsPanel, gbc);
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    JPanel ingredientsPanel = new JPanel(new BorderLayout());
	    JLabel ingredientsText = new JLabel("Ingredients in your component (click to remove):");
	    ingredientsPanel.add(ingredientsText, BorderLayout.NORTH);
	    JScrollPane scroll = new JScrollPane();
	    List<String> ingsInComp = ui.om.getIngredientsInComponent(component);
	    for(String ing: ingsInComp) {
	    	list.addElement(ing);
	    }
	    JList<String> ingredients = new JList<String>(list);
	    ingredients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    ingredients.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent evt) {
	    		list.removeElement(ingredients.getSelectedValue());
	    	}
	    });
	    scroll.setViewportView(ingredients);
	    ingredientsPanel.add(scroll, BorderLayout.CENTER);
	    contentsPanel.add(ingredientsPanel, gbc);
	    
	    gbc.gridx = 1;
	    gbc.gridy = 3;
	    JButton addButton = new JButton("Save");
	    addButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	List<String> selected = new ArrayList<String>();
				for (Object i: list.toArray()) {
					selected.add((String) i);
				}
	        	
	            ui.om.updateComponent(component, compName.getText(), selected);
	            JOptionPane.showMessageDialog(null, "Updated " + compName.getText() + ".");
	            ui.updateComponents();
	            ui.SwitchToFrame(QueryComponentPage.CARD);
	        }
	    });
	    contentsPanel.add(addButton, gbc);
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 4;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent e) {
	        	ui.SwitchToFrame(QueryComponentPage.CARD);
	        }
	    });
	    contentsPanel.add(backButton, gbc);
	    add(contentsPanel, BorderLayout.CENTER);
	}
	
	void reload() {
		allList.removeAllElements();
		List<String> allIngs = ui.om.getAllIngredientNames();
	    for(String ing: allIngs) {
	    	allList.addElement(ing);
	    }
	    
	}
}
