package com.testoauth.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;


public class CreateFiles {
	private static String  path = "/sdcard/test";

	// 创建文件夹及文件
	public void CreateText(String fileName) throws Exception {
		File file = new File(path);
		File dir = new File(path + "/" + fileName);
		if (!file.exists()) {
			// 按照指定的路径创建文件夹
			file.mkdirs();
		}else{
			deleteFile(file);
			file.mkdirs();
		}
		
		if (!dir.exists()) {
			// 在指定的文件夹中创建文件
			
			dir.createNewFile();
			
		}

	}

	// 向已创建的文件中写入数据
	public void print(String str,String XmlName) throws Exception {
		FileWriter fw = null;
		BufferedWriter bw = null;
		fw = new FileWriter(path + "/"+XmlName, false);
		// 创建FileWriter对象，用来写入字符流
		bw = new BufferedWriter(fw); // 将缓冲对文件的输出
		bw.write(str); // 写入文件
		bw.newLine();
		bw.flush(); // 刷新该流的缓冲
		bw.close();
		fw.close();

	}
	
	private void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            deleteFileSafely(file);
        }
    }
	  /**
     * 安全删除文件.
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

		
}
