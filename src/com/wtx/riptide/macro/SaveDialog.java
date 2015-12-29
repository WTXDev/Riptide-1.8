package com.wtx.riptide.macro;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.wtx.riptide.Settings;

import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.awt.event.ActionEvent;

public class SaveDialog {

	private JFrame frmRiptideSave;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveDialog window = new SaveDialog();
					window.frmRiptideSave.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SaveDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRiptideSave = new JFrame();
		frmRiptideSave.setTitle("Riptide - Save Macro");
		frmRiptideSave.setBounds(100, 100, 295, 195);
		frmRiptideSave.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRiptideSave.getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 46, 14);
		frmRiptideSave.getContentPane().add(lblName);
		
		textField = new JTextField();
		textField.setBounds(47, 8, 187, 20);
		frmRiptideSave.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setBounds(10, 39, 46, 14);
		frmRiptideSave.getContentPane().add(lblAuthor);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(57, 36, 187, 20);
		frmRiptideSave.getContentPane().add(textField_1);
		
		JLabel lblDesc = new JLabel("Desc:");
		lblDesc.setBounds(10, 67, 37, 14);
		frmRiptideSave.getContentPane().add(lblDesc);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("UNFINISHED");
		comboBox.addItem("COMPLETE");
		comboBox.addItem("BETA");
		comboBox.setBounds(45, 92, 97, 20);
		frmRiptideSave.getContentPane().add(comboBox);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(47, 64, 187, 20);
		frmRiptideSave.getContentPane().add(textField_2);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				if (((textField.getText() != null) && textField_1.getText() != null) && textField_2.getText() != null)
				{
					if (MacroBuilderWrapper.Instructions.size() != 0)
					{
						File file = new File(Settings.MacrosPath + "\\" + textField.getText() + ".macro");
						
						Writer output = null;
				        try {
							output = new BufferedWriter(new FileWriter(file));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				        
				        try {
							output.write("Name=" + textField.getText() + "\n");
							output.write("Desc=" + textField_2.getText() + "\n");
							output.write("Author=" + textField_1.getText() + "\n");
							output.write("Build: " + comboBox.getSelectedItem().toString() + "\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						for (Instruction inst: MacroBuilderWrapper.Instructions)
						{
							try {
								output.write("args=" + inst.getArgs() + ":" + inst.getInstructionID().toString() + "\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						try {
							output.flush();
							output.close();
							JOptionPane.showMessageDialog(null, "Successfully saved macro: " + textField.getText(), "Macro", JOptionPane.INFORMATION_MESSAGE);
							frmRiptideSave.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		});
		btnSave.setBounds(10, 123, 224, 23);
		frmRiptideSave.getContentPane().add(btnSave);
		
		JLabel lblBuild = new JLabel("Build:");
		lblBuild.setBounds(10, 98, 37, 14);
		frmRiptideSave.getContentPane().add(lblBuild);
	}
}
