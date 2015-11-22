package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SearchResultsGUI extends JPanel {
	private Vector<User> users;
	private Vector<Quote> quotes;
	
	private Vector<UserResultGUI> userResults;
	private Vector<QuoteGUI> quoteResults;
	
	private MainPanel mp;
	
	private JPanel outerPanel;

	public SearchResultsGUI(Vector<User> u, Vector<Quote> q, MainPanel mp) {
		users = u;
		quotes = q;
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
		
		Quote q = new Quote("QUOTE quote fuck", users.elementAt(0), users.elementAt(0), new Date(0), 0);
		QuoteGUI quote = new QuoteGUI(mp, q);
		quoteResults.add(quote);
	}

	private void createGUI() {
		int numberRows = userResults.size() + quoteResults.size();
		setLayout(new GridLayout(numberRows, 1));
		for (UserResultGUI u : userResults) {
			add(u);
		}
		for (QuoteGUI q : quoteResults) {
			add(q);
		}
	}
}