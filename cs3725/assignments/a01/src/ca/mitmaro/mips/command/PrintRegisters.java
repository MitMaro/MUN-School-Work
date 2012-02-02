/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Control Unit - One controller to rule them all
 */
package ca.mitmaro.mips.command;

import java.io.File;
import java.io.IOException;

import ca.mitmaro.commandline.command.Command;
import ca.mitmaro.commandline.term.Terminal;
import ca.mitmaro.mips.MIPS;

public class PrintRegisters extends Command {
	
	MIPS proc;
	
	public PrintRegisters(MIPS proc, Terminal term) {
		super(term);
		this.proc = proc;
	}
	
	public int call(String[] args) {
		
		int[] regs = this.proc.getRegisters();
		
		this.terminal.out.format("$zero: 0x%08X     $t0: 0x%08X     $s0: 0x%08X     $t8: 0x%08X\n", regs[0], regs[8], regs[16], regs[24]);
		this.terminal.out.format("  $at: 0x%08X     $t1: 0x%08X     $s1: 0x%08X     $t9: 0x%08X\n", regs[1], regs[9], regs[17], regs[25]);
		this.terminal.out.format("  $v0: 0x%08X     $t2: 0x%08X     $s2: 0x%08X     $k0: 0x%08X\n", regs[2], regs[10], regs[18], regs[26]);
		this.terminal.out.format("  $v1: 0x%08X     $t3: 0x%08X     $s3: 0x%08X     $k1: 0x%08X\n", regs[3], regs[11], regs[19], regs[27]);
		this.terminal.out.format("  $a0: 0x%08X     $t4: 0x%08X     $s4: 0x%08X     $gp: 0x%08X\n", regs[4], regs[12], regs[20], regs[28]);
		this.terminal.out.format("  $a1: 0x%08X     $t5: 0x%08X     $s5: 0x%08X     $sp: 0x%08X\n", regs[5], regs[13], regs[21], regs[29]);
		this.terminal.out.format("  $a2: 0x%08X     $t6: 0x%08X     $s6: 0x%08X     $fp: 0x%08X\n", regs[6], regs[14], regs[22], regs[30]);
		this.terminal.out.format("  $a3: 0x%08X     $t7: 0x%08X     $s7: 0x%08X     $ra: 0x%08X\n", regs[7], regs[15], regs[23], regs[31]);
		return 1;
	}
}
