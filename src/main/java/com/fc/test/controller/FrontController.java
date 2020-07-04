package com.fc.test.controller;

import com.fc.test.common.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/front")
public class FrontController  extends BaseController {
    private String prefix = "front";
    /*@ApiOperation(value="首页",notes="首页")
    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        return prefix+"/index";
    }

    @ApiOperation(value="课程",notes="课程")
    @GetMapping("/course")
    public String course(HttpServletRequest request) {
        return prefix+"/course.html";
    }

    @ApiOperation(value="首页",notes="首页")
    @GetMapping("/signin")
    public String signIn(HttpServletRequest request) {
        return prefix+"/signin";
    }

    @ApiOperation(value="首页",notes="首页")
    @GetMapping("/signup")
    public String signUp(HttpServletRequest request) {
        return prefix+"/signup";
    }

    @ApiOperation(value="首页",notes="首页")
    @GetMapping("/course/exam")
    public String exam(HttpServletRequest request) {
        return prefix+"/course/exam";
    }

    @ApiOperation(value="首页",notes="首页")
    @GetMapping("/course/page1")
    public String page1(HttpServletRequest request) {
        return prefix+"/course/page1";
    }

    @ApiOperation(value="首页",notes="首页")
    @GetMapping("/course/page2")
    public String page2(HttpServletRequest request) {
        return prefix+"/course/page2";
    }*/
}
