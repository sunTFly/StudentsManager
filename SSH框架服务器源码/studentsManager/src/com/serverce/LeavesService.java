package com.serverce;

import java.util.List;

import com.dao.LeavesDao;
import com.entiy.TbLea;
import com.entiy.TbStu;
import com.until.StringToTime;

public class LeavesService {
	private LeavesDao leavesDao;

	public LeavesDao getLeavesDao() {
		return leavesDao;
	}

	public void setLeavesDao(LeavesDao leavesDao) {
		this.leavesDao = leavesDao;
	}

	public boolean studentsLeave(String ispass,String stuNo, String leaTime, String backTime,
			String leaReson, String leaImg) {
		TbLea tblea = new TbLea();
		TbStu tbstu = leavesDao.getHibernateTemplate().get(TbStu.class, stuNo);
		tblea.setTbStu(tbstu);
		StringToTime totime = new StringToTime();
		tblea.setLeaTime(totime.Totime(leaTime));
		tblea.setBackTime(totime.Totime(backTime));
		tblea.setLeaRea(leaReson);
		tblea.setImagePath(leaImg);
		tblea.setPass(Integer.valueOf(ispass));
		System.out.println("GGG"+leaReson);
		return leavesDao.studentsLeave(tblea);
	}

	public List<TbLea> leaveRecord(String stuNo) {
		return leavesDao.leaveRecord(stuNo);
	}
}
