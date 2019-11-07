package com.sba.course.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sba.course.mapper.CourseMapper;
import com.sba.course.mapper.RateMapper;
import com.sba.course.model.BatchCourse;
import com.sba.course.model.Course;
import com.sba.course.model.CourseMentor;
import com.sba.course.model.Rate;
import com.sba.course.rspmodel.RspModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(description = "SBA Course Interface")
public class SbaCourseApi {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private RateMapper rateMapper;

	@RequestMapping(value = "/addcourse", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "SBA Account Register")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> addCourse(@ApiParam(name = "body", required = true) @RequestBody Course course) {

		try {

			courseMapper.addCourse(course);

			RspModel rsp = new RspModel();
			rsp.setCode(200);
			rsp.setMessage("Course Created");
			return new ResponseEntity<RspModel>(rsp, HttpStatus.CREATED);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/addrate", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "SBA Add Rate")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> addRate(@ApiParam(name = "body", required = true) @RequestBody Rate rate) {

		try {

			rateMapper.addRate(rate);

			RspModel rsp = new RspModel();
			rsp.setCode(200);
			rsp.setMessage("Rated");
			return new ResponseEntity<RspModel>(rsp, HttpStatus.CREATED);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/listcourse", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> finUserCourses(
			@ApiParam(name = "mentorname", required = true) @RequestParam String mentorname,
			@ApiParam(name = "progress", required = true) @RequestParam Integer progress) {

		try {

			List<CourseMentor> coursementors = courseMapper.findMentprCourse(mentorname, progress);

			if (coursementors.size() > 0) {

				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(coursementors);
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

			} else {
				RspModel rsp = new RspModel();
				rsp.setCode(404);
				rsp.setMessage("No Found Courses");
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);
			}

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/listavailablecourse", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> finMentorAvailableCourses(
			@ApiParam(name = "mentorname", required = true) @RequestParam String mentorname) {

		try {

			List<CourseMentor> coursementors = courseMapper.findMentprAvailableCourse(mentorname);

			if (coursementors.size() > 0) {

				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(coursementors);
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

			} else {
				RspModel rsp = new RspModel();
				rsp.setCode(404);
				rsp.setMessage("No Found Courses");
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);
			}

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/listbatchcourses", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> finBatchCourses() {

		try {

			List<BatchCourse> batchcourse = courseMapper.batchCourse();

			if (batchcourse.size() > 0) {

				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(batchcourse);
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

			} else {
				RspModel rsp = new RspModel();
				rsp.setCode(404);
				rsp.setMessage("No Found Courses");
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);
			}

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/updatestatus/{courseid}/{status}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "SBA Update Course status")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> updateCoursesStatus(
			@ApiParam(name = "courseid", required = true) @PathVariable("courseid") Integer courseid,
			@ApiParam(name = "progress", required = true) @PathVariable("status") String status) {

		try {

			if (status.equals("completed")) {

				courseMapper.updateCourseCompletedStatus(courseid, status);

			} else if (status.equals("progress-50")) {
				courseMapper.updateCourseStatus(courseid, status.split("-")[0], 50);
			} else if (status.equals("progress-75")) {
				courseMapper.updateCourseStatus(courseid, status.split("-")[0], 75);

			} else {
				courseMapper.updateCourseStatus(courseid, status, 25);
			}

			RspModel rsp = new RspModel();
			rsp.setCode(200);
			rsp.setMessage("Update Course");
			return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@RequestMapping(value = "/batchstatus/{courseid}", method = RequestMethod.PUT, produces = "application/json")
	@ApiOperation(value = "SBA Update Batch Course status")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> updateBatchCourse(
			@ApiParam(name = "courseid", required = true) @PathVariable("courseid") Integer courseid) {

		try {

			courseMapper.updateBatchCourseStatus(courseid);

			RspModel rsp = new RspModel();
			rsp.setCode(200);
			rsp.setMessage("Update Course");
			return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
