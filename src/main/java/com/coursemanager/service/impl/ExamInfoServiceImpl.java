package com.coursemanager.service.impl;

import com.coursemanager.dto.ExamInfoDto;
import com.coursemanager.mapper.ExamInfoMapper;
import com.coursemanager.model.ExamInfo;
import com.coursemanager.service.IExamInfoService;

import com.coursemanager.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

/**
 * 描述:  exam_info 对应的Service接口实现类.<br>
 *
 * @author framework generator
 * @date 2018年11月18日
 */
@Service
public class ExamInfoServiceImpl extends MyBatisServiceSupport implements IExamInfoService {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ExamInfoServiceImpl.class);

    @Autowired
    private ExamInfoMapper examInfoMapper;

    @Override
    public ExamInfoDto queryExamById(String examId, String courseExamId) {
        if(StringUtils.isBlank(examId)){
            logger.debug("[queryExamById] examId is null");
            return null;
        }
        ExamInfoDto exam = examInfoMapper.queryExamById(examId);
        if(exam == null) return null;
        String path = FileUtil.TEMP_PATH + "teacher/homework/" + courseExamId + "/file/"+ examId;
        File file = new File(path);
        if(file.exists() && file.isDirectory() && file.listFiles() !=null && Objects.requireNonNull(file.listFiles()).length>0)
            exam.setFilePath(StringUtils.join(file.list(),"|"));
        else
            logger.debug("[queryExamById] "+path+"该路径没有附件");
        return exam;
    }

    @Override
    public Integer deleteExamById(String examId) {
        ExamInfo examInfo = new ExamInfo();
        examInfo.setExamId(examId);
        return examInfoMapper.deleteByPrimaryKey(examId);
    }
}