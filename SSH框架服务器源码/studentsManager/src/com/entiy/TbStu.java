package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbStu entity. @author MyEclipse Persistence Tools
 */

public class TbStu implements java.io.Serializable {

	// Fields

	private String stuNo;
	private TbGra tbGra;
	private TbDor tbDor;
	private String stuName;
	private String stuSex;
	private Integer age;
	private String stuTel;
	private String stuAdr;
	private String stuPw;
	private String stuFeature;
	private String stuCharHead;
	private Integer stuState;
	private Set tbLeas = new HashSet(0);
	private Set tbSignins = new HashSet(0);
	private Set tbChos = new HashSet(0);
	private Set tbBacks = new HashSet(0);
	private Set tbHomes = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbStu() {
	}

	/** minimal constructor */
	public TbStu(String stuNo) {
		this.stuNo = stuNo;
	}

	/** full constructor */
	public TbStu(String stuNo, TbGra tbGra, TbDor tbDor, String stuName,
			String stuSex, Integer age, String stuTel, String stuAdr,
			String stuPw, String stuFeature, String stuCharHead,
			Integer stuState, Set tbLeas, Set tbSignins, Set tbChos,
			Set tbBacks, Set tbHomes) {
		this.stuNo = stuNo;
		this.tbGra = tbGra;
		this.tbDor = tbDor;
		this.stuName = stuName;
		this.stuSex = stuSex;
		this.age = age;
		this.stuTel = stuTel;
		this.stuAdr = stuAdr;
		this.stuPw = stuPw;
		this.stuFeature = stuFeature;
		this.stuCharHead = stuCharHead;
		this.stuState = stuState;
		this.tbLeas = tbLeas;
		this.tbSignins = tbSignins;
		this.tbChos = tbChos;
		this.tbBacks = tbBacks;
		this.tbHomes = tbHomes;
	}

	// Property accessors

	public String getStuNo() {
		return this.stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public TbGra getTbGra() {
		return this.tbGra;
	}

	public void setTbGra(TbGra tbGra) {
		this.tbGra = tbGra;
	}

	public TbDor getTbDor() {
		return this.tbDor;
	}

	public void setTbDor(TbDor tbDor) {
		this.tbDor = tbDor;
	}

	public String getStuName() {
		return this.stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuSex() {
		return this.stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getStuTel() {
		return this.stuTel;
	}

	public void setStuTel(String stuTel) {
		this.stuTel = stuTel;
	}

	public String getStuAdr() {
		return this.stuAdr;
	}

	public void setStuAdr(String stuAdr) {
		this.stuAdr = stuAdr;
	}

	public String getStuPw() {
		return this.stuPw;
	}

	public void setStuPw(String stuPw) {
		this.stuPw = stuPw;
	}

	public String getStuFeature() {
		return this.stuFeature;
	}

	public void setStuFeature(String stuFeature) {
		this.stuFeature = stuFeature;
	}

	public String getStuCharHead() {
		return this.stuCharHead;
	}

	public void setStuCharHead(String stuCharHead) {
		this.stuCharHead = stuCharHead;
	}

	public Integer getStuState() {
		return this.stuState;
	}

	public void setStuState(Integer stuState) {
		this.stuState = stuState;
	}

	public Set getTbLeas() {
		return this.tbLeas;
	}

	public void setTbLeas(Set tbLeas) {
		this.tbLeas = tbLeas;
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

	public Set getTbBacks() {
		return this.tbBacks;
	}

	public void setTbBacks(Set tbBacks) {
		this.tbBacks = tbBacks;
	}

	public Set getTbHomes() {
		return this.tbHomes;
	}

	public void setTbHomes(Set tbHomes) {
		this.tbHomes = tbHomes;
	}

}