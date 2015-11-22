package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SearchResultsGUI extends JPanel {
	private Vector<User> users;
	private Vector<Quote> quotes;
	
	private Vector<UserResultGUI> userResults;
	private Vector<QuoteGUI> quoteResults;
	
	private MainPanel mp;
	
	private JPanel outerPanel;
	private JScrollPane jsp;


	public SearchResultsGUI(Vector<User> users, Vector<Quote> quotes, MainPanel mp) {
		this.users = users;
		this.quotes = quotes;
		this.mp = mp;
		initializeComponents();
		createGUI();
	}

	private void initializeComponents() {
		outerPanel = new JPanel();
		userResults = new Vector<UserResultGUI>();
		
		if (users != null) {
			for (User u : users) {
				UserResultGUI userResult = new UserResultGUI(mp, u);
				userResults.add(userResult);
			}
		}
		
		quoteResults = new Vector<QuoteGUI>();
		if (quotes != null) {
			for (Quote q : quotes) {
				QuoteGUI quoteResult = new QuoteGUI(mp, q);
				quoteResults.add(quoteResult);
			}
		}
		
		/*
		if (users.size() != 0) {
			Quote q = new Quote("QUOTE quote fuck", users.elementAt(0), users.elementAt(0), new Date(0), 0);
			QuoteGUI quote = new QuoteGUI(mp, q);
			quoteResults.add(quote);
		}*/
	}

	private void createGUI() {
		JPanel centerPanel = new JPanel();
		JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
		// Add stuff to resultsPanel
		for (UserResultGUI u : userResults) {
			resultsPanel.add(u);
		}
		for (QuoteGUI q : quoteResults) {
			resultsPanel.add(q);
		}
		
		jsp = new JScrollPane(resultsPanel);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel.add(jsp);
		add(centerPanel, BorderLayout.CENTER);
		
//		int numberRows = userResults.size() + quoteResults.size();
//		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));


//		jsp = new JScrollPane(outerPanel);
//		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		add(jsp);
	}
}
