package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbDep entity. @author MyEclipse Persistence Tools
 */

public class TbDep implements java.io.Serializable {

	// Fields

	private String depNo;
	private String depName;
	private Set tbTeas = new HashSet(0);
	private Set tbPros = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbDep() {
	}

	/** minimal constructor */
	public TbDep(String depNo) {
		this.depNo = depNo;
	}

	/** full constructor */
	public TbDep(String depNo, String depName, Set tbTeas, Set tbPros) {
		this.depNo = depNo;
		this.depName = depName;
		this.tbTeas = tbTeas;
		this.tbPros = tbPros;
	}

	// Property accessors

	public String getDepNo() {
		return this.depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}

	public String getDepName() {
		return this.depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Set getTbTeas() {
		return this.tbTeas;
	}

	public void setTbTeas(Set tbTeas) {
		this.tbTeas = tbTeas;
	}

	public Set getTbPros() {
		return this.tbPros;
	}

	public void setTbPros(Set tbPros) {
		this.tbPros = tbPros;
	}

}