/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Entry Class
 */

import ca.mitmaro.mips.MIPS;
import ca.mitmaro.mips.MIPSCommandLine;
import ca.mitmaro.commandline.term.Terminal;

public class MIPSSim{
	public static void main(String[] args) {
		MIPSCommandLine cl = new MIPSCommandLine(new MIPS());
		System.exit(cl.mainloop());
	}
 }
