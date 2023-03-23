package customerUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class DishPage extends JPanel {

	UI ui;
	
	public DishPage(UI ui, String dish) {
		this.ui = ui;
		setLayout(new BorderLayout());

        JLabel title = new JLabel(dish);
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel contentsPanel = new JPanel(new GridBagLayout());
		contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 0, 16, 16);
	    gbc.insets = i;
	    
		JPanel ings = new JPanel(new BorderLayout());
		ings.setBorder(new CompoundBorder(new TitledBorder("Ingredients"), new EmptyBorder(16, 16, 16, 16)));
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.BOTH;
		List<String> ingredientsInDish = ui.om.getIngredientsInDish(dish);
		DefaultListModel<String> ingsList = new DefaultListModel<String>();
		for(String s: ingredientsInDish) {
			ingsList.addElement(s);
		}
		JList<String> ingredientsList = new JList<String>(ingsList);
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(ingredientsList);
		ings.add(scroll, BorderLayout.CENTER);
		contentsPanel.add(ings, gbc);
		
		JPanel comps = new JPanel(new BorderLayout());
		comps.setBorder(new CompoundBorder(new TitledBorder("Components"), new EmptyBorder(16, 16, 16, 16)));
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.fill = GridBagConstraints.NONE;
		JButton back = new JButton("Back to Search");
		back.setFont(new Font("Calibri", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryPage.CARD);
            }
        });
		contentsPanel.add(back, gbc);
		
		
		JPanel properties = new JPanel(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel dietPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		dietPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Suitable for")));
		List<String> diets = ui.om.getSuitableDietsForDish(dish);
		List<JCheckBox> dietBoxes = new ArrayList<JCheckBox>();
		JCheckBox vege = new JCheckBox("Vegetarian");
		dietBoxes.add(vege);
		JCheckBox vegan = new JCheckBox("Vegan");
		dietBoxes.add(vegan);
		JCheckBox halal = new JCheckBox("Halal");
		dietBoxes.add(halal);
		JCheckBox kosher = new JCheckBox("Kosher");
		dietBoxes.add(kosher);
		
		for(JCheckBox box: dietBoxes) {
			box.setSelected(diets.contains(box.getText()));
			box.setFocusable(false);
			box.setRolloverEnabled(false);
			box.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	box.setSelected(diets.contains(box.getText()));
	            }
	        });
			dietPanel.add(box);
		}
		properties.add(dietPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		JCheckBox gf = new JCheckBox("Gluten Free Alternative Available");
		boolean hasGFOption = ui.om.dishHasGlutenFreeOption(dish);
		gf.setSelected(hasGFOption);
		gf.setFocusable(false);
		gf.setRolloverEnabled(false);
		gf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gf.setSelected(hasGFOption);
            }
        });
		properties.add(gf, gbc);
		
		if (ui.showCalories) {
			gbc.gridx = 0;
			gbc.gridy = 2;
			JPanel calPanel = new JPanel();
			calPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Calories")));
			JLabel calories = new JLabel(Integer.toString(ui.om.getCaloriesInDish(dish)));

			calPanel.add(calories);		
			properties.add(calPanel, gbc);
		}
		
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		JPanel allergenPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		allergenPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Contains the following")));
		List<JCheckBox> allergenBoxes = new ArrayList<JCheckBox>();
		List<String> allAllergens = ui.om.getAllAllergenNames();
		List<String> allergens = ui.om.getAllergensInDish(dish);
		for (String allergen: allAllergens) {
			JCheckBox box = new JCheckBox(allergen);
			allergenBoxes.add(box);
		}
		
		for (JCheckBox box: allergenBoxes) {
			box.setSelected(allergens.contains(box.getText()));
			box.setFocusable(false);
			box.setRolloverEnabled(false);
			box.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	box.setSelected(allergens.contains(box.getText()));
	            }
	        });
			allergenPanel.add(box);
		}
		properties.add(allergenPanel, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		contentsPanel.add(properties, gbc);
		
		JButton extra = new JButton("Add/Remove Ingredients");
		extra.setFont(new Font("Calibri", Font.PLAIN, 16));
		extra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ExtraPage extraPage = new ExtraPage(ui, dish);
            	ui.SwitchToFrame(extraPage, "Extra" + dish);
            }
        });
		gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.fill = GridBagConstraints.NONE;
	    contentsPanel.add(extra, gbc);
	    
		add(contentsPanel);
	}
}
