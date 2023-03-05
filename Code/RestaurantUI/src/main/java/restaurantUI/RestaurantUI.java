package restaurantUI;

import restaurantUI.gui.*;

public class RestaurantUI {

    public static void main(String[] args) {
    	OntologyManager manager = new OntologyManager();
    	
        UI ui = new UI(manager);
        ui.Start();
    }
}
