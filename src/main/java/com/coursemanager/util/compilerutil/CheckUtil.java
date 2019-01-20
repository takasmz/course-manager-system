package com.coursemanager.util.compilerutil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import com.coursemanager.model.ExamInfo;

import ch.qos.logback.classic.Logger;


public class CheckUtil {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(CheckUtil.class);
	
	private static ThreadPoolExecutor executor =new ThreadPoolExecutor(10,15,200,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(10));

	@SuppressWarnings("unchecked")
	public synchronized static Object checkThread(final ExamInfo problemTest,final String src) throws Exception{
		//String[] names = problemTest.getParamsName().split(",");
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
	public synchronized static Object checkThreadByRunMethod(final ExamInfo problemTest,final String src) throws UnsupportedEncodingException, IOException, InterruptedException, ExecutionException, IllegalAccessException, InstantiationException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
		String[] inputs = problemTest.getInputs().split("\\|");
		String[] outputs = problemTest.getAnswer().split("\\|");
    	DynamicEngine de = DynamicEngine.getInstance();
        Map<String,Object> results =  (Map<String,Object>) de.javaCodeToObject("Main",src.toString());
        if(results.containsKey("instance")){
			Object instance = results.get("instance");
			Method method = (Method) results.get("method");
			for( int i=0;i<inputs.length;i++){
				try {
					String[] inputParams = inputs[i].split(",");
					String result = method.invoke(instance,(Object)inputParams).toString();//方式一
					if(CheckAnswer(result,outputs[i]) ) {
						results.put("status", "Wrong Answer");
						break;
					}
					results.put("status", "Accepted");
				} catch (Exception e) {
					System.out.println(e.getCause());
					e.printStackTrace();
					logger.debug("[checkThreadByRunMethod] "+problemTest.getExamId() + " Compile Error");
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
		Future<?> future = null;
		Map<String,Object> result = new HashMap<String,Object>();
		if(executor.getQueue().size()>0) {
			//result.put("status", "Penging");
		}
		Callable<?> myCallable = new Callable<Object>() {
			public Object call() throws Exception {
				Map<String,Object> map = (Map<String, Object>) checkThreadByRunMethod(problemTest,src);
				return map;
			}
		};
		future = executor.submit(myCallable);
		return future.get();
	}
	
//	public static boolean checkAnswer(ExamInfo problemTest) throws UnsupportedEncodingException, IOException {
//		String[] outputs = problemTest.getAnswer().split(",");
//		boolean check = true;
//	     String result = FileUtil.readFileByLine("E://workspace//onlinejudge//lrh_onlinejudge_web//src//main//webapp//WEB-INF//views//temple//out.txt");
//	     String[] results = result.split("\r\n");
//	     for(int j=0;j<results.length;j++) {
//	    	 if(!results[j].equals(outputs[j])) {
//	    		 check = false;
//	    	 }
//	     }
//	    return check;
//	}
	
	
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
				"public class Main{\r\n" + 
				"public int solution(String[] numbers){ \r\n"
				+ "	int a = 0;\r\n"
				+ "	for(int i=0;i<numbers.length+1;i++){\r\n"
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
