package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entiy.TbBack;
import com.entiy.TbSignin;
import com.entiy.TbWeek;
import com.serverce.SignInService;

public class SignInAction {
	private SignInService signInService;
	private List<TbSignin> signins;
	private Map<String, Object> signInRecordMap = new HashMap<String, Object>();
	private Map<String, Object> signInmap = null ;
	List<Map<String, Object>> signInlist = new ArrayList<Map<String, Object>>();
	private Map<String, Object> studentsSignInMap = new HashMap<String, Object>();

	public SignInService getSignInService() {
		return signInService;
	}

	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}

	public List<TbSignin> getSignins() {
		return signins;
	}

	public void setSignins(List<TbSignin> signins) {
		this.signins = signins;
	}

	public Map<String, Object> getSignInRecordMap() {
		return signInRecordMap;
	}

	public void setSignInRecordMap(Map<String, Object> signInRecordMap) {
		this.signInRecordMap = signInRecordMap;
	}

	public Map<String, Object> getSignInmap() {
		return signInmap;
	}

	public void setSignInmap(Map<String, Object> signInmap) {
		this.signInmap = signInmap;
	}

	public List<Map<String, Object>> getSignInlist() {
		return signInlist;
	}

	public void setSignInlist(List<Map<String, Object>> signInlist) {
		this.signInlist = signInlist;
	}

	public Map<String, Object> getStudentsSignInMap() {
		return studentsSignInMap;
	}

	public void setStudentsSignInMap(Map<String, Object> studentsSignInMap) {
		this.studentsSignInMap = studentsSignInMap;
	}

	public String singInRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String time = request.getParameter("time");
		String flag = request.getParameter("state");
		signins = signInService.SingInRecord(time, stuNo,flag);
		for (TbSignin tbSignin : signins) {
			signInmap=new HashMap<String, Object>();
			signInmap.put("ClaName", tbSignin.getClaName());
			signInmap.put("TeaName", tbSignin.getTeaName());
			signInmap.put("ClassRoom", tbSignin.getClassRoom());
			signInmap.put("SigState", tbSignin.getSigState());
			signInmap.put("Section", tbSignin.getSection());
			signInmap.put("TeaTel", tbSignin.getTeaTel());
			signInmap.put("Time", tbSignin.getTime());
			signInmap.put("TeaNo", tbSignin.getTbTea().getTeaNo());
			signInlist.add(signInmap);
		}
		signInRecordMap.put("stuSignIn", signInlist);
		return "success";
	}

	public String studentssignIn() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String time = request.getParameter("time");
		int section = 0;
		try {
			section = Integer.parseInt(request.getParameter("section"));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Boolean flag = false;
		flag = signInService.studentsSignIn(stuNo, section, time);
		if (flag) {
			studentsSignInMap.put("success", true);
			return "success";
		} else {
			studentsSignInMap.put("success", false);
			return "fail";
		}
	}
}
