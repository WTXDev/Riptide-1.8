package com.wtx.riptide.macro;

public class Instruction {

	private String args;

	private InstructionID instructionID;

	public Instruction(String args, InstructionID instructionID) {
		this.args = args;
		this.instructionID = instructionID;
	}

	public String getArgs() {
		return args;
	}

	public InstructionID getInstructionID() {
		return instructionID;
	}

}
