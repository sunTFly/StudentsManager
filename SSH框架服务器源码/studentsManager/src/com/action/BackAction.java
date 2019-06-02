package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;

import com.entiy.TbBack;
import com.serverce.BackService;

public class BackAction {
	private BackService backService;
	private Map<String, Object> studentsBackMap = new HashMap<String, Object>();
	private Map<String, Object> BackHomeMap = new HashMap<String, Object>();
	private List<TbBack> backs;
	private Map<String, Object> backRecordMap = new HashMap<String, Object>();
	private List<TbBack> backstate;
	private Map<String, Object> backStateMap = new HashMap<String, Object>();
	private Map<String, Object> backmap = null;
	List<Map<String, Object>> backlist = new ArrayList<Map<String, Object>>();

	public Map<String, Object> getBackHomeMap() {
		return BackHomeMap;
	}

	public void setBackHomeMap(Map<String, Object> backHomeMap) {
		BackHomeMap = backHomeMap;
	}

	public List<TbBack> getBacks() {
		return backs;
	}

	public void setBacks(List<TbBack> backs) {
		this.backs = backs;
	}

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

	public Map<String, Object> getStudentsBackMap() {
		return studentsBackMap;
	}

	public void setStudentsBackMap(Map<String, Object> studentsBackMap) {
		this.studentsBackMap = studentsBackMap;
	}

	public BackService getBackService() {
		return backService;
	}

	public void setBackService(BackService backService) {
		this.backService = backService;
	}

	public List<TbBack> getBackstate() {
		return backstate;
	}

	public void setBackstate(List<TbBack> backstate) {
		this.backstate = backstate;
	}

	public Map<String, Object> getBackStateMap() {
		return backStateMap;
	}

	public void setBackStateMap(Map<String, Object> backStateMap) {
		this.backStateMap = backStateMap;
	}

	public String studentsback() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String backTime = request.getParameter("backTime");
		String time = request.getParameter("time");
		Boolean flag = false;
		flag = backService.studentsback(stuNo, backTime, time);
		if (flag) {
			studentsBackMap.put("success", true);
			return "success";
		} else {
			studentsBackMap.put("success", false);
			return "fail";
		}
	}

	public String backhome() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String homeTime = request.getParameter("homeTime");
		String homeAda = request.getParameter("homeAda");
		Boolean flag = false;
		flag = backService.backHome(stuNo, homeTime, homeAda);
		if (flag) {
			BackHomeMap.put("success", true);
			return "success";
		} else {
			BackHomeMap.put("success", false);
			return "fail";
		}
	}

	public String backsRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		backs = backService.backRecord(stuNo);
		for (TbBack tbBack : backs) {
			backmap = new HashMap<String, Object>();
			backmap.put("date", tbBack.getTime());
			if (tbBack.getBackTime() == null) {
				backmap.put("backTime", "");
			} else {
				backmap.put("backTime", tbBack.getBackTime());
			}
			backmap.put("backState", tbBack.getBackState());
			backlist.add(backmap);
		}
		backRecordMap.put("stuBack", backlist);
		return "success";
	}

	public String backstate() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String date = request.getParameter("date");
		// System.out.println(stuNo+"::"+date);
		backstate = backService.backState(stuNo, date);
		for (TbBack tbbackstate : backstate) {
			backStateMap.put("state", tbbackstate.getBackState());
			if (tbbackstate.getBackTime() == null) {
				backStateMap.put("backTime", "");
			} else {
				backStateMap.put("backTime", tbbackstate.getBackTime());
			}
		}
		return "success";
	}

}
