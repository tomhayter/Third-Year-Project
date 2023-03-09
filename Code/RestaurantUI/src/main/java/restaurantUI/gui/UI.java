package restaurantUI.gui;

import restaurantUI.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI {
	
	public OntologyManager om;
	
	int height = 600;
	int width = 550;
	
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
	private RemoveIngredientPage RemoveIngredientPage;
	private RemoveComponentPage RemoveComponentPage;
	private RemoveDishPage RemoveDishPage;
	private QueryPage QueryPage;
	private SettingsPage SettingsPage;
	

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
		
		RemoveIngredientPage = new RemoveIngredientPage(this);
		cards.add(RemoveIngredientPage, restaurantUI.gui.RemoveIngredientPage.CARD);
		
		RemoveComponentPage = new RemoveComponentPage(this);
		cards.add(RemoveComponentPage, restaurantUI.gui.RemoveComponentPage.CARD);
		
		RemoveDishPage = new RemoveDishPage(this);
		cards.add(RemoveDishPage, restaurantUI.gui.RemoveDishPage.CARD);
		
		QueryPage = new QueryPage(this);
		cards.add(QueryPage, restaurantUI.gui.QueryPage.CARD);
		
		SettingsPage = new SettingsPage(this);
		cards.add(SettingsPage, restaurantUI.gui.SettingsPage.CARD);
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
	}
	
	public void updateIngredients() {
		AddComponentPage.reload();
		RemoveIngredientPage.reload();
	}
	
	public void updateComponents() {
		AddDishPage.reload();
		RemoveComponentPage.reload();
	}
	
	public void updateDishes() {
		RemoveDishPage.reload();
	}
	
	public void updateSettings() {
		QueryPage.refresh();
	}
	
}
