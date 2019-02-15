package com.coursemanager.sandbox.job;

import com.coursemanager.sandbox.SandboxService;
import com.coursemanager.util.common.JsonUtil;
import com.coursemanager.util.compilerutil.dto.ExamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @Author 李如豪
 * @Date 2019/2/14 15:12
 * @VERSION 1.0
 **/
public class JudgeExamJob implements Runnable, Serializable {

    private final static Logger logger = LoggerFactory.getLogger(JudgeExamJob.class);

    @Override
    public void run() {

    }


    /**
     * @Author 李如豪
     * @Description 判题监听器
     * @Date 10:42 2019/2/14
     **/
    private class JobJudgeResultListener implements
            SandboxService.JudgeResultListener {
        private Sorce sorce;

        @Override
        public void judgeResult(ExamResult examResult) {
            sorce = new Sorce();
            sorce.useTime = examResult.getUseTime();
            sorce.examId = examResult.getExamId();
            sorce.useMemory = examResult.getUseMemory();
            if(examResult.getStatus().equals("success")){
                sorce.error = "";
                sorce.isRight = true;
                sorce.message = "success";
            }else{
                sorce.isRight = false;
                sorce.error = examResult.getError();
                sorce.message = examResult.getStatus();
            }
        }

        /**
         * 如果还没有结果，就会阻塞到有结果为止才返回
         * @return score对象的String
         */
        public String getJsonSorce() {
            synchronized (this) {
                while (sorce == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
            return JsonUtil.toJson(sorce);
        }
    }

    private static class Sorce {
        long useTime;
        long useMemory;
        String error;
        String message;
        String examId;
        boolean isRight;
    }
}
