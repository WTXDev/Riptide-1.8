package com.wtx.riptide;

import javax.swing.JOptionPane;

public class Wrapper {

	private static Wrapper instance = new Wrapper();

	public static final Wrapper getInstance() {
		return instance;
	}

	public static String[] ChatBlockDetect = { "You must move before chatting!" };

	public static void Log(String msg, String cause) {
		System.out.print("[" + cause + "] " + msg + "\n");
		Gui.console.addElement("[" + cause + "] " + msg);
	}

	public static int getPort() {
		return Integer.parseInt(Gui.textField_1.getText());
	}

	public static void showErrorNotification(String data) {
		JOptionPane.showMessageDialog(null, "Error: " + data, "Error Message", JOptionPane.ERROR_MESSAGE);
	}

	public static String getServerIP() {
		if (Gui.textField.getText() != null) {
			return Gui.textField.getText();
		} else {
			showErrorNotification("Server IP is null");

			return "";

		}

	}

}
