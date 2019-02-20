package com.coursemanager.controller;

import com.coursemanager.service.IExamTestCaseService;
import com.coursemanager.util.common.AjaxResponse;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/examTestCase")
public class ExamTestCaseController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExamTestCaseController.class);

    @Autowired
    private IExamTestCaseService examTestCaseService;

    /**
     * @Author 李如豪
     * @Description 添加测试用例
     * @Date 11:32 2019/2/16
     **/
    @RequestMapping("/addTestCase")
    @ResponseBody
    public AjaxResponse addTestCase(HttpServletRequest request){
        logger.debug("[addTestCase] start");
        int num = examTestCaseService.addTestCase(request);
        if(num == 1){
            return AjaxResponse.success("测试用例添加成功");
        }else{
            return AjaxResponse.error("测试用例添加失败");
        }
    }

    @RequestMapping("checkTestCaseNum")
    @ResponseBody
    public int checkTestCaseNum(String examId){
        logger.debug("[checkTestCaseNum] start");
        return examTestCaseService.checkTestCaseNum(examId);
    }
}