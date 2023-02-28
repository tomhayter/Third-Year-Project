package restaurantUI.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPage extends JPanel {
	
	UI ui;
	public static final String CARD = "SettingsPage";
	
	public SettingsPage(UI ui) {
		this.ui = ui;
		
		JLabel title = new JLabel("Settings");
		add(title);
		
		JCheckBox calories = new JCheckBox("Show calorie information");
		calories.setSelected(ui.showCalories);
		calories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.showCalories = calories.isSelected();
			}
		});
		
		add(calories);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ui.updateSettings();
        		ui.SwitchToFrame(MainPage.CARD);
        	}
        });
		add(back);	
	}
}
