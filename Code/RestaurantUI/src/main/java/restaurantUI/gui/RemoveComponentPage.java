package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemoveComponentPage extends JPanel {

	UI ui;
	final static String CARD = "RemoveComponentPage";
	JComboBox<String> compName;
	
	public RemoveComponentPage(UI ui) {
		this.ui = ui;
		
		setName("Remove Component");
		JLabel title = new JLabel("Select a component to remove");
		add(title, BorderLayout.NORTH);
		
		List<String> compNames = ui.om.getAllComponentNames();
		
		compName = new JComboBox<String>();
		for(String name: compNames) {
			compName.addItem(name);
		}
		add(compName);
		

        JButton removeButton = new JButton("Remove Component");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		ui.om.removeComponent(compName.getSelectedItem().toString());
        		ui.updateComponents();
                
            	System.out.println("Deleting " + compName.getSelectedItem().toString());
                ui.SwitchToFrame(MainPage.CARD);
            }
        });
        add(removeButton);
	}
	
	
	void reload() {
		List<String> compNames = ui.om.getAllComponentNames();
		
		compName.removeAllItems();
		for(String name: compNames) {
			compName.addItem(name);
		}
	}
}
