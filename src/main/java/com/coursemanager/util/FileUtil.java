package com.coursemanager.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	public static final  String TEMP_PATH = Objects.requireNonNull(FileUtil.class.getClassLoader().getResource("")).getPath();

    /**
     * 上传作业文件
     * @param request 数据流
     * @param filePath 保存路径
     * @Param formName 表单文件名称
     * @Param  saveName 文件储存名称
     * @return map 储存的文件路径和名称和文件大小
     */
	public static Map<String, Object> uploadFile(HttpServletRequest request,String filePath,String formName,String saveName){
        logger.debug("开始上传文件---上传路径为:"+filePath);
		Map<String,Object> result = new HashMap<>();
        result.put("path",filePath);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(formName);
		filePath = TEMP_PATH + filePath;
		StringBuilder filenames = new StringBuilder();
		StringBuilder sizes = new StringBuilder();
        for (MultipartFile file:files) {    
            if (!file.isEmpty()) {    
                try {
                	String filename = file.getOriginalFilename();
                    filename = filename.substring(Objects.requireNonNull(filename).lastIndexOf("\\")+1);
                	String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                	//如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
//                    if(StringUtils.isEmpty(fileName)){
//                        fileName = makeFileName(filename);
//                    }
                    String saveFilename = StringUtils.isEmpty(saveName)?filename:saveName;
                    //得到文件的保存目录
                    File f = new File(filePath);
                    //如果目录不存在
                    if(!f.exists()){
                        //创建目录
                        f.mkdirs();
                    }
                    //获取item中的上传文件的输入流
                    InputStream in = file.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(filePath + "\\" + saveFilename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    //item.delete();
                    filenames.append(filename).append("|");
                    sizes.append(file.getSize()).append("|");
                } catch (Exception ignored) {    }
            } else {  
            	logger.debug("文件为空");
            }  
        }
        logger.debug("[uploadFile] 上传的文件为：" + filenames);
        result.put("filenames", filenames.toString());
        result.put("sizes", sizes.toString());
        return result;
	}


	
	public static Map<String, Object> uploadFile(HttpServletRequest request){
		return uploadFile(request,TEMP_PATH,"files[]",null);
	}
	
	private static String makeFileName(String filename){  //2.jpg
        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString();
    }

	private static String makePath(String filename,String savePath){
        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
        int hashcode = filename.hashCode();
        int dir1 = hashcode&0xf;  //0--15
        int dir2 = (hashcode&0xf0)>>4;  //0-15
        //构造新的保存目录
        String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
        //File既可以代表文件也可以代表目录
        File file = new File(dir);
        //如果目录不存在
        if(!file.exists()){
            //创建目录
            file.mkdirs();
        }
        return dir;
    }

    public static void SetDownloadResponseHeader(String filename,HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setContentType(request.getServletContext().getMimeType(filename));
        String browser;
        // 设置响应头，控制浏览器下载该文件
        browser = request.getHeader("User-Agent");
        if (-1 < browser.indexOf("MSIE 6.0") || -1 < browser.indexOf("MSIE 7.0")) {
            // IE6, IE7 浏览器
            response.addHeader("content-disposition", "attachment;filename="
                    + new String(filename.getBytes(), "ISO8859-1"));
        } else if (-1 < browser.indexOf("MSIE 8.0")) {
            // IE8
            response.addHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(filename, "UTF-8"));
        } else if (-1 < browser.indexOf("MSIE 9.0")) {
            // IE9
            response.addHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode(filename, "UTF-8"));
        } else if (-1 < browser.indexOf("Chrome")) {
            // 谷歌
            response.addHeader("content-disposition",
                    "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
        } else if (-1 < browser.indexOf("Safari")) {
            // 苹果
            response.addHeader("content-disposition", "attachment;filename="
                    + new String(filename.getBytes(), "ISO8859-1"));
        } else {
            // 火狐或者其他的浏览器
            response.addHeader("content-disposition",
                    "attachment;filename*=UTF-8''" + URLEncoder.encode(filename, "UTF-8"));
        }
    }
	
	public static void download(String pathAndFilename,HttpServletRequest request,HttpServletResponse response) throws Exception {
		File file = new File( pathAndFilename);
		String filename = pathAndFilename.substring(pathAndFilename.lastIndexOf("/")+1);
		if (!file.exists()) {
            logger.error("您要下载的资源不存在");
            return;
        }
		SetDownloadResponseHeader(filename,request,response);
        //response.setHeader("Content-Disposition", "attachment;filename="+filename);
        // 读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream( pathAndFilename);
        // 创建输出流
        OutputStream out = response.getOutputStream();
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            // 输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        // 关闭文件输入流
        in.close();
        // 关闭输出流
        out.close();		
	}


}
