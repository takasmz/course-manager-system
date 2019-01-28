package com.coursemanager.util.compilerutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.coursemanager.model.ExamInfo;

import ch.qos.logback.classic.Logger;


public class CheckUtil {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(CheckUtil.class);
	
	private static ThreadPoolExecutor executor =new ThreadPoolExecutor(10,15,200,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(10));

//	// 用于重定向输出流，即代码输出的结果，将会输出到这个缓冲区中
//	private volatile CacheOutputStream resultBuffer = new CacheOutputStream();
//	private volatile ThreadInputStream systemThreadIn = new ThreadInputStream();



	@SuppressWarnings("unchecked")
	public synchronized static Object checkThreadRunMain(final ExamInfo problemTest,final String src) throws Exception{
		String[] inputs = problemTest.getInputs().split("//|");
		String[] outputs = problemTest.getAnswer().split("//|");
    	DynamicEngine de = DynamicEngine.getInstance();
        Map<String,Object> results =  (Map<String,Object>) de.javaCodeToObject("Main",src.toString());
        Object instance = results.get("instance");
        Method method = (Method) results.get("method");
        Integer runTime = Integer.parseInt(Long.toString((long) results.get("time")));	
        Map<String,Object> map = new HashMap<String,Object>();
		for( int i=0;i<inputs.length;i++){	    	 	
			//final String temp=""+i;
            final String input=inputs[i];
			try {
				//System.setOut(new PrintStream("E://workspace//onlinejudge//lrh_onlinejudge_web//src//main//webapp//WEB-INF//views//temple//out.txt"));
               // String testname = "E://workspace//onlinejudge//lrh_onlinejudge_web//src//main//webapp//WEB-INF//views//temple//in"+temp+".txt";
	            //FileUtil.writetofile(testname,input);
	    		//InputStream is = new FileInputStream(testname);
               // System.setIn(is);
				List<Object> list = new ArrayList<Object>();
				String[] inputParams = input.split(","); 
				for(int j=0;j<inputParams.length;j++) {
					list.add(Integer.parseInt(inputParams[j]));
				}
				Object[] params = list.toArray();
                String result = method.invoke(instance,inputParams).toString();//方式一
       		 	if(CheckAnswer(result,outputs[i]) ) {
       		 		map.put("status", "Wrong Answer");	
       		 		break;
       		 	}
       		 	map.put("status", "Accepted");
            } catch (Exception e) {
				StackTraceElement[] stackTrace = e.getStackTrace();
				String errorMsg = e.toString()+"<br/>";
				for(int j=0;j<stackTrace.length;j++){
					errorMsg += stackTrace[j];
					errorMsg += "<br/>";
				}
				System.out.println(errorMsg);
				System.out.println(e.getMessage());
                e.printStackTrace();
                logger.debug("[checkThread] "+problemTest.getExamId() + " Compile Error");
                map.put("status", "Compile Error");
                break;
            }
			//System.setOut(new PrintStream(System.out));
		}
	     return map;
	}
	
	@SuppressWarnings("unchecked")
	private synchronized static Object checkThreadByRunMethod(final ExamInfo examInfo,final String src) throws IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		String[] inputs = examInfo.getInputs().split("\\|");
		String[] outputs = examInfo.getAnswer().split("\\|");
    	DynamicEngine de = DynamicEngine.getInstance();
        Map<String,Object> results =  (Map<String,Object>) de.javaCodeToObject("Main",src);
        if(results.containsKey("instance")){
			Object instance = results.get("instance");
			Method method = (Method) results.get("method");
			for( int i=0;i<inputs.length;i++){
				try {
					Object inputParams = inputs[i].split(",");
					String result = method.invoke(instance,(Object)inputParams).toString();//方式一
					//res.getClass().getTypeName();
					if(CheckAnswer(result,outputs[i]) ) {
						results.put("status", "Wrong Answer");
						break;
					}
					results.put("status", "Accepted");
				} catch (Exception e) {
					System.out.println(e.getCause());
					e.printStackTrace();
					logger.debug("[checkThreadByRunMethod] "+examInfo.getExamId() + " Compile Error");
					results.put("status", "Compile Error");
					results.put("error",e.getCause().toString());
					break;
				}
			}
		}else{
			results.put("status", "Compile Error");
		}
		return results;
	}
	
	
	
	
	private static boolean CheckAnswer(String result,String answer) {
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


	public static Object check (final ExamInfo problemTest,final String src) throws InterruptedException, ExecutionException {
		Future<?> future;
		if(executor.getQueue().size()>0) {
		}
		Callable<?> myCallable = (Callable<Object>) () -> {
			Map<String,Object> map = (Map<String, Object>) checkThreadByRunMethod(problemTest,src);
			return map;
		};
		future = executor.submit(myCallable);
		return future.get();
	}

	/**
	 * @Author 李如豪
	 * @Description 编译前进行代码检查
	 * @Date 12:04 2019/1/24
	 * @Param code 提交的代码
	 * @return 代码是否符合规范
	 **/
	public static boolean beforeRunCheckCode(String code){
		if(StringUtils.isBlank(code)){
			logger.debug("[beforeRunCheckCode] 代码为空");
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExamInfo problemTest = new ExamInfo();
		problemTest.setInputs("123,100,200|-123|120");
		problemTest.setAnswer("423|-123|120");
//		String test =
//				 "public class Main{\n"
//       		+ "public int reverse(int x){\n"
//       		+ "int rev = 0;\r\n" + 
//       		"        while (x != 0) {\r\n" + 
//       		"            int pop = x % 10;\r\n" + 
//       		"            x /= 10;\r\n" + 
//       		"            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;\r\n" + 
//       		"            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;\r\n" + 
//       		"            rev = rev * 10 + pop;\r\n" + 
//       		"        }\r\n" + 
//       		"        return rev;\n"
//       		+ "}\n"
//       		+ "}\n";
		String test = 
				"import java.util.*;" +
						"public class Main{\r\n" +
				"public int solution(String[] numbers){ \r\n"
				+ "	int a = 0;\r\n"
				+		"List<Integer> list = new ArrayList<>();"
				+ "	for(int i=0;i<numbers.length;i++){\r\n"
				+ "		a += Integer.parseInt(numbers[i]);\r\n"
				+ "	}\r\n"
				+ "	return a;\r\n"
				+ "}\r\n"
				+"}";
		System.out.println(test);
		Map<String, Object> map;
		try {
			map = (Map<String, Object>) checkThreadByRunMethod(problemTest, test);
			for(Entry<String, Object> entry:map.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
