package com.sba.course.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sba.course.client.PaymentClient;
import com.sba.course.mapper.UserCourseMapper;
import com.sba.course.model.MentorCourse;
import com.sba.course.model.UserCourse;
import com.sba.course.rspmodel.RspModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/user")
@Api(description = "SBA User Interface")
public class SbaUserCourseApi {

	@Autowired
	private UserCourseMapper usercoursemapper;

	@Autowired
	private PaymentClient paymentclient;

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA User Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> finUserCourses(
			@ApiParam(name = "username", required = true) @RequestParam String username,
			@ApiParam(name = "progress", required = true) @RequestParam Integer progress) {

		try {

			List<UserCourse> usercourses = usercoursemapper.findUserCourse(username, progress);

			if (usercourses.size() > 0) {

				for (int i = 0; i < usercourses.size(); i++) {

					ResponseEntity<Object> result = paymentclient.checkPayment(usercourses.get(i).getId());
					JsonObject paymentresult = getResult(result);
					usercourses.get(i).setCost(paymentresult.getAsJsonObject("data").get("cost").getAsFloat());
				}

				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(usercourses);
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

	@RequestMapping(value = "/listdone", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "SBA User Completed Course List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "ok"), @ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 401, message = "No Authroization"), @ApiResponse(code = 403, message = "No Permission"),
			@ApiResponse(code = 404, message = "No Mentors Found"),
			@ApiResponse(code = 500, message = "Internal Error") })
	public ResponseEntity<RspModel> finUserCompletedCourses(
			@ApiParam(name = "username", required = true) @RequestParam String username) {

		try {

			List<MentorCourse> usercompletedcourses = usercoursemapper.findUserCompletedCourse(username);

			if (usercompletedcourses.size() > 0) {

				RspModel rsp = new RspModel();
				rsp.setCode(200);
				rsp.setMessage("Found Courses");
				rsp.setData(usercompletedcourses);
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

	public JsonObject getResult(ResponseEntity<Object> result) {
		Gson gson = new Gson();
		String jsonResultStr = gson.toJson(result.getBody());
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(jsonResultStr);

		return object;

	}

}
