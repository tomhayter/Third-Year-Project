package restaurantUI.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class QueryComponentPage extends JPanel {
	
	UI ui;
	final static String CARD = "QueryComponentPage";
	DefaultListModel<String> allList;
	
	public QueryComponentPage(UI ui) {


		this.ui = ui;
		
		setName("Query Component");
		setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Search Components");
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
	    JPanel allComponentsPanel = new JPanel(new BorderLayout());
	    JLabel allComponentsText = new JLabel("Components");
	    allComponentsPanel.add(allComponentsText, BorderLayout.NORTH);
	    List<String> allComps = ui.om.getAllComponentNames();	    
	    allList = new DefaultListModel<String>();
	    for(String comp: allComps) {
	    	allList.addElement(comp);
	    }
	    JList<String> allComponents = new JList<String>(allList);
	    allComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane allScroll = new JScrollPane();
	    allScroll.setViewportView(allComponents);
	    allComponentsPanel.add(allScroll, BorderLayout.CENTER);
	    contentsPanel.add(allComponentsPanel, gbc);
	    
	    
	    gbc.gridx = 1;
	    gbc.gridy = 1;
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
		allList.removeAllElements();
		List<String> allComps = ui.om.getAllComponentNames();
	    for(String comp: allComps) {
	    	allList.addElement(comp);
	    }
	}

	
}
