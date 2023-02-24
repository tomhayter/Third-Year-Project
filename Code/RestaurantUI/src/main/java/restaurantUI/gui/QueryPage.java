package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class QueryPage extends JPanel {
	
	UI ui;
	final static String CARD = "QueryPage";
	DefaultListModel<String> list = new DefaultListModel<String>();
	List<JCheckBox> allergenBoxes = new ArrayList<JCheckBox>();
	
	
	public QueryPage(UI ui) {
		this.ui = ui;
		
		setLayout(new GridLayout(1, 2, 10, 10));
		
		JPanel results = new JPanel(new GridLayout(0, 1, 10, 10));
		
		JLabel title = new JLabel("Results:");
		results.add(title, BorderLayout.NORTH);
		
		List<String> allDishes = ui.om.getAllDishNames();
		for(String s: allDishes) {
			list.addElement(s);
		}
		
		JList<String> resultsList = new JList<String>(list);
		resultsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		results.add(resultsList);
		
		JButton back = new JButton("Back to Main Menu");
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
		results.add(back);
		
		add(results);
		
		JPanel options = new JPanel(new GridLayout(0, 1, 10, 10));
		
		JPanel dietPanel = new JPanel();
		dietPanel.add(new JLabel("Diets:"));
		JCheckBox vege = new JCheckBox("Vegetarian");
		dietPanel.add(vege);
		JCheckBox vegan = new JCheckBox("Vegan");
		dietPanel.add(vegan);
		JCheckBox halal = new JCheckBox("Halal");
		dietPanel.add(halal);
		JCheckBox kosher = new JCheckBox("Kosher");
		dietPanel.add(kosher);
		
		options.add(dietPanel);
		
		JPanel calPanel = new JPanel();
		calPanel.add(new JLabel("Calories"));
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		slider.setMinorTickSpacing(50);
		slider.setMajorTickSpacing(200);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		calPanel.add(slider);
		
		options.add(calPanel);
		
		
		JPanel allergenPanel = new JPanel();
		allergenPanel.add(new JLabel("Allergen:"));
		List<String> allergens = ui.om.getAllAllergenNames();
		for (String allergen: allergens) {
			JCheckBox box = new JCheckBox(allergen);
			allergenBoxes.add(box);
			allergenPanel.add(box);
		}
		options.add(allergenPanel);
		
		JButton search = new JButton("Search");
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
}
