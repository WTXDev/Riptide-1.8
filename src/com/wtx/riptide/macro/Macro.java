package com.wtx.riptide.macro;

import java.util.List;

public class Macro {

	private List<Instruction> instructions;

	private String author, name, description;

	private Build build;

	public Macro(List<Instruction> instructions, String macroName, String author, String description, Build build) {
		this.instructions = instructions;
		this.name = macroName;
		this.author = author;
		this.description = description;
		this.build = build;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public String getAuthor() {
		return author;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Build getBuild() {
		return build;
	}

}
