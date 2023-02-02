package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddPage extends JPanel {
	
	UI ui;

	public AddPage(UI ui) {
		this.ui = ui;
		setName("Add to ontology");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("What would you like to add to the ontology?");
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(title, BorderLayout.NORTH);

        JButton addIng = new JButton("Ingredient");
        addIng.setSize(2, 4);
        addIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(UI.ADDINGREDIENTPAGE);
            }
        });
        add(addIng, BorderLayout.CENTER);

        JButton backToMain = new JButton("Back to Main Menu");
        backToMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(UI.MAINPAGE);
            }
        });
        add(backToMain, BorderLayout.SOUTH);
	}
}
