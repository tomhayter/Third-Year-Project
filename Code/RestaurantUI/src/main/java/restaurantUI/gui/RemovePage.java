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

public class RemovePage extends JPanel {
	
	UI ui;
	final static String CARD = "RemovePage";
	
	public RemovePage(UI ui) {
		this.ui = ui;
		
		setName("Remove from ontology");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("What would you like to remove from the ontology?");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(7, 3, 0, 32));
        buttonsPanel.add(new JLabel(""));
        
        JButton removeAllergen = new JButton("Allergen");
        removeAllergen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ui.SwitchToFrame(RemoveAllergenPage.CARD);
            }
        });
        buttonsPanel.add(removeAllergen);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton removeIng = new JButton("Ingredient");
        removeIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveIngredientPage.CARD);
            }
        });
        buttonsPanel.add(removeIng);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton removeComp = new JButton("Component");
        removeComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveComponentPage.CARD);
            }
        });
        buttonsPanel.add(removeComp);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton removeDish = new JButton("Dish");
        removeDish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveDishPage.CARD);
            }
        });
        buttonsPanel.add(removeDish);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        buttonsPanel.add(backToMain);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        buttonsPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        
        add(buttonsPanel, BorderLayout.CENTER);
	}
}
