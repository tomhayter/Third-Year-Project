package restaurantUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class OntologyManager {
	
	private OWLOntologyManager man;
	private OWLOntology ontology;
	private OWLDataFactory df;
	private IRI iri;
	private String ontologyLocation = "..\\Ontology\\Menu.owl";
	private OWLReasoner reasoner;
	
	public OntologyManager() {
		man = OWLManager.createOWLOntologyManager();
		try {
			File file = new File(ontologyLocation);
			ontology = man.loadOntologyFromOntologyDocument(file);
			df = ontology.getOWLOntologyManager().getOWLDataFactory();
			iri = ontology.getOntologyID().getOntologyIRI().get();
			OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
			reasoner = reasonerFactory.createReasoner(ontology);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean saveOntology() {
		try {
			man.saveOntology(ontology);
			return true;
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	void removeClass(OWLClass owlClass) {
		Set<OWLClassAxiom> allAxiomsForClass = ontology.getAxioms(owlClass, Imports.INCLUDED);
		for (OWLClassAxiom axiom: allAxiomsForClass) {
			System.out.println(axiom);
			ontology.remove(axiom);
			
		}
		OWLDeclarationAxiom delcaration = df.getOWLDeclarationAxiom(owlClass);
		ontology.remove(delcaration);
		saveOntology();
		runReasoner();
	}
	
	
	public void removeIngredient(String ingredient) {
		OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		removeClass(ing);
	}
	
	
	public void removeComponent(String component) {
		OWLClass comp = df.getOWLClass(iri + "#" + component + "Component");
		removeClass(comp);
	}
	
	
	public void removeDish(String dish) {
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		removeClass(dishClass);
	}
	
	
	public void addIngredient(
			String ingredient,
			String ingType,
			String[] allergens
			) {
		OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		OWLClass type = df.getOWLClass(iri + "#" + ingType + "Ingredient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(ing,  type);
		ontology.add(axiom);
		
		for (String allergen : allergens) {
			System.out.println(allergen);
			OWLClass nutrient = df.getOWLClass(iri + "#" + allergen + "Nutrient");
			OWLObjectProperty hasNutrient = df.getOWLObjectProperty(iri + "#hasNutrient");
			OWLSubClassOfAxiom ingHasNutrient = df.getOWLSubClassOfAxiom(ing, df.getOWLObjectSomeValuesFrom(hasNutrient, nutrient));
			System.out.println(ingHasNutrient);
			ontology.add(ingHasNutrient);
		}
		
		saveOntology();
		runReasoner();
	}
	
	
	public void addAllergen(String name) {
		OWLClass nutrient = df.getOWLClass(iri + "#Nutrient");
		OWLClass allergen = df.getOWLClass(iri + "#" + name + "Nutrient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(allergen, nutrient);
		ontology.add(axiom);
		saveOntology();
	}
	
	
	public void addComponent(String component, String[] ingredients) {
		OWLClass genComp = df.getOWLClass(iri + "#Component");
		OWLClass comp = df.getOWLClass(iri + "#" + component + "Component");
		OWLObjectProperty hasIng = df.getOWLObjectProperty(iri + "#hasIngredient");
		
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(comp,  genComp);
		ontology.add(axiom);
		
		List<OWLClass> allIngs = new ArrayList<OWLClass>();
		
		for (String ingredient : ingredients) {
			OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			allIngs.add(ing);
			OWLSubClassOfAxiom compHasIng = df.getOWLSubClassOfAxiom(comp, df.getOWLObjectSomeValuesFrom(hasIng, ing));
			ontology.add(compHasIng);
		}
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allIngs);;
		OWLSubClassOfAxiom compMustHaveIngs = df.getOWLSubClassOfAxiom(comp, df.getOWLObjectAllValuesFrom(hasIng, combination));
		ontology.add(compMustHaveIngs);
		saveOntology();
	}
	
		
	public void addDish(String dishName, String[] components, String[] ingredients, boolean halal, boolean kosher) {
		OWLClass genDish = df.getOWLClass(iri + "#NamedDish");
		OWLClass dish = df.getOWLClass(iri + "#" + dishName + "Dish");
		OWLObjectProperty hasComp = df.getOWLObjectProperty(iri + "#hasComponent");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(dish,  genDish);
		ontology.add(axiom);
		
		List<OWLClass> allComps = new ArrayList<OWLClass>();
		for (String component : components) {
			OWLClass comp = df.getOWLClass(iri + "#" + component + "Component");
			allComps.add(comp);
			OWLSubClassOfAxiom dishHasComp = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasComp, comp));
			ontology.add(dishHasComp);
		}
		for (String ingredient : ingredients) {
			OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			OWLObjectProperty hasIng = df.getOWLObjectProperty(iri + "#hasIngredient");
			OWLSubClassOfAxiom dishHasIng = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasIng, ing));
			ontology.add(dishHasIng);
		}
		
		if (halal) {
			OWLClass halalMethod = df.getOWLClass(iri + "#HalalPreparationMethod");
			OWLObjectProperty hasMethod = df.getOWLObjectProperty(iri + "#preparedUsingMethod");
			OWLSubClassOfAxiom dishIsHalal = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasMethod, halalMethod));
			ontology.add(dishIsHalal);
		}
		
		if (kosher) {
			OWLClass kosherMethod = df.getOWLClass(iri + "#KosherPreparationMethod");
			OWLObjectProperty hasMethod = df.getOWLObjectProperty(iri + "#preparedUsingMethod");
			OWLSubClassOfAxiom dishIsKosher = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasMethod, kosherMethod));
			ontology.add(dishIsKosher);
		}
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allComps);;
		OWLSubClassOfAxiom dishMustHaveComps = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectAllValuesFrom(hasComp, combination));
		ontology.add(dishMustHaveComps);
		
		saveOntology();
	}
	

	public List<String> getAllDishNames() {
		List<String> dishNames = new ArrayList<>();
		List<Node<OWLClass>> dishes = new ArrayList<Node<OWLClass>>();
		
		reasoner.getSubClasses(df.getOWLClass(iri + "#NamedDish"), false).forEach(dishes::add);

		for(Node<OWLClass> dish: dishes) {
			if (dish.entities().findFirst().get().getIRI().getShortForm().equals("Nothing")) {
				continue;
			}
			dishNames.add(dish.entities().findFirst().get().getIRI().getShortForm().replace("Dish", ""));

		}
		return dishNames;
	}


	List<String> getCustomerTypes() {
		ArrayList<Node<OWLClass>> customers = new ArrayList<Node<OWLClass>>();
		reasoner.getSubClasses(df.getOWLClass(iri + "#Customer"), false).forEach(customers::add);
		ArrayList<String> customerTypes = new ArrayList<>();
		for(Node<OWLClass> customer: customers) {
			if (customer.entities().findFirst().get().getIRI().getShortForm().equals("Nothing")) {
				continue;
			}
			customerTypes.add(customer.entities().findFirst().get().getIRI().getShortForm());
		}
		return customerTypes;
	}
	
	
	public List<String> getAllAllergenNames() {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Node<OWLClass>> allergens = new ArrayList<Node<OWLClass>>();
		reasoner.getSubClasses(df.getOWLClass(iri + "#Nutrient"), false).forEach(allergens::add);
		
		for(Node<OWLClass> comp: allergens) {
			String name = comp.entities().findFirst().get().getIRI().getShortForm().replace("Nutrient", "");
			if (name.equals("Nothing")) {
				continue;
			}
			names.add(name);
		}
		return names;
	}
	
	
	public List<String> getIngredientNamesOfType(IngredientType type) {
		ArrayList<Node<OWLClass>> ingredients = new ArrayList<Node<OWLClass>>();	
		ArrayList<String> names = new ArrayList<String>();
		reasoner.getSubClasses(df.getOWLClass(iri + "#" + type + "Ingredient"), false).forEach(ingredients::add);
		for(Node<OWLClass> ing: ingredients) {
			String name = ing.entities().findFirst().get().getIRI().getShortForm().replace("Ingredient", "");
			if (name.equals("Nothing")) {
				continue;
			}
			names.add(name);
		}
		return names;
	}
	
	public List<String> getIngredientTypeNames() {
		ArrayList<String> names = new ArrayList<String>();
		List<Node<OWLClass>> types = getIngredientTypes();
		for(Node<OWLClass> type: types) {
			String name = type.entities().findFirst().get().getIRI().getShortForm().replace("Ingredient", "");
			if (name.equals("Nothing")) {
				continue;
			}
			names.add(name);
		}
		
		return names;
	}
	
	List<Node<OWLClass>> getIngredientTypes() {
		ArrayList<Node<OWLClass>> ingredientTypes = new ArrayList<Node<OWLClass>>();	
		reasoner.getSubClasses(df.getOWLClass(iri + "#Ingredient"), true).forEach(ingredientTypes::add);
		return ingredientTypes;
	}
	
	
	public List<String> getAllIngredientNames() {
		List<Node<OWLClass>> types = getIngredientTypes();
		ArrayList<String> names = new ArrayList<String>();
		for(Node<OWLClass> ingType: types) {
			ArrayList<Node<OWLClass>> ingredients = new ArrayList<Node<OWLClass>>();
			reasoner.getSubClasses(df.getOWLClass(ingType.entities().findFirst().get().getIRI()), false).forEach(ingredients::add);
			
			for(Node<OWLClass> ing: ingredients) {
				String name = ing.entities().findFirst().get().getIRI().getShortForm().replace("Ingredient", "");
				if (name.equals("Nothing")) {
					continue;
				}
				names.add(name);
			}
		}
		return names;
	}
	
	
	public List<String> getAllComponentNames() {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Node<OWLClass>> components = new ArrayList<Node<OWLClass>>();
		reasoner.getSubClasses(df.getOWLClass(iri + "#Component"), false).forEach(components::add);
		
		for(Node<OWLClass> comp: components) {
			
			String name = comp.entities().findFirst().get().getIRI().getShortForm().replace("Component", "");
			List<String> invalid_names = Arrays.asList("Nothing", "Vegan", "Pescetarian", "Vegetarian");
			if (invalid_names.contains(name)) {
				continue;
			}
			names.add(name);
		}
		return names;
	}
	
	
	boolean runReasoner() {
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		return reasoner.isConsistent();
	}
}
