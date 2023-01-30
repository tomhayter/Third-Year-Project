package restaurantUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.reasoner.Node;

public class CommandLineUI {
	
	String[] MainMenu = new String[] {
			"Welcome to the ontology manager. Please choose one of the following options:",
			"1. Add to ontology",
			"2. Remove from ontology",
			"3. Query ontology (TODO)",
			"4. Exit",
			"",
			"Enter an option: "
	};
	
	String[] AddMenu = new String[] {
			"What would you like to add to the ontology? Please choose one of the following options:",
			"1. Ingredient",
			"2. Component (TODO)",
			"3. Meal (TODO)",
			"4. Allergen (TODO)",
			"5. Customer Type (TODO)",
			"6. Back to Main Menu",
			"",
			"Enter an option: "
	};
	
	String[] RemoveMenu = new String[] {
			"What would you like to add to the ontology? Please choose one of the following options:",
			"1. Ingredient",
			"2. Component (TODO)",
			"3. Meal (TODO)",
			"4. Allergen (TODO)",
			"5. Customer Type (TODO)",
			"6. Back to Main Menu",
			"",
			"Enter an option: "
	};
	
	String[] IngredientTypeMenu = new String[] {
			"What category does the ingredient belong to? Please choose one of the following options:",
			"1. Carbohydrate",
			"2. Dairy",
			"3. Fat",
			"4. Meat",
			"5. Vegetable",
			"6. Other",
			"",
			"Enter an option: "
	};
	
	Map<Integer, IngredientType> IntToIngredientType = new HashMap<Integer, IngredientType>();
	
	OntologyManager om;
	
	public CommandLineUI() {
		om = new OntologyManager();
		IntToIngredientType.put(1, IngredientType.Carbohydrate);
		IntToIngredientType.put(2, IngredientType.Dairy);
		IntToIngredientType.put(3, IngredientType.Fat);
		IntToIngredientType.put(4, IngredientType.Meat);
		IntToIngredientType.put(5, IngredientType.Vegetable);
		IntToIngredientType.put(6, IngredientType.Other);
	}
	
	public int PrintMenu(String[] menu, int options) {
		int choice = 0;
		while (true) {
			for(String line: menu) {
				System.out.println(line);
			}
			BufferedReader reader = new BufferedReader(
		            new InputStreamReader(System.in));
		 
	        String input;
			try {
				input = reader.readLine();
			} catch (IOException e) {
				input = "";
			}
	 
	        try {
	        	choice = Integer.parseInt(input);
	        	if ((choice > 0) && (choice <= options)) {
	        		break;
	        	}
	        } catch (Exception e) {}
	        System.out.println();
	        System.out.println("Please choose one of the available options.");
	        System.out.println();
		}
		return choice;
	}
	
	void AddDish() {
		
	}
	
	void AddComponent() {
		
	}
	

	
	void AddIngredient() {
		System.out.println("You have chosen to add an Ingredient. What do you want to call your ingredient?");
		System.out.println("Name: ");
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	 
        String name;
		try {
			name = reader.readLine();
		} catch (IOException e) {
			name = "";
		}
		
		int choice = PrintMenu(IngredientTypeMenu, 6);
		IngredientType type = IntToIngredientType.get(choice);
		
		om.addIngredient(name, type, new String[0], false);
	}
	
	void AddMenu() {
		int choice = PrintMenu(AddMenu, 6);
		switch (choice) {
		case 1:
			AddIngredient();
			break;
		case 6:
			break;
		case 2:
			AddComponent();
			break;
		case 3:
			AddDish();
			break;
		}
	}
	
	void RemoveIngredient() {
		int choice = PrintMenu(IngredientTypeMenu, 6);
		IngredientType type = IntToIngredientType.get(choice);
		
		List<Node<OWLClass>> ingredientsOfType = om.getIngredientsOfType(type);
		
		System.out.println("Choose one of the following to remove:");
		for(Node<OWLClass> node: ingredientsOfType) {
			Set<OWLClass> ents = node.getEntities();
			for(OWLClass ent: ents) {
				System.out.println(ent.getIRI().getFragment().replace("Ingredient", ""));
			}
		}
		
		BufferedReader reader = new BufferedReader(
	            new InputStreamReader(System.in));
	 
        String name;
		try {
			name = reader.readLine();
		} catch (IOException e) {
			name = "";
		}
		
		om.removeIngredient(name, type);
		
	}
	
	void RemoveMenu() {
		int choice = PrintMenu(RemoveMenu, 6);
		switch (choice) {
		case 1:
			RemoveIngredient();
			break;
		case 6:
			break;
		}
	}
	
	void MainMenu() {
		while (true) {
			int main = PrintMenu(MainMenu, 4);
			switch (main) {
			case 1: 
				AddMenu();
				break;
			case 2:
				RemoveMenu();
				break;
			case 3:
				break;
			case 4:
				System.out.println("Quitting...");
				return;
			default:
				break;
			}
			System.out.println();
			
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CommandLineUI ui = new CommandLineUI();
		ui.MainMenu();
	}

}
