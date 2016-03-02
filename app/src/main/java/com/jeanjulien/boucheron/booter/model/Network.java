package com.jeanjulien.boucheron.booter.model;

import java.io.Serializable;

/**
 * The network class defines the name and the ip range of address where to broadcast
 */
public class Network implements Serializable {

    /**
     * For serialisation
     */
    private static final long serialVersionUID = 8468618588352607882L;
    // the id of the network
    private long id;
    private String name;
    private String ipRange;

    /**
     * Creates a default network
     *
     * @param name    Name of the network
     * @param ipRange Ip range of the network
     */
    public Network(String name, String ipRange) {
        this(-1, name, ipRange);
    }

    /**
     * Constructor for db linked networks
     *
     * @param id      DB id of the network
     * @param name    Name of the network
     * @param ipRange Ip range where the network will be broadcast
     */
    public Network(long id, String name, String ipRange) {
        this.id = id;
        this.name = name;
        this.ipRange = ipRange;

    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }


}
