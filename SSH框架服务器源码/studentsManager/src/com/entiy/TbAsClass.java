package com.entiy;

/**
 * TbAsClass entity. @author MyEclipse Persistence Tools
 */

public class TbAsClass implements java.io.Serializable {

	// Fields

	private Integer asNo;
	private TbGra tbGra;
	private TbCla tbCla;
	private TbTea tbTea;
	private Integer section;
	private Integer week;

	// Constructors

	/** default constructor */
	public TbAsClass() {
	}

	/** full constructor */
	public TbAsClass(TbGra tbGra, TbCla tbCla, TbTea tbTea, Integer section,
			Integer week) {
		this.tbGra = tbGra;
		this.tbCla = tbCla;
		this.tbTea = tbTea;
		this.section = section;
		this.week = week;
	}

	// Property accessors

	public Integer getAsNo() {
		return this.asNo;
	}

	public void setAsNo(Integer asNo) {
		this.asNo = asNo;
	}

	public TbGra getTbGra() {
		return this.tbGra;
	}

	public void setTbGra(TbGra tbGra) {
		this.tbGra = tbGra;
	}

	public TbCla getTbCla() {
		return this.tbCla;
	}

	public void setTbCla(TbCla tbCla) {
		this.tbCla = tbCla;
	}

	public TbTea getTbTea() {
		return this.tbTea;
	}

	public void setTbTea(TbTea tbTea) {
		this.tbTea = tbTea;
	}

	public Integer getSection() {
		return this.section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public Integer getWeek() {
		return this.week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

}