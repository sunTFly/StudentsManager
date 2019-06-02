package com.entiy;

import java.util.Date;

/**
 * TbSignin entity. @author MyEclipse Persistence Tools
 */

public class TbSignin implements java.io.Serializable {

	// Fields

	private Integer sigNo;
	private TbTea tbTea;
	private TbStu tbStu;
	private Integer sigState;
	private String claName;
	private Date time;
	private Integer section;
	private String teaName;
	private String classRoom;
	private String teaTel;

	// Constructors

	/** default constructor */
	public TbSignin() {
	}

	/** full constructor */
	public TbSignin(TbTea tbTea, TbStu tbStu, Integer sigState, String claName,
			Date time, Integer section, String teaName, String classRoom,
			String teaTel) {
		this.tbTea = tbTea;
		this.tbStu = tbStu;
		this.sigState = sigState;
		this.claName = claName;
		this.time = time;
		this.section = section;
		this.teaName = teaName;
		this.classRoom = classRoom;
		this.teaTel = teaTel;
	}

	// Property accessors

	public Integer getSigNo() {
		return this.sigNo;
	}

	public void setSigNo(Integer sigNo) {
		this.sigNo = sigNo;
	}

	public TbTea getTbTea() {
		return this.tbTea;
	}

	public void setTbTea(TbTea tbTea) {
		this.tbTea = tbTea;
	}

	public TbStu getTbStu() {
		return this.tbStu;
	}

	public void setTbStu(TbStu tbStu) {
		this.tbStu = tbStu;
	}

	public Integer getSigState() {
		return this.sigState;
	}

	public void setSigState(Integer sigState) {
		this.sigState = sigState;
	}

	public String getClaName() {
		return this.claName;
	}

	public void setClaName(String claName) {
		this.claName = claName;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getSection() {
		return this.section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public String getTeaName() {
		return this.teaName;
	}

	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getClassRoom() {
		return this.classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getTeaTel() {
		return this.teaTel;
	}

	public void setTeaTel(String teaTel) {
		this.teaTel = teaTel;
	}

}