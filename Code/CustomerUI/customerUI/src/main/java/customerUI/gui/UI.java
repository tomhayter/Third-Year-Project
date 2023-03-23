package customerUI.gui;

import customerUI.*;

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
		cards.add(MainPage, customerUI.gui.MainPage.CARD);
		
		QueryPage = new QueryPage(this);
		cards.add(QueryPage, customerUI.gui.QueryPage.CARD);
		
		SettingsPage = new SettingsPage(this);
		cards.add(SettingsPage, customerUI.gui.SettingsPage.CARD);
	}
	
	public void Start() { 
		frame.add(cards, BorderLayout.CENTER);
		frame.setVisible(true);
		cardLayout.show(cards, customerUI.gui.MainPage.CARD);
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
	
	public void updateSettings() {
		QueryPage.refresh();
	}
	
}
