package com.coursemanager.util.common;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public class ZipUtils {
	private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 压缩单个文件multipartFile
	 */
	public static void doCompressMultipartFile(MultipartFile file, ZipOutputStream out) throws IOException {
		// 判断是否为空
		if (!file.isEmpty()) {
			InputStream input = file.getInputStream();
			out.putNextEntry(new ZipEntry(file.getOriginalFilename()));
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = input.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			if (input != null) {
				input.close();
			}
		}
	}
	
	public static void doCompressFileArray(File[] file, ZipOutputStream out) throws IOException {
		if (file ==null || file.length == 0) {
			return;
		}
		for(int i=0; i < file.length; i++) {
			doCompressFile(file[i], out);
		}
		
	}
	
	
	
	public static void doCompressFile(File file, ZipOutputStream out) throws IOException {
		// 判断是否存在
		if (file.exists()) {
			InputStream input = new FileInputStream(file);
			String name = file.getName();
			try {
				out.putNextEntry(new ZipEntry(name));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = input.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}
	}
	
	/**
	 * 压缩多个文件
	 * @param file
	 * @param out
	 * @throws IOException
	 */
	public static void doCompressMultipartFileArray(MultipartFile[] file, ZipOutputStream out) throws IOException{
		if (file == null || file.length ==0) {
			return ;
		}
		for(int i=0; i < file.length; i++) {
			doCompressMultipartFile(file[i], out);
		}
	}
	
	
	/** 
     * 判断文件名是否以.zip为后缀 
     * @param fileName        需要判断的文件名 
     * @return 是zip文件返回true,否则返回false 
     */  
    public static boolean isEndsWithZip(String fileName) {  
        boolean flag = false;  
        if(fileName != null && !"".equals(fileName.trim())) {  
            if(fileName.endsWith(".ZIP")||fileName.endsWith(".zip")){  
                flag = true;  
            }  
        }  
        return flag;  
    }  
    
    
    public static void unZipFiles(File zipFile, String descDir) throws IOException {  
        
		ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));// 解决中文文件夹乱码
		
		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			
			FileOutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		return;
    }

	public static void unZipAndDelete(File zipFile, String descDir) throws IOException {
		ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));// 解决中文文件夹乱码
		
		for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + "/" + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			
			FileOutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		zip.close();
		zipFile.delete();
		return;
	}
    

	public static void delZipFile(File file) {
		if (file.getName().endsWith(".zip")) {  // zip文件  判断 是否存在
			if (file.delete()) {
				log.info("zip文件已经删除");
			} else {
				log.info("zip文件删除失败");
			}
		}

	}

	/**
	 * 压缩文件
	 * @param srcFilePath 压缩源路径
	 */
	public static void compress(String srcFilePath){
		compress(srcFilePath,srcFilePath+".zip");
	}

	/**
	 * 压缩文件
	 * @param srcFilePath 压缩源路径
	 * @param destFilePath 压缩目的路径
	 */
	public static void compress(String srcFilePath, String destFilePath) {
		//
		File src = new File(srcFilePath);

		if (!src.exists()) {
			throw new RuntimeException(srcFilePath + "不存在");
		}
		File zipFile = new File(destFilePath);

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			String baseDir = "";
			compressbyType(src, zos, baseDir);
			zos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
	/**
	 * 按照原路径的类型就行压缩。文件路径直接把文件压缩，
	 * @param src
	 * @param zos
	 * @param baseDir
	 */
	private static void compressbyType(File src, ZipOutputStream zos,String baseDir) {

		if (!src.exists())
			return;
		log.debug("压缩路径" + baseDir + src.getName());
		//判断文件是否是文件，如果是文件调用compressFile方法,如果是路径，则调用compressDir方法；
		if (src.isFile()) {
			//src是文件，调用此方法
			compressFile(src, zos, baseDir);

		} else if (src.isDirectory()) {
			//src是文件夹，调用此方法
			compressDir(src, zos, baseDir);

		}

	}

	/**
	 * 压缩文件
	 */
	private static void compressFile(File file, ZipOutputStream zos,String baseDir) {
		if (!file.exists())
			return;
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(baseDir + file.getName());
			zos.putNextEntry(entry);
			int count;
			byte[] buf = new byte[1024];
			while ((count = bis.read(buf)) != -1) {
				zos.write(buf, 0, count);
			}
			bis.close();

		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	/**
	 * 压缩文件夹
	 */
	private static void compressDir(File dir, ZipOutputStream zos,String baseDir) {
		if (!dir.exists())
			return;
		File[] files = dir.listFiles();
		if(files.length == 0){
			try {
				zos.putNextEntry(new ZipEntry(baseDir + dir.getName()+File.separator));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (File file : files) {
			compressbyType(file, zos, baseDir + dir.getName() + File.separator);
		}
	}


	public static void main(String[] args) {
		compress("/E:/workspace/course-manager-system/target/classes/homework/matrixcomputations20191/2015326601098李如豪第1次作业");
	}
}
