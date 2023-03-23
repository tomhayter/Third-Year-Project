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

public class QueryPage extends JPanel {

	UI ui;
	final static String CARD = "QueryPage";
	
	public QueryPage(UI ui) {
		this.ui = ui;
		
		setName("Search ontology");
		setLayout(new BorderLayout());

        JLabel title = new JLabel("What would you like to search for in the ontology?");
        title.setFont(new Font("Calibri", Font.BOLD, 24));
        JPanel titlePanel = new JPanel();
        titlePanel.add(title, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel buttonsPanel = new JPanel(new GridLayout(7, 3, 0, 32));
        buttonsPanel.add(new JLabel(""));
        
        JButton searchIng = new JButton("Ingredient");
        searchIng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryIngredientPage.CARD);
            }
        });
        buttonsPanel.add(searchIng);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton searchComp = new JButton("Component");
        searchComp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryComponentPage.CARD);
            }
        });
        buttonsPanel.add(searchComp);
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        JButton searchDish = new JButton("Dish");
        searchDish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.SwitchToFrame(QueryDishPage.CARD);
            }
        });
        buttonsPanel.add(searchDish);
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
        
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        buttonsPanel.add(new JLabel(""));
        
        buttonsPanel.setBorder(new EmptyBorder(32, 0, 0, 0));
        
        add(buttonsPanel, BorderLayout.CENTER);
	}
}