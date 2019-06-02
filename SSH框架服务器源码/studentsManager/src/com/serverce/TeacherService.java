package com.serverce;

import java.util.List;

import com.dao.TeacherDao;
import com.entiy.TbAsClass;
import com.entiy.TbBack;
import com.entiy.TbHome;
import com.entiy.TbLea;
import com.entiy.TbSignin;
import com.entiy.TbStu;
import com.entiy.TbTea;
import com.until.StringToTime;

public class TeacherService {
	private TeacherDao teacherDao;
	StringToTime totime = new StringToTime();

	public TeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	// 登录
	public List<TbTea> checkTeacher(String teaNo, String password) {
		return teacherDao.checkTeacher(teaNo, password);
	}

	public List<TbLea> leaveRecord(String depName, String pass, String leaTime) {
		return teacherDao.leaveRecord(depName, pass, leaTime);
	}

	// 请假处理
	public boolean leaveDeal(String stuNo, String leaTime, String pass) {
		@SuppressWarnings("unchecked")
		List<TbLea> tbLeas = teacherDao.getHibernateTemplate().find(
				"from TbLea where tbStu.stuNo=? and leaTime=?", stuNo,
				totime.Totime(leaTime));
		TbLea tbLea = null;
		for (TbLea LeaNo : tbLeas) {
			tbLea = teacherDao.getHibernateTemplate().get(TbLea.class,
					LeaNo.getLeaNo());
			System.out.println("leadao" + LeaNo.getLeaNo());
		}
		tbLea.setPass(Integer.valueOf(pass));
		return teacherDao.leaveDeal(tbLea);
	}

	// 更新教师信息
	public boolean updateTeacher(String teaNo, String teaPw, String teaTel) {
		return teacherDao.updateTeacher(teaNo, teaPw, teaTel);
	}

	// 查询晚归记录
	public List<TbBack> backRecord(String depName, String dorName,
			String backTime) {
		return teacherDao.backRecord(depName, dorName, backTime);
	}

	public List<TbStu> homeRecord(String depName) {
		return teacherDao.homeRecord(depName);
	}

	// 教师查看课程
	public List<TbAsClass> asClass(String week, String teaNo) {
		return teacherDao.asClass(week, teaNo);
	}

	// 教师查看签到记录
	public List<TbSignin> signInRecord(String teaName, String section,
			String signDate) {
		return teacherDao.signInRecord(teaName, section, signDate);
	}

	// 发起签到
	public Boolean teacherSignIn(String teaNo, Double longitude, Double latitude) {
		@SuppressWarnings("unchecked")
		TbTea tbtea = teacherDao.getHibernateTemplate().get(TbTea.class, teaNo);
		tbtea.setLongitude(longitude);
		tbtea.setLatitude(latitude);
		tbtea.setSignstate(1);
		return teacherDao.TeacherSignIn(tbtea);
	}

	//查看是否发起签到
	public List<TbTea> issignin(String teaNo) {
		return teacherDao.issignin(teaNo);
	}
}
