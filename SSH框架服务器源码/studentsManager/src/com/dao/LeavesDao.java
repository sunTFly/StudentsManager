package com.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entiy.TbLea;
import com.entiy.TbStu;
import com.until.StringToTime;

public class LeavesDao extends HibernateDaoSupport {
	public boolean studentsLeave(TbLea tblea) {
		try {
			this.getHibernateTemplate().save(tblea);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<TbLea> leaveRecord(String stuNo) {
		@SuppressWarnings("unchecked")
		List<TbLea> leaList = this.getHibernateTemplate().find(
				"from TbLea where tbStu.stuNo=?", stuNo);
		//System.out.println("asd");
		return leaList;

	}
}
