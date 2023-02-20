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
	
	private MainPage MainPage;
	private AddPage AddPage;
	private AddAllergenPage AddAllergenPage;
	private AddIngredientPage AddIngredientPage;
	private AddComponentPage AddComponentPage;
	private AddDishPage AddDishPage;
	private RemovePage RemovePage;
	private RemoveIngredientPage RemoveIngredientPage;
//	private RemoveComponentPage RemoveComponentPage;
	private RemoveDishPage RemoveDishPage;
	private QueryPage QueryPage;
	

	public UI() {
		
		om = new OntologyManager();
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Restaurant Ontology Manager");
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		
		MainPage = new MainPage(this);
		cards.add(MainPage, MainPage.CARD);
		
		AddPage = new AddPage(this);
		cards.add(AddPage, AddPage.CARD);
		
		AddAllergenPage = new AddAllergenPage(this);
		cards.add(AddAllergenPage, AddAllergenPage.CARD);
		
		AddIngredientPage = new AddIngredientPage(this);
		cards.add(AddIngredientPage, AddIngredientPage.CARD);
		
		AddComponentPage = new AddComponentPage(this);
		cards.add(AddComponentPage, AddComponentPage.CARD);
		
		AddDishPage = new AddDishPage(this);
		cards.add(AddDishPage, AddDishPage.CARD);
		
		RemovePage = new RemovePage(this);
		cards.add(RemovePage, RemovePage.CARD);
		
		RemoveIngredientPage = new RemoveIngredientPage(this);
		cards.add(RemoveIngredientPage, RemoveIngredientPage.CARD);
		
		RemoveDishPage = new RemoveDishPage(this);
		cards.add(RemoveDishPage, RemoveDishPage.CARD);
		
		QueryPage = new QueryPage(this);
		cards.add(QueryPage, QueryPage.CARD);
		
		frame.add(cards, BorderLayout.CENTER);
		frame.setVisible(true);
		cardLayout.show(cards, MainPage.CARD);

		
	}
	
	public void SwitchToFrame(String page) {
		System.out.println("Switching to " + page);
		cardLayout.show(cards, page);
	}
	
	public void Quit() {
		frame.dispose();
	}
	
	public void newIngredient() {
		AddComponentPage.reload();
		RemoveIngredientPage.reload();
	}
	
	public void newDish() {
		RemoveDishPage.reload();
	}
	
}
