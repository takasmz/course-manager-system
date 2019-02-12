package com.coursemanager.test;

import com.coursemanager.model.ExamTestCase;
import com.coursemanager.util.compilerutil.dto.CommunicationSignal;
import com.coursemanager.util.compilerutil.dto.Request;
import com.coursemanager.util.compilerutil.dto.TestCaseDto;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 李如豪
 * @Date 2019/2/8 13:10
 * @VERSION 1.0
 **/
public class socketTest {

//    public static void main(String[] args) {
//        try {
//            //ServerSocket serverSocket = new ServerSocket(8089);
//            Socket socket = new Socket("localhost",8089);
//            TestCaseDto testCaseDto = new TestCaseDto();
//            testCaseDto.setCode("import java.util.*;\n" +
//                    "public class Main{\n" +
//                    "public int solution(String x){ \n" +
//                    "String[] numbers = x.split(\",\");\n" +
//                    "\tint a = 0;\n" +
//                    "List<Integer> list = new ArrayList<>();\n" +
//                    "\tfor(int i=0;i<numbers.length;i++){\n" +
//                    "\t\ta += Integer.parseInt(numbers[i]);\n" +
//                    "\t}\n" +
//                    "\treturn a;\n" +
//                    "}\n" +
//                    "public static void main(String[] args){\n" +
//                    "Main m = new Main();\n" +
//                    "Scanner scanner = new Scanner(System.in);\n" +
//                    "String x = scanner.nextLine();\n" +
//                    "System.out.println(m.solution(x));\n" +
//                    "}\n" +
//                    "}");
//            List<ExamTestCase> list = new ArrayList<>();
//            for(int i =0;i<10;i++){
//                ExamTestCase examTestCase = new ExamTestCase();
//                examTestCase.setExamId(i);
//                examTestCase.setId(i+1);
//                examTestCase.setInput("100,200,301");
//                examTestCase.setOutput("601");
//                list.add(examTestCase);
//            }
//            testCaseDto.setTestCaseItem(list);
//            Gson gson = new Gson();
//            Request request = new Request();
//            request.setCommand(CommunicationSignal.RequestSignal.REQUSET_JUDGED_PROBLEM);
//            request.setData(gson.toJson(testCaseDto));
//            request.setSignalId("111");
//
//            OutputStream outputStream = socket.getOutputStream();
//            PrintWriter printWriter = new PrintWriter(outputStream);
//            String reJson = gson.toJson(request);
//            printWriter.write(reJson);
//            printWriter.flush();
//            socket.shutdownOutput();
//            InputStream  inputStream = socket.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String str;
//            StringBuilder stringBuilder = new StringBuilder();
//            while( (str = br.readLine()) != null){
//                stringBuilder.append(str);
//            }
//            System.out.println(stringBuilder);
//            br.close();
//            inputStream.close();
//            printWriter.close();
//            outputStream.close();
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
