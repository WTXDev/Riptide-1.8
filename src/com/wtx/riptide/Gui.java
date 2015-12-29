package com.wtx.riptide;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;

import com.wtx.riptide.macro.Instruction;
import com.wtx.riptide.macro.InstructionID;
import com.wtx.riptide.macro.Macro;
import com.wtx.riptide.macro.MacroBuilderWrapper;
import com.wtx.riptide.macro.MacroManager;
import com.wtx.riptide.macro.SaveDialog;

public class Gui extends Riptide {

	public static JFrame frame;
	static JTextField textField;
	static JTextField textField_1;
	private JTextField textField_3;
	private JScrollPane jScrollPane;
	public static JLabel name;
	public static JLabel author;
	public static JLabel desc;
	public static JLabel connectionLbl;
	public static JCheckBox onConnect;
	public static JComboBox botDir;
	@SuppressWarnings("rawtypes")
	public JComboBox jComboBox;
	public static DefaultListModel<Object> console = new DefaultListModel<Object>();
	public static DefaultListModel<Object> InstructionList = new DefaultListModel<Object>();
	public static Macro activeMacro;

	// public MacroManager macroManager;
	private JTextField textField_2;
	static JTextField textField_4;

	public static JComboBox macroBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			@SuppressWarnings("static-access")
			public void run() {
				try {
					Gui window = new Gui();
					Gui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public Gui() throws IOException {
		initialize();
		addInstructionIDS(jComboBox);

		if (Settings.ServerIP != "") {
			textField.setText(Settings.ServerIP);
		}

		macroBox = new JComboBox();
		macroBox.setBounds(10, 340, 465, 27);
		frame.getContentPane().add(macroBox);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 318, 465, 2);
		frame.getContentPane().add(separator);

		JLabel lblRiptideMacros = new JLabel("Riptide Macros");
		lblRiptideMacros.setBounds(10, 320, 212, 14);
		frame.getContentPane().add(lblRiptideMacros);

		JButton btnNewButton_1 = new JButton("Refresh");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				MacroManager.refreshMacros(macroBox);

			}
		});
		btnNewButton_1.setBounds(10, 368, 161, 23);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnSelectMacro = new JButton("Select Macro");
		btnSelectMacro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					activeMacro = MacroManager.getByName(macroBox.getSelectedItem().toString());
					name.setText("Name: " + activeMacro.getName());
					author.setText("Author: " + activeMacro.getAuthor());
					desc.setText("Desc: " + activeMacro.getDescription());

				} catch (Exception ex) {
					showErrorNotification("Macro was invalid or null! Try refreshing");
				}
			}
		});
		btnSelectMacro.setBounds(10, 389, 161, 23);
		frame.getContentPane().add(btnSelectMacro);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 456, 465, 2);
		frame.getContentPane().add(separator_1);

		JCheckBox chckbxLoop = new JCheckBox("Loop");
		chckbxLoop.setBounds(10, 411, 147, 23);
		frame.getContentPane().add(chckbxLoop);

		JLabel lblMacroInformation = new JLabel("Macro Information");
		lblMacroInformation.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblMacroInformation.setBounds(185, 376, 210, 14);
		frame.getContentPane().add(lblMacroInformation);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(185, 389, 212, 2);
		frame.getContentPane().add(separator_2);

		name = new JLabel("Name:");
		name.setBounds(185, 393, 290, 14);
		frame.getContentPane().add(name);

		author = new JLabel("Author:");
		author.setBounds(185, 410, 284, 14);
		frame.getContentPane().add(author);

		desc = new JLabel("Description:");
		desc.setBounds(185, 425, 290, 14);
		frame.getContentPane().add(desc);

		onConnect = new JCheckBox("Send on Join");
		onConnect.setBounds(261, 169, 141, 23);
		frame.getContentPane().add(onConnect);

		botDir = new JComboBox();
		botDir.addItem("X");
		botDir.addItem("Z");
		botDir.setBounds(167, 685, 40, 23);
		frame.getContentPane().add(botDir);

		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Riptide.connectVar = !Riptide.connectVar;
			}
		});
		btnPause.setBounds(346, 600, 129, 27);
		frame.getContentPane().add(btnPause);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(452, 368, 332, 0);
		frame.getContentPane().add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(534, 365, 1, 2);
		frame.getContentPane().add(separator_4);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(495, 706, 364, 2);
		frame.getContentPane().add(separator_5);
		loadMacros();
	}

	@SuppressWarnings("unchecked")
	public static void addInstructionIDS(@SuppressWarnings("rawtypes") JComboBox jcb) {
		jcb.addItem("CHAT");
		jcb.addItem("WAIT");
		jcb.addItem("DISCONNECT");
		jcb.addItem("MOVE");
	}

	public static void keepAlive() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000L);

						for (BotObject bot : Main.Bots) {
							if (bot != null) {
								if (bot.getSession() != null) {
									bot.getSession().send(new ClientKeepAlivePacket(1));
								}
							}
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 885, 748);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Riptide");
		frame.getContentPane().setLayout(null);

		JLabel lblInstructionList = new JLabel("Instruction List");
		lblInstructionList.setBounds(495, 98, 315, 16);
		frame.getContentPane().add(lblInstructionList);

		JButton btnSaveMacro = new JButton("Save");
		btnSaveMacro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SaveDialog.main(null);

			}
		});
		btnSaveMacro.setBounds(495, 672, 95, 23);
		frame.getContentPane().add(btnSaveMacro);

		JButton btnReloadMacros = new JButton("Reload Macros");
		btnReloadMacros.setBounds(600, 672, 125, 23);
		frame.getContentPane().add(btnReloadMacros);

		JButton btnLoadAndEdit = new JButton("Load and Edit");
		btnLoadAndEdit.setBounds(735, 672, 124, 23);
		frame.getContentPane().add(btnLoadAndEdit);

		JButton btnDropItems = new JButton("Drop Inventory");
		btnDropItems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				dropItems();

			}
		});
		btnDropItems.setToolTipText("Spam /f create messages");
		btnDropItems.setBounds(10, 651, 147, 23);
		frame.getContentPane().add(btnDropItems);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 638, 465, 2);
		frame.getContentPane().add(separator);

		JButton btnImport_1 = new JButton("Import");
		btnImport_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				final JFileChooser fileBrowser = new JFileChooser();
				final int button = fileBrowser.showOpenDialog(frame);
				if (button == 0) {
					loadFile(String.valueOf(fileBrowser.getCurrentDirectory().toString()) + "\\"
							+ fileBrowser.getSelectedFile().getName(), Main.Accounts);
					Log("Successfully imported " + Main.Accounts.size() + " accounts", "Import");
				}

			}
		});
		btnImport_1.setBounds(138, 91, 132, 20);
		frame.getContentPane().add(btnImport_1);

		JButton btnImport_2 = new JButton("Import");
		btnImport_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/*
				 * final JFileChooser fileBrowser = new JFileChooser(); final
				 * int button = fileBrowser.showOpenDialog(frame); if (button ==
				 * 0) {
				 * loadProxy(String.valueOf(fileBrowser.getCurrentDirectory().
				 * toString()) + "\\" + fileBrowser.getSelectedFile().getName(),
				 * Main.Proxies); Log("Successfully imported " +
				 * Main.Accounts.size() + " proxies", "Import"); }
				 */

				showErrorNotification("Currently not implemented, it will be soon!");
			}
		});
		btnImport_2.setBounds(10, 138, 132, 20);
		frame.getContentPane().add(btnImport_2);

		textField_4 = new JTextField();
		textField_4.setToolTipText("Input: Server Address");
		textField_4.setColumns(10);
		textField_4.setBounds(11, 169, 252, 22);
		frame.getContentPane().add(textField_4);

		JLabel lblRiptideBuild = new JLabel("Riptide Build: 1.8 alpha");
		lblRiptideBuild.setBounds(138, 11, 212, 14);
		frame.getContentPane().add(lblRiptideBuild);

		JLabel lblServer = new JLabel("Server:");
		lblServer.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblServer.setBounds(10, 237, 80, 27);
		frame.getContentPane().add(lblServer);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(10, 25, 465, 2);
		frame.getContentPane().add(separator);

		textField = new JTextField();
		textField.setToolTipText("Input: Server Address");
		textField.setBounds(76, 241, 161, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel(":");
		label.setToolTipText("Input: Server Port");
		label.setFont(new Font("Calibri", Font.PLAIN, 20));
		label.setBounds(247, 243, 15, 14);
		frame.getContentPane().add(label);

		textField_1 = new JTextField();
		textField_1.setText("25565");
		textField_1.setBounds(257, 241, 46, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("Set");
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				updateConnectionThrottle(connectionLbl.getText());
				Settings.ServerIP = textField.getText();

			}
		});

		btnNewButton.setBounds(313, 240, 89, 23);
		frame.getContentPane().add(btnNewButton);

		JButton connect = new JButton("Connect");
		connect.setBounds(10, 600, 189, 27);

		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				connect();
			}
		});

		frame.getContentPane().add(connect);

		JButton btnNewButton_2 = new JButton("Disconnect");
		btnNewButton_2.setBounds(200, 600, 147, 27);

		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				connectVar = false;
				if (Main.Bots.size() > 0) {
					for (BotObject bot : Main.Bots) {
						if ((bot != null) && bot.getSession() != null && (bot.getSession().isConnected())) {
							bot.getSession().disconnect(bot.getUsername());
						}
					}

					Main.Bots.clear();

				} else {
					showErrorNotification("Bot count is 0");
				}

			}

		});
		frame.getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("Settings and Values");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 36, 211, 27);
		frame.getContentPane().add(lblNewLabel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 275, 465, 2);
		frame.getContentPane().add(separator_1);

		JLabel lblNewLabel_1 = new JLabel("Accounts: 0");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 66, 112, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblProxies = new JLabel("Proxies: 0");
		lblProxies.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblProxies.setBounds(10, 122, 118, 14);
		frame.getContentPane().add(lblProxies);

		JButton btnImport = new JButton("Pastebin Import");
		btnImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				URL oracle = null;
				try {
					oracle = new URL(Settings.PasteBin);
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BufferedReader in = null;
				try {
					in = new BufferedReader(new InputStreamReader(oracle.openStream()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String inputLine;
				try {
					while ((inputLine = in.readLine()) != null) {
						Main.Accounts.add(new Account(inputLine.split(":")[0], inputLine.split(":")[1]));
					}
					lblNewLabel_1.setText("Accounts: " + Main.Accounts.size() + "");
					Log("Imported " + Main.Accounts.size() + " from alts list", "Database Import");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					in.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnImport.setBounds(10, 91, 132, 20);
		frame.getContentPane().add(btnImport);

		JButton button1 = new JButton("Import");
		button1.setBounds(138, 138, 132, 20);
		frame.getContentPane().add(btnImport_2);

		JCheckBox chckbxProxyEnabled = new JCheckBox("Proxy Enabled");
		chckbxProxyEnabled.setBounds(148, 137, 141, 23);
		frame.getContentPane().add(chckbxProxyEnabled);

		JCheckBox chckbxBypassSpamFilter = new JCheckBox("Bypass Spam Filter");
		chckbxBypassSpamFilter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BypassSpam = chckbxBypassSpamFilter.isSelected();
				System.out.print(BypassSpam);
			}

		});
		chckbxBypassSpamFilter.setBounds(332, 288, 147, 23);
		frame.getContentPane().add(chckbxBypassSpamFilter);

		JSlider slider = new JSlider(0, 0, 6000, Settings.ConnectionThrottle);

		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {

				connectionLbl.setText(slider.getValue() + "");
			}

		});

		slider.setBounds(163, 203, 200, 23);
		frame.getContentPane().add(slider);

		JLabel lblSessionThrottleDelay = new JLabel("Connect Throttle Delay:");
		lblSessionThrottleDelay.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblSessionThrottleDelay.setBounds(10, 208, 161, 14);
		frame.getContentPane().add(lblSessionThrottleDelay);

		connectionLbl = new JLabel("1000");
		connectionLbl.setFont(new Font("Calibri", Font.PLAIN, 16));
		connectionLbl.setBounds(365, 206, 46, 14);
		frame.getContentPane().add(connectionLbl);

		JButton btnNewButton_3 = new JButton("Send To Server");
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					massSendText(textField_3.getText());

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		btnNewButton_3.setToolTipText("Send a command or text message to the server");
		btnNewButton_3.setBounds(185, 288, 147, 23);
		frame.getContentPane().add(btnNewButton_3);

		textField_3 = new JTextField();
		textField_3.setToolTipText("Input: Server Address");
		textField_3.setColumns(10);
		textField_3.setBounds(10, 288, 174, 22);
		frame.getContentPane().add(textField_3);

		jScrollPane = new JScrollPane();
		jScrollPane.setBounds(10, 469, 465, 120);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setFont(new Font("Calibri", 0, 10));
		jScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		frame.getContentPane().add(jScrollPane);

		JList list = new JList();
		jScrollPane.setViewportView(list);
		list.setModel(Gui.console);

		JButton btnFactionSpam = new JButton("FactionSpam");
		btnFactionSpam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				factionCreateSpam();

			}
		});
		btnFactionSpam.setToolTipText("Spam /f create messages");
		btnFactionSpam.setBounds(301, 651, 147, 23);
		frame.getContentPane().add(btnFactionSpam);

		JButton btnDerp = new JButton("Derp");
		btnDerp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				derp();
			}
		});
		btnDerp.setToolTipText("Send a command or text message to the server");
		btnDerp.setBounds(156, 651, 147, 23);
		frame.getContentPane().add(btnDerp);

		// TODO Movement

		JButton btnMove_1 = new JButton("Move Bots");
		btnMove_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					Riptide.move(botDir.getSelectedItem().toString());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnMove_1.setToolTipText("Send a command or text message to the server");
		btnMove_1.setBounds(10, 685, 147, 23);
		frame.getContentPane().add(btnMove_1);

		JButton btnNewButton_4 = new JButton("Validate Bot Credentials");
		btnNewButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				validateBotCredentials();

			}
		});
		btnNewButton_4.setToolTipText("Validate all bot credentials");
		btnNewButton_4.setBounds(268, 91, 201, 20);
		frame.getContentPane().add(btnNewButton_4);

		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(485, 11, 50, 703);
		frame.getContentPane().add(separator_3);

		JLabel lblRiptideMacro = new JLabel("Riptide - Macro Builder");
		lblRiptideMacro.setBounds(569, 10, 300, 16);
		frame.getContentPane().add(lblRiptideMacro);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(495, 25, 364, 2);
		frame.getContentPane().add(separator_4);

		JButton btnAddInstruction = new JButton("Add Instruction");
		btnAddInstruction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String instArgs = textField_2.getText();
				InstructionID id = MacroBuilderWrapper.getIdByName(jComboBox.getSelectedItem().toString());

				if (instArgs.length() > 1) {

					Instruction inst = new Instruction(instArgs, id);
					MacroBuilderWrapper.addInstruction(inst);
					InstructionList.addElement("[Index: " + MacroBuilderWrapper.Instructions.indexOf(inst) + " , Args: "
							+ instArgs + " , ID: " + id.toString() + "]");
					Log("Successfully added Instruction [ Args: " + instArgs + " , ID: " + id.toString() + " . Index: "
							+ MacroBuilderWrapper.Instructions.indexOf(inst) + "]", "Macro Builder");
				} else {
					showErrorNotification("All fields' aren't filled out.");
				}

			}
		});

		btnAddInstruction.setBounds(495, 70, 364, 23);
		frame.getContentPane().add(btnAddInstruction);

		jComboBox = new JComboBox();
		jComboBox.setBounds(764, 37, 95, 27);
		frame.getContentPane().add(jComboBox);

		textField_2 = new JTextField();
		textField_2.setBounds(505, 40, 249, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JScrollPane iscrollPane = new JScrollPane();
		iscrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		iscrollPane.setFont(new Font("Calibri", Font.PLAIN, 10));
		iscrollPane.setBounds(495, 112, 364, 549);
		iscrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()

		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});

		JList iList = new JList();
		iList.setModel(Gui.InstructionList);
		iscrollPane.setViewportView(iList);
		frame.getContentPane().add(iscrollPane);

	}
}
