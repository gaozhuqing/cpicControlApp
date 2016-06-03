package com.testoauth.util;

import java.io.File;
import java.util.ArrayList;


public class FileScan {
	private static  String path="/sdcard/test2";
	private static final String TAG = "FileScan";
	
	public FileScan(String path){
		this.path = path;
	}
	

 /**
 * @param path
 * @param fileList
 * 注意的是并不是所有的文件夹都可以进行读取的，权限问题
 */
    public static  ArrayList<String>  getFileList(String packagePath) throws Exception{
    	 ArrayList<String> fileList = new ArrayList<String>();
    //如果是文件夹的话
    File file = new File(path+"/"+packagePath);
      if(file.isDirectory()){
     //返回文件夹中有的数据
      File[] files = file.listFiles();
        //先判断下有没有权限，如果没有权限的话，就不执行了
    
     for(int i = 0; i < files.length; i++){
    	 String filePath = files[i].getAbsolutePath();
         //文件名
    	 String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
        //添加
      	fileList.add(fileName);
        }
      } 
      return fileList;
    }
    public static ArrayList<String> getFileXml(String taskName) throws Exception{
    	ArrayList<String> fileNameList=new ArrayList<String>();
    	File file=new File(path+"/"+taskName);
    	
    	File[] files=file.listFiles();
    	
    	 for(File file1:files){
    		 if(file1.isDirectory()){
    		
    		 }else if(file1.isFile()){
    			String fileName=file1.getName();
    			String name=fileName.substring(fileName.lastIndexOf("/")+1, fileName.lastIndexOf("."));
    			
    			fileNameList.add(name);
    		 }
    	 }
		return fileNameList;
    	
    }
   
 }

