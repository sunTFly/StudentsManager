package com.entiy;

import java.sql.Timestamp;
import java.util.Date;

/**
 * TbBack entity. @author MyEclipse Persistence Tools
 */

public class TbBack implements java.io.Serializable {

	// Fields

	private Integer backNo;
	private TbStu tbStu;
	private Timestamp backTime;
	private Integer backState;
	private Date time;

	// Constructors

	/** default constructor */
	public TbBack() {
	}

	/** full constructor */
	public TbBack(TbStu tbStu, Timestamp backTime, Integer backState, Date time) {
		this.tbStu = tbStu;
		this.backTime = backTime;
		this.backState = backState;
		this.time = time;
	}

	// Property accessors

	public Integer getBackNo() {
		return this.backNo;
	}

	public void setBackNo(Integer backNo) {
		this.backNo = backNo;
	}

	public TbStu getTbStu() {
		return this.tbStu;
	}

	public void setTbStu(TbStu tbStu) {
		this.tbStu = tbStu;
	}

	public Timestamp getBackTime() {
		return this.backTime;
	}

	public void setBackTime(Timestamp backTime) {
		this.backTime = backTime;
	}

	public Integer getBackState() {
		return this.backState;
	}

	public void setBackState(Integer backState) {
		this.backState = backState;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}