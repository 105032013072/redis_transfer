package com.bosssoft.platform.redis.transfer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bosssoft.platform.common.httpclient.MultipartFileSender;
import com.bosssoft.platform.redis.transfer.service.RedisOperation;

@Controller
public class MainController {

	@RequestMapping("/exportData")
	 public String exportData(HttpServletRequest request,HttpServletResponse response){
		System.out.println("exportData");
		
		RedisOperation operation=new RedisOperation("127.0.0.1", 6379);
		String savefile="d:\\test\\redis_transfer\\export\\redisdata.xml";
		try {
			//operation.export(savefile, "gateway:file:");
			//operation.export(savefile, "name");
			
			serveResource(savefile,response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	 }

	public void importData(@RequestParam("file")CommonsMultipartFile file){
	    String savePath="d:\\test\\redis_transfer\\import";
		String fileName=file.getOriginalFilename();
	  
		File newFile=new File(savePath+File.pathSeparator+fileName);
		if(newFile.exists()) newFile.delete();
		try {
			 //接收文件
			file.transferTo(newFile);
			
			RedisOperation operation=new RedisOperation("127.0.0.1", 6378);
			operation.importData(newFile.getPath());

		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	};
	
	
	private void serveResource(String filePath, HttpServletResponse response) throws Exception {

		   String fileName=new File(filePath).getName();
		   response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		   
		 //读取要下载的文件，保存到文件输入流
		   FileInputStream in = new FileInputStream(filePath);
		   
		   //创建输出流
		   OutputStream out = response.getOutputStream();
		   
		 //创建缓冲区
		   byte buffer[] = new byte[1024];
		    int len = 0;
		   //循环将输入流中的内容读取到缓冲区当中
		  while((len=in.read(buffer))>0){
		   //输出缓冲区的内容到浏览器，实现文件下载
		 out.write(buffer, 0, len);
		   }
		   //关闭文件输入流
		  in.close();
		   //关闭输出流
		   out.close();
		
	}
	 
	 
}
