//package restaurantUI;
//
//import java.util.List;
//import java.util.Set;
//
//import org.semanticweb.owlapi.model.OWLAxiom;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLEntity;
//import org.semanticweb.owlapi.reasoner.Node;
//
//public class Main {
//
//	public static void main(String[] args) {
//		OntologyManager om = new OntologyManager();
//		
//		om.addIngredient("Chicken", IngredientType.Meat, new String[0], false);
//		om.addIngredient("Mushroom", IngredientType.Vegetable, new String[0], false);
//		om.addIngredient("Pastry", IngredientType.Other, new String[0], false);
//		String[] ings = {"Chicken", "Mushroom", "Pastry"};
//		om.addComponent("ChickenPie", ings);
//		om.addIngredient("Broccoli", IngredientType.Vegetable, new String[0], false);
//		om.addIngredient("RoastPotatoes", IngredientType.Carbohydrate, new String[0], false);
//		String[] comps = {"ChickenPie"};
//		String[] sides = {"Broccoli", "RoastPotatoes"};
//		om.addDish("ChickenPie", comps, sides);
//		
//		System.out.println(om.runReasoner());
//		om.test();
//		
//		List<Node<OWLClass>> dishes = om.getDishes();
//		for (Node<OWLClass> dish: dishes) {
//			System.out.println(dish);
//		}
//		
//		System.out.println();
//		
//		List<OWLEntity> chicken_things = om.getEntities("Chicken");
//		for (OWLEntity entity: chicken_things) {
//			System.out.println(entity);
//		}
//		
//		System.out.println();
//		
//		Set<OWLAxiom> all = om.getAllAxioms();
//		for (OWLAxiom axiom: all) {
//			System.out.println(axiom);
//		}
//		
//
//	}
//
//}
