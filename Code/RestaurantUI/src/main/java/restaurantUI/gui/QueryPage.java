package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class QueryPage extends JPanel {
	
	UI ui;
	final static String CARD = "QueryPage";
	
	public QueryPage(UI ui) {
		this.ui = ui;
		
		setLayout(new GridLayout(1, 2, 10, 10));
		
		JPanel results = new JPanel(new GridLayout(0, 1, 10, 10));
		
		JLabel title = new JLabel("Results:");
		results.add(title, BorderLayout.NORTH);
		
		ArrayList<String> resultDishes = new ArrayList<String>();
//		resultDishes = ui.om.search();
		resultDishes.add("ChickenCurry");
		resultDishes.add("Spaghetti Bolognese");
		
		JList list = new JList(resultDishes.toArray());
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		results.add(list);
		
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
		JCheckBox gluten = new JCheckBox("Gluten");
		allergenPanel.add(gluten);
		JCheckBox peanut = new JCheckBox("Peanut");
		allergenPanel.add(peanut);
		options.add(allergenPanel);
		
		JButton search = new JButton("Search");
		options.add(search);
		add(options);
		
	}
}
