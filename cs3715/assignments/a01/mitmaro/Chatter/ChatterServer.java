/*
Chatter
            By: Tim Oram
Student Number: #########
           For: CS-3715 Assignment #1
*/
package mitmaro.Chatter;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * A Chatter Server
 * 
 * @author Tim Oram <toram@mun.ca>
 */
public class ChatterServer {
	
	public static void main(String args[]) {
	
		Logger log = Logger.getLogger("global");
		log.setLevel(Level.ALL);
		
		Server server = null;
		
		String line;
		String[] tmp;
		
		HashMap<String, Client> clients;
		
		Client client = null;
		Client otherClient = null;
		
		int client_port = 0;
		String client_host = "";
		
		try {
			// make server socket on a random port
			server = new Server();
			System.out.println(String.format("Server Running on Port: %d", server.getPort()));
		}
		catch (IOException e) {
			log.severe("Socket Error: " + e.getMessage());
			System.exit(1);
		}
		
		// the hash map of clients
		clients = new HashMap<String, Client>();
		
		while (true) {
			try {
				// wait for a ChatterClient 
				client = server.waitForClient();
			}
			catch (SocketTimeoutException time_e) {} // ok exception, just a timeout
			catch (IOException e) {
				log.warning("Client Connect Error: " + e.getMessage());
			}
				
			// no client, poll for disconnected
			if (client == null) {
				// check for disconnected clients
				Iterator it = clients.entrySet().iterator();
				while (it.hasNext()) {
					// why couldn't sun get generics right.... maybe oracle will fix them
					@SuppressWarnings("unchecked")
					Map.Entry<String, Client> pairs = (Map.Entry<String, Client>)it.next();
					
					// if the client is not connected
					if (!server.isClientConnected(pairs.getValue())) {
						// disconnect the client and do some clean up
						log.info(String.format("Client Disconnect: %s", pairs.getValue().getUUID()));
						pairs.getValue().detachOther();
						it.remove();
					}
				}
			} else { // client connected
				// send wait
				server.sendClientMessage(client, "WAIT");
				
				try {
					// wait for response from the ChatterClient
					line = client.waitForResponse();
				}
				catch (IOException e) {
					log.warning("Error: " + e.getMessage());
					client.disconnect();
					clients.remove(client.getUUID());
					continue;
				}
				
				// tmp[0] = CLIENT_PORT, tmp[1] = port
				tmp = line.split("\\s+");
				
				if (tmp.length == 2 && tmp[0].equals("CLIENT_PORT")) {
					try {
						client_port = Integer.parseInt(tmp[1]);
					}
					catch(NumberFormatException e) {
						log.warning(String.format("Supplied port, %s, was invalid.", tmp[1]));
						client.disconnect();
						clients.remove(client.getUUID());
						continue;
					}
					
					// get the clients host
					client_host = client.client.getInetAddress().getHostName();
					log.fine(String.format("Client Available: %s:%d", client_host, client_port));
					client.setHostAndPort(client_host, client_port);
					clients.put(client.uuid, client);
				}
				else {
					log.warning(String.format("Invalid Response: %s", line));
					client.disconnect();
					clients.remove(client.getUUID());
				}
					
			}
			
			// match clients, not the most efficient way to do so
			for (Client c: clients.values()) {
				if (c.otherClient == null) { // a client is not connected when it is unmatched
					log.fine(String.format("Found a unmatched client: %s", c.uuid));
					if (client == null) { // client 1
						client = c;
					} else if(c.uuid != client.uuid){
						otherClient = c; // client 2
						break;
					}
				}
			}
			
			// match found
			if (otherClient != null) {
				// send the client the information of one of the other clients
				server.sendClientMessage(client, String.format("PEER_LOC %s:%d", otherClient.host, otherClient.port));
				client.setHostAndPort(client.client.getInetAddress().getHostName(), otherClient.port);
				otherClient.otherClient = client;
				client.otherClient = otherClient;
			}
			
			otherClient = null;
			client = null;
			
			try {
				Thread.sleep(20); // wait a small bit before the next loop
			}
			catch(InterruptedException e) {} // it didn't sleep, do I care?
		}
		
	}
}