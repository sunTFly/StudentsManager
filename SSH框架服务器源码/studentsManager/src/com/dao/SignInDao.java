package com.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entiy.TbSignin;
import com.until.StringToTime;

public class SignInDao extends HibernateDaoSupport {
	@SuppressWarnings("unchecked")
	public List<TbSignin> SingInRecord(String time, String stuNo, String flag) {
		StringToTime totime = new StringToTime();
		List<TbSignin> signins;
		if (flag.equals("1")) {
			signins = this.getHibernateTemplate().find(
					"from TbSignin where tbStu.stuNo=? and time>=?", stuNo,
					totime.ToDate(totime.TwoDayTime()));
		} else {
			signins = this.getHibernateTemplate().find(
					"from TbSignin where tbStu.stuNo=? and time=?", stuNo,
					totime.ToDate(time));
		}
		return signins;
	}

	public Boolean studentsSignIn(TbSignin tbSignin) {
		try {
			this.getHibernateTemplate().update(tbSignin);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
