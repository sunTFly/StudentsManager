package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbGra entity. @author MyEclipse Persistence Tools
 */

public class TbGra implements java.io.Serializable {

	// Fields

	private String garNo;
	private TbPro tbPro;
	private String garName;
	private Set tbDors = new HashSet(0);
	private Set tbStus = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbGra() {
	}

	/** minimal constructor */
	public TbGra(String garNo) {
		this.garNo = garNo;
	}

	/** full constructor */
	public TbGra(String garNo, TbPro tbPro, String garName, Set tbDors,
			Set tbStus) {
		this.garNo = garNo;
		this.tbPro = tbPro;
		this.garName = garName;
		this.tbDors = tbDors;
		this.tbStus = tbStus;
	}

	// Property accessors

	public String getGarNo() {
		return this.garNo;
	}

	public void setGarNo(String garNo) {
		this.garNo = garNo;
	}

	public TbPro getTbPro() {
		return this.tbPro;
	}

	public void setTbPro(TbPro tbPro) {
		this.tbPro = tbPro;
	}

	public String getGarName() {
		return this.garName;
	}

	public void setGarName(String garName) {
		this.garName = garName;
	}

	public Set getTbDors() {
		return this.tbDors;
	}

	public void setTbDors(Set tbDors) {
		this.tbDors = tbDors;
	}

	public Set getTbStus() {
		return this.tbStus;
	}

	public void setTbStus(Set tbStus) {
		this.tbStus = tbStus;
	}

}