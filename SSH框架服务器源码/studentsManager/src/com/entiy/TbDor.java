package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbDor entity. @author MyEclipse Persistence Tools
 */

public class TbDor implements java.io.Serializable {

	// Fields

	private String droNo;
	private TbGra tbGra;
	private String dorName;
	private Set tbStus = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbDor() {
	}

	/** minimal constructor */
	public TbDor(String droNo) {
		this.droNo = droNo;
	}

	/** full constructor */
	public TbDor(String droNo, TbGra tbGra, String dorName, Set tbStus) {
		this.droNo = droNo;
		this.tbGra = tbGra;
		this.dorName = dorName;
		this.tbStus = tbStus;
	}

	// Property accessors

	public String getDroNo() {
		return this.droNo;
	}

	public void setDroNo(String droNo) {
		this.droNo = droNo;
	}

	public TbGra getTbGra() {
		return this.tbGra;
	}

	public void setTbGra(TbGra tbGra) {
		this.tbGra = tbGra;
	}

	public String getDorName() {
		return this.dorName;
	}

	public void setDorName(String dorName) {
		this.dorName = dorName;
	}

	public Set getTbStus() {
		return this.tbStus;
	}

	public void setTbStus(Set tbStus) {
		this.tbStus = tbStus;
	}

}