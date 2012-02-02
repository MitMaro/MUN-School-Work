/**
 * CS 3725 - Assignment #1
 * By: Tim Oram (200529220)
 * Simple MIPS Processor
 * 
 * Arithmetic Logic Unit - it does mathy things
 */

package ca.mitmaro.mips.processor;

import java.util.HashMap;
import java.util.EnumSet;

public class ALU {
	
	// operations
	public enum Op {
		FUNCT((byte)0x0),
		ADD((byte)0x20),
		SUB((byte)0x22);
		
		private byte value;
		
		private Op(byte v) {
			this.value = v;
		}
	
		// opeartion lookup map
		private static final HashMap<Byte, Op> byte_op_map = new HashMap<Byte, Op>();
		static {
			for (Op type : EnumSet.allOf(Op.class)) {
				byte_op_map.put(type.value, type);
			}
		}
		
		public int getValue() { return this.value; }
		
		public static Op get(byte v) {
			return byte_op_map.get(v);
		}
		
	}
	// result caching
	private boolean use_cache = false;
	private int result_cache = 0;
	
	// alu inputs
	private int a = 0;
	private int b = 0;
	
	// alu function
	private Op op = null;
	private Op funct = null; 
	
	// set the operands for the alu
	public void setOperands(int a, int b) {
		this.a = a;
		this.b = b;
		this.use_cache = false;
	}
	public void setOp(Op op) {
		this.op = op;
	}
	public void setFunct(byte func) {
		this.funct = Op.get(func);
	}
	
	// return the result of the alu, multiple calls use a cache
	public int getResult() {
		if (!this.use_cache) {
			Op op;
			// determine op source
			if (this.op == Op.FUNCT) {
				op = this.funct;
			} else {
				op = this.op;
			}
			
			switch (op) {
				case ADD:
					this.result_cache = this.a + this.b;
					break;
				case SUB:
					this.result_cache = this.a - this.b;
			}
			
			this.use_cache = true;
		}
		
		return this.result_cache;
	}
	
	// true if result is a zero
	public boolean zero() {
		return this.getResult() == 0;
	}
	
}
