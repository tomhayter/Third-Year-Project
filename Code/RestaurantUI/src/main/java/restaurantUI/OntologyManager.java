package restaurantUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
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
	
	void addIngredient(
			String ingredient,
			IngredientType ingType,
			String[] allergens,
			boolean isSpicy
			) {
		OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		OWLClass type = df.getOWLClass(iri + "#" + ingType + "Ingredient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(ing,  type);
		ontology.add(axiom);
		saveOntology();
	}
	
	void addIngredient(String ingredient, String ingType) {
		OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
		OWLClass type = df.getOWLClass(iri + "#" + ingType + "Ingredient");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(ing,  type);
		ontology.add(axiom);
		saveOntology();
	}
	
	void addComponent(String component, String[] ingredients) {
		OWLClass genComp = df.getOWLClass(iri + "#Component");
		OWLClass comp = df.getOWLClass(iri + "#" + component + "Component");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(comp,  genComp);
		ontology.add(axiom);
		for (String ingredient : ingredients) {
			OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			OWLObjectProperty hasIng = df.getOWLObjectProperty(iri + "#hasIngredient");
			OWLSubClassOfAxiom compHasIng = df.getOWLSubClassOfAxiom(comp, df.getOWLObjectSomeValuesFrom(hasIng, ing));
			ontology.add(compHasIng);
		}
		saveOntology();
	}
	
	void addDish(String dishName, String[] components, String[] ingredients) {
		OWLClass genDish = df.getOWLClass(iri + "#Dish");
		OWLClass dish = df.getOWLClass(iri + "#" + dishName + "Dish");
		OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(dish,  genDish);
		ontology.add(axiom);
		for (String component : components) {
			OWLClass comp = df.getOWLClass(iri + "#" + component + "Component");
			OWLObjectProperty hasComp = df.getOWLObjectProperty(iri + "#hasComponent");
			OWLSubClassOfAxiom dishHasComp = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasComp, comp));
			ontology.add(dishHasComp);
		}
		for (String ingredient : ingredients) {
			OWLClass ing = df.getOWLClass(iri + "#" + ingredient + "Ingredient");
			OWLObjectProperty hasIng = df.getOWLObjectProperty(iri + "#hasIngredient");
			OWLSubClassOfAxiom dishHasIng = df.getOWLSubClassOfAxiom(dish, df.getOWLObjectSomeValuesFrom(hasIng, ing));
			ontology.add(dishHasIng);
		}
		saveOntology();
	}
	
	
	List<Node<OWLClass>> getDishes() {
		
		ArrayList<Node<OWLClass>> dishes = new ArrayList<Node<OWLClass>>();
//		ontology.signature().filter((e->!e.isBuiltIn() && 
//									 e.getIRI().getRemainder().orElse("").contains("Dish") &&
//									 !e.getIRI().getRemainder().orElse("").equals("Dish")))
//			.forEach(dishes::add);
		
		reasoner.getSubClasses(df.getOWLClass(iri + "#Dish"), false).forEach(dishes::add);
//		reasoner.getSubClass
		
		return dishes;
	}
	
	List<OWLEntity> getEntities(String search) {
		ArrayList<OWLEntity> entities = new ArrayList<OWLEntity>();
		ontology.signature().filter((e->!e.isBuiltIn() && 
									 e.getIRI().getRemainder().orElse("").contains(search)))
			.forEach(entities::add);
		
		return entities;
	}
	
	Set<OWLAxiom> getAllAxioms() {
		return ontology.getAxioms();
	}
	
	boolean runReasoner() {
		reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		return reasoner.isConsistent();
	}
	
	void test() {
		reasoner.getSubClasses(df.getOWLClass(iri + "#Dish"), false).forEach(System.out::println);
	}
}
