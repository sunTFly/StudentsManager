package com.serverce;

import java.util.List;

import com.dao.SignInDao;
import com.entiy.TbSignin;
import com.entiy.TbWeek;
import com.until.StringToTime;

public class SignInService {
	private SignInDao signInDao;

	public SignInDao getSignInDao() {
		return signInDao;
	}

	public void setSignInDao(SignInDao signInDao) {
		this.signInDao = signInDao;
	}

	public List<TbSignin> SingInRecord(String time, String stuNo,String flag) {
		return signInDao.SingInRecord(time, stuNo,flag);
	}

	public Boolean studentsSignIn(String stuNo, int section, String time) {
		StringToTime totime = new StringToTime();
		@SuppressWarnings("unchecked")
		List<TbSignin> signlist = signInDao.getHibernateTemplate().find(
				"from TbSignin where tbStu.stuNo=? and time=? and section=?",
				stuNo, totime.ToDate(time), section);
		TbSignin tbSignin = null;
		for (TbSignin SigInNo : signlist) {
			tbSignin = signInDao.getHibernateTemplate().get(TbSignin.class,
					SigInNo.getSigNo());
		}
		tbSignin.setSigState(2);
		return signInDao.studentsSignIn(tbSignin);
	}
}
