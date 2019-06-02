package com.serverce;

import java.util.List;

import com.dao.WeekDao;
import com.entiy.TbWeek;
public class WeekService {
	private WeekDao weekDao;
	
	public WeekDao getWeekDao() {
		return weekDao;
	}

	public void setWeekDao(WeekDao weekDao) {
		this.weekDao = weekDao;
	}

	public List<TbWeek> claweek() {
		return weekDao.claweek();
	}
}
