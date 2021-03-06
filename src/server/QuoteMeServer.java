package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import client.DataManager;
import client.Notification;
import client.Quote;
import client.User;

public class QuoteMeServer {
	
	private ServerSocket ss;
//	private ServerListener serverListener;
	private Vector<ServerClientCommunicator> sccVector = new Vector<ServerClientCommunicator>();
	private DataManager dataManager;
	
	public QuoteMeServer()
	{
		loadQuoteMeUniverse(); // sets dataManager
		
		try {
			ss = new ServerSocket(6789);
			while (true) {
				System.out.println("Waiting for client to connect...");
				Socket s = ss.accept();
				System.out.println("Client " + s.getInetAddress() + ":" + s.getPort() + " connected");
				
				ServerClientCommunicator scc = new ServerClientCommunicator(s, this, dataManager);
				sccVector.add(scc);
//				System.out.println("About to start");
				scc.start();
//				System.out.println("AFTER START");
//				System.out.println("DataManager in server try null?: " + (dataManager==null));

				if (dataManager != null) {
					System.out.println('\n' + "QuoteMeServer: New client connected. Sending DM instance through SCC: --->");
					dataManager.printThis();
					scc.sendAppInstance(dataManager);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
						
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOE closing ss " + ioe.getMessage());
				}
			}
		}
	}
	
	public void loadQuoteMeUniverse() {
		try {
			File file = new File("QuoteMeUniverse.txt");
			
			// if file doesn't exist, create it and push an empty Data Manager to it
			if(!file.exists()) {
				file.createNewFile();
				DataManager newDataManager = new DataManager();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("QuoteMeUniverse.txt"));
				oos.writeObject(newDataManager);
				oos.flush();
				oos.close();
			}
			
			// read the Data Manager
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("QuoteMeUniverse.txt"));
			dataManager = (DataManager)ois.readObject();
			
			System.out.println("QuoteMeServer: Load QuoteMeUniverse.txt:");
			dataManager.printThis();
			
			ois.close();
		} catch (FileNotFoundException fnfe) { System.out.println("FileNotFoundException: " + fnfe.getMessage()); } catch (IOException ioe) { System.out.println("IOException: " + ioe.getMessage()); } catch (ClassNotFoundException cnfe) { System.out.println("ClassNotFoundException: " + cnfe.getMessage()); }
	}
	
	public void sendAppInstanceToAllClients(DataManager updatedDataManager) {
		this.dataManager = updatedDataManager;
		System.out.println("QuoteMeServer: Sending DM to all clients through SCC--->");
		this.dataManager.printThis();
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendAppInstance(this.dataManager);
		}
	}
	
	public void sendObjectToAllClients(Object info) {
		if (info instanceof client.Quote) {
            System.out.println("QuoteMeServer: Received Object of type Quote." + '\n');
			this.dataManager.addQuote((Quote)info);
		}
		
		else if (info instanceof client.User) {
            System.out.println("QuoteMeServer: Received Object of type User." + '\n');
			this.dataManager.addUser((User)info);
		}
		
		else if (info instanceof client.Notification) {
            System.out.println("QuoteMeServer: Received Object of type Notification." + '\n');
            this.dataManager.addNotification((Notification)info);
		}
		System.out.println("QuoteMeServer: Sending some object to all clients through SCC--->");
		this.dataManager.printThis();
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendObject(info);
		}
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
	}
	
	public DataManager getDataManager(){
		return dataManager;
	}
	
	/* Will likely not need this method, but good to have.
	public void sendObjectToClients(ServerClientCommunicator comm, Object obj) {
		for (ServerClientCommunicator scc : sccVector) {
			if (!comm.equals(scc)) {
				scc.sendObject(obj);
			}
		}
	} */
	
	public void pushToTextFile() {
		
		System.out.println("In pushToTextFile()");
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("QuoteMeUniverse.txt"));
			oos.writeObject(dataManager);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException fnfe) { System.out.println("FileNotFoundException: " + fnfe.getMessage()); } catch (IOException ioe) { System.out.println("IOException: " + ioe.getMessage()); }
	}
	
	public static void main(String[] args) {
		new QuoteMeServer();
	}	
}