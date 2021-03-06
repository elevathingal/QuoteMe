package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import custom.QuoteMeButton;
import custom.QuoteMeTextField;
import library.ImageLibrary;
import resources.Constants;
import resources.CustomListeners;
import resources.Images;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = -4350754167494644417L;
	private FeedPageGUI feed;
	private ProfilePageGUI profilePage;
	private NotificationPageGUI notifications;
	private WriteQuoteGUI writeQuotePanel;
	
	private QuoteMeTextField searchField;
	private QuoteMeButton notificationButton, writeQuoteButton, profilePageButton, feedPageButton;
	private QuoteMeButton searchButton, logoutButton;
	
	public ClientPanel clientPanel;

	private User currentUser;
	private JPanel currentPanelShown;
	
	public MainPanel(ClientPanel clientPanel) {
		this.clientPanel = clientPanel;
		
		//needs to be initialized
		currentUser = null;
		
		initializeVariables();
		createGUI();
		addEvents();
	}
	
	private void initializeVariables() {
		feed = new FeedPageGUI(this);
		notifications = new NotificationPageGUI(this);
		writeQuotePanel = new WriteQuoteGUI(this);
		
		// top buttons
		searchButton = new QuoteMeButton("Search",
				ImageLibrary.getImage(Images.greenButton),
				15,100,25);
		logoutButton = new QuoteMeButton("Logout",
				ImageLibrary.getImage(Images.greyButton),
				15,70,25);
		
		// bottom tab buttons
		notificationButton = new QuoteMeButton("Notifications",
				ImageLibrary.getImage(Images.greyButton),
				15,100,25);
		writeQuoteButton = new QuoteMeButton("Write Quote",
				ImageLibrary.getImage(Images.greyButton),
				15,100,25);
		profilePageButton = new QuoteMeButton("My Profile",
				ImageLibrary.getImage(Images.greyButton),
				15,100,25);
		feedPageButton = new QuoteMeButton("Feed",
				ImageLibrary.getImage(Images.greyButton),
				15,100,25);
	}
	
	private void createGUI() {
		setLayout(new BorderLayout());
		
		// NORTH Panel
		Image image = ImageLibrary.getImage(Images.parrotAvatarRedPixellated).getScaledInstance(Constants.AvatarButtonSize.width, Constants.AvatarButtonSize.height,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon logo = new ImageIcon(image);
		JLabel logoLabel = new JLabel(logo);
		searchField = new QuoteMeTextField("Search QuoteMe");
		searchField.setPreferredSize(new Dimension(160, searchField.getPreferredSize().height));
		
		JPanel northPanel = new JPanel();
		northPanel.setBorder(new EmptyBorder(3,3,3,3));
        northPanel.setBackground(new Color(204, 0, 0, 246));
        northPanel.setLayout(new BorderLayout());
		northPanel.add(logoLabel, BorderLayout.WEST);
		northPanel.add(searchField, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setBackground(new Color(204, 0, 0, 123));
		buttonPanel.add(Box.createHorizontalStrut(7));
		buttonPanel.add(searchButton);
		buttonPanel.add(Box.createHorizontalStrut(7));
		buttonPanel.add(logoutButton);
		buttonPanel.add(Box.createHorizontalStrut(7));
		northPanel.add(buttonPanel, BorderLayout.EAST);
		add(northPanel, BorderLayout.NORTH);

		add(feed, BorderLayout.CENTER);
		feed.setVisible(true);
		currentPanelShown = feed;
		
		// SOUTH Panel
		JPanel outerSouthPanel = new JPanel();
		outerSouthPanel.setBackground(new Color(204, 0, 0, 246));
		outerSouthPanel.setLayout(new BoxLayout(outerSouthPanel, BoxLayout.Y_AXIS));
		JPanel southPanel = new JPanel(new GridLayout(1,4));
        southPanel.setOpaque(false);

		southPanel.add(feedPageButton);
		southPanel.add(writeQuoteButton);
		southPanel.add(notificationButton);
		southPanel.add(profilePageButton);
		outerSouthPanel.add(Box.createVerticalStrut(7));
		outerSouthPanel.add(southPanel);
		outerSouthPanel.add(Box.createVerticalStrut(7));
		add(outerSouthPanel, BorderLayout.SOUTH);
		add(writeQuotePanel, BorderLayout.CENTER);
		writeQuotePanel.setVisible(false);
		add(feed, BorderLayout.CENTER);
	}
	
	private void addEvents() {
		searchField.addFocusListener(new CustomListeners.RemoveTextAdapter(searchField, "Search QuoteMe"));
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (!searchField.getText().equals("Search QuoteMe") && !searchField.getText().equals("")) {
					System.out.println("Clicked search button.");
					search(searchField.getText());
				}
				else {
					System.out.println("Wrong search format");
				}

			}
		});
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(
			            null,
			            "Are you sure you want to logout?",
			            "Confirm logout",
			            JOptionPane.YES_NO_OPTION);
				if(n == 0) {	// User wants to logout
					System.out.println("User confirms logout.");
					clientPanel.refreshComponents();
					clientPanel.moveToHomePanel();
				}
				else {
					System.out.println("User does not confirm logout.");
				}
			}
		});
		feedPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayFeedPage();
				System.out.println("Clicked Feed Button");
			}
		});
		writeQuoteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayWriteQuotePage();		
				System.out.println("Clicked Write Quote Button");
			}
		});
		notificationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayNotificationPage();
				System.out.println("Clicked Notifications Button");
			}
		});
		profilePageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				displayCurrentUserProfilePage();
				System.out.println("Clicked Profile Page");
			}
		});
	}
	
	public void removeCurrentPanel() {
		this.remove(currentPanelShown);
	}
	
	public void addNewPanel(JPanel jp) {
		this.add(jp, BorderLayout.CENTER);
		currentPanelShown = jp;
		this.repaint();
		this.revalidate();
	}
	
	private void search(String text) {
		String[] searchTerms = text.split(" ");
		DataManager dm = clientPanel.quoteMeClient.dataManager;
		Vector<Quote> quotes = dm.getAllQuotes();
		Vector<User> users = dm.getAllUsers();
		
		Vector<User> userResults = new Vector<User>();
		Vector<Quote> quoteResults = new Vector<Quote>();
		
		if (users != null) {
			for (int i=0; i<users.size(); i++) {
				User u = users.elementAt(i);
				userResults.add(u);
				for (int j=0; j<searchTerms.length; j++) {
					if (!u.toString().contains(searchTerms[j])) {
						userResults.remove(u);
						break;
					}
				}
			}
		}
		
		if (quotes != null) {
			for (int i=0; i<quotes.size(); i++) {
				Quote q = quotes.elementAt(i);
				quoteResults.add(q);
				for (int j=0; j<searchTerms.length; j++) {
					if (!q.toString().contains(searchTerms[j])) {
						quoteResults.remove(q);
						break;
					}
				}
			}
		}
		
		SearchResultsGUI resultsPanel = new SearchResultsGUI(userResults, quoteResults, this);
		removeCurrentPanel();
		addNewPanel(resultsPanel);
		resultsPanel.setVisible(true);
	}
	
	public void displayFeedPage() {
		refreshFeed();
		feed.setVisible(true);
		removeCurrentPanel();
		addNewPanel(feed);
		clearSearchResult();
	}
	
	public void displayWriteQuotePage() {
		writeQuotePanel.setVisible(true);
		writeQuotePanel.resetComponents();
		removeCurrentPanel();
		addNewPanel(writeQuotePanel);
		clearSearchResult();
	}
	
	public void displayNotificationPage() {
		notifications.refresh();
		notifications.setVisible(true);
		removeCurrentPanel();
		addNewPanel(notifications);
		clearSearchResult();
	}
	
	public void displayProfilePage(User user) {
		ProfilePageGUI otherProfilePage = new ProfilePageGUI(this, user);
		otherProfilePage.setVisible(true);
		removeCurrentPanel();
		addNewPanel(otherProfilePage);
		clearSearchResult();
	}
	
	public void displayCurrentUserProfilePage() {
		removeCurrentPanel();
		profilePage.refresh();
		addNewPanel(profilePage);
		clearSearchResult();
	}
	
	public void displayPage(JPanel page) {
		removeCurrentPanel();
		addNewPanel(page);
		clearSearchResult();
	}
	
	public void refreshFeed() {
		feed.refresh();
	}
	
	public void clearSearchResult() {
		searchField.setText("Search QuoteMe");
	}
	
	public String getSearchInput() {
		return searchField.getText();
	}

	public void setCurrentUser(User user) {
		currentUser = user;
		profilePage = new ProfilePageGUI(this, currentUser);
	}
}
