package com.jeanjulien.boucheron.booter.model;

import android.provider.BaseColumns;

import com.jeanjulien.boucheron.booter.model.database.DataBaseHelper;

import java.io.Serializable;

public class Network implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8468618588352607882L;
	private long id;
	private String name;
	private String ipPlage;

	public Network(String name, String ipPlage) {
		this(-1, name,ipPlage);
	}



	public Network(long id,String name, String ipPlage) {
		this.id = id;
		this.name = name;
		this.ipPlage = ipPlage;

	}


	public long getId(){
		return id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpPlage() {
		return ipPlage;
	}

	public void setIpPlage(String ipPlage) {
		this.ipPlage = ipPlage;
	}






}
