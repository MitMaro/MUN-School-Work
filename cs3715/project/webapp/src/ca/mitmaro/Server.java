package ca.mitmaro;
/*
Computer Science 3715
Team Project
Music Wishlist

By: Lauren Stratton #########
    Tim Oram        #########
*/


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
		ArtistsService.createTable();
		
		// full package name to stop conflicts with this class
		org.mortbay.jetty.Server server = new org.mortbay.jetty.Server(port);
		
		ResourceHandler resource = new ResourceHandler();
		resource.setResourceBase(baseDir);
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		Context servlets = new Context(contexts,"/");
		servlets.addServlet(ArtistsServlet.class, "/artists/*");
		
		server.addHandler(resource);
		server.addHandler(contexts);
		server.addHandler(new DefaultHandler());
		
		server.start();
		server.join();
	}
}
