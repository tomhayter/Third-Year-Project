package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainPage extends JPanel {
	
	UI ui;
	public final static String CARD = "MainPage";
	
	public MainPage(UI ui) {
		this.ui = ui;
		setName("RestaurantUI");
		setLayout(new GridLayout(3, 1, 20, 20));

        JLabel title = new JLabel("An ontology-based restaurant system.");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        
        

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 3, 20, 20));
        buttonsPanel.add(new JLabel(""));
        
        
        JButton goToAddButton = new JButton("Add to ontology");
        goToAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                ui.SwitchToFrame(AddPage.CARD);
            }
        });
        buttonsPanel.add(goToAddButton);
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton goToRemoveButton = new JButton("Remove from ontology");
        goToRemoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(RemovePage.CARD);
            }
        });
        buttonsPanel.add(goToRemoveButton);
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton goToQueryButton = new JButton("Query the ontology");
        goToQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryPage.CARD);
            }
        });
        buttonsPanel.add(goToQueryButton);
        
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));        
        
        JButton quit = new JButton("Quit");
        quit.setPreferredSize(new Dimension(10,50));
        quit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ui.Quit();
        	}
        });
        buttonsPanel.add(quit);
        
        add(buttonsPanel);
	}

}
