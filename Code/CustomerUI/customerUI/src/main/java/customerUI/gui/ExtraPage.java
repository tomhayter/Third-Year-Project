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

public class ExtraPage extends JPanel {

	UI ui;
	
	JLabel calories;
	
	public ExtraPage(UI ui, String dish) {
		this.ui = ui;
		setLayout(new BorderLayout());

        JLabel title = new JLabel(dish + " - Extras");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel contentsPanel = new JPanel(new GridBagLayout());
		contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
		GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 0, 16, 16);
	    gbc.insets = i;
	    
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
		
		if (ui.showCalories) {
			gbc.gridx = 0;
			gbc.gridy = 1;
			JPanel calPanel = new JPanel();
			calPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Calories")));
			calories = new JLabel(Integer.toString(ui.om.getCaloriesInDish(dish)));

			calPanel.add(calories);		
			properties.add(calPanel, gbc);
		}
		
		
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		contentsPanel.add(properties);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
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
		contentsPanel.add(allergenPanel, gbc);
	    
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		gbc.fill = GridBagConstraints.NONE;
		JButton back = new JButton("Back to Dish");
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(dish);
            }
        });
		contentsPanel.add(back, gbc);
		
		
		JPanel ings = new JPanel(new BorderLayout());
		ings.setBorder(new CompoundBorder(new TitledBorder("Remove"), new EmptyBorder(16, 16, 16, 16)));
		
		gbc.gridx = 1;
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
		
		JPanel addPanel = new JPanel(new BorderLayout());
		addPanel.setBorder(new CompoundBorder(new TitledBorder("Add"), new EmptyBorder(16, 16, 16, 16)));
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		List<String> allIngs = ui.om.getAllIngredientNames();
		DefaultListModel<String> allIngList = new DefaultListModel<String>();
		for(String s: allIngs) {
			allIngList.addElement(s);
		}
		JList<String> allIngredientsList = new JList<String>(allIngList);
		JScrollPane addScroll = new JScrollPane();
		addScroll.setViewportView(allIngredientsList);
		addPanel.add(addScroll, BorderLayout.CENTER);
		contentsPanel.add(addPanel, gbc);
		
		
		
		gbc.gridx = 1;
	    gbc.gridy = 2;
	    JButton calculate = new JButton("Calculate");
		calculate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<String> modIngs = new ArrayList<String>();
					modIngs.addAll(ingredientsInDish);
					modIngs.removeAll(ingredientsList.getSelectedValuesList());
					modIngs.addAll(allIngredientsList.getSelectedValuesList());
					
					calories.setText(Integer.toString(ui.om.getCaloriesInIngredients(modIngs)));
					
					List<String> newAllergens = ui.om.getAllergensForEditedDish(modIngs);
					for (JCheckBox allergen: allergenBoxes) {
						allergen.setSelected(newAllergens.contains(allergen.getText()));
					}
				}
		});
	    gbc.fill = GridBagConstraints.NONE;
	    contentsPanel.add(calculate, gbc);
	    
		add(contentsPanel);
	}
	
	
}