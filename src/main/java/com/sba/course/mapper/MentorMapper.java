package com.sba.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sba.course.model.CourseMentor;
import com.sba.course.model.MentorCourse;

@Mapper
public interface MentorMapper {
	
	@Select("SELECT a.id,a.name,a.mentorName,a.skill,a.startDate,a.endDate,DATEDIFF(a.endDate, a.startDate) as duration,a.fee, coalesce(b.rating, 0) as rate, a.description FROM course.course a  left join (SELECT courseId, round(avg(rating)) as rating FROM course.rate group by courseId) b on a.id =b.courseid where a.status='available'")
	List<MentorCourse> findMentors();
	
	@Select("SELECT a.id,a.name,a.mentorName,a.skill,a.startDate,a.endDate,DATEDIFF(a.endDate, a.startDate) as duration,a.fee, a.description, a.userName FROM course.course a where  a.status='available' and a.userName is null")
	List<MentorCourse> searchMentors();
	
	@Update("update course.course set userName=#{username},progress=1,status='booked',schedule=0 where id = #{id}")
	void bookCourse(@Param("username") String username, @Param("id") Integer id);
	
	@Select("SELECT a.id,a.name,a.userName,a.skill,a.startDate,a.endDate, DATEDIFF(a.endDate, a.startDate) as duration,a.fee as cost, coalesce(b.rating, 0) as rate, a.description FROM course.course a  left join (SELECT courseId, round(avg(rating)) as rating FROM course.rate group by courseId) b on a.id =b.courseid where a.status='completed' and a. mentorName=#{mentorname}")
	List<CourseMentor> findCompeletedMentors(@Param("mentorname") String mentorname);

}