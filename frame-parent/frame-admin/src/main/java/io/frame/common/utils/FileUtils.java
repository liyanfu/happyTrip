package io.frame.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件操作类
 * 
 * @author Fury
 *
 */
public class FileUtils {

	static Logger log = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 删除本地文件
	 * 
	 * @param path
	 */
	public static void delLocalFile(String path) throws Exception {
		// 删除临时文件
		File file2 = new File(path);
		if (!file2.exists()) {
			System.out.println("没有该文件");
		}
		if (!file2.isDirectory()) {
			System.out.println("没有该文件夹");
		}
		String[] tempList = file2.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile() || temp.isDirectory()) {
				temp.delete(); // 删除文件夹里面的文件
			}
		}
	}

	/**
	 * 创建本地文件夹
	 * 
	 * @param path
	 */
	public static void createLoaclFileDirectory(String path) throws Exception {
		File TempFile = new File(path);
		if (TempFile.exists()) {
			if (TempFile.isDirectory()) {
				System.out.println("该文件夹存在。");
			} else {
				System.out.println("同名的文件存在，不能创建文件夹。");
			}
		} else {
			System.out.println("文件夹不存在，创建该文件夹。");
			TempFile.mkdirs();
		}
	}

	/**
	 * 读取本地文件流
	 * 
	 * @param path
	 */
	public static void getLocalFile(String path, HttpServletResponse response) throws Exception {
		FileInputStream ips = null;
		ServletOutputStream out = null;
		try {
			// 获取图片存放路径 
			File file = new File(path);
			if (!file.exists()) {
				return;
			}
			ips = new FileInputStream(file);
			response.setContentType("multipart/form-data;charset=UTF-8");
			out = response.getOutputStream();
			// 读取文件流  
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("服务异常", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (ips != null) {
					ips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传文件到本地服务器
	 * 
	 * @param file     文件
	 * @param path     路径
	 * @param fileName 文件名
	 */
	public static String uploadFileToLocal(MultipartFile file, String path) throws Exception {

		// 创建文件夹
		FileUtils.createLoaclFileDirectory(path);

		// 获取上传时候的文件名
		String filename = file.getOriginalFilename();

		// 获取文件后缀名
		String filename_extension = filename.substring(filename.lastIndexOf(".") + 1);

		// 当前时间做新的文件名，避免中文乱码-重新生成filename
		filename = DateUtils.format(new Date(), "yyyyMMddHHmmss") + "." + filename_extension;

		BufferedInputStream ips = null;
		BufferedOutputStream out = null;
		try {
			ips = new BufferedInputStream(file.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(new File(path, filename)));
			int len = 0;
			byte[] buffer = new byte[2048];
			while ((len = ips.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("服务异常", e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("服务异常", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (ips != null) {
					ips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return path + filename;
	}

}
