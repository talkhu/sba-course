package com.sba.course.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sba.course.client.PaymentClient;
import com.sba.course.mapper.MentorMapper;
import com.sba.course.model.CourseMentor;
import com.sba.course.model.MentorCourse;
import com.sba.course.model.Payment;
import com.sba.course.rspmodel.RspModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/mentor")
@Api(description = "SBA Mentor Interface")
public class SbaMentorApi {
	
	@Autowired
	private MentorMapper mentorcoursemapper;
	
	@Autowired
	private PaymentClient paymentclient;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> findCourses() {

		try {

			List<MentorCourse> mentorcourses = mentorcoursemapper.findMentors();
			
			if (mentorcourses.size() > 0) {
				
				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(mentorcourses);
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
	
	@RequestMapping(value = "/searchcourse", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> searchCourses() {

		try {

			List<MentorCourse> mentorcourses = mentorcoursemapper.searchMentors();
			
			if (mentorcourses.size() > 0) {
				
				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(mentorcourses);
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
	
	@RequestMapping(value = "/book", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> bookCourses(@ApiParam(name = "body", required = true) @RequestBody Payment bookcourse) {

		try {
				
				mentorcoursemapper.bookCourse(bookcourse.getUserName(), bookcourse.getCourseId());			
				
				Payment payment = new Payment();
				payment.setCourseId(bookcourse.getCourseId());
				payment.setUserName(bookcourse.getUserName());
				payment.setMentorName(bookcourse.getMentorName());
				payment.setStartDate(bookcourse.getStartDate());
				payment.setEndDate(bookcourse.getEndDate());
				payment.setFee(bookcourse.getFee());
				
				paymentclient.addPayment(payment);
				
				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Book Sucessful");
				return new ResponseEntity<RspModel>(rsp, HttpStatus.OK);

		} catch (Exception ex) {
			RspModel rsp = new RspModel();
			rsp.setCode(500);
			rsp.setMessage(ex.getMessage());
			return new ResponseEntity<RspModel>(rsp, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}
	
	@RequestMapping(value = "/listdone", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA Mentor Completed Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> findCompletedMentors(@ApiParam(name = "mentorname", required = true) @RequestParam String mentorname) {

		try {

			List<CourseMentor> coursementors = mentorcoursemapper.findCompeletedMentors(mentorname);
			
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

}
