package com.entiy;

import java.sql.Timestamp;

/**
 * TbLea entity. @author MyEclipse Persistence Tools
 */

public class TbLea implements java.io.Serializable {

	// Fields

	private Integer leaNo;
	private TbStu tbStu;
	private String leaRea;
	private Timestamp leaTime;
	private Timestamp backTime;
	private Integer pass;
	private String imagePath;

	// Constructors

	/** default constructor */
	public TbLea() {
	}

	/** full constructor */
	public TbLea(TbStu tbStu, String leaRea, Timestamp leaTime,
			Timestamp backTime, Integer pass, String imagePath) {
		this.tbStu = tbStu;
		this.leaRea = leaRea;
		this.leaTime = leaTime;
		this.backTime = backTime;
		this.pass = pass;
		this.imagePath = imagePath;
	}

	// Property accessors

	public Integer getLeaNo() {
		return this.leaNo;
	}

	public void setLeaNo(Integer leaNo) {
		this.leaNo = leaNo;
	}

	public TbStu getTbStu() {
		return this.tbStu;
	}

	public void setTbStu(TbStu tbStu) {
		this.tbStu = tbStu;
	}

	public String getLeaRea() {
		return this.leaRea;
	}

	public void setLeaRea(String leaRea) {
		this.leaRea = leaRea;
	}

	public Timestamp getLeaTime() {
		return this.leaTime;
	}

	public void setLeaTime(Timestamp leaTime) {
		this.leaTime = leaTime;
	}

	public Timestamp getBackTime() {
		return this.backTime;
	}

	public void setBackTime(Timestamp backTime) {
		this.backTime = backTime;
	}

	public Integer getPass() {
		return this.pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	

}