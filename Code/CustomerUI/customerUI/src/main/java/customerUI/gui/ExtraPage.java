package customerUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ExtraPage extends JPanel {

	UI ui;
	
	JLabel calories;
	List<JCheckBox> allergenBoxes;
	DefaultListModel<String> modList  = new DefaultListModel<String>();
	List<String> originalIngredients;
	
	public ExtraPage(UI ui, String dish) {
		this.ui = ui;
		originalIngredients = ui.om.getIngredientsInDish(dish);
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
		
	    
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.fill = GridBagConstraints.BOTH;
		JPanel ings = new JPanel(new BorderLayout());
		ings.setBorder(new CompoundBorder(new TitledBorder("Your Dish (click to remove)"), new EmptyBorder(16, 16, 16, 16)));
		for(String s: originalIngredients) {
			modList.addElement(s);
		}
		JList<String> ingredientsList = new JList<String>(modList);
		ingredientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ingredientsList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
	            modList.remove(ingredientsList.getSelectedIndex());
		    }
		});
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(ingredientsList);
		ings.add(scroll, BorderLayout.CENTER);
		contentsPanel.add(ings, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		JPanel addPanel = new JPanel(new BorderLayout());
		addPanel.setBorder(new CompoundBorder(new TitledBorder("Add Extra Ingredients"), new EmptyBorder(16, 16, 16, 16)));
		List<String> allIngs = ui.om.getAllIngredientNames();
		DefaultListModel<String> allIngList = new DefaultListModel<String>();
		for(String s: allIngs) {
			allIngList.addElement(s);
		}
		JList<String> allIngredientsList = new JList<String>(allIngList);
		allIngredientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		allIngredientsList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
	            modList.addElement(allIngredientsList.getSelectedValue());
		    }
		});
		JScrollPane addScroll = new JScrollPane();
		addScroll.setViewportView(allIngredientsList);
		addPanel.add(addScroll, BorderLayout.CENTER);
		contentsPanel.add(addPanel, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		JButton back = new JButton("Back to Dish");
		back.setFont(new Font("Calibri", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(dish);
            }
        });
		contentsPanel.add(back, gbc);
		
		// RHS
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		JPanel properties = new JPanel(new GridBagLayout());
//		JPanel dietPanel = new JPanel(new GridLayout(0, 2, 3, 3));
//		dietPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Suitable for")));
//		List<String> diets = ui.om.getSuitableDietsForDish(dish);
//		List<JCheckBox> dietBoxes = new ArrayList<JCheckBox>();
//		JCheckBox vege = new JCheckBox("Vegetarian");
//		dietBoxes.add(vege);
//		JCheckBox vegan = new JCheckBox("Vegan");
//		dietBoxes.add(vegan);
//		JCheckBox halal = new JCheckBox("Halal");
//		dietBoxes.add(halal);
//		JCheckBox kosher = new JCheckBox("Kosher");
//		dietBoxes.add(kosher);
//		
//		for(JCheckBox box: dietBoxes) {
//			box.setSelected(diets.contains(box.getText()));
//			box.setFocusable(false);
//			box.setRolloverEnabled(false);
//			box.addActionListener(new ActionListener() {
//	            @Override
//	            public void actionPerformed(ActionEvent e) {
//	            	box.setSelected(diets.contains(box.getText()));
//	            }
//	        });
//			dietPanel.add(box);
//		}
//		properties.add(dietPanel, gbc);
		
		JButton calculate = new JButton("Calculate");
		calculate.setFont(new Font("Calibri", Font.PLAIN, 16));
		calculate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List<String> modIngs = new ArrayList<String>();
					for (Object i: modList.toArray()) {
						modIngs.add((String) i);
					}
					
					calories.setText(Integer.toString(ui.om.getCaloriesInIngredients(modIngs)));
					
					List<String> newAllergens = ui.om.getAllergensForEditedDish(modIngs);
					for (JCheckBox allergen: allergenBoxes) {
						allergen.setSelected(newAllergens.contains(allergen.getText()));
					}
				}
		});
	    gbc.fill = GridBagConstraints.NONE;
	    properties.add(calculate, gbc);
		
		if (ui.showCalories) {
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.fill = GridBagConstraints.BOTH;
			JPanel calPanel = new JPanel();
			calPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Calories")));
			calories = new JLabel(Integer.toString(ui.om.getCaloriesInDish(dish)));

			calPanel.add(calories);		
			properties.add(calPanel, gbc);
		}
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    contentsPanel.add(properties, gbc);
	    
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		
		
		JPanel allergenPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		allergenPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Contains the following")));
		allergenBoxes = new ArrayList<JCheckBox>();
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
		
		gbc.gridx = 1;
	    gbc.gridy = 2;
	    gbc.fill = GridBagConstraints.NONE;
	    JButton reset = new JButton("Reset Dish");
	    reset.setFont(new Font("Calibri", Font.PLAIN, 16));
	    reset.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		modList.removeAllElements();
	    		for(String s: originalIngredients) {
	    			modList.addElement(s);
	    		}
	    		
	    		calories.setText(Integer.toString(ui.om.getCaloriesInIngredients(originalIngredients)));
				
				List<String> newAllergens = ui.om.getAllergensForEditedDish(originalIngredients);
				for (JCheckBox allergen: allergenBoxes) {
					allergen.setSelected(newAllergens.contains(allergen.getText()));
				}
	    	}
	    });
	    contentsPanel.add(reset, gbc);
	    
	    
		add(contentsPanel);
	}
}