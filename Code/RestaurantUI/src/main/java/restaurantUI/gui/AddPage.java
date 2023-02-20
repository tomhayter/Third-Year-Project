package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddPage extends JPanel {
	
	UI ui;
	public final static String CARD = "AddPage";

	public AddPage(UI ui) {
		this.ui = ui;
		setName("Add to ontology");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("What would you like to add to the ontology?");
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(5, 1, 20, 20));
        
        JButton addAllergen = new JButton("Allergen");
        addAllergen.setSize(2, 4);
        addAllergen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddAllergenPage.CARD);
            }
        });
        buttons.add(addAllergen);
        
        JButton addIng = new JButton("Ingredient");
        addIng.setSize(2, 4);
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddIngredientPage.CARD);
            }
        });
        buttons.add(addIng);
        
        JButton addComp = new JButton("Component");
        addComp.setSize(2, 4);
        addComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddComponentPage.CARD);
            }
        });
        buttons.add(addComp);
        
        JButton addDish = new JButton("Dish");
        addDish.setSize(2, 4);
        addDish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddDishPage.CARD);
            }
        });
        buttons.add(addDish);

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        buttons.add(backToMain);
        
        add(buttons);
	}
}
