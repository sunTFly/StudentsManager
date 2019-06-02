package com.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entiy.TbAsClass;
import com.entiy.TbBack;
import com.entiy.TbLea;
import com.entiy.TbSignin;
import com.entiy.TbStu;
import com.entiy.TbTea;
import com.opensymphony.xwork2.ActionSupport;
import com.serverce.TeacherService;
import com.until.WriteText;

public class TeacherAction extends ActionSupport {
	private List<TbTea> tbtea, tbissigin;
	private String imgStr;
	private List<TbLea> leaves;
	private TeacherService teacherService;
	private Map<String, Object> TeacherLeaveRecordMap = new HashMap<String, Object>();
	private Map<String, Object> teacherUpadatetMap = new HashMap<String, Object>();
	private Map<String, Object> TeacherLeaveDealMap = new HashMap<String, Object>();
	private Map<String, Object> TeacherSigninMap = new HashMap<String, Object>();
	private Map<String, Object> TeacherissignMap = new HashMap<String, Object>();
	private Map<String, Object> teacherLogintMap = new HashMap<String, Object>();
	private Map<String, Object> teacherClassMap = new HashMap<String, Object>();
	private Map<String, Object> homeRecordMap = new HashMap<String, Object>();
	private Map<String, Object> signRecordMap = new HashMap<String, Object>();
	List<Map<String, Object>> lealist = new ArrayList<Map<String, Object>>();
	private Map<String, Object> backRecordMap = new HashMap<String, Object>();
	private Map<String, Object> backmap = null;
	private Map<String, Object> homemap = null;
	private Map<String, Object> signmap = null;
	private Map<String, Object> classmap = null;
	List<Map<String, Object>> backlist = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> homelist = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> calsslist = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> signlist = new ArrayList<Map<String, Object>>();
	List<TbBack> backrecord;
	List<TbSignin> signinRecord;
	List<TbStu> homerecord;
	List<TbAsClass> asClasses;
	Map<String, Object> teamap = null;
	private Map<String, Object> leavemap = null;
	private String head, holidays_img;

	public Map<String, Object> getBackRecordMap() {
		return backRecordMap;
	}

	public void setBackRecordMap(Map<String, Object> backRecordMap) {
		this.backRecordMap = backRecordMap;
	}

	public Map<String, Object> getBackmap() {
		return backmap;
	}

	public void setBackmap(Map<String, Object> backmap) {
		this.backmap = backmap;
	}

	public List<Map<String, Object>> getBacklist() {
		return backlist;
	}

	public void setBacklist(List<Map<String, Object>> backlist) {
		this.backlist = backlist;
	}

	public List<TbBack> getBackrecord() {
		return backrecord;
	}

	public void setBackrecord(List<TbBack> backrecord) {
		this.backrecord = backrecord;
	}

	public String getImgStr() {
		return imgStr;
	}

	public void setImgStr(String imgStr) {
		this.imgStr = imgStr;
	}

	public Map<String, Object> getTeacherUpadatetMap() {
		return teacherUpadatetMap;
	}

	public void setTeacherUpadatetMap(Map<String, Object> teacherUpadatetMap) {
		this.teacherUpadatetMap = teacherUpadatetMap;
	}

	public List<TbTea> getTbtea() {
		return tbtea;
	}

	public void setTbtea(List<TbTea> tbtea) {
		this.tbtea = tbtea;
	}

	public TeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public Map<String, Object> getTeacherLogintMap() {
		return teacherLogintMap;
	}

	public void setTeacherLogintMap(Map<String, Object> teacherLogintMap) {
		this.teacherLogintMap = teacherLogintMap;
	}

	public Map<String, Object> getTeamap() {
		return teamap;
	}

	public void setTeamap(Map<String, Object> teamap) {
		this.teamap = teamap;
	}

	public List<TbLea> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<TbLea> leaves) {
		this.leaves = leaves;
	}

	public Map<String, Object> getTeacherLeaveRecordMap() {
		return TeacherLeaveRecordMap;
	}

	public void setTeacherLeaveRecordMap(
			Map<String, Object> teacherLeaveRecordMap) {
		TeacherLeaveRecordMap = teacherLeaveRecordMap;
	}

	public List<Map<String, Object>> getLealist() {
		return lealist;
	}

	public void setLealist(List<Map<String, Object>> lealist) {
		this.lealist = lealist;
	}

	public Map<String, Object> getLeavemap() {
		return leavemap;
	}

	public void setLeavemap(Map<String, Object> leavemap) {
		this.leavemap = leavemap;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHolidays_img() {
		return holidays_img;
	}

	public void setHolidays_img(String holidays_img) {
		this.holidays_img = holidays_img;
	}

	public Map<String, Object> getTeacherLeaveDealMap() {
		return TeacherLeaveDealMap;
	}

	public void setTeacherLeaveDealMap(Map<String, Object> teacherLeaveDealMap) {
		TeacherLeaveDealMap = teacherLeaveDealMap;
	}

	public Map<String, Object> getHomeRecordMap() {
		return homeRecordMap;
	}

	public void setHomeRecordMap(Map<String, Object> homeRecordMap) {
		this.homeRecordMap = homeRecordMap;
	}

	public Map<String, Object> getHomemap() {
		return homemap;
	}

	public void setHomemap(Map<String, Object> homemap) {
		this.homemap = homemap;
	}

	public List<Map<String, Object>> getHomelist() {
		return homelist;
	}

	public void setHomelist(List<Map<String, Object>> homelist) {
		this.homelist = homelist;
	}

	public List<TbStu> getHomerecord() {
		return homerecord;
	}

	public void setHomerecord(List<TbStu> homerecord) {
		this.homerecord = homerecord;
	}

	public Map<String, Object> getTeacherClassMap() {
		return teacherClassMap;
	}

	public void setTeacherClassMap(Map<String, Object> teacherClassMap) {
		this.teacherClassMap = teacherClassMap;
	}

	public Map<String, Object> getClassmap() {
		return classmap;
	}

	public void setClassmap(Map<String, Object> classmap) {
		this.classmap = classmap;
	}

	public List<Map<String, Object>> getCalsslist() {
		return calsslist;
	}

	public void setCalsslist(List<Map<String, Object>> calsslist) {
		this.calsslist = calsslist;
	}

	public List<TbAsClass> getAsClasses() {
		return asClasses;
	}

	public void setAsClasses(List<TbAsClass> asClasses) {
		this.asClasses = asClasses;
	}

	public Map<String, Object> getSignRecordMap() {
		return signRecordMap;
	}

	public void setSignRecordMap(Map<String, Object> signRecordMap) {
		this.signRecordMap = signRecordMap;
	}

	public Map<String, Object> getSignmap() {
		return signmap;
	}

	public void setSignmap(Map<String, Object> signmap) {
		this.signmap = signmap;
	}

	public List<Map<String, Object>> getSignlist() {
		return signlist;
	}

	public void setSignlist(List<Map<String, Object>> signlist) {
		this.signlist = signlist;
	}

	public List<TbSignin> getSigninRecord() {
		return signinRecord;
	}

	public void setSigninRecord(List<TbSignin> signinRecord) {
		this.signinRecord = signinRecord;
	}

	public Map<String, Object> getTeacherSigninMap() {
		return TeacherSigninMap;
	}

	public void setTeacherSigninMap(Map<String, Object> teacherSigninMap) {
		TeacherSigninMap = teacherSigninMap;
	}

	public List<TbTea> getTbissigin() {
		return tbissigin;
	}

	public void setTbissigin(List<TbTea> tbissigin) {
		this.tbissigin = tbissigin;
	}

	public Map<String, Object> getTeacherissignMap() {
		return TeacherissignMap;
	}

	public void setTeacherissignMap(Map<String, Object> teacherissignMap) {
		TeacherissignMap = teacherissignMap;
	}

	// 教师登录
	public String teacherLogin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String teaNo = request.getParameter("teaNo");
		String password = request.getParameter("password");
		tbtea = teacherService.checkTeacher(teaNo, password);
		WriteText writeText = new WriteText();
		try {
			head = writeText.redFile(teaNo, "head_img");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(head+"123");
		if (tbtea.size() > 0) {
			for (TbTea tbTea : tbtea) {
				teamap = new HashMap<String, Object>();
				teacherLogintMap.put("TeaName", tbTea.getTeaName());
				if (tbTea.getTeaTel() == null) {
					teacherLogintMap.put("TeaTel", "");
				} else {
					teacherLogintMap.put("TeaTel", tbTea.getTeaTel());
				}
				teacherLogintMap.put("Permission",
						String.valueOf(tbTea.getPermission()));
				// System.out.println("stata"+tbTea.getTeaTel());
				teacherLogintMap.put("DepName", tbTea.getTbDep().getDepName());
				// System.out.println("s"+ tbStu.getStuFeature());

			}
			teacherLogintMap.put("head", head);

			teacherLogintMap.put("success", true);

			return "success";
		} else
			teacherLogintMap.put("success", false);
		return "fail";
	}

	// 请假记录
	public String leaveRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String depName = request.getParameter("depName");
		String pass = request.getParameter("pass");
		String leaTime = request.getParameter("leaTime");
		System.out.println("aa" + leaTime + "::" + depName);
		leaves = teacherService.leaveRecord(depName, pass, leaTime);
		for (TbLea tblea : leaves) {
			leavemap = new HashMap<String, Object>();
			leavemap.put("stuNo", tblea.getTbStu().getStuNo());
			leavemap.put("stuName", tblea.getTbStu().getStuName());
			leavemap.put("leaTime", tblea.getLeaTime());
			leavemap.put("backTime", tblea.getBackTime());
			leavemap.put("leaRea", tblea.getLeaRea());
			System.out.println(tblea.getLeaRea());
			leavemap.put("pass", tblea.getPass());
			WriteText writeText = new WriteText();

			try {
				holidays_img = writeText.redFile(tblea.getImagePath(),
						"holidays_img");
			} catch (IOException e) { // TODO Auto-generated
				e.printStackTrace();
			}

			leavemap.put("img", holidays_img);
			lealist.add(leavemap);
		}
		// System.out.println(lealist);
		TeacherLeaveRecordMap.put("stuLea", lealist);
		return "success";
	}

	// 请假处理
	public String leaveDeal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String leaTime = request.getParameter("leaTime");
		String pass = request.getParameter("pass");
		boolean flag = false;
		flag = teacherService.leaveDeal(stuNo, leaTime, pass);
		if (flag) {
			TeacherLeaveDealMap.put("success", true);
			return "success";
		} else {
			TeacherLeaveDealMap.put("success", false);
			return "fail";
		}
	}

	// 发起定位处理
	public String teacherSignIn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String teaNo = request.getParameter("teaNo");
		Double logtude = Double.valueOf(request.getParameter("logtude"));
		Double lattude = Double.valueOf(request.getParameter("lattude"));
		System.out.println(logtude + ":" + lattude);
		boolean flag = false;
		flag = teacherService.teacherSignIn(teaNo, logtude, lattude);
		if (flag) {
			TeacherSigninMap.put("success", true);
			return "success";
		} else {
			TeacherSigninMap.put("success", false);
			return "fail";
		}
	}

	// 修改教师资料
	public String updateTeacher() {
		HttpServletRequest request = ServletActionContext.getRequest();
		final String teaNo = request.getParameter("teaNo");
		String teaPw = request.getParameter("teaPw");
		String teaTel = request.getParameter("teaTel");
		imgStr = request.getParameter("imgStr");

		new Thread(new Runnable() {

			@Override
			public void run() { // TODO Auto-generated method stub
				WriteText writeText = new WriteText();
				writeText.addFile(imgStr, teaNo, "head_img");
			}
		}).start();

		boolean flag = false;
		flag = teacherService.updateTeacher(teaNo, teaPw, teaTel);
		if (flag) {
			teacherUpadatetMap.put("success", true);
			return "success";
		} else {
			teacherUpadatetMap.put("success", false);
			return "fail";
		}
	}

	// 晚归记录
	public String backsRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String depName = request.getParameter("depName");
		String dorName = request.getParameter("dorName");
		String backTime = request.getParameter("backTime");
		backrecord = teacherService.backRecord(depName, dorName, backTime);
		for (TbBack tbBack : backrecord) {
			backmap = new HashMap<String, Object>();
			backmap.put("stuName", tbBack.getTbStu().getStuName());
			backmap.put("date", tbBack.getTime());
			backmap.put("backState", tbBack.getBackState());
			backmap.put("DorName", tbBack.getTbStu().getTbDor().getDorName());
			backlist.add(backmap);
		}
		// System.out.println(backlist);
		backRecordMap.put("stuBack", backlist);
		return "success";
	}

	// 归家记录
	public String homeRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String depName = request.getParameter("depName");
		homerecord = teacherService.homeRecord(depName);
		for (TbStu tbstu : homerecord) {
			homemap = new HashMap<String, Object>();
			homemap.put("stuName", tbstu.getStuName());
			homemap.put("StuNo", tbstu.getStuNo());
			homemap.put("StuState", tbstu.getStuState());
			homelist.add(homemap);
		}
		// System.out.println(homelist);
		homeRecordMap.put("homelist", homelist);
		return "success";
	}

	// 教师端签到查看课表
	public String signasclass() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String week = request.getParameter("week");
		String teaNo = request.getParameter("teaNo");
		asClasses = teacherService.asClass(week, teaNo);
		for (TbAsClass tbasclass : asClasses) {
			classmap = new HashMap<String, Object>();
			classmap.put("ClaName", tbasclass.getTbCla().getClaName());
			classmap.put("Section", tbasclass.getSection());
			classmap.put("week", tbasclass.getWeek());
			classmap.put("ProName", tbasclass.getTbGra().getTbPro()
					.getProName());
			classmap.put("GarName", tbasclass.getTbGra().getGarName());
			calsslist.add(classmap);
		}
		//System.out.println(calsslist+"ss");
		teacherClassMap.put("calsslist", calsslist);
		return "success";
	}

	// 教师端查看学生签到记录
	public String signInRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String teaName = request.getParameter("teaName");
		String section = request.getParameter("section");
		String signDate = request.getParameter("signDate");
		// System.out.println(signDate);
		signinRecord = teacherService.signInRecord(teaName, section, signDate);
		for (TbSignin tbsignin : signinRecord) {
			signmap = new HashMap<String, Object>();
			signmap.put("SigState", tbsignin.getSigState());
			signmap.put("StuName", tbsignin.getTbStu().getStuName());
			signmap.put("GarName", tbsignin.getTbStu().getTbGra().getGarName());
			signmap.put("ProName", tbsignin.getTbStu().getTbGra().getTbPro().getProName());
			signlist.add(signmap);
			System.out.println(tbsignin.getTbStu().getTbGra().getTbPro().getProName()+"pro");
			System.out.println(tbsignin.getTbStu().getTbGra().getGarName()+"grd");
		}
		// System.out.println(signlist.size());
		signRecordMap.put("signlist", signlist);
		return "success";
	}
	
	//经纬度和教师状态获取
	public String issignin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String teaNo = request.getParameter("teaNo");
		//System.out.println(teaNo+"ss");
		tbissigin = teacherService.issignin(teaNo);
		for (TbTea tbTea : tbissigin) {
			TeacherissignMap.put("Signstate", tbTea.getSignstate());
			TeacherissignMap.put("Latitude", tbTea.getLatitude());
			TeacherissignMap.put("Longitude", tbTea.getLongitude());
			//System.out.println(tbTea.getSignstate()+"s");
		}
		return "success";
	}
}
