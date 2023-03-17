package customerUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainPage extends JPanel {
	
	UI ui;
	public final static String CARD = "MainPage";
	
	public MainPage(UI ui) {
		this.ui = ui;
		setName("RestaurantUI");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("Honest Burger's Menu");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(7, 3, 0, 32));
        
        buttonsPanel.add(new JLabel(""));
        
        JButton goToQueryButton = new JButton("Search the menu");
        goToQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryPage.CARD);
            }
        });
        buttonsPanel.add(goToQueryButton);
        
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));        
        
        JButton settings = new JButton("Settings");
        settings.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ui.SwitchToFrame(SettingsPage.CARD);
        	}
        });
        buttonsPanel.add(settings);
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));        
        
        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ui.Quit();
        	}
        });
        buttonsPanel.add(quit);
        
        buttonsPanel.add(new JLabel(""));
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
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
