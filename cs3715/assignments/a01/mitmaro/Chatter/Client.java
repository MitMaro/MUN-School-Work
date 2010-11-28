/*
Chatter
            By: Tim Oram
Student Number: #########
           For: CS-3715 Assignment #1
*/
package mitmaro.Chatter;

import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.InetAddress;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * A connected Socket
 * 
 * @author Tim Oram <toram@mun.ca>
 */
class Client {
	
	/** The connected socket */
	protected Socket client;
	/** For reading from the server */
	protected BufferedReader in;
	/** For writing to the server */
	protected PrintWriter out;
	/** The port of */
	protected int port;
	/** The host of */
	protected String host;
	/** A unique id */
	protected String uuid;
	/** Simple logger */
	protected Logger log;
	/** Acknowledge counter */
	protected int ack = 0;
	/** The other client connected to this client */
	public Client otherClient;
	
	/**
	 * Associates the given Socket to this Client
	 * 
	 * @param Socket client The socket connection
	 * @param int timeout The timeout for the connection
	 */
	public Client(Socket client, int timeout) throws IOException {
		this.client = client;
		try {
			this.client.setSoTimeout(timeout);
		}
		catch(SocketException e) {} // rare error, won't handle this for now
		this.out = new PrintWriter(this.client.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		this.log = Logger.getLogger("global");
	}
	
	/**
	 * Creates a socket connection for the given host and port
	 * 
	 * @param Socket client The socket connection
	 * @param int timeout The timeout for the connection
	 */
	public Client(String host, int port, int timeout) throws IOException {
		this(new Socket(InetAddress.getByName(host).getHostName(), port), timeout);
	}
	
	/**
	 * Wait for this client to respond
	 *
	 * @return String The response from the connected socket
	 */
	public String waitForResponse() throws IOException {
		this.log.info("Waiting For Response");
		String msg = this.in.readLine();
		this.log.info(String.format("Client Response: %s", msg));
		return msg;
	}
	
	/**
	 * Set the host and port for this client. Also sets the Unique ID.
	 *
	 * @param String host The host name
	 * @param int post The port number
	 */
	public void setHostAndPort(String host, int port) {
		this.host = host;
		this.port = port;
		this.uuid = String.format("%s:%d", host, port);
		this.log.info(String.format("Client UUID Set: %s", this.uuid));
	}
	
	/**
	 * Send a message to the connected socket
	 * @param String msg The message to send
	 */
	public void sendMessage(String msg) throws IOException {
		this.log.info(String.format("Sending Message: %s", msg));
		this.out.println(msg);
		if (this.out.checkError()){
			throw new IOException ("Error Sending Message");
		}
	}
	
	/**
	 * Disconnect the socket connection
	 */
	public void disconnect() {
		this.log.info("Disconnecting Client");
		this.detachOther();
		try {
			this.client.close();
		}
		catch (IOException e) {
			this.log.warning(String.format("Error Disconnecting Client: %s", this.uuid));
		}
	}
	
	/**
	 * Detach the otherClient of this client from this client
	 */
	public void detachOther() {
		if (this.otherClient != null) {
			this.otherClient.otherClient = null;
		}
	}
	
	/**
	 * Get the Unique Identifier
	 * 
	 * @return String The uuid
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * Should this client send an acknowledge
	 *
	 * @return boolean True if an acknowledge request should be sent. False otherwise.
	 */
	public boolean doAcknowledge() {
		this.ack++;
		if (this.ack > 10) {
			this.ack = 0;
			return true;
		}
		return false;
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