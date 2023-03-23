package restaurantUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
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
import org.semanticweb.owlapi.util.OWLEntityRenamer;


public class OntologyManager {
	
	private OWLOntologyManager man;
	private OWLOntology ontology;
	private OWLDataFactory df;
	private IRI iri;
	private String ontologyLocation = "Menu.owl";
	private OWLReasoner reasoner;
	private List<String> invalid_names = Arrays.asList("Nothing", "Vegan", "Pescetarian", "Vegetarian");
	
	
	public OntologyManager() {
		man = OWLManager.createOWLOntologyManager();
		try {
			File file = new File(ontologyLocation);
			ontology = man.loadOntologyFromOntologyDocument(file);
			df = ontology.getOWLOntologyManager().getOWLDataFactory();
			iri = ontology.getOntologyID().getOntologyIRI().get();
			
			ReasonerFactory reasonerFactory = new ReasonerFactory();
			reasoner = reasonerFactory.createReasoner(ontology);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean saveOntology() {
		try {
			man.saveOntology(ontology);
			return true;
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public String getNameFromClass(OWLClass clss, String appendix) {
		return clss.getIRI().getShortForm().replace(appendix, "");
	}
	
	
	void removeClass(OWLClass owlClass) {
		Set<OWLClassAxiom> allAxiomsForClass = new HashSet<OWLClassAxiom>();
		ontology.axioms(owlClass, Imports.INCLUDED).forEach(allAxiomsForClass::add);;
		for (OWLClassAxiom axiom: allAxiomsForClass) {
			ontology.remove(axiom);
			
		}
		OWLDeclarationAxiom delcaration = df.getOWLDeclarationAxiom(owlClass);
		ontology.remove(delcaration);
		saveOntology();
		runReasoner();
	}
	
	
	public void removeAllergen(String allergen) {
		OWLClass allergenClass = df.getOWLClass(iri + "#" + allergen + "Nutrient");
		removeClass(allergenClass);
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
			String[] allergens,
			int calories
			) {
		OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		OWLClass type = df.getOWLClass(iri + "#" + ingType + "Ingredient");
		OWLObjectProperty hasNutrient = df.getOWLObjectProperty(iri + "#hasNutrient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(ing,  type);
		ontology.add(axiom);
		
		List<OWLClass> allAllergens = new ArrayList<OWLClass>();
		for (String allergen : allergens) {
			OWLClass nutrient = df.getOWLClass(iri + "#" + allergen + "Nutrient");
			allAllergens.add(nutrient);
			OWLSubClassOfAxiom ingHasNutrient = df.getOWLSubClassOfAxiom(ing, df.getOWLObjectSomeValuesFrom(hasNutrient, nutrient));
			ontology.add(ingHasNutrient);
		}
		OWLClassExpression combination = df.getOWLObjectUnionOf(allAllergens);
		OWLSubClassOfAxiom ingMustHaveAllergens = df.getOWLSubClassOfAxiom(ing, df.getOWLObjectAllValuesFrom(hasNutrient, combination));
		ontology.add(ingMustHaveAllergens);
		
		OWLDataProperty hasCalories = df.getOWLDataProperty(iri + "#hasCalories");
		OWLLiteral literal = df.getOWLLiteral(calories);
		OWLSubClassOfAxiom ingHasCalories = df.getOWLSubClassOfAxiom(ing, df.getOWLDataHasValue(hasCalories, literal));
		ontology.add(ingHasCalories);
		
		saveOntology();
		runReasoner();
	}
	
	
	public void addAllergen(String name) {
		OWLClass nutrient = df.getOWLClass(iri + "#Nutrient");
		OWLClass allergen = df.getOWLClass(iri + "#" + name + "Nutrient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(allergen, nutrient);
		ontology.add(axiom);
		saveOntology();
		runReasoner();
	}
	
	
	public void addComponent(String component, List<String> ingredients) {
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
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allIngs);
		OWLSubClassOfAxiom compMustHaveIngs = df.getOWLSubClassOfAxiom(comp, df.getOWLObjectAllValuesFrom(hasIng, combination));
		ontology.add(compMustHaveIngs);
		saveOntology();
		runReasoner();
	}
	
		
	public void addDish(
			String dishName,
			List<String> components,
			boolean halal,
			boolean kosher,
			boolean glutenFree) {
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
		
		OWLDataProperty hasGFOption = df.getOWLDataProperty(iri + "#hasGlutenFreeOption");
		OWLLiteral literal = df.getOWLLiteral(glutenFree);
		OWLSubClassOfAxiom dishHasGFOption = df.getOWLSubClassOfAxiom(dish, df.getOWLDataHasValue(hasGFOption, literal));
		ontology.add(dishHasGFOption);
		
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allComps);;
		OWLSubClassOfAxiom dishMustHaveComps = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectAllValuesFrom(hasComp, combination));
		ontology.add(dishMustHaveComps);
		
		saveOntology();
		runReasoner();
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
		Collections.sort(dishNames);
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
		runReasoner();
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
		Collections.sort(names);
		return names;
	}
	
	
	public List<String> getIngredientNamesOfType(String type) {
		runReasoner();
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
		Collections.sort(names);
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
		Collections.sort(names);
		return names;
	}
	
	List<Node<OWLClass>> getIngredientTypes() {
		runReasoner();
		ArrayList<Node<OWLClass>> ingredientTypes = new ArrayList<Node<OWLClass>>();	
		reasoner.getSubClasses(df.getOWLClass(iri + "#Ingredient"), true).forEach(ingredientTypes::add);
		return ingredientTypes;
	}
	
	
	public List<String> getAllIngredientNames() {
		runReasoner();
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
		Collections.sort(names);
		return names;
	}
	
	
	public List<String> getAllComponentNames() {
		runReasoner();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Node<OWLClass>> components = new ArrayList<Node<OWLClass>>();
		reasoner.getSubClasses(df.getOWLClass(iri + "#Component"), false).forEach(components::add);
		
		for(Node<OWLClass> comp: components) {
			
			String name = comp.entities().findFirst().get().getIRI().getShortForm().replace("Component", "");
			
			if (invalid_names.contains(name)) {
				continue;
			}
			names.add(name);
		}
		Collections.sort(names);
		return names;
	}
	
	
	public List<String> getAllergensForEditedDish(List<String> ingredients) {
		List<OWLClass> allergens = new ArrayList<OWLClass>();
		OWLObjectProperty hasAllergen = df.getOWLObjectProperty(iri + "#hasNutrient");
		Set<String> allergenNamesInDish = new HashSet<String>();
		
		for (String ingredient: ingredients) {
			OWLClass ingClass = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			
			Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
			ontology.subClassAxiomsForSubClass(ingClass).forEach(allAxiomsForClass::add);;
			for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
				OWLClassExpression exp = ax.getSuperClass();
				Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
				exp.objectPropertiesInSignature().forEach(objectProperties::add);
				if (!objectProperties.contains(hasAllergen)) {
					continue;
				}
				if (!exp.toString().contains("ObjectAllValuesFrom")) {
					continue;
				}
				exp.classesInSignature().forEach(allergens::add);		
			}
			for(OWLClass ing: allergens) {
				allergenNamesInDish.add(getNameFromClass(ing, "Nutrient"));
			}
		}
		return new ArrayList<String>(allergenNamesInDish);
	}
	
	
	public List<String> getSuitableDietsForDish(String dish) {
		runReasoner();
		List<String> suitableDiets = new ArrayList<String>();
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		
		if (reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLClass(iri + "#VegetarianDish")))) {
			suitableDiets.add("Vegetarian");
		}
		
		if (reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLClass(iri + "#VeganDish")))) {
			suitableDiets.add("Vegan");
		}
		
		if (reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLClass(iri + "#PescetarianDish")))) {
			suitableDiets.add("Pescetarian");
		}
		
		if (reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLClass(iri + "#KosherDish")))) {
			suitableDiets.add("Kosher");
		}
		
		if (reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLClass(iri + "#HalalDish")))) {
			suitableDiets.add("Halal");
		}
		return suitableDiets;
	}
	
	
	public List<String> getAllergensInDish(String dish) {
		runReasoner();
		List<String> allAllergens = getAllAllergenNames();
		List<String> allergensInDish = new ArrayList<String>();
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		OWLObjectProperty hasPart = df.getOWLObjectProperty(iri + "#hasPart");
		for (String a: allAllergens) {
			OWLClass allergen = df.getOWLClass(iri + "#" + a +  "Nutrient");
			ArrayList<OWLClass> equivalents = new ArrayList<OWLClass>();
			reasoner.getEquivalentClasses(df.getOWLObjectIntersectionOf(dishClass, df.getOWLObjectSomeValuesFrom(hasPart, allergen))).forEach(equivalents::add);
			if (equivalents.contains(dishClass)) {
				allergensInDish.add(a);
			}
		}
		return allergensInDish;
	}
	
	
	public List<String> getAllergensInIngredient(String ingredient) {
		List<OWLClass> allergensInIng = new ArrayList<OWLClass>();
		OWLClass ingClass = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		OWLObjectProperty hasAllergen = df.getOWLObjectProperty(iri + "#hasNutrient");
		
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		ontology.subClassAxiomsForSubClass(ingClass).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
			exp.objectPropertiesInSignature().forEach(objectProperties::add);
			if (!objectProperties.contains(hasAllergen)) {
				continue;
			}
			if (!exp.toString().contains("ObjectAllValuesFrom")) {
				continue;
			}

			exp.classesInSignature().forEach(allergensInIng::add);		
		}
		List<String> allergenNamesInDish = new ArrayList<String>();
		for(OWLClass allergen: allergensInIng) {
			allergenNamesInDish.add(getNameFromClass(allergen, "Nutrient"));
		}
		Collections.sort(allergenNamesInDish);
		return allergenNamesInDish;
	}

	
	public List<String> getIngredientsInComponent(String component) {
		List<OWLClass> ingsInComp = new ArrayList<OWLClass>();
		OWLClass compClass = df.getOWLClass(iri + "#" + component + "Component");
		OWLObjectProperty hasIngredient = df.getOWLObjectProperty(iri + "#hasIngredient");
		
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		ontology.subClassAxiomsForSubClass(compClass).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
			exp.objectPropertiesInSignature().forEach(objectProperties::add);
			if (!objectProperties.contains(hasIngredient)) {
				continue;
			}
			if (!exp.toString().contains("ObjectAllValuesFrom")) {
				continue;
			}

			exp.classesInSignature().forEach(ingsInComp::add);		
		}
		List<String> ingNamesInDish = new ArrayList<String>();
		for(OWLClass ing: ingsInComp) {
			ingNamesInDish.add(getNameFromClass(ing, "Ingredient"));
		}
		Collections.sort(ingNamesInDish);
		return ingNamesInDish;
	}
	
	
	public List<String> getComponentsInDish(String dish) {
		List<OWLClass> compsInDish = new ArrayList<OWLClass>();
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		OWLObjectProperty hasComponent = df.getOWLObjectProperty(iri + "#hasComponent");
		
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		ontology.subClassAxiomsForSubClass(dishClass).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
			exp.objectPropertiesInSignature().forEach(objectProperties::add);
			if (!objectProperties.contains(hasComponent)) {
				continue;
			}
			if (!exp.toString().contains("ObjectAllValuesFrom")) {
				continue;
			}

			exp.classesInSignature().forEach(compsInDish::add);		
		}
		List<String> compNamesInDish = new ArrayList<String>();
		for(OWLClass ing: compsInDish) {
			compNamesInDish.add(getNameFromClass(ing, "Component"));
		}
		Collections.sort(compNamesInDish);
		return compNamesInDish;
	}
	
	
	public List<String> getIngredientsInDish(String dish) {
		List<OWLClass> ingsInDish = new ArrayList<OWLClass>();
		OWLObjectProperty hasIngredient = df.getOWLObjectProperty(iri + "#hasIngredient");

		List<String> compsInDish = getComponentsInDish(dish);
		for (String comp: compsInDish) {
			OWLClass compClass = df.getOWLClass(iri + "#" + comp + "Component");
			Set<OWLSubClassOfAxiom> allAxiomsForComp = new HashSet<OWLSubClassOfAxiom>();
			ontology.subClassAxiomsForSubClass(compClass).forEach(allAxiomsForComp::add);
			for(OWLSubClassOfAxiom compAx: allAxiomsForComp) {
				OWLClassExpression compExp = compAx.getSuperClass();
				Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
				compExp.objectPropertiesInSignature().forEach(objectProperties::add);
				if (!objectProperties.contains(hasIngredient)) {
					continue;
				}
				if (!compExp.toString().contains("ObjectAllValuesFrom")) {
					continue;
				}
				compExp.classesInSignature().forEach(ingsInDish::add);
			}
		}
		List<String> ingNamesInDish = new ArrayList<String>();
		for(OWLClass ing: ingsInDish) {
			ingNamesInDish.add(getNameFromClass(ing, "Ingredient"));
		}
		Collections.sort(ingNamesInDish);
		
		return ingNamesInDish;
	}
	
	
	public int getCaloriesInDish(String dish) {
		List<String> ingsInDish = getIngredientsInDish(dish);
		return getCaloriesInIngredients(ingsInDish);
	}
	
	
	public int getCaloriesInIngredients(List<String> ingredients) {
		int totalCalories = 0;
		OWLDataProperty hasCalories = df.getOWLDataProperty(iri + "#hasCalories");
		for (String ing: ingredients) {
			Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
			
			ontology.subClassAxiomsForSubClass(df.getOWLClass(iri + "#" + ing + "Ingredient")).forEach(allAxiomsForClass::add);;
			for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
				OWLClassExpression exp = ax.getSuperClass();
				Set<OWLDataProperty> dataProperties = new HashSet<OWLDataProperty>();
				exp.dataPropertiesInSignature().forEach(dataProperties::add);
				if (!dataProperties.contains(hasCalories)) {
					continue;
				}
				Pattern pattern = Pattern.compile("\\\"[0-9]+\\\"", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(exp.toString());
				int cals = 0;
				if(matcher.find()) {
					cals = Integer.valueOf(matcher.group(0).replace("\"", ""));
				}
				totalCalories += cals;
			}
		}
		
		return totalCalories;
	}
	
	
	public boolean dishHasGlutenFreeOption(String dish) {
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		OWLDataProperty hasGFOption = df.getOWLDataProperty(iri + "#hasGlutenFreeOption");
		
		ontology.subClassAxiomsForSubClass(df.getOWLClass(iri + "#" + dish + "Dish")).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLDataProperty> dataProperties = new HashSet<OWLDataProperty>();
			exp.dataPropertiesInSignature().forEach(dataProperties::add);
			if (!dataProperties.contains(hasGFOption)) {
				continue;
			}
			Pattern pattern = Pattern.compile("true", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(exp.toString());
			if(matcher.find()) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean dishIsHalal(String dish) {
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		OWLClass halalClass = df.getOWLClass(iri + "#HalalPreparationMethod");
		OWLObjectProperty prepMethod = df.getOWLObjectProperty(iri + "#preparedUsingMethod");
		return reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLObjectSomeValuesFrom(prepMethod, halalClass)));
	}
	
	
	public boolean dishIsKosher(String dish) {
		OWLClass dishClass = df.getOWLClass(iri + "#" + dish + "Dish");
		OWLClass kosherClass = df.getOWLClass(iri + "#KosherPreparationMethod");
		OWLObjectProperty prepMethod = df.getOWLObjectProperty(iri + "#preparedUsingMethod");
		return reasoner.isEntailed(df.getOWLSubClassOfAxiom(dishClass, df.getOWLObjectSomeValuesFrom(prepMethod, kosherClass)));
	}
	
	
	public List<String> dishSearch(
			List<String> allergens,
			boolean vegetarian,
			boolean vegan,
			boolean kosher,
			boolean halal,
			boolean glutenFree,
			int maxCalories) 
	{
		runReasoner();
		HashSet<String> allDishes = new HashSet<String>(getAllDishNames());
		
		List<HashSet<String>> allowedDishGroups = new ArrayList<HashSet<String>>();
		List<HashSet<String>> notAllowedDishGroups = new ArrayList<HashSet<String>>();
		
		
		if (vegetarian) {
			ArrayList<Node<OWLClass>> vegeDishNodes = new ArrayList<Node<OWLClass>>();
			reasoner.getSubClasses(df.getOWLClass(iri + "#VegetarianDish"), false).forEach(vegeDishNodes::add);
			HashSet<String> vegeDishes = new HashSet<String>();
			for(Node<OWLClass> dish: vegeDishNodes) {
				String name = dish.entities().findFirst().get().getIRI().getShortForm().replace("Dish", "");
				if (invalid_names.contains(name)) {
					continue;
				}
				vegeDishes.add(name);
			}
			allowedDishGroups.add(vegeDishes);
		}
		
		
		if (vegan) {
			ArrayList<Node<OWLClass>> veganDishNodes = new ArrayList<Node<OWLClass>>();
			reasoner.getSubClasses(df.getOWLClass(iri + "#VeganDish"), false).forEach(veganDishNodes::add);
			HashSet<String> veganDishes = new HashSet<String>();
			for(Node<OWLClass> dish: veganDishNodes) {
				String name = dish.entities().findFirst().get().getIRI().getShortForm().replace("Dish", "");
				if (invalid_names.contains(name)) {
					continue;
				}
				veganDishes.add(name);
			}
			allowedDishGroups.add(veganDishes);
		}
		
		
		if (kosher) {
			ArrayList<Node<OWLClass>> kosherDishNodes = new ArrayList<Node<OWLClass>>();
			reasoner.getSubClasses(df.getOWLClass(iri + "#KosherDish"), false).forEach(kosherDishNodes::add);
			HashSet<String> kosherDishes = new HashSet<String>();
			for(Node<OWLClass> dish: kosherDishNodes) {
				String name = dish.entities().findFirst().get().getIRI().getShortForm().replace("Dish", "");
				if (invalid_names.contains(name)) {
					continue;
				}
				kosherDishes.add(name);
			}
			allowedDishGroups.add(kosherDishes);
		}
		
		
		if (halal) {
			ArrayList<Node<OWLClass>> halalDishNodes = new ArrayList<Node<OWLClass>>();
			reasoner.getSubClasses(df.getOWLClass(iri + "#HalalDish"), false).forEach(halalDishNodes::add);
			HashSet<String> halalDishes = new HashSet<String>();
			for(Node<OWLClass> dish: halalDishNodes) {
				String name = dish.entities().findFirst().get().getIRI().getShortForm().replace("Dish", "");
				if (invalid_names.contains(name)) {
					continue;
				}
				halalDishes.add(name);
			}
			allowedDishGroups.add(halalDishes);
		}
		
		
		for(String allergen: allergens) {
			OWLClass allergenClass = df.getOWLClass(iri + "#" + allergen + "Nutrient");
			OWLClass dishClass = df.getOWLClass(iri + "#Dish");
			OWLObjectProperty hasPart = df.getOWLObjectProperty(iri + "#hasPart");
			ArrayList<Node<OWLClass>> allowedClasses = new ArrayList<Node<OWLClass>>();
			HashSet<String> containingDishes = new HashSet<String>();
			reasoner.getSubClasses(df.getOWLObjectIntersectionOf(dishClass, df.getOWLObjectSomeValuesFrom(hasPart, allergenClass))).forEach(allowedClasses::add);
			for(Node<OWLClass> allowedClass: allowedClasses) {
				String name = allowedClass.entities().findFirst().get().getIRI().getShortForm().replace("Dish", "");
				if (invalid_names.contains(name)) {
					continue;
				}
				containingDishes.add(name);
			}
			notAllowedDishGroups.add(containingDishes);
		}
		
		
		HashSet<String> lowCalDishes = new HashSet<String>();
		for(String dish: allDishes) {
			int calories = getCaloriesInDish(dish);
			if (calories <= maxCalories) {
				lowCalDishes.add(dish);
			}
		}
		allowedDishGroups.add(lowCalDishes);
		
		
		if (glutenFree) {
			HashSet<String> gfDishes = new HashSet<String>();
			for(String dish: allDishes) {
				if (dishHasGlutenFreeOption(dish)) {
					gfDishes.add(dish);
				}
			}
			allowedDishGroups.add(gfDishes);
		}
		
		
		
		for(HashSet<String> group: allowedDishGroups) {
			allDishes.retainAll(group);
		}
		for(HashSet<String> group: notAllowedDishGroups) {
			allDishes.removeAll(group);
		}
		List<String> result = new ArrayList<String>(allDishes);
		Collections.sort(result);
		return result;
	}
	
	
	public void updateIngredient(
			String oldName,
			String newName,
			String[] newAllergens,
			int calories) {
		
		OWLClass oldIng = df.getOWLClass(iri + "#" + oldName + "Ingredient");
		OWLObjectProperty hasAllergen = df.getOWLObjectProperty(iri + "#hasNutrient");
		OWLDataProperty hasCalories = df.getOWLDataProperty(iri + "#hasCalories");
		
		// Remove old subclass definitions
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		ontology.subClassAxiomsForSubClass(oldIng).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
			exp.objectPropertiesInSignature().forEach(objectProperties::add);
			if (!objectProperties.contains(hasAllergen)) {
				continue;
			}
			ontology.remove(ax);
		}
		
		
		// Add new subclass definitions
		List<OWLClass> allAllergens = new ArrayList<OWLClass>();
		for (String allergen : newAllergens) {
			OWLClass allergenClass = df.getOWLClass(iri + "#" + allergen + "Nutrient");
			allAllergens.add(allergenClass);
			OWLSubClassOfAxiom compHasIng = df.getOWLSubClassOfAxiom(oldIng, df.getOWLObjectSomeValuesFrom(hasAllergen, allergenClass));
			ontology.add(compHasIng);
		}
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allAllergens);
		OWLSubClassOfAxiom compMustHaveIngs = df.getOWLSubClassOfAxiom(oldIng, df.getOWLObjectAllValuesFrom(hasAllergen, combination));
		ontology.add(compMustHaveIngs);
		
		// Delete Calories
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLDataProperty> dataProperties = new HashSet<OWLDataProperty>();
			exp.dataPropertiesInSignature().forEach(dataProperties::add);
			if (!dataProperties.contains(hasCalories)) {
				continue;
			}
			ontology.remove(ax);	
		}
		
		// Add new Calories
		OWLLiteral literal = df.getOWLLiteral(calories);
		OWLSubClassOfAxiom ingHasCalories = df.getOWLSubClassOfAxiom(oldIng, df.getOWLDataHasValue(hasCalories, literal));
		ontology.add(ingHasCalories);
		
		// Rename
		OWLEntityRenamer renamer = new OWLEntityRenamer(man, Collections.singleton(ontology));
		Map<OWLEntity, IRI> entity2IRIMap = new HashMap<>();
		entity2IRIMap.put(oldIng, IRI.create(iri + "#" + newName + "Ingredient"));
		ontology.applyChanges(renamer.changeIRI(entity2IRIMap));
		
		
		saveOntology();
		runReasoner();
		
	}
			
	
	
	public void updateComponent(
			String oldComponentName,
			String newComponentName,
			List<String> newIngredients) {
		
		OWLClass oldComp = df.getOWLClass(iri + "#" + oldComponentName + "Component");
		OWLObjectProperty hasIng = df.getOWLObjectProperty(iri + "#hasIngredient");
		
		// Remove old subclass definitions
		Set<OWLSubClassOfAxiom> allAxiomsForClass = new HashSet<OWLSubClassOfAxiom>();
		ontology.subClassAxiomsForSubClass(oldComp).forEach(allAxiomsForClass::add);;
		for(OWLSubClassOfAxiom ax: allAxiomsForClass) {
			OWLClassExpression exp = ax.getSuperClass();
			Set<OWLObjectProperty> objectProperties = new HashSet<OWLObjectProperty>();
			exp.objectPropertiesInSignature().forEach(objectProperties::add);
			if (!objectProperties.contains(hasIng)) {
				continue;
			}
			ontology.remove(ax);	
		}
		
		
		// Add new subclass definitions
		List<OWLClass> allIngs = new ArrayList<OWLClass>();
		for (String ingredient : newIngredients) {
			OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			allIngs.add(ing);
			OWLSubClassOfAxiom compHasIng = df.getOWLSubClassOfAxiom(oldComp, df.getOWLObjectSomeValuesFrom(hasIng, ing));
			ontology.add(compHasIng);
		}
		
		OWLClassExpression combination = df.getOWLObjectUnionOf(allIngs);
		OWLSubClassOfAxiom compMustHaveIngs = df.getOWLSubClassOfAxiom(oldComp, df.getOWLObjectAllValuesFrom(hasIng, combination));
		ontology.add(compMustHaveIngs);
		
		// Rename
		OWLEntityRenamer renamer = new OWLEntityRenamer(man, Collections.singleton(ontology));
		Map<OWLEntity, IRI> entity2IRIMap = new HashMap<>();
		entity2IRIMap.put(oldComp, IRI.create(iri + "#" + newComponentName + "Component"));
		ontology.applyChanges(renamer.changeIRI(entity2IRIMap));
		
		
		saveOntology();
		runReasoner();
	}
	
	
	
	public void updateDish(
			String oldDishName,
			String newDishName,
			List<String> components,
			boolean halal,
			boolean kosher,
			boolean glutenFree) {
		removeDish(oldDishName);
		addDish(newDishName, components, halal, kosher, glutenFree);
	}
	
	
	
	boolean runReasoner() {
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		reasoner.flush();
		return reasoner.isConsistent();
	}
}
