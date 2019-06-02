package com.entiy;

import java.sql.Time;

/**
 * TbWeek entity. @author MyEclipse Persistence Tools
 */

public class TbWeek implements java.io.Serializable {

	// Fields

	private Integer weekNo;
	private TbCla tbCla;
	private Integer week;
	private String classroom;
	private Time time;
	private Integer section;

	// Constructors

	/** default constructor */
	public TbWeek() {
	}

	/** full constructor */
	public TbWeek(TbCla tbCla, Integer week, String classroom, Time time,
			Integer section) {
		this.tbCla = tbCla;
		this.week = week;
		this.classroom = classroom;
		this.time = time;
		this.section = section;
	}

	// Property accessors

	public Integer getWeekNo() {
		return this.weekNo;
	}

	public void setWeekNo(Integer weekNo) {
		this.weekNo = weekNo;
	}

	public TbCla getTbCla() {
		return this.tbCla;
	}

	public void setTbCla(TbCla tbCla) {
		this.tbCla = tbCla;
	}

	public Integer getWeek() {
		return this.week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getClassroom() {
		return this.classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Integer getSection() {
		return this.section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

}