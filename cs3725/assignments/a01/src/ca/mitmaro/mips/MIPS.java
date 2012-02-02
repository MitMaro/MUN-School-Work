/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 *
 * Control Unit - One controller to rule them all
 */
package ca.mitmaro.mips;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import ca.mitmaro.mips.processor.ALU;
import ca.mitmaro.mips.processor.ControlUnit;
import ca.mitmaro.mips.processor.DataMemory;
import ca.mitmaro.mips.processor.InstructionMemory;
import ca.mitmaro.mips.processor.Memory;
import ca.mitmaro.mips.processor.Multiplexer;
import ca.mitmaro.mips.processor.RegisterFile;
import ca.mitmaro.mips.processor.RegisterMultiplexer;
import ca.mitmaro.mips.processor.controller.BranchController;

public class MIPS {
	
	private InstructionMemory instruction_memory;
	private RegisterMultiplexer write_register_source_mux;
	private RegisterFile register_file;
	private Multiplexer alu_data_mux;
	private ALU alu;
	private DataMemory data_memory;
	private Multiplexer write_register_data_mux;
	private Multiplexer branch_select_mux;
	private Multiplexer jump_select_mux;
	private BranchController branch_control;
	
	private ControlUnit control_unit;
	
	public MIPS() {
		Memory memory = new Memory(0x00007fff); // 32KBytes
		this.instruction_memory = new InstructionMemory(memory); // instructions start at 0x0
		
		// register file
		this.write_register_source_mux = new RegisterMultiplexer();
		this.register_file = new RegisterFile();
		
		// alu
		this.alu_data_mux = new Multiplexer();
		this.alu = new ALU();
		
		// data memory
		this.data_memory = new DataMemory(memory); // start of .data
		this.write_register_data_mux = new Multiplexer();
		
		// branch selector
		this.branch_control = new BranchController();
		this.branch_select_mux = new Multiplexer();
		
		// jump selector
		this.jump_select_mux = new Multiplexer();
		
		this.control_unit = new ControlUnit(
			this.alu, this.data_memory, this.register_file, this.write_register_source_mux,
			this.write_register_data_mux, this.alu_data_mux, this.branch_control,
			this.jump_select_mux
		);
	}
	
	public void loadInstructions(File file) throws IOException{
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
		byte[] d = new byte[4];
		
		int instruction;
		this.instruction_memory.clearInstructionMemory();
		
		// read while there were 4 bytes being read
		while(in.read(d, 0, 4) == 4) {
			instruction = 0;
			instruction |= (d[3] & 0xFF);
			instruction <<= 8;
			instruction |= (d[2] & 0xFF);
			instruction <<= 8;
			instruction |= (d[1] & 0xFF);
			instruction <<= 8;
			instruction |= (d[0] & 0xFF);
			
			this.instruction_memory.loadInstruction(instruction);
		}
	}
	
	public void loadMemoryDump(File file) throws IOException {
		
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		
		byte[] d = new byte[4];
		byte t;
		
		int memory_address = 0x2000;
		
		// read while there were 4 bytes being read
		while(in.read(d, 0, 4) != -1) {
			// mars data files are in reverse byte order... swap the other
			t = d[0]; d[0] = d[3]; d[3] = t;
			t = d[1]; d[1] = d[2]; d[2] = t;
			memory_address = this.data_memory.writeRawData(memory_address, d);
		}
	}
	
	public void run() {
		
		this.instruction_memory.setPC(0);
		int instruction;
		while (true) {
			instruction = this.instruction_memory.getInstruction();
			if (instruction == 0) {
				break;
			}
			this.cycle(instruction);
		}
	}
	
	public int[] getRegisters() {
		
		int regs[] = new int[32];
		
		for (byte i = (byte)0; i < 32; i++) {
			this.register_file.setReadRegisterA(i);	
			regs[i] = this.register_file.readDataA();
		}
		
		return regs;
	}
	
	private void cycle(int instruction) {
		
		// op - instruction [31-26]
		byte operation = 0;
		// address - instruction [25-0]
		int address = 0;
		// rs - instruction [25-21]
		byte rs = 0;
		// rt - instruction [20-16]
		byte rt = 0;
		// rd - instruction [15-11]
		byte rd = 0;
		// funct - instruction [5-0]
		byte funct = 0;
		// immediate - instruction [15-0]
		int immediate = 0;
		
		int pc;
		// jump address - pc+4 [31-28] . (instruction [25-0]<<2) . 00
		int jump_address = 0;
		// branch address - pc+4 + (immediate<<2)
		int branch_address = 0;
		
		int i;
		
		// break apart the instruction
		operation = (byte)((instruction & 0xFC000000) >>> 26);
		address = (instruction & 0x3FFFFFF);
		funct = (byte)(instruction & 0xFFFF);
		rs = (byte)((instruction & 0x3E00000) >>> 21);
		rt = (byte)((instruction & 0x1F0000) >>> 16);
		rd = (byte)((instruction & 0xF800) >>> 11);
		immediate = (int)(instruction & 0xFFFF);
		
		// sign extend immediate (16 bit to 32 bit)
		if ((immediate & 0x8000) != 0) {
			immediate |= 0xFFFF0000;
		}
		
		// set control operation and set state of all modules and muxes
		this.control_unit.setOperation(operation);
		this.control_unit.setStates();
		
		// get the pc values
		pc = this.instruction_memory.getPC() + 4;
		jump_address = (address << 2) | (pc & 0xF0000000);
		branch_address = pc + (immediate << 2);
		
		// provide data for register file
		this.register_file.setReadRegisterA(rs);
		this.register_file.setReadRegisterB(rt);
		this.write_register_source_mux.setValueA(rt);
		this.write_register_source_mux.setValueB(rd);
		this.register_file.setWriteRegister(this.write_register_source_mux.getValue());
		
		// provide data for the alu
		this.alu_data_mux.setValueA(this.register_file.readDataB());
		this.alu_data_mux.setValueB(immediate);
		this.alu.setOperands(this.register_file.readDataA(), this.alu_data_mux.getValue());
		this.alu.setFunct(funct);
		
		// provide data for the data memory
		this.data_memory.setAddress(this.alu.getResult());
		this.data_memory.writeData(this.register_file.readDataB());
		
		// set and write registers
		this.write_register_data_mux.setValueA(this.alu.getResult());
		this.write_register_data_mux.setValueB(this.data_memory.readData());
		this.register_file.writeData(this.write_register_data_mux.getValue());
		
		// pc address selection
		this.branch_select_mux.setValueA(pc);
		this.branch_select_mux.setValueB(branch_address);
		this.branch_control.setAluZero(this.alu.zero());
		if (this.branch_control.getValue()) {
			this.branch_select_mux.useB();
		} else {
			this.branch_select_mux.useA();
		}
		this.jump_select_mux.setValueA(jump_address);
		this.jump_select_mux.setValueB(this.branch_select_mux.getValue());
		this.instruction_memory.setPC(this.jump_select_mux.getValue());
		
	}
}
