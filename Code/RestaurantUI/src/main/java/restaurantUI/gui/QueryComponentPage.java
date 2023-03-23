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
	DefaultListModel<String> list;
	
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
	    JLabel allComponentsText = new JLabel("All Components:");
	    allComponentsPanel.add(allComponentsText, BorderLayout.NORTH);
	    List<String> allComps = ui.om.getAllComponentNames();	    
	    list = new DefaultListModel<String>();
	    for(String comp: allComps) {
	    	list.addElement(comp);
	    }
	    JList<String> allComponents = new JList<String>(list);
	    allComponents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    allComponents.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		    	EditComponentPage editComp = new EditComponentPage(ui, allComponents.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
	            ui.SwitchToFrame(editComp, allComponents.getSelectedValue().replace("<html><u>", "").replace("</u></html>", ""));
		    }
		    
		    public void mouseExited(MouseEvent evt) {
		    	for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
		    }
		});
		
		allComponents.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent me) {
				Point p = new Point(me.getX(), me.getY());
				for(int i=0; i<list.getSize(); i++) {
					list.set(i, list.get(i).replace("<html><u>", "").replace("</u></html>", ""));
				}
				list.set(allComponents.locationToIndex(p), "<html><u>" + list.get(allComponents.locationToIndex(p)) + "</u></html>");
			}
		});
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
		list.removeAllElements();
		List<String> allComps = ui.om.getAllComponentNames();
	    for(String comp: allComps) {
	    	System.out.println(comp);
	    	list.addElement(comp);
	    }
	    
	    revalidate();
		repaint();
	}

	
}
