package com.wtx.riptide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Random;

import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.ClientRequest;
import org.spacehq.mc.protocol.data.game.values.Face;
import org.spacehq.mc.protocol.data.game.values.entity.player.PlayerAction;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientSwingArmPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerChangeHeldItemPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import org.spacehq.packetlib.event.session.ConnectedEvent;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

import com.wtx.riptide.macro.MacroManager;

public class Riptide extends Wrapper {

	public static double y, x, z;
	public static Proxy PROXY;
	public static int ConnectedCount = 0;
	public static boolean connectVar = true;
	public static boolean BypassSpam = false;
	public static boolean derp = false;
	public static MacroManager macroManager;

	public static void updateConnectionThrottle(String str) {
		Settings.ConnectionThrottle = Integer.parseInt(str);

		Log("Connection Throttle set to: " + Settings.ConnectionThrottle, "Values");
	}

	public void massSendText(String str) {
		for (BotObject bot : Main.Bots) {
			if (bot != null && bot.getSession().isConnected()) {
				if (!BypassSpam) {
					bot.getSession().send(new ClientChatPacket(str));
				} else {
					bot.getSession().send(new ClientChatPacket(str + " | " + new Random().nextInt(10000)));

				}

			}
		}

		Log("Successfully sent '" + str + "'", "Chat");
	}

	public void factionCreateSpam() {
		for (BotObject bot : Main.Bots) {
			if (bot != null && bot.getSession() != null) {
				bot.getSession().send(new ClientChatPacket("/f create RTide" + new Random().nextInt(10000)));
				bot.getSession().send(new ClientChatPacket("/f desc this server is fucking garbage"));

			}
		}
	}

	public static void moveFrom(String axis, BotObject bot) {
		if (axis.equalsIgnoreCase("x")) {
			Riptide.x += 0.15;
			bot.getSession().send(new ClientPlayerPositionPacket(false, x, Riptide.y, Riptide.z));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (axis.equalsIgnoreCase("z")) {
			Riptide.z += 0.15;
			bot.getSession().send(new ClientPlayerPositionPacket(false, Riptide.x, Riptide.y, z));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void move(String direction) {
		for (BotObject bot : Main.Bots) {
			if ((bot.getSession() != null)) {
				if (bot.getSession().isConnected()) {
					for (int i = 0; i < 20; i++) {
						switch (direction) {
						case "X":
							moveFrom("x", bot);
							break;
						case "Z":
							moveFrom("z", bot);
							break;
						}
					}
				}
			}
		}
	}

	public void derp() {
		derp = !derp;

		for (BotObject bot : Main.Bots) {
			if (bot != null && bot.getSession() != null) {

				new Thread() {

					@Override
					public void run() {

						for (int i = 0; i < 1000; i++) {

							bot.getSession().send(new ClientPlayerRotationPacket(true, new Random().nextInt(120) - 60,
									new Random().nextInt(360) - 180));
							bot.getSession().send(new ClientSwingArmPacket());
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								Log("Derp Exception: " + e.getMessage(), "Exception");
							}
						}

					}

				}.start();

			}
		}
	}

	public static void dropItems() {

		new Thread() {

			@Override
			public void run() {
				for (final BotObject bot : Main.Bots) {
					for (int index = 0; index < 9; index++) {
						bot.getSession().send(new ClientPlayerActionPacket(PlayerAction.DROP_ITEM_STACK,
								new Position((int) x, (int) y, (int) z), Face.BOTTOM));
						bot.getSession().send(new ClientChangeHeldItemPacket(index));
						try {
							Thread.sleep(50L);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}.start();

	}

	public static final void validateBotCredentials() {

		new Thread() {

			@Override
			public void run() {
				try {

					if (Main.Accounts.size() != 0) {
						@SuppressWarnings("unused")
						MinecraftProtocol validate = null;

						int nonWorkingCount = 0;
						int workingCount = 0;

						for (Account acc : Main.Accounts) {
							try {
								validate = new MinecraftProtocol(acc.getUsername(), acc.getPassword(), false);
								Log(acc.getUsername() + " was Successfully Validated", "Bot Validate");
								nonWorkingCount++;
							} catch (RequestException e) {
								Main.Bots.remove(acc);
								Log(acc.getUsername()
										+ " was NOT validated, the account may not work or your proxy has been banned.",
										"Bot Validate");
								nonWorkingCount++;
								e.printStackTrace();
							}
						}

						Log(nonWorkingCount + " bots were non-validated, " + workingCount
								+ " were successfully validated", "Bot Validate");
					} else {
						showErrorNotification("Not accounts are loaded");
					}

				} catch (Exception ex) {
					Log(ex.getMessage(), "Exception");
				}
			}

		}.start();
	}

	public static void loadMacros() {
		macroManager = new MacroManager();
	}

	public static void loadFile(final String fileLocation, final ArrayList<Account> array) {
		try {
			final File file = new File(fileLocation);
			@SuppressWarnings("resource")
			final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String readString = "";
			while ((readString = bufferedReader.readLine()) != null) {
				array.add(new Account(readString.split(":")[0], readString.split(":")[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadProxy(final String fileLocation, final ArrayList<Proxy> array) {
		try {
			final File file = new File(fileLocation);
			@SuppressWarnings("resource")
			final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String readString = "";
			while ((readString = bufferedReader.readLine()) != null) {
				// array.add(new Proxy());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect() {
		Settings.ServerIP = getServerIP();
		Gui.keepAlive();

		new Thread() {
			@Override
			public void run() {
				if (Main.Accounts.size() > 0) {

					for (Account account : Main.Accounts) {

						try {

							final BotObject bot;

							if (!(connectVar)) {
								return;
							}

							if (Settings.UseProxy) {

								bot = new BotObject(account.getUsername(), account.getPassword(), getServerIP(),
										getPort(), PROXY);
							} else {
								PROXY = Proxy.NO_PROXY;
								bot = new BotObject(account.getUsername(), account.getPassword(), getServerIP(),
										getPort(), PROXY);
							}

							Main.Bots.add(bot);

							Log("Got session for " + bot.getUsername(), "Session");
							
							bot.addClientListener(new SessionAdapter() {

								@Override
								public void connected(ConnectedEvent event) {
									Log(bot.getUsername() + " Successfully Connected", "Connect Event");
									ConnectedCount++;
									Gui.frame.setTitle("Riptide - " + getServerIP() + " | " + ConnectedCount);

									if (Gui.onConnect.isSelected()) {
										bot.getSession().send(new ClientChatPacket(Gui.textField_4.getText()));
									}

									try {

										if (Gui.activeMacro != null) {
											
											new Thread()
											{
												public void run()
												{
													try {
														Thread.sleep(3000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													MacroManager.ExecuteMacro(Gui.activeMacro, bot);
												}
											}.start();
											
										}

									} catch (Exception e) {
										showErrorNotification("Failed to execute macro");
									}
								}

								@Override
								public void disconnected(DisconnectedEvent event) {
									Log(bot.getUsername() + " Disconnected: " + event.getReason(), "Disconnect Event");
									ConnectedCount--;
									Gui.frame.setTitle(
											"Riptide - " + getServerIP() + " | Active Bots: " + ConnectedCount);
								}

								@Override
								public void packetReceived(PacketReceivedEvent event) {

									if (event.getPacket() instanceof ServerChatPacket) {

										final ServerChatPacket p = event.getPacket();

										String msg = p.getMessage().getFullText();
										// System.out.println(msg);

										for (String str : ChatBlockDetect) {
											if (msg.contains(str)) {
												showErrorNotification(
														"Server possibly has Chat Block, move the players and try again if you cannot send messages");
											}
										}

									} else if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
										x = event.<ServerPlayerPositionRotationPacket> getPacket().getX();
										z = event.<ServerPlayerPositionRotationPacket> getPacket().getZ();
										y = event.<ServerPlayerPositionRotationPacket> getPacket().getY();
									} else if (event.getPacket() instanceof ServerChangeHeldItemPacket) {
										final ServerChangeHeldItemPacket p = event.getPacket();
										bot.getSession().send(new ClientChangeHeldItemPacket(p.getSlot()));
									} else if (event.getPacket() instanceof ServerConfirmTransactionPacket) {
										final ServerConfirmTransactionPacket p = event.getPacket();
										bot.getSession().send(new ClientConfirmTransactionPacket(p.getWindowId(),
												p.getActionId(), p.getAccepted()));
									} else if (event.getPacket() instanceof ServerRespawnPacket) {
										final ServerRespawnPacket p = event.getPacket();
										bot.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
									}

								}

							});

							bot.getSession().connect(true);

							try {
								Thread.sleep(Settings.ConnectionThrottle);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						} catch (RequestException e) {
							e.printStackTrace();
						}

					}

				} else {
					showErrorNotification("Accounts were not found");

					return;
				}

			}
		}.start();
	}

}
