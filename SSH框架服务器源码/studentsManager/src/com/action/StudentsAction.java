package com.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Flags.Flag;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.entiy.TbStu;
import com.entiy.TbWeek;
import com.opensymphony.xwork2.ActionSupport;
import com.serverce.StudentsService;
import com.until.WriteText;

public class StudentsAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1397377872152987317L;
	private StudentsService studentsService = new StudentsService();
	private Map<String, Object> studentsLogintMap = new HashMap<String, Object>();
	private Map<String, Object> studentsfeatureMap = new HashMap<String, Object>();
	private Map<String, Object> studentsUpadatetMap = new HashMap<String, Object>();
	private List<TbStu> tbStus;
	private String head;
	private String imgStr;
	Map<String, Object> stumap = null;

	public StudentsService getStudentsService() {
		return studentsService;
	}

	public void setStudentsService(StudentsService studentsService) {
		this.studentsService = studentsService;
	}

	public Map<String, Object> getStudentsLogintMap() {
		return studentsLogintMap;
	}

	public void setStudentsLogintMap(Map<String, Object> studentsLogintMap) {
		this.studentsLogintMap = studentsLogintMap;
	}

	public Map<String, Object> getStudentsUpadatetMap() {
		return studentsUpadatetMap;
	}

	public void setStudentsUpadatetMap(Map<String, Object> studentsUpadatetMap) {
		this.studentsUpadatetMap = studentsUpadatetMap;
	}

	public String studentsLogin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String password = request.getParameter("password");
		tbStus = studentsService.checkStudents(stuNo, password);
		WriteText writeText = new WriteText();
		try {
			head = writeText.redFile(stuNo, "head_img");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(head+"123");
		if (tbStus.size() > 0) {
			for (TbStu tbStu : tbStus) {
				stumap = new HashMap<String, Object>();
				studentsLogintMap.put("stuName", tbStu.getStuName());
				if(tbStu.getStuAdr()==null){
					studentsLogintMap.put("StuAdr","");
				}else {
					studentsLogintMap.put("StuAdr", tbStu.getStuAdr());
				}
				if (tbStu.getStuTel()==null) {
					studentsLogintMap.put("StuTel", "");
				} else {
					studentsLogintMap.put("StuTel", tbStu.getStuTel());
				}
				studentsLogintMap.put("StuState", String.valueOf(tbStu.getStuState()));
				//System.out.println("stata"+tbStu.getStuState());
				studentsLogintMap.put("StuFeature", tbStu.getStuFeature());
				// System.out.println("s"+ tbStu.getStuFeature());

			}
			studentsLogintMap.put("head", head);
			studentsLogintMap.put("success", true);

			return "success";
		} else
			studentsLogintMap.put("success", false);
		return "fail";
	}

	public String stuFeature() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String stuNo = request.getParameter("stuNo");
		String feature = request.getParameter("features");
		boolean flag = false;
		flag = studentsService.Studentsfeatures(stuNo, feature);
		if (flag) {
			studentsfeatureMap.put("success", true);
			return "success";
		} else {
			studentsfeatureMap.put("success", false);
			return "fail";
		}
	}

	public String updateStudents() {
		HttpServletRequest request = ServletActionContext.getRequest();
		final String stuNo = request.getParameter("stuNo");
		String stuPw = request.getParameter("stuPw");
		String stuTel = request.getParameter("StuTel");
		String stuAdr = request.getParameter("stuAdr");
		String feature = request.getParameter("feature");
		imgStr = request.getParameter("imgStr");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				WriteText writeText = new WriteText();
				writeText.addFile(imgStr, stuNo, "head_img");
			}
		}).start();
		boolean flag = false;
		flag = studentsService.updateStudents(stuNo, stuPw, stuTel, stuAdr,
				feature);
		if (flag) {
			studentsUpadatetMap.put("success", true);
			return "success";
		} else {
			studentsUpadatetMap.put("success", false);
			return "fail";
		}
	}

}
