package customerUI;

import	customerUI.gui.*;

public class CustomerUI {

    public static void main(String[] args) {
    	OntologyManager manager = new OntologyManager();
    	
        UI ui = new UI(manager);
        ui.Start();
    }
}
