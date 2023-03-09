package restaurantUI.gui;

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
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
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
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Search");
	    title.setFont(new Font("Calibri", Font.BOLD, 24));
	    JPanel titlePanel = new JPanel();
	    titlePanel.add(title, BorderLayout.CENTER);
	    add(titlePanel, BorderLayout.NORTH);
		
		JPanel contentsPanel = new JPanel(new GridBagLayout());
		contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
	    GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 0, 16, 16);
	    gbc.insets = i;
		JPanel results = new JPanel(new BorderLayout());
		results.setBorder(new CompoundBorder(new TitledBorder("Results"), new EmptyBorder(16, 16, 16, 16)));
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.weighty = 2;
	    gbc.fill = GridBagConstraints.BOTH;
		List<String> allDishes = ui.om.getAllDishNames();
		for(String s: allDishes) {
			list.addElement(s);
		}
		JList<String> resultsList = new JList<String>(list);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        if (evt.getClickCount() > 1) {
		            DishPage dish = new DishPage(ui, resultsList.getSelectedValue());
		            ui.SwitchToFrame(dish, resultsList.getSelectedValue());
		        }
		    }
		});
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(resultsList);
		results.add(scroll, BorderLayout.CENTER);
		contentsPanel.add(results, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 1;
		
		gbc.fill = GridBagConstraints.NONE;
		JButton back = new JButton("Back to Main Menu");
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
		contentsPanel.add(back, gbc);
		
		
		options = new JPanel(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel dietPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		dietPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Diets")));
		JCheckBox vege = new JCheckBox("Vegetarian");
		dietPanel.add(vege);
		JCheckBox vegan = new JCheckBox("Vegan");
		dietPanel.add(vegan);
		JCheckBox halal = new JCheckBox("Halal");
		dietPanel.add(halal);
		JCheckBox kosher = new JCheckBox("Kosher");
		dietPanel.add(kosher);
		options.add(dietPanel, gbc);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 500, 2000, 2000);
		
		if (ui.showCalories) {
			gbc.gridx = 0;
			gbc.gridy = 1;
			calPanel = new JPanel();
			calPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Max Calories")));
			
			slider.setMinorTickSpacing(100);
			slider.setMajorTickSpacing(500);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			calPanel.add(slider);
			
			options.add(calPanel, gbc);
		}
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		allergenPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		allergenPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Allergens")));
		List<String> allergens = ui.om.getAllAllergenNames();
		for (String allergen: allergens) {
			JCheckBox box = new JCheckBox(allergen);
			allergenBoxes.add(box);
			allergenPanel.add(box);
		}
		options.add(allergenPanel, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		contentsPanel.add(options);
		
		search = new JButton("Search");
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
                List<String> dishes = ui.om.dishSearch(
                		selectedAllergens,
                		vege.isSelected(),
                		vegan.isSelected(),
                		kosher.isSelected(),
                		halal.isSelected(),
                		slider.getValue());
                for(String s: dishes) {
                	list.addElement(s);
                }
            }
        });
		gbc.gridx = 1;
	    gbc.gridy = 1;
	    gbc.fill = GridBagConstraints.NONE;
	    contentsPanel.add(search, gbc);
	    
		add(contentsPanel);
		
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
