package customerUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SettingsPage extends JPanel {
	
	UI ui;
	public static final String CARD = "SettingsPage";
	
	public SettingsPage(UI ui) {
		this.ui = ui;
		
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Settings");
		title.setFont(new Font("Calibri", Font.BOLD, 24));
		JPanel titlePanel = new JPanel();
		titlePanel.add(title);
		add(titlePanel, BorderLayout.NORTH);
		
		JPanel contentsPanel = new JPanel(new GridLayout(7, 3, 0, 32));
		contentsPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
		
		contentsPanel.add(new JLabel(""));
		JCheckBox calories = new JCheckBox("Show calorie information");
		calories.setSelected(ui.showCalories);
		calories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.showCalories = calories.isSelected();
			}
		});
		contentsPanel.add(calories);
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		JButton back = new JButton("Back");
		back.setFont(new Font("Calibri", Font.PLAIN, 16));
		back.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ui.updateSettings();
        		ui.SwitchToFrame(MainPage.CARD);
        	}
        });
		contentsPanel.add(back);
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		contentsPanel.add(new JLabel(""));
		
		add(contentsPanel, BorderLayout.CENTER);
	}
}
