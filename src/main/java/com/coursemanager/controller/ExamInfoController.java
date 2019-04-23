package com.coursemanager.controller;

import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.model.ExamInfo;
import com.coursemanager.service.IExamInfoService;
import com.coursemanager.util.common.AjaxResponse;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述:  exam_info 对应的Controller类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Controller
@RequestMapping("course/exam")
public class ExamInfoController extends BaseController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ExamInfoController.class);

    @Autowired
    private IExamInfoService examInfoService;

    @RequestMapping("/queryExamById")
    @ResponseBody
    public AjaxResponse queryExamById(String examId , String courseExamId){
        logger.debug("[queryExamById] start");
        ExamInfoDto exam = examInfoService.queryExamById(examId,courseExamId);
        if(exam == null){
            logger.debug("[queryExamById] 未找到相应题目信息");
            return AjaxResponse.error("未找到相应题目信息");
        }else{
            return AjaxResponse.success("数据获取成功",exam);
        }
    }

    @RequestMapping("/deleteExamById")
    @ResponseBody
    public AjaxResponse deleteExamById(String examId){
        logger.debug("[deleteExamById] start");
        if(StringUtils.isBlank(examId)){
            logger.debug("[queryExamById] examId is null");
            return AjaxResponse.error("参数为空");
        }
        Integer num = examInfoService.deleteExamById(examId);
        if(num != 1){
            return AjaxResponse.error("删除失败");
        }else{
            return AjaxResponse.success("删除成功");
        }
    }

}