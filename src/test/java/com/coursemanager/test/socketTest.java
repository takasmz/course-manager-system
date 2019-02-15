package com.coursemanager.test;

import com.coursemanager.model.ExamTestCase;
import com.coursemanager.util.compilerutil.dto.CommunicationSignal;
import com.coursemanager.util.compilerutil.dto.Request;
import com.coursemanager.util.compilerutil.dto.TestCaseDto;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author 李如豪
 * @Date 2019/2/8 13:10
 * @VERSION 1.0
 **/
public class socketTest implements Runnable {

    private socketTest(){
        this.run();
    }

    public static void main(String[] args) {
        new socketTest();
    }

    public void connect(){
        try {
            //ServerSocket serverSocket = new ServerSocket(8089);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost",8089));
            socket.setSoTimeout(9999999);
            Scanner scanner = new Scanner(System.in);
            final Scanner scanner2 = new Scanner(socket.getInputStream());
            new Thread(() -> {
                while (true) {
                    if (scanner2.hasNextLine()) {
                        System.out.println(scanner2.nextLine());
                    }
                }
            }).start();
            TestCaseDto testCaseDto = new TestCaseDto();
            testCaseDto.setCode("import java.util.*;\n" +
                    "public class Main{\n" +
                    "public int solution(String x){ \n" +
                    "String[] numbers = x.split(\",\");\n" +
                    "\tint a = 0;\n" +
                    "List<Integer> list = new ArrayList<>();\n" +
                    "\tfor(int i=0;i<numbers.length;i++){\n" +
                    "\t\ta += Integer.parseInt(numbers[i]);\n" +
                    "\t}\n" +
                    "\treturn a;\n" +
                    "}\n" +
                    "public static void main(String[] args){\n" +
                    "Main m = new Main();\n" +
                    "Scanner scanner = new Scanner(System.in);\n" +
                    "String x = scanner.nextLine();\n" +
                    "System.out.println(m.solution(x));\n" +
                    "}\n" +
                    "}");
            List<ExamTestCase> list = new ArrayList<>();
            for(int i =0;i<10;i++){
                ExamTestCase examTestCase = new ExamTestCase();
                examTestCase.setExamId(i);
                examTestCase.setId(i+1);
                examTestCase.setInput("100,200,301");
                examTestCase.setOutput("601");
                list.add(examTestCase);
            }
            testCaseDto.setTestCaseItem(list);
            Gson gson = new Gson();
            int i;
            while (scanner.hasNext()) {
                i = scanner.nextInt();
                Request request = new Request();
                if (i == 0) {
                    request.setCommand(CommunicationSignal.RequestSignal.SANDBOX_STATUS);
                } else if (i == 1) {
                    request.setCommand(CommunicationSignal.RequestSignal.CLOSE_SANDBOX);
                } else if (i == 2) {
                    request.setCommand(CommunicationSignal.RequestSignal.REQUSET_JUDGED_PROBLEM);
                    request.setData(gson.toJson(testCaseDto));
                    request.setSignalId("111");
                } else if (i == 3) {
                    request.setCommand(CommunicationSignal.RequestSignal.IS_BUSY);
                }
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write((gson.toJson(request) + "\n").getBytes());
            }

//            synchronized (this) {
//                while (socket.getInputStream().available() == 0) {
//
//                }
//                //notifyAll();
//            }

//            br.close();
//            inputStream.close();
//            printWriter.close();
//            outputStream.close();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        connect();
    }
}
