package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class QueryIngredientPage extends JPanel {

	UI ui;
	final static String CARD = "QueryIngredientPage";
	DefaultListModel<String> list;
	
	public QueryIngredientPage(UI ui) {


		this.ui = ui;
		
		setName("Query Ingredients");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Search Ingredients");
	    title.setFont(new Font("Calibri", Font.BOLD, 24));
	    JPanel titlePanel = new JPanel();
	    titlePanel.add(title, BorderLayout.CENTER);
	    add(titlePanel, BorderLayout.NORTH);
	    
	    JPanel contentsPanel = new JPanel(new GridBagLayout());
	    contentsPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
	    GridBagConstraints gbc = new GridBagConstraints();
	    Insets i = new Insets(0, 0, 16, 0);
	    gbc.insets = i;
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    
	    gbc.gridx = 1;
	    gbc.gridy = 0;
	    JPanel typePanel = new JPanel(new BorderLayout());
	    JLabel typeText = new JLabel("Filter by ingredient type:");
	    typePanel.add(typeText, BorderLayout.NORTH);
		List<String> ingTypesList = ui.om.getIngredientTypeNames();
		ingTypesList.add(0, "All");
		
        String[] ingTypesArray = new String[ingTypesList.size()];
        ingTypesArray = ingTypesList.toArray(ingTypesArray);
        JComboBox<String> ingTypes = new JComboBox<String>(ingTypesArray);
        ingTypes.setSelectedItem("All");
        typePanel.add(ingTypes, BorderLayout.CENTER);
        contentsPanel.add(typePanel, gbc);
        ingTypes.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String selection = ingTypes.getSelectedItem().toString();
        		List<String> ings = new ArrayList<String>();
        		if (selection.equals("All")) {
        			ings = ui.om.getAllIngredientNames();
        		}
        		else {
        			ings = ui.om.getIngredientNamesOfType(selection);
        		}
        		list.removeAllElements();
        		for(String item: ings) {
        			list.addElement(item);
        		}
        	}
        });
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
	    JPanel allIngredientsPanel = new JPanel(new BorderLayout());
	    JLabel allIngredientsText = new JLabel("Ingredients");
	    allIngredientsPanel.add(allIngredientsText, BorderLayout.NORTH);
	    List<String> allIngs = ui.om.getAllIngredientNames();	    
	    list = new DefaultListModel<String>();
	    for(String ing: allIngs) {
	    	list.addElement(ing);
	    }
	    JList<String> allIngredients = new JList<String>(list);
	    allIngredients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    allIngredients.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	EditIngredientPage editIng = new EditIngredientPage(ui, allIngredients.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
	            ui.SwitchToFrame(editIng, allIngredients.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
		    }
		    
		    public void mouseExited(MouseEvent evt) {
		    	for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
		    }
		});
	    
	    allIngredients.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent me) {
				Point p = new Point(me.getX(), me.getY());
				for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
				list.set(allIngredients.locationToIndex(p), "<html><u>" + list.get(allIngredients.locationToIndex(p)) + "</u></html>");
			}
		});
	    JScrollPane allScroll = new JScrollPane();
	    allScroll.setViewportView(allIngredients);
	    allIngredientsPanel.add(allScroll, BorderLayout.CENTER);
	    contentsPanel.add(allIngredientsPanel, gbc);
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 2;
	    JButton backButton = new JButton("Back");
	    backButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            ui.SwitchToFrame(QueryPage.CARD);
	            
	        }
	    });
	    contentsPanel.add(backButton, gbc);
	    
	    add(contentsPanel, BorderLayout.CENTER);
	}
	
	void reload() {
		list.removeAllElements();
		List<String> allIngs = ui.om.getAllIngredientNames();
	    for(String ing: allIngs) {
	    	list.addElement(ing);
	    }
	}

	
}
