package com.testoauth.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.testoauth.object.TestCase;



public class FindXML {
	public static String PATH="/sdcard/test2";
	
	public List<TestCase> findAllCase(String path) throws Exception{
		List<TestCase> list=new ArrayList<TestCase>();
		File filefirst=new File(PATH+"/"+path);
		if(filefirst.exists()){
			File[] fileAll=filefirst.listFiles();
			for (int i = 0; i < fileAll.length; i++) {
				if(fileAll[i].getName().indexOf(".xml")!=-1){
					TestCase test=new TestCase();
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = factory.newDocumentBuilder();
					
					Document doc = db.parse(fileAll[i]);
					Element root = doc.getDocumentElement();
					test.setCaseName(root.getAttribute("desc"));
					test.setCaseID(root.getAttribute("id"));
					//把文件转换为String
					InputStream in = new FileInputStream(fileAll[i]);
					test.setContent(getString(in));
					test.setState("0");
					//运行
					test.setTime("2016-5-23");
					list.add(test);
				}
			}
			return list;
		}else{
			return null;
		}
	}
	
	public TestCase findCase(String path) throws Exception{
		File file = new File(PATH+"/"+path);
		TestCase test=new TestCase();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factory.newDocumentBuilder();
		
		Document doc = db.parse(file);
		Element root = doc.getDocumentElement();
		test.setCaseName(root.getAttribute("desc"));
		test.setCaseID(root.getAttribute("id"));
		//把文件转换为String
		InputStream in = new FileInputStream(file);
		test.setContent(getString(in));
		test.setState("0");
		//运行
		test.setTime("2016-5-23");
		return test;
	}
	
public String getString(InputStream is) throws IOException{
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	 StringBuffer buffer = new StringBuffer();
	 String line = "";
	   while ((line = br.readLine()) != null){
	     buffer.append(line);
	   }
	   return buffer.toString();
	
}

//查询ID
	public static String findID(String res) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		InputStream in = new ByteArrayInputStream(res.getBytes());
		DocumentBuilder db = factory.newDocumentBuilder();
		Document doc = db.parse(in);
		String id = (String) xpath.evaluate("//TestCase/@id", doc,
				XPathConstants.STRING);

		return id;
	}
}
