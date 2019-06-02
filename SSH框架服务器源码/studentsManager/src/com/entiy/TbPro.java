package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbPro entity. @author MyEclipse Persistence Tools
 */

public class TbPro implements java.io.Serializable {

	// Fields

	private String proNo;
	private TbDep tbDep;
	private String proName;
	private Set tbGras = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbPro() {
	}

	/** minimal constructor */
	public TbPro(String proNo) {
		this.proNo = proNo;
	}

	/** full constructor */
	public TbPro(String proNo, TbDep tbDep, String proName, Set tbGras) {
		this.proNo = proNo;
		this.tbDep = tbDep;
		this.proName = proName;
		this.tbGras = tbGras;
	}

	// Property accessors

	public String getProNo() {
		return this.proNo;
	}

	public void setProNo(String proNo) {
		this.proNo = proNo;
	}

	public TbDep getTbDep() {
		return this.tbDep;
	}

	public void setTbDep(TbDep tbDep) {
		this.tbDep = tbDep;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Set getTbGras() {
		return this.tbGras;
	}

	public void setTbGras(Set tbGras) {
		this.tbGras = tbGras;
	}

}