/*
Chatter
            By: Tim Oram
Student Number: #########
           For: CS-3715 Assignment #1
*/
package mitmaro.Chatter;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A Chatter Client
 * 
 * @author Tim Oram <toram@mun.ca>
 */
public class ChatterClient {
	public static void main(String[] args) {
		String line = "";
		String[] tmp;
		
		Level log_level = Level.WARNING;
		
		BufferedReader stdin;
		
		Client client = null;
		Client otherClient = null;
		Server server = null;
		
		Logger log = Logger.getLogger("global");
		log.setLevel(log_level);
		
		String host;
		int port = 0;
		
		// check args
		if (args.length != 2) {
			System.out.println("usage: java ChatterClient host port");
			System.exit(1);
		}
		
		host = args[0];
		
		try {
			port = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			System.out.println("Invalid port provided: " + args[1]);
			System.exit(1);
		}
		
		stdin = new BufferedReader(new InputStreamReader(System.in));
		
		
		try {
			// create a client that is connected to a ChatterServer
			client = new Client(host, port, 500);
			client.setLogLevel(log_level);
			// Create a server for other ChatterClients to connect
			server = new Server();
			server.setLogLevel(log_level);
		} catch (UnknownHostException e) {
			log.warning("Unknown Host Provided");
		} catch (IOException e) {
			log.warning("Error Making Socket Connection");
			System.exit(1);
		}
		
		while (true) {
			try {
				// wait for a response from the ChatterServer
				line = client.waitForResponse();
				// server disconnect
				if (line == null) {
					System.out.println("Server Disconnected");
					System.exit(1);
				}
			} catch (SocketTimeoutException time_e) { // ok exception, just a timeout
			} catch (IOException e) {
				log.severe("Error Reading From Server: " + e.getMessage());
				client.disconnect();
				break;
			} catch (NullPointerException e) {
				log.severe("Error Reading From Server: " + e.getMessage());
				client.disconnect();
				break;
			}
			
			try {
				// Send acknowledge for ALIVE request
				if (line.equals("ALIVE")) {
					client.sendMessage("ACK");
				} else if(line.equals("WAIT")) { // ChatterServer says wait, send this clients connection information
					port = server.getPort();
					client.sendMessage(String.format("CLIENT_PORT %d", port));
					System.out.println("Waiting For Other Client.");
				} else if (line.startsWith("PEER_LOC") && otherClient == null) { // ChatterClient found
					// tmp[0] = PEER_LOC, tmp[1] = host:port
					tmp = line.split("\\s+");
					if (tmp.length == 2) {
						// tmp[0] = host, tmp[1] = port
						tmp = tmp[1].split(":");
						if (tmp.length == 2) {
							host = tmp[0];
							try {
								port = Integer.parseInt(tmp[1]);
								try {
									// connect to the other ChatterClient
									otherClient = new Client(host, port, 500);
									otherClient.setLogLevel(log_level);
									System.out.println("A Client Has Connected. Have Fun Chatting.");
								} catch (IOException e) {
									log.warning("Client Connect Error: " + e.getMessage());
								}
							}
							catch(NumberFormatException e) {
								log.warning(String.format("Supplied port, %s, was invalid.", tmp[1]));
								continue;
							}
						} else {
							log.warning(String.format("Invalid Response From Client: %s", line));
						}
					} else {
						log.warning(String.format("Invalid Response From Client: %s", line));
					}
				}
				
				// if a ChatterClient was not connected
				if (otherClient == null) {
					try {
						// try to wait for a client
						otherClient = server.waitForClient();
						otherClient.setLogLevel(log_level);
						System.out.println("A Client Has Been Found. Have Fun Chatting.");
					} catch (SocketTimeoutException time_e) {} // ok exception, just a timeout
				} else { // we are communicating with another chat client
					try {
						// read a line from the client
						line = otherClient.waitForResponse();
						// client disconnect
						if (line == null) {
							otherClient = null;
							System.out.println("Client Has Disconnected.");
						} else {
							System.out.println(String.format("Received: %s", line));
						}
					} catch (SocketTimeoutException time_e) {} // ok exception, just a timeout
					
					// wait for input from the user
					if (otherClient != null && stdin.ready()) {
						// read and send user input
						line = stdin.readLine();
						otherClient.sendMessage(line);
						System.out.println(String.format("    Sent: %s", line));
					}
				}
				
				line = "";
				
			} catch (IOException e) {
				log.severe("Error Sending Message: " + e.getMessage());
				client.disconnect();
				break;
			}
		}
	}
	
}