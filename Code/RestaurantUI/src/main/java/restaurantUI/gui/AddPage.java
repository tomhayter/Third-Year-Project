package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AddPage extends JPanel {
	
	UI ui;
	public final static String CARD = "AddPage";

	public AddPage(UI ui) {
		this.ui = ui;
		setName("Add to ontology");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("What would you like to add to the ontology?");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridLayout(7, 3, 0, 32));
        buttons.add(new JLabel(""));
        
        JButton addAllergen = new JButton("Allergen");
        addAllergen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddAllergenPage.CARD);
            }
        });
        buttons.add(addAllergen);
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        
        JButton addIng = new JButton("Ingredient");
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddIngredientPage.CARD);
            }
        });
        buttons.add(addIng);
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        
        JButton addComp = new JButton("Component");
        addComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddComponentPage.CARD);
            }
        });
        buttons.add(addComp);
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        
        JButton addDish = new JButton("Dish");
        addDish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(AddDishPage.CARD);
            }
        });
        buttons.add(addDish);
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        buttons.add(backToMain);
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        buttons.add(new JLabel(""));
        
        buttons.setBorder(new EmptyBorder(32, 0, 0, 0));
        
        add(buttons, BorderLayout.CENTER);
	}
}
