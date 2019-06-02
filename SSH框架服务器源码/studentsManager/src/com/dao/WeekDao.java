package com.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.entiy.TbWeek;

public class WeekDao extends HibernateDaoSupport {
	public List<TbWeek> claweek() {
		@SuppressWarnings("unchecked")
		List<TbWeek> weeks = this.getHibernateTemplate().find("from TbWeek");
		return weeks;
	}
}
