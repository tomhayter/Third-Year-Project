package restaurantUI.gui;

import restaurantUI.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI {
	
	public OntologyManager om;
	
	int height = 725;
	int width = 600;
	
	JFrame frame;
	
	public boolean showCalories = true;
	
	private JPanel cards;
	private CardLayout cardLayout;
	
	private MainPage MainPage;
	private AddPage AddPage;
	private AddAllergenPage AddAllergenPage;
	private AddIngredientPage AddIngredientPage;
	private AddComponentPage AddComponentPage;
	private AddDishPage AddDishPage;
	private RemovePage RemovePage;
	private RemoveAllergenPage RemoveAllergenPage;
	private RemoveIngredientPage RemoveIngredientPage;
	private RemoveComponentPage RemoveComponentPage;
	private RemoveDishPage RemoveDishPage;
	private QueryPage QueryPage;
	private QueryIngredientPage QueryIngredientPage;
	private QueryComponentPage QueryComponentPage;
	private QueryDishPage QueryDishPage;
	

	public UI(OntologyManager manager) {
		
		om = manager;
		
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("Restaurant Ontology Manager");
		cardLayout = new CardLayout();
		cards = new JPanel(cardLayout);
		
		MainPage = new MainPage(this);
		cards.add(MainPage, restaurantUI.gui.MainPage.CARD);
		
		AddPage = new AddPage(this);
		cards.add(AddPage, restaurantUI.gui.AddPage.CARD);
		
		AddAllergenPage = new AddAllergenPage(this);
		cards.add(AddAllergenPage, restaurantUI.gui.AddAllergenPage.CARD);
		
		AddIngredientPage = new AddIngredientPage(this);
		cards.add(AddIngredientPage, restaurantUI.gui.AddIngredientPage.CARD);
		
		AddComponentPage = new AddComponentPage(this);
		cards.add(AddComponentPage, restaurantUI.gui.AddComponentPage.CARD);
		
		AddDishPage = new AddDishPage(this);
		cards.add(AddDishPage, restaurantUI.gui.AddDishPage.CARD);
		
		RemovePage = new RemovePage(this);
		cards.add(RemovePage, restaurantUI.gui.RemovePage.CARD);
		
		RemoveAllergenPage = new RemoveAllergenPage(this);
		cards.add(RemoveAllergenPage, restaurantUI.gui.RemoveAllergenPage.CARD);
		
		RemoveIngredientPage = new RemoveIngredientPage(this);
		cards.add(RemoveIngredientPage, restaurantUI.gui.RemoveIngredientPage.CARD);
		
		RemoveComponentPage = new RemoveComponentPage(this);
		cards.add(RemoveComponentPage, restaurantUI.gui.RemoveComponentPage.CARD);
		
		RemoveDishPage = new RemoveDishPage(this);
		cards.add(RemoveDishPage, restaurantUI.gui.RemoveDishPage.CARD);
		
		QueryPage = new QueryPage(this);
		cards.add(QueryPage, restaurantUI.gui.QueryPage.CARD);
		
		QueryIngredientPage = new QueryIngredientPage(this);
		cards.add(QueryIngredientPage, restaurantUI.gui.QueryIngredientPage.CARD);
		
		QueryComponentPage = new QueryComponentPage(this);
		cards.add(QueryComponentPage, restaurantUI.gui.QueryComponentPage.CARD);
		
		QueryDishPage = new QueryDishPage(this);
		cards.add(QueryDishPage, restaurantUI.gui.QueryDishPage.CARD);
	}
	
	public void Start() { 
		frame.add(cards, BorderLayout.CENTER);
		frame.setVisible(true);
		cardLayout.show(cards, restaurantUI.gui.MainPage.CARD);
	}
	
	public void SwitchToFrame(String page) {
		System.out.println("Switching to " + page);
		cardLayout.show(cards, page);
	}
	
	public void SwitchToFrame(JPanel panel, String page) {
		System.out.println("Switching to " + page);
		cards.add(panel, page);
		cardLayout.show(cards, page);
	}
	
	public void Quit() {
		om.saveOntology();
		frame.dispose();
	}
	
	public void updateAllergens() {
		AddIngredientPage.reload();
		RemoveAllergenPage.reload();
	}
	
	public void updateIngredients() {
		AddComponentPage.reload();
		QueryIngredientPage.reload();
		RemoveIngredientPage.reload();
	}
	
	public void updateComponents() {
		AddDishPage.reload();
		QueryComponentPage.reload();
		RemoveComponentPage.reload();
	}
	
	public void updateDishes() {
		RemoveDishPage.reload();
		QueryDishPage.refresh();
	}
	
	public void updateSettings() {
		QueryDishPage.refresh();
	}
	
}
