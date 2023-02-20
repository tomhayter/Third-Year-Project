package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemovePage extends JPanel {
	
	UI ui;
	final static String CARD = "RemovePage";
	
	public RemovePage(UI ui) {
		this.ui = ui;
		
		setName("Remove from ontology");

        JLabel title = new JLabel("What would you like to remove from the ontology?");
        add(title, BorderLayout.NORTH);
        
//        JPanel buttonsPanel = new JPanel(new GridLayout(4, 3, 20, 20));
        
        
        JButton removeIng = new JButton("Ingredient");
        removeIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveIngredientPage.CARD);
            }
        });
        add(removeIng, BorderLayout.CENTER);
        
        JButton removeComp = new JButton("Component");
        removeComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveComponentPage.CARD);
            }
        });
        add(removeComp);
        
        JButton removeDish = new JButton("Dish");
        removeDish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemoveDishPage.CARD);
            }
        });
        add(removeDish);
        

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        add(backToMain, BorderLayout.SOUTH);
	}
}
