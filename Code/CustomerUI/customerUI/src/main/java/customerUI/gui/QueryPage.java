package customerUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
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
	List<JCheckBox> dietBoxes = new ArrayList<JCheckBox>();
	
	JSlider slider;
	
	JPanel options;
	JPanel calPanel;
	JPanel allergenPanel;
	JButton search;
	JCheckBox gf;
	GridBagConstraints gbc;
	
	
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
	    gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 32, 16, 8);
	    gbc.insets = i;
		JPanel results = new JPanel(new BorderLayout());
		results.setBorder(new CompoundBorder(new TitledBorder("Results"), new EmptyBorder(16, 16, 16, 16)));
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.weightx = 1;
	    gbc.weighty = 1;
	    gbc.fill = GridBagConstraints.BOTH;
		List<String> allDishes = ui.om.getAllDishNames();
		for(String s: allDishes) {
			list.addElement(s);
		}
		JList<String> resultsList = new JList<String>(list);
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
	            DishPage dish = new DishPage(ui, resultsList.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
	            ui.SwitchToFrame(dish, resultsList.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
	            resultsList.setSelectedIndices(new int[0]);
		    }
		    
		    public void mouseExited(MouseEvent evt) {
		    	for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
		    }
		});
		
		resultsList.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent me) {
				Point p = new Point(me.getX(), me.getY());
				for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
				list.set(resultsList.locationToIndex(p), "<html><u>" + list.get(resultsList.locationToIndex(p)) + "</u></html>");
			}
		});
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(resultsList);
		results.add(scroll, BorderLayout.CENTER);
		contentsPanel.add(results, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		
		gbc.fill = GridBagConstraints.NONE;
		JButton back = new JButton("Back to Main Menu");
		back.setFont(new Font("Calibri", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
		contentsPanel.add(back, gbc);
		
		
		options = new JPanel(new GridBagLayout());
		options.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Filter")));
		gbc.fill = GridBagConstraints.BOTH;
		i = new Insets(0, 0, 16, 16);
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel dietPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		dietPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Diets")));
		JCheckBox vege = new JCheckBox("Vegetarian");
		dietBoxes.add(vege);
		dietPanel.add(vege);
		JCheckBox vegan = new JCheckBox("Vegan");
		dietBoxes.add(vegan);
		dietPanel.add(vegan);
		JCheckBox halal = new JCheckBox("Halal");
		dietBoxes.add(halal);
		dietPanel.add(halal);
		JCheckBox kosher = new JCheckBox("Kosher");
		dietBoxes.add(kosher);
		dietPanel.add(kosher);
		options.add(dietPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gf = new JCheckBox("Gluten Free Alternative Available");		
		options.add(gf, gbc);
		
		slider = new JSlider(JSlider.HORIZONTAL, 500, 1500, 1500);
		
		if (ui.showCalories) {
			gbc.gridx = 0;
			gbc.gridy = 2;
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
		gbc.gridy = 3;
		allergenPanel = new JPanel(new GridLayout(0, 2, 3, 3));
		allergenPanel.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new TitledBorder("Allergens")));
		List<String> allergens = ui.om.getAllAllergenNames();
		for (String allergen: allergens) {
			JCheckBox box = new JCheckBox(allergen);
			allergenBoxes.add(box);
			allergenPanel.add(box);
		}
		options.add(allergenPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		JButton reset = new JButton("Reset Filters");
	    reset.setFont(new Font("Calibri", Font.PLAIN, 16));
	    reset.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		refresh();
	    	}
	    });
	    options.add(reset, gbc);
		
		
		
		search = new JButton("Search");
		search.setFont(new Font("Calibri", Font.PLAIN, 16));
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
            	
                List<String> dishes = ui.om.dishSearch(
                		selectedAllergens,
                		vege.isSelected(),
                		vegan.isSelected(),
                		kosher.isSelected(),
                		halal.isSelected(),
                		gf.isSelected(),
                		slider.getValue());
                for(String s: dishes) {
                	list.addElement(s);
                }
            }
        });
		gbc.gridx = 0;
	    gbc.gridy = 5;
	    gbc.fill = GridBagConstraints.NONE;
	    options.add(search, gbc);
	    
	    i = new Insets(0, 8, 16, 16);
	    gbc.gridx = 1;
	    gbc.gridy = 0;
		contentsPanel.add(options, gbc);
		
		add(contentsPanel);
	}
	
	public void refresh() {
		if (ui.showCalories) {
			options.remove(calPanel);
			gbc.gridx = 0;
		    gbc.gridy = 2;
			options.add(calPanel, gbc);
		} else {
			options.remove(calPanel);
		}
		
		list.removeAllElements();
        List<String> dishes = ui.om.getAllDishNames();
        for(String s: dishes) {
        	list.addElement(s);
        }
        
        slider.setValue(slider.getMaximum());
        for (JCheckBox allergenBox: allergenBoxes) {
        	allergenBox.setSelected(false);
        }
        for (JCheckBox dietBox: dietBoxes) {
        	dietBox.setSelected(false);
        }
        gf.setSelected(false);
		
		revalidate();
		repaint();
	}
}
