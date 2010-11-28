/*
Chatter
            By: Tim Oram
Student Number: #########
           For: CS-3715 Assignment #1
*/
package mitmaro.Chatter;

import java.net.SocketException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * A Server
 * 
 * @author Tim Oram <toram@mun.ca>
 */
class Server {
	
	/** The socket */
	protected ServerSocket sock;
	/** A simple logger */
	protected Logger log = null;
	/** The port for the socket */
	protected int port;
	
	/**
	 * Defaults constructor
	 */
	public Server() throws IOException {
		this(0, 500);
	}
	
	/**
	 * Create server with provided port
	 *
	 * @param int port The port number
	 */
	public Server(int port) throws IOException {
		this(port, 500);
	}
	
	/**
	 * Create a server providing a port and timeout
	 *
	 * @param int port The port number
	 * @param int timeout The timeout in milliseconds
	 */
	public Server(int port, int timeout) throws IOException {
		this.sock = new ServerSocket(port);
		this.sock.setSoTimeout(timeout);
		this.port = this.sock.getLocalPort();
		this.log = Logger.getLogger("global");
		this.log.setLevel(Level.ALL);
	}
	
	/**
	 * Wait for a client to connect
	 * @return Client A connected client
	 */
	public Client waitForClient() throws IOException {
		this.log.info("Waiting For Client");
		Socket sock = this.sock.accept();
		this.log.info(String.format("Client Connected From: %s", sock.getInetAddress().getHostName()));
		return new Client(sock, 500);
	}
	
	/**
	 * Send a message to a client
	 * 
	 * @param Client client The client to send the message to
	 * @param String msg The message to send
	 */
	public void sendClientMessage(Client client, String msg) {
		this.log.info(String.format("Sending Message: %s", msg));
		try {
			client.sendMessage(msg);
		}
		catch (IOException e) {
			this.log.warning("Error Sending Message");
		}
	}
	
	/**
	 * Is the provided client still connected
	 *
	 * @param Client A client
	 * @return boolean True is client is still connected and false if not
	 */
	public boolean isClientConnected(Client client) {
		
		// if client is null it is no longer connected
		if (client == null) {
			return false;
		}
		
		// do we check this client yet?
		if (client.doAcknowledge()) {
			try {
				// send alive message
				this.sendClientMessage(client, "ALIVE");
				// check for acknowledge response
				return client.waitForResponse().equals("ACK");
			}
			catch (SocketTimeoutException e) {
				return false;
			}
			catch (IOException e) {
				return false;
			}
			catch (NullPointerException e) { // sometimes a client disconnects between calls
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the port for the server
	 *
	 * @return int The port
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Set the log level
	 *
	 * @param Level log_level The logging level
	 */
	public void setLogLevel(Level log_level) {
		this.log.setLevel(log_level);
	}

}