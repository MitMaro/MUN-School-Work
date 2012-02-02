/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * RegisterFile - because MIPS is register-register based we need registers
 */
package ca.mitmaro.mips.processor;

public class RegisterFile {
	
	// 32 registers
	private int registers[] = new int[32];
	
	// the register selectors
	private byte read_register_a;
	private byte read_register_b;
	private byte write_register;
	
	// enable writing of register flag
	private boolean write_enabled = false;
	
	// set register numbers
	public void setReadRegisterA(byte register_number) {
		this.read_register_a = register_number;
	}
	public void setReadRegisterB(byte register_number) {
		this.read_register_b = register_number;
	}
	public void setWriteRegister(byte register_number) {
		this.write_register = register_number;
	}
	
	// read data from the registers selected
	public int readDataA() {
		return this.registers[this.read_register_a];
	}
	public int readDataB() {
		return this.registers[this.read_register_b];
	}
	
	// enable and disable writing of a register
	public void enableWrite() {
		this.write_enabled = true;
	}
	public void disableWrite() {
		this.write_enabled = false;
	}
	
	// write register, but never register 0
	public void writeData(int data) {
		if (this.write_enabled && this.write_register != 0) {
			this.registers[this.write_register] = data;
		}
	}
	
}
