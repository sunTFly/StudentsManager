package com.serverce;

import java.util.List;

import com.dao.BackDao;
import com.entiy.TbBack;
import com.entiy.TbHome;
import com.entiy.TbStu;
import com.until.StringToTime;

public class BackService {
	StringToTime totime = new StringToTime();
	private BackDao backDao;

	public BackDao getBackDao() {
		return backDao;
	}

	public void setBackDao(BackDao backDao) {
		this.backDao = backDao;
	}

	public Boolean studentsback(String stuNo, String backTime, String time) {
		@SuppressWarnings("unchecked")
		List<TbBack> backlist = backDao.getHibernateTemplate().find(
				"from TbBack where tbStu.stuNo=? and time=?", stuNo,
				totime.ToDate(time));
		TbBack tbBack = null;
		for (TbBack backNo : backlist) {
			tbBack = backDao.getHibernateTemplate().get(TbBack.class,
					backNo.getBackNo());
			// System.out.println("backdao" + backNo.getBackNo());
		}
		tbBack.setBackState(2);
		tbBack.setBackTime(totime.Totime(backTime));
		return backDao.studentsback(tbBack);
	}

	public List<TbBack> backRecord(String stuNo) {
		return backDao.backRecord(stuNo);
	}
	public List<TbBack> backState(String stuNo,String date) {
		return backDao.backState(stuNo, date);
	}
	//Ñ§Éú¹é¼Ò
	public Boolean backHome(String stuNo, String homeTime, String homeAda) {
		TbStu tbstu = backDao.getHibernateTemplate().get(TbStu.class, stuNo);
		TbHome tbHome = new TbHome();
		tbstu.setStuState(3);
		tbHome.setHomeAda(homeAda);
		tbHome.setTbStu(tbstu);
		tbHome.setHomeTime(totime.Totime(homeTime));
		tbHome.setHomeState(1);
		return backDao.backHome(tbHome,tbstu);
	}
}
