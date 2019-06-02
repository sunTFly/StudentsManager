package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbCla entity. @author MyEclipse Persistence Tools
 */

public class TbCla implements java.io.Serializable {

	// Fields

	private String claNo;
	private String claName;
	private Integer weekNum;
	private String term;
	private Set tbSignins = new HashSet(0);
	private Set tbChos = new HashSet(0);
	private Set tbAsClasses = new HashSet(0);
	private Set tbWeeks = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbCla() {
	}

	/** minimal constructor */
	public TbCla(String claNo) {
		this.claNo = claNo;
	}

	/** full constructor */
	public TbCla(String claNo, String claName, Integer weekNum, String term,
			Set tbSignins, Set tbChos, Set tbAsClasses, Set tbWeeks) {
		this.claNo = claNo;
		this.claName = claName;
		this.weekNum = weekNum;
		this.term = term;
		this.tbSignins = tbSignins;
		this.tbChos = tbChos;
		this.tbAsClasses = tbAsClasses;
		this.tbWeeks = tbWeeks;
	}

	// Property accessors

	public String getClaNo() {
		return this.claNo;
	}

	public void setClaNo(String claNo) {
		this.claNo = claNo;
	}

	public String getClaName() {
		return this.claName;
	}

	public void setClaName(String claName) {
		this.claName = claName;
	}

	public Integer getWeekNum() {
		return this.weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Set getTbSignins() {
		return this.tbSignins;
	}

	public void setTbSignins(Set tbSignins) {
		this.tbSignins = tbSignins;
	}

	public Set getTbChos() {
		return this.tbChos;
	}

	public void setTbChos(Set tbChos) {
		this.tbChos = tbChos;
	}

	public Set getTbAsClasses() {
		return this.tbAsClasses;
	}

	public void setTbAsClasses(Set tbAsClasses) {
		this.tbAsClasses = tbAsClasses;
	}

	public Set getTbWeeks() {
		return this.tbWeeks;
	}

	public void setTbWeeks(Set tbWeeks) {
		this.tbWeeks = tbWeeks;
	}

}