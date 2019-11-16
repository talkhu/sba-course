package com.sba.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sba.course.model.BatchCourse;
import com.sba.course.model.Course;
import com.sba.course.model.CourseMentor;


@Mapper
public interface CourseMapper {

	@Insert("insert into course.course(name,description,skill,startDate,endDate,mentorName,fee) values(#{name},#{description},#{skill},date_add(#{startDate},interval 1 day),date_add(#{endDate},interval 1 day),#{mentorName},#{fee})")
	@SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
	void addCourse(Course course);
	
	@Select("SELECT id,name,skill,startDate,endDate,status, userName,description, DATEDIFF(endDate, startDate) as duration, schedule FROM course.course where mentorName=#{mentorname} and progress = #{progress}")
	List<CourseMentor> findMentprCourse(@Param("mentorname") String mentorname, @Param("progress") Integer progress);
	
	@Select("SELECT id,name,skill,startDate,endDate,status, userName,description, DATEDIFF(endDate, startDate) as duration, schedule FROM course.course where mentorName=#{mentorname} and status = 'available' and progress is null")
	List<CourseMentor> findMentprAvailableCourse(@Param("mentorname") String mentorname);

	@Select("SELECT id,name,skill,startDate,endDate,status, userName,description, DATEDIFF(endDate, startDate) as duration, schedule FROM course.course where mentorName=#{mentorname} and status = 'disable' and progress is null")
	List<CourseMentor> findMentprDisableCourse(@Param("mentorname") String mentorname);

	@Update("update course.course set status = #{status},schedule=#{schedule} where id = #{courseid}")
	void updateCourseStatus(@Param("courseid") Integer courseid, @Param("status") String status, @Param("schedule") Integer schedule);
	
	@Update("update course.course set status = #{status},progress = 2,schedule=100 where id = #{courseid}")
	void updateCourseCompletedStatus(@Param("courseid") Integer courseid, @Param("status") String status);
	
	@Update("update course.course set status = 'expried' where id = #{courseid}")
	void updateBatchCourseStatus(@Param("courseid") Integer courseid);
	
	@Select("select id,endDate FROM course.course where progress is null and status='available'")
	List<BatchCourse> batchCourse();

	@Update("update course.course set status = 'disable' where name like #{name} and status = 'available' and mentorName=#{mentorname} and progress is null")
	void updateCourseAsDisable(@Param("name") String name,@Param("mentorname") String mentorname);

	@Update("update course.course set status = 'available' where name like #{name} and status = 'disable' and mentorName=#{mentorname} and progress is null")
	void updateCourseAsAvailable(@Param("name") String name,@Param("mentorname") String mentorname);
}
