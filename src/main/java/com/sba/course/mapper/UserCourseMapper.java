package com.sba.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sba.course.model.MentorCourse;
import com.sba.course.model.UserCourse;


@Mapper
public interface UserCourseMapper {

	@Select("SELECT id,skill,name,description,startDate,endDate,mentorName,fee,status,DATEDIFF(endDate, startDate) as duration, schedule FROM course.course where progress=#{progress} and userName=#{username}")
	List<UserCourse> findUserCourse(@Param("username") String username, @Param("progress") Integer progress);
	
	@Select("SELECT a.id,a.name,a.mentorName,a.skill,a.startDate,a.endDate, DATEDIFF(a.endDate, a.startDate) as duration,a.fee, coalesce(b.rating, 0) as rate, a.description FROM course.course a  left join (SELECT courseId, round(avg(rating)) as rating FROM course.rate group by courseId) b on a.id =b.courseid where a.status='completed' and a.userName=#{username}")
	List<MentorCourse> findUserCompletedCourse(@Param("username") String username);
}
