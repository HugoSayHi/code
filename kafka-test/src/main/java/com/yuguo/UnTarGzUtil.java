package com.yuguo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.log4j.Logger;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;



public class UnTarGzUtil {

	private static final int SIZE = 1024;

	private static final Logger logger = Logger.getLogger(UnTarGzUtil.class);

	public static List<String> unTarGz(File file, String outputDir) throws IOException {
		List<String> re = new ArrayList<>();
		TarInputStream tarIn = null;
		long i = 0;
		try {
			tarIn = new TarInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))));

			createDirectory(outputDir, null);// 创建输出目录

			TarEntry entry = null;

			while ((entry = tarIn.getNextEntry()) != null) {

				if (entry.isDirectory()) {// 是目录
					logger.info("目录entry---" + entry.getName() + "\n");
					entry.getName();
					createDirectory(outputDir, entry.getName());// 创建空目录
				} else {// 是文件
					logger.info("文件entry---" + entry.getName() + "\n");
					File tmpFile = new File(outputDir + "/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_" + entry.getName());
					createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
					OutputStream out = null;
					try {
						logger.info("entry文件tmpFile---" + tmpFile.getName() + "\n");
						out = new FileOutputStream(tmpFile);
						int length = 0;

						byte[] b = new byte[SIZE];

						while ((length = tarIn.read(b)) != -1) {
							out.write(b, 0, length);
						}

						re.add(outputDir + "/" + tmpFile.getName());

					} catch (IOException ex) {
						logger.error("entry文件tmpFile---" + tmpFile.getName() + "解压22\n");
						throw ex;
					} finally {
						logger.info("entry文件tmpFile---" + tmpFile.getName() + "解压11\n");
						if (out != null)
							out.close();
					}
				}
			}

			return re;
		} catch (IOException ex) {
			logger.error("",ex);
			throw new IOException("解压归档文件出现异常", ex);
		} finally {
			try {
				if (tarIn != null) {
					tarIn.close();
				}
			} catch (IOException ex) {
				throw new IOException("关闭tarFile出现异常", ex);
			}
			logger.info(file.getPath() + "解压出：" + i + "个文件。\n");
		}
	}
	protected static void createDirectory(String outputDir, String subDir) {
		File file = new File(outputDir);
		if (!isNull(subDir)) {
			file = new File(outputDir + File.separator + subDir);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	public static boolean isNull(Object val){
		if(null == val || "null".equalsIgnoreCase(toString(val)) || "".equals(toString(val))){
			return true;
		}
		return false;
	}
	
	public static String toString(Object val){
		if(null != val ){
			String temp =  String.valueOf(val).trim();
			if("null".equalsIgnoreCase(temp)){
				return "";
			}
			return temp;
		}
		return "";
	}
}
