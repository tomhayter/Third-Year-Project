package restaurantUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

public class Frames {

    int height = 400;
    int width = 400;

    private JFrame mainFrame;
    private JFrame addFrame;
    private JFrame addIngFrame;
    private JFrame removeFrame;
    private JFrame removeIngFrame;
    private JFrame queryFrame;

    private JFrame currentFrame;

    private OntologyManager om;

    public Frames() {

        om = new OntologyManager();

        createMainFrame();

        createAddFrame();
        createAddIngFrame();
        
        createRemoveFrame();
        createRemoveIngFrame();

        createQueryFrame();


        mainFrame.setVisible(true);
        currentFrame = mainFrame;



    }

    private void createMainFrame() {
        mainFrame = new JFrame("RestaurantUI");
        mainFrame.setSize(width, height);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel title = new JLabel("An ontology-based restaurant system.");
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        mainFrame.add(titlePanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(0, 1));
        JButton goToAddButton = new JButton("Add to ontology");
        goToAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(addFrame);
            }
        });
        buttonsPanel.add(goToAddButton, BorderLayout.NORTH);
        JButton goToRemoveButton = new JButton("Remove from ontology");
        goToRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(removeFrame);
            }
        });
        buttonsPanel.add(goToRemoveButton, BorderLayout.CENTER);
        JButton goToQueryButton = new JButton("Query the ontology");
        goToQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(queryFrame);
            }
        });
        buttonsPanel.add(goToQueryButton, BorderLayout.SOUTH);

        mainFrame.add(buttonsPanel, BorderLayout.CENTER);

    }

    private void createAddFrame() {
        addFrame = new JFrame("Add to ontology");
        applyBasicFrameSettings(addFrame);

        JLabel title = new JLabel("What would you like to add to the ontology?");
        addFrame.add(title, BorderLayout.NORTH);

        JButton addIng = new JButton("Ingredient");
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(addIngFrame);
            }
        });
        addFrame.add(addIng, BorderLayout.CENTER);

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(mainFrame);
            }
        });
        addFrame.add(backToMain, BorderLayout.SOUTH);
    }

    private void createAddIngFrame() {
        addIngFrame = new JFrame("Add Ingredient");
        applyBasicFrameSettings(addIngFrame);
        String[] ingTypesList = {"Meat", "Vegetable"};
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesList);
        addIngFrame.add(ingTypes, BorderLayout.NORTH);
        JTextField ingName = new JTextField("Enter Ingredient Name");
        ingName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ingName.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ingName.getText().equals(null)) {
                    ingName.setText("Enter Ingredient Name");
                }
            }
        });
        addIngFrame.add(ingName, BorderLayout.CENTER);
        JButton addButton = new JButton("Add Ingredient");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                om.addIngredient(ingName.getText(),ingTypes.getSelectedItem().toString());
                SwitchToFrame(mainFrame);
            }
        });
        addIngFrame.add(addButton, BorderLayout.SOUTH);
    }
    
    private void createRemoveFrame() {
        removeFrame = new JFrame("Remove from ontology");
        applyBasicFrameSettings(removeFrame);

        JLabel title = new JLabel("What would you like to remove from the ontology?");
        removeFrame.add(title, BorderLayout.NORTH);

        JButton removeIng = new JButton("Ingredient");
        removeIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(removeIngFrame);
            }
        });
        removeFrame.add(removeIng, BorderLayout.CENTER);

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwitchToFrame(mainFrame);
            }
        });
        removeFrame.add(backToMain, BorderLayout.SOUTH);
    }
    
    private void createRemoveIngFrame() {
        removeIngFrame = new JFrame("Remove Ingredient");
        applyBasicFrameSettings(removeIngFrame);
        String[] ingTypesList = {"Meat", "Vegetable", "All"};
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesList);
        removeIngFrame.add(ingTypes, BorderLayout.NORTH);

        
        
        JComboBox<String>ingName = new JComboBox<String>();
        
        ingTypes.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selection = ingTypes.getSelectedItem().toString();
        		List<String> ings = new ArrayList<String>();
        		if (selection.equals("All")) {
        			ings = om.getAllIngredientNames();
        		}
        		else {
        			ings = om.getIngredientNamesOfType(IngredientType.valueOf(selection));
        		}
        		ingName.removeAllItems();
        		for(String item: ings) {
        			ingName.addItem(item);
        		}
        	}
        });
        
        
        removeIngFrame.add(ingName, BorderLayout.CENTER);
        JButton addButton = new JButton("Remove Ingredient");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if (ingTypes.getSelectedItem().toString().equals("All")) {
            		
            	} else {
            		om.removeIngredient(ingName.getSelectedItem().toString(), IngredientType.valueOf(ingTypes.getSelectedItem().toString()));
            	}
                
            	System.out.println("Deleting " + ingName.getSelectedItem().toString());
                SwitchToFrame(mainFrame);
            }
        });
        removeIngFrame.add(addButton, BorderLayout.SOUTH);
    }

    private void createQueryFrame() {
        queryFrame = new JFrame("Query Ontology");
        JLabel title = new JLabel("Results");
        queryFrame.add(title, BorderLayout.NORTH);
        applyBasicFrameSettings(queryFrame);
        JPanel results = new JPanel(new GridLayout(0, 1));


        for(String dish: om.getAllDishNames()) {
            results.add(new JLabel(dish));
        }
        queryFrame.add(results, BorderLayout.WEST);

        JPanel filters = new JPanel(new GridLayout(0, 1));
        JPanel diets = new JPanel(new GridLayout(0, 1));
        diets.add(new JLabel("Diets"));
        for(String customer: om.getCustomerTypes()) {
            diets.add(new JCheckBox(customer));
        }
        filters.add(diets);

        JPanel calories = new JPanel(new GridLayout(0, 1));
        calories.add(new JLabel("Calories"));
        JSlider calorieSlider = new JSlider(0, 2000);
        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(2000, new JLabel("2000"));
        calorieSlider.setLabelTable(labelTable);
        calories.add(calorieSlider);
        filters.add(calories);

        queryFrame.add(filters, BorderLayout.EAST);

    }

    private void applyBasicFrameSettings(JFrame frame) {
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void SwitchToFrame(JFrame newFrame) {
        currentFrame.setVisible(false);
        newFrame.setVisible(true);
        currentFrame = newFrame;
    }
}
