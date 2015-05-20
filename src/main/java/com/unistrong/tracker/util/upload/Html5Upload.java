package com.unistrong.tracker.util.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Html5Upload implements UploadImageInterface {
	
	private String deviceSn;
	private String icon;
	private long sizeMax;
	private File dir = null;
	private InputStream ins = null;
	private int contentLength=0;
	private Long userId;
	
	private static Logger logger = LoggerFactory.getLogger(Html5Upload.class);
	
	
	public Html5Upload(HttpServletRequest request, HttpServletResponse response,long maxSize,String root) throws Exception {
		this.sizeMax=maxSize;
		deviceSn=request.getParameter("deviceSn");
		if(StringUtils.isBlank(deviceSn)){
			deviceSn=request.getHeader("deviceSn");
		}
		String strUid=request.getParameter("user_id");
		if(StringUtils.isBlank(strUid)){
			strUid=request.getHeader("user_id");
		}
		if(!StringUtils.isBlank(strUid)){
			userId=Long.valueOf(strUid);
		}
		icon=deviceSn+".jpg";
		dir = new File(root);
		String contentType = request.getContentType();
		if(!contentType.equals("application/octet-stream")){
			throw new Exception("不支持的contentType");
		}
		contentLength = request.getContentLength();
		logger.info("data.length:"+contentLength);
        if(contentLength>sizeMax){
			throw new SizeLimitExceededException("");
        }
		String dispoString = request.getHeader("Content-Disposition");
		logger.info("dispoString:"+dispoString);
		ins = request.getInputStream();
	}

	public String getIcon() {
		return icon;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void writeFile() throws Exception {
		OutputStream out=null;
			try {
				File file=new File(dir.getAbsoluteFile()+"/"+deviceSn+".jpg");
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				
			  int data;
			  out=new BufferedOutputStream(new FileOutputStream(file));
			  while((data = ins.read()) != -1) {
                  out.write(data);
              }
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}finally{
				if(ins!=null){
					ins.close();
				}
				if(out!=null){
					out.close();
				}
			}
			
	}

	public Long getUserId() {
		return userId;
	}

	
	
}
