package com.coursemanager.util.compilerutil;

import ch.qos.logback.classic.Logger;
import com.coursemanager.model.ExamTestCase;
import com.coursemanager.util.compilerutil.dto.ExamResultDto;
import com.coursemanager.util.compilerutil.dto.TestCaseDto;
import com.coursemanager.util.compilerutil.stream.CacheOutputStream;
import com.coursemanager.util.compilerutil.stream.ThreadInputStream;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author 李如豪
 * @Date 2019/2/1 11:45
 * @VERSION 1.0
 **/
public class ExamCallable implements Callable<ExamResultDto> {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SandBox.class);
    private ThreadInputStream systemThreadIn;
    private TestCaseDto testCaseDto;
    private CacheOutputStream resultBuffer;

    public ExamCallable(ThreadInputStream systemThreadIn,TestCaseDto testCaseDto,CacheOutputStream resultBuffer){
        this.resultBuffer = resultBuffer;
        this.systemThreadIn = systemThreadIn;
        this.testCaseDto = testCaseDto;
    }

    @Override
    public ExamResultDto call() throws Exception {
        DynamicEngine de = DynamicEngine.getInstance();
        ExamResultDto results = (ExamResultDto) de.javaCodeToObject("Main",testCaseDto.getCode());
        if(results.getInstance() != null){
            Object instance = results.getInstance();
            Method method = results.getMethod();
            if (!Modifier.isStatic(method.getModifiers()))
                logger.error("main方法不是静态方法");
            method.setAccessible(true);
            List<ExamTestCase> testCaseList = testCaseDto.getTestCaseItem();
            for( int i=0;i<testCaseList.size();i++){
                try {
                    ExamTestCase testCase = testCaseList.get(i);
                    String inputParams = testCase.getInput();
                    systemThreadIn.setThreadIn(new ByteArrayInputStream(inputParams.getBytes()));
                    System.setIn(systemThreadIn);
                    System.setOut(new PrintStream(resultBuffer));
                    method.invoke(instance,(Object) new String[]{});//方式一
                    if(CheckAnswer(testCase.getOutput()) ) {
                        results.setStatus("Wrong Answer");
                        break;
                    }
                    results.setStatus("Accepted");
                } catch (InvocationTargetException e) {
                    logger.error(e.getCause().toString());
                    e.printStackTrace();
                    results.setStatus("Compile Error");
                    results.setError(e.getCause().toString());
                    Throwable throwable = e.getTargetException();
                    if (throwable instanceof OutOfMemoryError) {
                        results.setError("内存溢出");
                    } else {
                        results.setError(throwable.getMessage());
                    }
                    results.setNormal(false);
                    break;
                } catch (RuntimeException runtimeException) {
                    results.setError(runtimeException.getMessage());
                    results.setNormal(false);
                    break;
                } finally {
                    systemThreadIn.removeAndCloseThreadIn();
                }
            }
        }else{
            results.setStatus("Compile Error");
        }
        return results;
    }

    private boolean CheckAnswer(String answer) {
        String result = new String(resultBuffer.removeBytes(Thread
                .currentThread().getId())).replaceAll("\r\n","");
        if(answer.contains(" ")) {
            long num = Arrays.asList(answer.split(" ")).stream().filter(t -> result.contains(t)).count();
            if(num == answer.split(" ").length) {
                return true;
            }else {
                return false;
            }
        }else {
            return !result.equals(answer);
        }
    }
}
