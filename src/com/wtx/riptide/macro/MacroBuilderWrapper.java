package com.wtx.riptide.macro;

import java.util.ArrayList;

public class MacroBuilderWrapper {

	public static ArrayList<Instruction> Instructions = new ArrayList<Instruction>();

	public static void addInstruction(Instruction inst) {
		Instructions.add(inst);
	}

	public static InstructionID getIdByName(String str) {
		switch (str) {
		case "CHAT":
			return InstructionID.CHAT;
		case "WAIT":
			return InstructionID.WAIT;
		case "DISCONNECT":
			return InstructionID.DISCONNECT;
		case "MOVE":
			return InstructionID.MOVE;
		default:
			return null;
		}
	}

}
