package com.jeanjulien.boucheron.booter.controller;

import android.content.Context;

import com.jeanjulien.boucheron.booter.model.Computer;
import com.jeanjulien.boucheron.booter.model.Network;
import com.jeanjulien.boucheron.booter.model.database.ComputerLoader;

import java.util.ArrayList;
import java.util.List;


public class AppController {

	private static AppController appController = null;
	private List<Network>  networks = new ArrayList<Network>();
	private List<Computer> computers = new ArrayList<Computer>();
	Context context ;

	private AppController(Context context) {
		this.context =  context;
		loadConfiguration();
	}

	public static AppController getInstance(Context context) {
		appController = appController == null ? new AppController(context) : appController;

		return appController;
	}

	public List<Network> getNetworks() {

		return networks;
	}

	public Network addNetwork(String name, String ipPlage) {
		Network network = new Network(1,name, ipPlage);
		networks.add(network);
		return network;
	}

	public void removeNetwork(Network network){
		networks.remove(network);

	}

	public void removeComputer(Computer computer){
		computers.remove(computer);
		ComputerLoader.removeComputer(context,computer.getId());
	}


	public Computer addComputer(String name, String macAddress, int port, Network network) {
		Computer computer = new Computer(name, macAddress, port, network);
		computers.add(computer);
		ComputerLoader.save(context,computer);

		return computer;
	}

	public void setNetworks(List<Network> networks) {
		this.networks = networks;
	}

	public List<Computer> getComputers() {
		return computers;
	}



	private <T> void  saveConfiguration(List<T> objectToWrite) {

	}

	private void loadConfiguration() {

		Network network = this.addNetwork("Home", "192.168.1.255");
		if(networks.size()!=0){
			computers = ComputerLoader.loadComputers(this.context, networks.get(0));
		}
	}


}
