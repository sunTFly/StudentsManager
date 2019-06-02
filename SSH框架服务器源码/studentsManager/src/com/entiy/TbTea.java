package com.entiy;

import java.util.HashSet;
import java.util.Set;

/**
 * TbTea entity. @author MyEclipse Persistence Tools
 */

public class TbTea implements java.io.Serializable {

	// Fields

	private String teaNo;
	private TbDep tbDep;
	private String teaName;
	private String teaSex;
	private Integer age;
	private Integer permission;
	private String teaTel;
	private String teaPw;
	private Double longitude;
	private Double latitude;
	private Integer signstate;
	private Set tbAsClasses = new HashSet(0);
	private Set tbChos = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbTea() {
	}

	/** minimal constructor */
	public TbTea(String teaNo) {
		this.teaNo = teaNo;
	}

	/** full constructor */
	public TbTea(String teaNo, TbDep tbDep, String teaName, String teaSex,
			Integer age, Integer permission, String teaTel, String teaPw,
			Double longitude, Double latitude, Integer signstate,
			Set tbAsClasses, Set tbChos) {
		this.teaNo = teaNo;
		this.tbDep = tbDep;
		this.teaName = teaName;
		this.teaSex = teaSex;
		this.age = age;
		this.permission = permission;
		this.teaTel = teaTel;
		this.teaPw = teaPw;
		this.longitude = longitude;
		this.latitude = latitude;
		this.signstate = signstate;
		this.tbAsClasses = tbAsClasses;
		this.tbChos = tbChos;
	}

	// Property accessors

	public String getTeaNo() {
		return this.teaNo;
	}

	public void setTeaNo(String teaNo) {
		this.teaNo = teaNo;
	}

	public TbDep getTbDep() {
		return this.tbDep;
	}

	public void setTbDep(TbDep tbDep) {
		this.tbDep = tbDep;
	}

	public String getTeaName() {
		return this.teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getTeaSex() {
		return this.teaSex;
	}

	public void setTeaSex(String teaSex) {
		this.teaSex = teaSex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public String getTeaTel() {
		return this.teaTel;
	}

	public void setTeaTel(String teaTel) {
		this.teaTel = teaTel;
	}

	public String getTeaPw() {
		return this.teaPw;
	}

	public void setTeaPw(String teaPw) {
		this.teaPw = teaPw;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getSignstate() {
		return this.signstate;
	}

	public void setSignstate(Integer signstate) {
		this.signstate = signstate;
	}

	public Set getTbAsClasses() {
		return this.tbAsClasses;
	}

	public void setTbAsClasses(Set tbAsClasses) {
		this.tbAsClasses = tbAsClasses;
	}

	public Set getTbChos() {
		return this.tbChos;
	}

	public void setTbChos(Set tbChos) {
		this.tbChos = tbChos;
	}

}