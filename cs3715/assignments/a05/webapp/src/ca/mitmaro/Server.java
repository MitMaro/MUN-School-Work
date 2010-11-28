/*
Computer Science 3715
Assignment #5
Image Rotate With Comments

By: Tim Oram
Student Number: #########
*/

package ca.mitmaro;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;

import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;

/**
 * A simple Jetty server
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		int port = 8888;
		String baseDir = ".";
		
		// handle arguments
		if (args.length == 2 ) {
			port = Integer.parseInt(args[0]);
			baseDir = args[1];
		} else if (args.length == 1 ) {
			port = Integer.parseInt(args[0]);
		}
		
		// setup database for comments
		CommentsServlet.checkDatabase();
		
		// full package name to stop conflicts with this class
		org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(port);
		
		ResourceHandler resource = new ResourceHandler();
		resource.setResourceBase(baseDir);
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		Context servlets = new Context(contexts,"/");
		servlets.addServlet(CommentsServlet.class, "/comments/*");
		
		server.addHandler(resource);
		server.addHandler(contexts);
		server.addHandler(new DefaultHandler());
		
		server.start();
		server.join();
	}
}