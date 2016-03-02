package com.jeanjulien.boucheron.booter.controller;

import android.content.Context;

import com.jeanjulien.boucheron.booter.model.Computer;
import com.jeanjulien.boucheron.booter.model.Network;
import com.jeanjulien.boucheron.booter.model.database.ComputerLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to keep data model coherent it's only access point to the data management.
 */
public class AppController {

    private static AppController appController = null;
    private List<Network> networks = new ArrayList<>();
    private List<Computer> computers = new ArrayList<>();
    Context context;

    private AppController(Context context) {
        this.context = context;
        loadConfiguration();
    }

    public static AppController getInstance(Context context) {
        appController = appController == null ? new AppController(context) : appController;

        return appController;
    }

    public List<Network> getNetworks() {

        return networks;
    }

    /**
     * Adds a network to the current NetworkList
     *
     * @param name    The name of the network to add
     * @param ipRange The ip range of the network to add
     * @return The newly created network.
     */
    public Network addNetwork(String name, String ipRange) {
        // creates a network with the given datas
        Network network = new Network(1, name, ipRange);
        // adds this network to the current network collection
        networks.add(network);
        return network;
    }

    /**
     * Removes the given network from the network collection
     *
     * @param network
     * @// TODO: 02/03/2016 should delete it
     */
    public void removeNetwork(Network network) {
        networks.remove(network);
    }


    public void removeComputer(Computer computer) {
        computers.remove(computer);
        ComputerLoader.removeComputer(context, computer.getId());
    }


    public Computer addComputer(String name, String macAddress, int port, Network network) {
        Computer computer = new Computer(name, macAddress, port, network);
        computers.add(computer);
        ComputerLoader.save(context, computer);

        return computer;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<Computer> getComputers() {
        return computers;
    }


    private <T> void saveConfiguration(List<T> objectToWrite) {

    }

    private void loadConfiguration() {

        Network network = this.addNetwork("Home", "192.168.1.255");
        if (networks.size() != 0) {
            computers = ComputerLoader.loadComputers(this.context, networks.get(0));
        }
    }


}
