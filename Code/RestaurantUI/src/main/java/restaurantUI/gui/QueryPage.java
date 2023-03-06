package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JSlider;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class QueryPage extends JPanel {
	
	UI ui;
	final static String CARD = "QueryPage";
	DefaultListModel<String> list = new DefaultListModel<String>();
	List<JCheckBox> allergenBoxes = new ArrayList<JCheckBox>();
	
	JPanel options;
	JPanel calPanel;
	JPanel allergenPanel;
	JButton search;
	
	
	public QueryPage(UI ui) {
		this.ui = ui;
		
		setLayout(new GridLayout(1, 2, 10, 10));
		
		JPanel results = new JPanel(new GridLayout(2, 1, 50, 50));
		results.setBorder(new CompoundBorder(new TitledBorder("Results"), new EmptyBorder(16, 16, 16, 16)));
		
		List<String> allDishes = ui.om.getAllDishNames();
		for(String s: allDishes) {
			list.addElement(s);
		}
		
		JList<String> resultsList = new JList<String>(list);
//		resultsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		results.add(resultsList);
		
		JButton back = new JButton("Back to Main Menu");
//		back.set
//		back.setPreferredSize(new Dimension(20, 20));
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
		results.add(back);
		
		add(results);
		
		options = new JPanel(new GridLayout(0, 1, 10, 10));
		
		JPanel dietPanel = new JPanel();
		dietPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Diets")));
		JCheckBox vege = new JCheckBox("Vegetarian");
		dietPanel.add(vege);
		JCheckBox vegan = new JCheckBox("Vegan");
		dietPanel.add(vegan);
		JCheckBox halal = new JCheckBox("Halal");
		dietPanel.add(halal);
		JCheckBox kosher = new JCheckBox("Kosher");
		dietPanel.add(kosher);
		
		options.add(dietPanel);
		
		if (ui.showCalories) {
			calPanel = new JPanel();
			calPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Calories")));
			JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
			slider.setMinorTickSpacing(50);
			slider.setMajorTickSpacing(200);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			calPanel.add(slider);
			
			options.add(calPanel);
		}
		
		
		allergenPanel = new JPanel();
		allergenPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Allergens")));
		List<String> allergens = ui.om.getAllAllergenNames();
		for (String allergen: allergens) {
			JCheckBox box = new JCheckBox(allergen);
			allergenBoxes.add(box);
			allergenPanel.add(box);
		}
		options.add(allergenPanel);
		
		search = new JButton("Search");
		search.setMargin(new Insets(32, 32, 32, 32));
		search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	list.removeAllElements();
            	List<String> selectedAllergens = new ArrayList<String>();
            	for(JCheckBox box: allergenBoxes) {
            		if (box.isSelected()) {
            			selectedAllergens.add(box.getText());
            		}
            	}
            	System.out.println(selectedAllergens);
                List<String> dishes = ui.om.dishSearch(selectedAllergens, vege.isSelected(), vegan.isSelected(), kosher.isSelected(), halal.isSelected());
                for(String s: dishes) {
                	list.addElement(s);
                }
            }
        });
		options.add(search);
		add(options);
		
	}
	
	public void refresh() {
		if (ui.showCalories) {
			options.remove(calPanel);
			options.remove(allergenPanel);
			options.remove(search);
			options.add(calPanel);
			options.add(allergenPanel);
			options.add(search);
		} else {
			options.remove(calPanel);
		}
		
		revalidate();
		repaint();
		
	}
}
