package com.jeanjulien.boucheron.booter.model;

import com.jeanjulien.boucheron.booter.model.database.DataBaseHelper;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Computer  implements Serializable {

	private static final long serialVersionUID = -7202376202435861589L;
	private long id=-1;
	private String name;
	private String macAddress;
	private int wolPort;
	private Network network;
	private DataBaseHelper dataBaseHelper =null;

	public Computer(String name, String macAddress, int port, Network network) {
		this(-1, name,macAddress,port,network);
	}

	public Computer(long id,String name, String macAddress, int port, Network network) {
		this.id = id;
		this.name = name;
		this.macAddress = macAddress;
		this.wolPort = port;
		this.network = network;
	}

	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id = id;
	}

	/**
	 * Boots the application
	 */
	public void boot() {
		try {
			byte[] macBytes = getMacBytes(macAddress);
			byte[] bytes = new byte[6 + 16 * macBytes.length];
			for (int i = 0; i < 6; i++) {
				bytes[i] = (byte) 0xff;
			}
			for (int i = 6; i < bytes.length; i += macBytes.length) {
				System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
			}

			InetAddress address = InetAddress.getByName(network.getIpPlage());
			DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, wolPort);
			DatagramSocket socket = new DatagramSocket();
			for (int i = 0; i < 40; i++) {
				socket.send(packet);
			}
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Get the byte array from the string mac address
	 * @param macStr
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
		byte[] bytes = new byte[6];
		String[] hex = macStr.split("(\\:|\\-)");
		if (hex.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address.");
		}
		try {
			for (int i = 0; i < 6; i++) {
				bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid hex digit in MAC address.");
		}
		return bytes;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getWolPort() {
		return wolPort;
	}

	public void setWolPort(int wolPort) {
		this.wolPort = wolPort;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}




}
