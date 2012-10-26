import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.ldb.Application;
import ca.mitmaro.ldb.CommandLineInterface;


public class LDBTest {

	private static CommandLineInterface cli;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		Application application = new Application();
		Terminal term = new Terminal();
		cli = new CommandLineInterface(application, term);
		application.setInterface(cli);
		runCommand("help");
		runCommand("loadpap data/pd1.dat as One");
		runCommand("loadpap data/pd2.dat as Two");
		runCommand("loadpap data/pd3.dat as Three");
		runCommand("loadpap data/pd4.dat as Four");
		runCommand("loadpap data/pd5.dat as Five");
		runCommand("printpap One");
		runCommand("printpap Two");
		runCommand("printpap Three");
		runCommand("printpap Four");
		runCommand("printpap Five");
		runCommand("addpap One");
		runCommand("addpap Two");
		runCommand("addpap Three");
		runCommand("addpap Four");
		runCommand("addpap Five");
		runCommand("editpap P001");
		runCommand("editpap P008");
		runCommand("editpap P009");
		runCommand("editpap P010");
		runCommand("editpap P011");
	}
	
	public static void runCommand(String command) throws Throwable {
		System.out.format("$ %s\n", command);
		cli.runCommand(command);
	}

}
