package com.entiy;

import java.sql.Timestamp;

/**
 * TbHome entity. @author MyEclipse Persistence Tools
 */

public class TbHome implements java.io.Serializable {

	// Fields

	private Integer homeNo;
	private TbStu tbStu;
	private String homeAda;
	private Integer homeState;
	private Timestamp homeTime;

	// Constructors

	/** default constructor */
	public TbHome() {
	}

	/** full constructor */
	public TbHome(TbStu tbStu, String homeAda, Integer homeState,
			Timestamp homeTime) {
		this.tbStu = tbStu;
		this.homeAda = homeAda;
		this.homeState = homeState;
		this.homeTime = homeTime;
	}

	// Property accessors

	public Integer getHomeNo() {
		return this.homeNo;
	}

	public void setHomeNo(Integer homeNo) {
		this.homeNo = homeNo;
	}

	public TbStu getTbStu() {
		return this.tbStu;
	}

	public void setTbStu(TbStu tbStu) {
		this.tbStu = tbStu;
	}

	public String getHomeAda() {
		return this.homeAda;
	}

	public void setHomeAda(String homeAda) {
		this.homeAda = homeAda;
	}

	public Integer getHomeState() {
		return this.homeState;
	}

	public void setHomeState(Integer homeState) {
		this.homeState = homeState;
	}

	public Timestamp getHomeTime() {
		return this.homeTime;
	}

	public void setHomeTime(Timestamp homeTime) {
		this.homeTime = homeTime;
	}

}