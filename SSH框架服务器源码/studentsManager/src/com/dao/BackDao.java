package com.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entiy.TbBack;
import com.entiy.TbHome;
import com.entiy.TbStu;
import com.until.StringToTime;

public class BackDao extends HibernateDaoSupport {
	// 晚归签到
	public Boolean studentsback(TbBack tbBack) {
		try {
			this.getHibernateTemplate().update(tbBack);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 学生归家
	public Boolean backHome(TbHome tbHome,TbStu tbstu) {
		try {
			this.getHibernateTemplate().update(tbstu);
			this.getHibernateTemplate().save(tbHome);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<TbBack> backRecord(String stuNo) {
		StringToTime totime = new StringToTime();
		@SuppressWarnings("unchecked")
		List<TbBack> tbBacks = this.getHibernateTemplate().find(
				"from TbBack where tbStu.stuNo=? and time>=?", stuNo,
				totime.ToDate(totime.ThreeTime()));
		return tbBacks;
	}

	public List<TbBack> backState(String stuNo, String date) {
		StringToTime totime = new StringToTime();
		@SuppressWarnings("unchecked")
		List<TbBack> backstate = this.getHibernateTemplate().find(
				"from TbBack where tbStu.stuNo=? and time=?", stuNo,
				totime.ToDate(date));
		return backstate;
	}
}
