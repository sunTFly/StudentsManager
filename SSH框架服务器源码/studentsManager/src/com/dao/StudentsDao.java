package com.dao;

import java.util.List;

import com.entiy.TbStu;


import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class StudentsDao extends HibernateDaoSupport {

	public List<TbStu> checkStudents(String stuNo, String password) {
		@SuppressWarnings("unchecked")
		List<TbStu> studentsList = this.getHibernateTemplate().find(
				"from TbStu where stuNo=? and stuPW=?", stuNo, password);
			return studentsList;
		
	}
	public boolean Studentsfeatures(String stuNo, String feature) {
		TbStu tbstu = getHibernateTemplate().get(TbStu.class, stuNo);
		tbstu.setStuFeature(feature);
		try {
			this.getHibernateTemplate().update(tbstu);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	//更新学生信息
	public boolean updateStudents(String stuNo, String stuPw, String stuTel,
			String stuAdr,String feature) {
		TbStu tbstu = getHibernateTemplate().get(TbStu.class, stuNo);
		tbstu.setStuPw(stuPw);
		tbstu.setStuAdr(stuAdr);
		tbstu.setStuTel(stuTel);
		tbstu.setStuFeature(feature);
		
		try {
			this.getHibernateTemplate().update(tbstu);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}


}
