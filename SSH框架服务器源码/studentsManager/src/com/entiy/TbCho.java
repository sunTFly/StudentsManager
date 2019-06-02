package com.entiy;

/**
 * TbCho entity. @author MyEclipse Persistence Tools
 */

public class TbCho implements java.io.Serializable {

	// Fields

	private Integer choNo;
	private TbCla tbCla;
	private TbTea tbTea;
	private TbStu tbStu;

	// Constructors

	/** default constructor */
	public TbCho() {
	}

	/** full constructor */
	public TbCho(TbCla tbCla, TbTea tbTea, TbStu tbStu) {
		this.tbCla = tbCla;
		this.tbTea = tbTea;
		this.tbStu = tbStu;
	}

	// Property accessors

	public Integer getChoNo() {
		return this.choNo;
	}

	public void setChoNo(Integer choNo) {
		this.choNo = choNo;
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

	public TbStu getTbStu() {
		return this.tbStu;
	}

	public void setTbStu(TbStu tbStu) {
		this.tbStu = tbStu;
	}

}