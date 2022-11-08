package restaurantUI;

public class Main {

	public static void main(String[] args) {
		OntologyManager om = new OntologyManager();
		
		om.addIngredient("Chicken", IngredientType.Meat, new String[0], false);
		om.addIngredient("Mushroom", IngredientType.Vegetable, new String[0], false);
		om.addIngredient("Pastry", IngredientType.Other, new String[0], false);
		String[] ings = {"Chicken", "Mushroom", "Pastry"};
		om.addComponent("ChickenPie", ings);
		om.addIngredient("Broccoli", IngredientType.Vegetable, new String[0], false);
		om.addIngredient("RoastPotatoes", IngredientType.Carbohydrate, new String[0], false);
		String[] comps = {"ChickenPie"};
		String[] sides = {"Broccoli", "RoastPotatoes"};
		om.addDish("ChickenPie", comps, sides);

	}

}
