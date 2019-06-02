package com.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entiy.HomeRecord;
import com.entiy.TbAsClass;
import com.entiy.TbBack;
import com.entiy.TbHome;
import com.entiy.TbLea;
import com.entiy.TbSignin;
import com.entiy.TbStu;
import com.entiy.TbTea;
import com.until.StringToTime;

public class TeacherDao extends HibernateDaoSupport {
	StringToTime totime = new StringToTime();

	// 教师登录
	public List<TbTea> checkTeacher(String teaNo, String password) {
		@SuppressWarnings("unchecked")
		List<TbTea> teacherlist = this.getHibernateTemplate().find(
				"from TbTea where teaNo=? and teaPw=?", teaNo, password);
		return teacherlist;
	}

	// 查询请假记录
	public List<TbLea> leaveRecord(String depName, String pass, String leaTime) {
		if (Integer.valueOf(pass) == 3) {// 查询全部
			@SuppressWarnings("unchecked")
			List<TbLea> leavelist = this
					.getHibernateTemplate()
					.find("from TbLea where leaTime=? and tbStu.tbGra.tbPro.tbDep.depName=?",
							totime.Totime(leaTime), depName);
			return leavelist;
		} else if (Integer.valueOf(pass) == 0) {// 未处理
			@SuppressWarnings("unchecked")
			List<TbLea> leavelist = this
					.getHibernateTemplate()
					.find("from TbLea where pass=? and leaTime=? and tbStu.tbGra.tbPro.tbDep.depName=?",
							Integer.valueOf(pass), totime.Totime(leaTime),
							depName);
			return leavelist;
		} else {// 已处理
			@SuppressWarnings("unchecked")
			List<TbLea> leavelist = this
					.getHibernateTemplate()
					.find("from TbLea where pass!=? and leaTime=? and tbStu.tbGra.tbPro.tbDep.depName=?",
							0, totime.Totime(leaTime), depName);
			return leavelist;
		}
	}

	// 请假处理
	public boolean leaveDeal(TbLea tblea) {

		try {
			this.getHibernateTemplate().update(tblea);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	// 查询晚归记录
	public List<TbBack> backRecord(String depName, String dorName,
			String backTime) {
		if (dorName.equals("全部")) {// 学院全部晚归状况
			@SuppressWarnings("unchecked")
			List<TbBack> backlist = this
					.getHibernateTemplate()
					.find("from TbBack where time=? and tbStu.tbGra.tbPro.tbDep.depName=?",
							totime.ToDate(backTime), depName);
			return backlist;
		} else {
			@SuppressWarnings("unchecked")
			List<TbBack> backlist = this
					.getHibernateTemplate()
					.find("from TbBack where time=? and tbStu.tbDor.dorName=? and tbStu.tbGra.tbPro.tbDep.depName=?",
							totime.ToDate(backTime), dorName, depName);
			return backlist;
		}
	}

	// 更新教师信息
	public boolean updateTeacher(String teaNo, String teaPw, String teaTel) {
		TbTea tbtea = getHibernateTemplate().get(TbTea.class, teaNo);
		tbtea.setTeaPw(teaPw);
		tbtea.setTeaTel(teaTel);
		tbtea.setTeaPw(teaPw);
		try {
			this.getHibernateTemplate().update(tbtea);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	// 查询学生归家状态
	public List<TbStu> homeRecord(String depName) {
		@SuppressWarnings("unchecked")
		List<TbStu> students = this.getHibernateTemplate().find(
				"from TbStu where tbGra.tbPro.tbDep.depName=?", depName);
		return students;
	}

	// 教师端签查看课程
	public List<TbAsClass> asClass(String week, String teaNo) {
		if (Integer.valueOf(week) == 1) {
			@SuppressWarnings("unchecked")
			List<TbAsClass> listclass = this.getHibernateTemplate().find(
					"from TbAsClass where (week=? or week>=?) and tbTea.teaNo=?",
					Integer.valueOf(week), 6, teaNo);
			return listclass;
		} else if (Integer.valueOf(week) == 2) {
			@SuppressWarnings("unchecked")
			List<TbAsClass> listclass = this
					.getHibernateTemplate()
					.find("from TbAsClass where tbTea.teaNo=? and (week=? or week=? or week=?)",
							teaNo,Integer.valueOf(week), 1, 6);
			return listclass;
		} else {
			@SuppressWarnings("unchecked")
			List<TbAsClass> listclass = this
					.getHibernateTemplate()
					.find("from TbAsClass where week<=? and week>=? and tbTea.teaNo=?",
							Integer.valueOf(week), Integer.valueOf(week) - 2,
							teaNo);
			return listclass;
		}

	}

	// 查看签到记录
	public List<TbSignin> signInRecord(String teaName, String section,
			String signDate) {
		@SuppressWarnings("unchecked")
		List<TbSignin> signlist = this.getHibernateTemplate().find(
				"from TbSignin where section=? and time=? and teaName=?",
				Integer.valueOf(section), totime.ToDate(signDate), teaName);
		// System.out.println(signDate+":"+teaName+":"+section);
		return signlist;
	}

	// 发起签到
	public Boolean TeacherSignIn(TbTea tbtea) {
		try {
			this.getHibernateTemplate().update(tbtea);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	//获取经纬度判断是否发起签到
	public List<TbTea> issignin(String teaNo) {
		@SuppressWarnings("unchecked")
		List<TbTea> teacherissignlist = this.getHibernateTemplate().find(
				"from TbTea where teaNo=?", teaNo);
		return teacherissignlist;
	}
}
