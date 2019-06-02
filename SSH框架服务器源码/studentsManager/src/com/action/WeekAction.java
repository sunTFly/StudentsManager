package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entiy.TbCla;
import com.entiy.TbWeek;
import com.serverce.WeekService;

public class WeekAction {
	private WeekService weekService;
	private Map<String, Object> weekmap = null;
	List<Map<String, Object>> weeklist = new ArrayList<Map<String, Object>>();
	private Map<String, Object> studentWeekMap = new HashMap<String, Object>();
	private List<TbWeek> weekcs;
	private List<TbCla> clas;

	public List<TbCla> getClas() {
		return clas;
	}

	public void setClas(List<TbCla> clas) {
		this.clas = clas;
	}

	public WeekService getWeekService() {
		return weekService;
	}

	public void setWeekService(WeekService weekService) {
		this.weekService = weekService;
	}

	public Map<String, Object> getWeekmap() {
		return weekmap;
	}

	public void setWeekmap(Map<String, Object> weekmap) {
		this.weekmap = weekmap;
	}

	public List<Map<String, Object>> getWeeklist() {
		return weeklist;
	}

	public void setWeeklist(List<Map<String, Object>> weeklist) {
		this.weeklist = weeklist;
	}

	public Map<String, Object> getStudentWeekMap() {
		return studentWeekMap;
	}

	public void setStudentWeekMap(Map<String, Object> studentWeekMap) {
		this.studentWeekMap = studentWeekMap;
	}

	public List<TbWeek> getWeekcs() {
		return weekcs;
	}

	public void setWeekcs(List<TbWeek> weekcs) {
		this.weekcs = weekcs;
	}

	public String weekClass() {
		weekcs = weekService.claweek();
		for (TbWeek tbWeek : weekcs) {
			weekmap = new HashMap<String, Object>();
			weekmap.put("Section", tbWeek.getSection());
			weekmap.put("ClaName", tbWeek.getTbCla().getClaName());
			weekmap.put("ClassRoom", tbWeek.getClassroom());
			weekmap.put("Week", tbWeek.getWeek());
			weeklist.add(weekmap);
		}
		//System.out.println("aa"+weeklist);
		studentWeekMap.put("weekclass", weeklist);
		return "success";
	}
}
