package com.sba.course.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import com.sba.course.model.Rate;

@Mapper
public interface RateMapper {

	@Insert("insert into course.rate(courseId,rating) values(#{courseId},#{rating})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
	void addRate(Rate rate);
}
