package com.serverce;

import java.util.List;

import com.dao.StudentsDao;
import com.entiy.TbStu;

public class StudentsService {
	private StudentsDao studentsDao;

	public StudentsDao getStudentsDao() {
		return studentsDao;
	}

	public void setStudentsDao(StudentsDao studentsDao) {
		this.studentsDao = studentsDao;
	}

	public List<TbStu> checkStudents(String stuNo, String password) {
		return studentsDao.checkStudents(stuNo, password);

	}

	public boolean updateStudents(String stuNo, String stuPw, String stuTel,
			String stuAdr,String feature) {
		return studentsDao.updateStudents(stuNo, stuPw, stuTel, stuAdr,feature);
	}
	public boolean Studentsfeatures(String stuNo, String feature) {
		return studentsDao.Studentsfeatures(stuNo, feature);
	}
	
}
