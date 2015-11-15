package client;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import resources.Images;


public class HomePageGUI extends JPanel {

	private static final long serialVersionUID = -7248616684444196676L;
	public ImageIcon logo;
	public JButton loginPageButton, newUserPageButton;
	
	private ClientPanel clientPanel;
	
	public HomePageGUI(ClientPanel clientPanel) {
		initializeVariables();
		this.clientPanel = clientPanel;
		createGUI();
		addEvents();
	}
	
	// Trying to paint background. Not working yet
	@Override 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Image panelBackground = ImageIO.read(new File(Images.plainHomePageBackground));
			g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initializeVariables() {
		setLayout(new BorderLayout());
		setSize(200, 400);
		setVisible(true);
		logo = new ImageIcon();
		loginPageButton = new JButton("Login");
		newUserPageButton = new JButton("New User");
	}
	
	private void createGUI() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loginPageButton);
		buttonPanel.add(newUserPageButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void addEvents() {
		loginPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				goToLoginPage();
			}
		});
		newUserPageButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent ae) {
				goToNewUserPage();
			}
		});
	}
	
	private void goToLoginPage() {
		System.out.println("Going to login page");
		clientPanel.moveToLoginPanel();
	}
	
	private void goToNewUserPage() {
		System.out.println("Going to new user page");
		clientPanel.moveToCreateUserPanel();
	}
	
}
