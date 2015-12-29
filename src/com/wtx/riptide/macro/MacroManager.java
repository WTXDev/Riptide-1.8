package com.wtx.riptide.macro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;

import com.google.common.io.Files;
import com.wtx.riptide.BotObject;
import com.wtx.riptide.Gui;
import com.wtx.riptide.Riptide;
import com.wtx.riptide.Settings;
import com.wtx.riptide.Wrapper;

public class MacroManager {

	private static ArrayList<Macro> MacroList = new ArrayList<Macro>();

	public MacroManager() {

	}

	public static Macro getByName(String name) {
		for (Macro macro : MacroList) {
			if (name == macro.getName()) {
				return macro;
			}
		}

		return null;

	}

	public static void move(BotObject bot) {

		new Thread() {
			@Override
			public void run() {
				Riptide.move("z");
			}
		}.start();

	}

	@SuppressWarnings("unchecked")
	public static void refreshMacros(@SuppressWarnings("rawtypes") JComboBox box) {
		MacroList.clear();

		try {

			loadModularMacros();

			if (MacroList.size() != 0) {
				for (Macro macro : MacroList) {
					box.addItem(macro.getName());
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void loadModularMacros() throws IOException {
		Wrapper.Log("Parsing macros...", "Debug");

		for (final File file : Settings.MacrosPath.listFiles()) {
			boolean macro = Files.getFileExtension(file.getName()).equals("macro");

			if (macro) {
				FileReader inputFile = new FileReader(file.getPath());
				BufferedReader bufferReader = new BufferedReader(inputFile);

				String line;

				ArrayList<Instruction> instructions = new ArrayList<Instruction>();

				String name = null;
				String author = null;
				String desc = null;

				Build build = null;

				while ((line = bufferReader.readLine()) != null) {

					try {

						String[] arguments = line.split("=");

						String ident = arguments[0];
						String value = arguments[1];
						
						switch(ident)
						{
						case "Name": name = value; break;
						
						case "Author": author = value; break;
						
						case "Desc": desc = value; break;
						
						case "Build": build = Build.valueOf(value); break;
						
						case "args": InstructionID id = InstructionID.valueOf(value.split(":")[1]);
						
						String args = value.split(":")[0];
						Instruction inst = new Instruction(args, id);
						instructions.add(inst); break;
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				Macro newMacro = new Macro(instructions, name, author, desc, build);
				MacroManager.addMacro(newMacro);
				System.out.print("Successfully parsed Macro. [Name: " + name + " | Author: " + author + " | Desc: "
						+ desc + " | Build: " + build + "]\n");

				bufferReader.close();
			}

		}

	}

	public static void ExecuteMacro(Macro Macro, BotObject bot) {
		Thread t;

		t = new Thread(new Runnable() {

			@Override
			public void run() {

				for (Instruction inst : Macro.getInstructions()) {
					try {

						Wrapper.Log("Executing macro: " + Macro.getName(), "Macro");

						switch (inst.getInstructionID()) {

						case CHAT:
							bot.getSession().send(new ClientChatPacket(inst.getArgs()));
							Wrapper.Log("Fired macro instruction: " + inst.getArgs(), "Macro");
							Thread.sleep(50);
							break;
						case DISCONNECT:
							bot.getSession().disconnect(bot.getUsername());
							Wrapper.Log("Fired macro instruction: disconnect", "Macro");
							Thread.sleep(50);
							break;
						case MOVE:
							move(bot);
							Wrapper.Log("Fired macro instruction: move", "Macro");
							Thread.sleep(50);
							break;
						case WAIT:
							Thread.sleep(Long.parseLong(inst.getArgs()));
							Wrapper.Log("Fired macro instruction: wait = " + inst.getArgs(), "Macro");
							Thread.sleep(50);
							break;
							
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}

		});

		t.start();

	}

	public static void addMacro(Macro macro) {
		MacroList.add(macro);
		Wrapper.Log("Successfully added macro: " + macro.getName(), "Macro");
	}

	public static ArrayList<Macro> getMacroList() {
		return MacroList;
	}

}
