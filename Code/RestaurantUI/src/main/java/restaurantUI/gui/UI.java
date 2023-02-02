package restaurantUI.gui;

import restaurantUI.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI {
	
	public OntologyManager om;
	
	int height = 500;
	int width = 500;
	
	JFrame frame;
	
	private JPanel cards;
	private CardLayout cardLayout;
	final static String MAINPAGE = "MainPage";
	final static String ADDPAGE = "AddPage";
	final static String ADDINGREDIENTPAGE = "AddIngredientPage";
	final static String REMOVEPAGE = "RemovePage";
	final static String REMOVEINGREDIENTPAGE = "RemoveIngredientPage";
	final static String QUERYPAGE = "QueryPage";

	public UI() {
		
		om = new OntologyManager();
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setName("Restaurant Ontology Manager");
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		
		MainPage card1 = new MainPage(this);
		cards.add(card1, MAINPAGE);
		
		AddPage card2 = new AddPage(this);
		cards.add(card2, ADDPAGE);
		
		AddIngredientPage card3 = new AddIngredientPage(this);
		cards.add(card3, ADDINGREDIENTPAGE);
		
		RemovePage card4 = new RemovePage(this);
		cards.add(card4, REMOVEPAGE);
		
		RemoveIngredientPage card5 = new RemoveIngredientPage(this);
		cards.add(card5, REMOVEINGREDIENTPAGE);
		
		
		frame.add(cards, BorderLayout.CENTER);
		frame.setVisible(true);
		cardLayout.show(cards, MAINPAGE);

		
	}
	
	public void SwitchToFrame(String page) {
		cardLayout.show(cards, page);
	}
	
	public void Quit() {
		frame.dispose();
	}
	
}
