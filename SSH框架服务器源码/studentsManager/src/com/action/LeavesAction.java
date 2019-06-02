package com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entiy.TbLea;
import com.serverce.LeavesService;
import com.until.WriteText;

public class LeavesAction {
	private LeavesService leavesService;
	private Map<String, Object> studentsLeaveMap = new HashMap<String, Object>();
	private Map<String, Object> LeaveRecordMap = new HashMap<String, Object>();
	private Map<String, Object> leavemap = null;
	List<Map<String, Object>> lealist = new ArrayList<Map<String, Object>>();
	private List<TbLea> leaves;

	public Map<String, Object> getLeaveRecordMap() {
		return LeaveRecordMap;
	}

	public List<Map<String, Object>> getLealist() {
		return lealist;
	}

	public void setLealist(List<Map<String, Object>> lealist) {
		this.lealist = lealist;
	}

	public void setLeaveRecordMap(Map<String, Object> leaveRecordMap) {
		LeaveRecordMap = leaveRecordMap;
	}

	public Map<String, Object> getLeavemap() {
		return leavemap;
	}

	public void setLeavemap(Map<String, Object> leavemap) {
		this.leavemap = leavemap;
	}

	WriteText wiText = new WriteText();

	public List<TbLea> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<TbLea> leaves) {
		this.leaves = leaves;
	}

	public void setStudentsLeaveMap(Map<String, Object> studentsLeaveMap) {
		this.studentsLeaveMap = studentsLeaveMap;
	}

	public Map<String, Object> getStudentsLeaveMap() {
		return studentsLeaveMap;
	}

	public LeavesService getLeavesService() {
		return leavesService;
	}

	public void setLeavesService(LeavesService leavesService) {
		this.leavesService = leavesService;
	}

	public String studentsLeave() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ispass = request.getParameter("isPass");
		final String stuNo = request.getParameter("stuNo");
		String leaTime = request.getParameter("leaTime");
		String backTime = request.getParameter("backTime");
		String leaReson = request.getParameter("leaReson");
		final String leaImg = request.getParameter("leaImg");
		final String leavetime = leaTime.replaceAll("[[\\s-:punct:]]", "");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				WriteText writeText = new WriteText();
				writeText.addFile(leaImg, stuNo + leavetime, "holidays_img");
			}
		}).start();
		// wiText.saveText(leaImg, stuNo + leavetime);
		boolean flag = false;
		flag = leavesService.studentsLeave(ispass,stuNo, leaTime, backTime, leaReson,
				stuNo + leavetime);
		if (flag) {
			studentsLeaveMap.put("success", true);
			return "success";
		} else {
			studentsLeaveMap.put("success", false);
			return "fail";
		}
	}

	public String leaveRecord() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		leaves = leavesService.leaveRecord(stuNo);
		for (TbLea tblea : leaves) {
			leavemap = new HashMap<String, Object>();
			leavemap.put(stuNo, tblea.getTbStu().getStuNo());
			leavemap.put("leaTime", tblea.getLeaTime());
			leavemap.put("backTime", tblea.getBackTime());
			leavemap.put("leaRea", tblea.getLeaRea());
			leavemap.put("pass", tblea.getPass());
			lealist.add(leavemap);
		}
		// System.out.println(lealist);
		System.out.println("GG"+lealist);
		LeaveRecordMap.put("stuLea", lealist);
		return "success";
	}
}
