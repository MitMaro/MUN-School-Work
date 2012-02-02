/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Control Unit - One controller to rule them all
 */
package ca.mitmaro.mips.processor;

import ca.mitmaro.mips.processor.ALU.Op;
import ca.mitmaro.mips.processor.controller.BranchController;

public class ControlUnit {
	
	// the processor parts that must be controlled
	private ALU alu;
	private DataMemory data_memory;
	private RegisterFile register_file;
	private RegisterMultiplexer write_register_source_mux;
	private Multiplexer write_register_data_mux;
	private Multiplexer alu_data_mux;
	private BranchController branch_controller;
	private Multiplexer jump_select_mux;
	
	private byte operation;
	
	// wrsmux = write source register mux, admux = alu data mux, wrdmux = write register data mux
	// brctrl = branch selector mux, jmux = jump mux
	public ControlUnit(
		ALU alu, DataMemory dm, RegisterFile rf, RegisterMultiplexer wrsmux, 
		Multiplexer wrdmux, Multiplexer admux, BranchController brctrl,
		Multiplexer jmux
	) {
		this.alu = alu;
		this.data_memory = dm;
		this.register_file = rf;
		this.write_register_source_mux = wrsmux;
		this.write_register_data_mux = wrdmux;
		this.alu_data_mux = admux;
		this.branch_controller = brctrl;
		this.jump_select_mux = jmux;
	}
	
	// set to the instruction operation
	public void setOperation(byte op) {
		this.operation = op;
	}
	
	// set the states of all the components depending on the value of the operation
	public void setStates() {
		
		Op op;
		
		if (this.operation == 0x0) { // R-Type
			this.write_register_source_mux.useB();
			this.write_register_data_mux.useA();
			this.register_file.enableWrite();
			this.alu_data_mux.useA();
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(false);
			this.jump_select_mux.useB();
			this.alu.setOp(Op.FUNCT);
		} else if (this.operation == 0x8) { // addi
			this.write_register_source_mux.useA();
			this.write_register_data_mux.useA();
			this.register_file.enableWrite();
			this.alu_data_mux.useB();
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(false);
			this.jump_select_mux.useB();
			this.alu.setOp(Op.ADD);
		} else if (this.operation == 0x5) { // bne
			this.register_file.disableWrite();
			// this.write_register_source_mux.useB(); don't care
			// this.write_register_data_mux.useA(); don't care
			this.alu_data_mux.useA();
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(true);
			this.branch_controller.setBranchType(false);
			this.jump_select_mux.useB();
			this.alu.setOp(Op.SUB);
		} else if (this.operation == 0x4) { // beq
			this.register_file.disableWrite();
			// this.write_register_source_mux.useB(); don't care
			// this.write_register_data_mux.useA(); don't care
			this.alu_data_mux.useA();
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(true);
			this.branch_controller.setBranchType(true);
			this.jump_select_mux.useB();
			this.alu.setOp(Op.SUB);
		} else if (this.operation == 0x23) { // lw
			this.register_file.enableWrite();
			this.write_register_source_mux.useA();
			this.write_register_data_mux.useB();
			this.alu_data_mux.useB();
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(false);
			//this.branch_controller.setBranchType(true); don't care
			this.jump_select_mux.useB();
			this.alu.setOp(Op.ADD);
		} else if (this.operation == 0x2B) { // sw
			this.register_file.disableWrite();
			//this.write_register_source_mux.useA(); don't care
			//this.write_register_data_mux.useB(); don't care
			this.alu_data_mux.useB();
			this.data_memory.enableWrite();
			this.branch_controller.setIsBranchInstruction(false);
			//this.branch_controller.setBranchType(true); don't care
			this.jump_select_mux.useB();
			this.alu.setOp(Op.ADD);
		} else if (this.operation == 0x2) { // j
			this.register_file.disableWrite();
			//this.write_register_source_mux.useA(); don't care
			//this.write_register_data_mux.useB(); don't care
			//this.alu_data_mux.useB(); don't care
			this.data_memory.disableWrite();
			this.branch_controller.setIsBranchInstruction(false);
			//this.branch_controller.setBranchType(true); don't care
			this.jump_select_mux.useA();
			//this.alu.setOp(Op.ADD); don't care
		} else {
			throw new RuntimeException("Unknown instruction provided");
		}
		
	}
	
}
