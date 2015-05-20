package com.unistrong.tracker.util.upload;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.unistrong.tracker.util.ImageUtil;

public class NormalFormUpload implements UploadImageInterface {

	private String deviceSn;
	private String icon;
	private Long userId;
	private long sizeMax;
	private List<FileItem> fileItemList;
	private int[] cut=null;//裁剪的参数
	private File dir = null;
	
	/** 3.取upload表单input中的value,request.getParameter(paramName) */
	private String getValue(String paramName) {
		Iterator<FileItem> fileItemIterator = this.fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();
			if (fileItem.getName() == null && fileItem.getFieldName().equals(paramName)) {
				return fileItem.getString();
			}
		}
		return null;
	}

	/** 2.取upload表单input中的file */
	private String uploadWrite(File uploadFileParent, int[] cut) throws Exception {
		Iterator<FileItem> fileItemIterator = this.fileItemList.iterator();
		while (fileItemIterator.hasNext()) {
			FileItem fileItem = fileItemIterator.next();
			if (fileItem.getName() != null) {
				String suffix = fileItem.getName();
				suffix = suffix.substring(suffix.lastIndexOf("."));
				if (suffix != null
						&& (suffix.equalsIgnoreCase(".png") || suffix.equalsIgnoreCase(".jpg"))) {
					File icon = new File(uploadFileParent + suffix);
					fileItem.write(icon);
					if(cut!=null&&cut.length==4){
						ImageUtil.cutImage(icon.getAbsolutePath(), cut[0], cut[1], cut[2], cut[3]);
					}
					return icon.getName();
				} else
					throw new IllegalArgumentException("file format error,must jpg/png");
			}
		}
		return null;
	}

	/** 1.初始化servletFileUpload */
	private ServletFileUpload getServletFileUpload(long sizeMax) {
		File tempfile = new File(System.getProperty("java.io.tmpdir"));
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(4096);// 1024*4K
		diskFileItemFactory.setRepository(tempfile);
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setSizeMax(sizeMax);// 1024*1024*4M=4194304
		return servletFileUpload;
	}
	
	
	public NormalFormUpload(HttpServletRequest request, HttpServletResponse response,long maxSize,String root) throws FileUploadException  {
		this.sizeMax=maxSize;
		ServletFileUpload servletFileUpload = this.getServletFileUpload(sizeMax);
		this.fileItemList = servletFileUpload.parseRequest(request);
		deviceSn = this.getValue("device_sn");
		String strUid=this.getValue("user_id");
		if(!StringUtils.isBlank(strUid)){
			userId= Long.valueOf(strUid);
		}
		dir = new File(root + deviceSn);
		String cutStr=request.getParameter("cutImg");
		if(!StringUtils.isBlank(cutStr)){
			String[] cutArry=cutStr.split(",");
			if(cutArry.length==4){
				cut=new int[4];
				for(int i=0;i<cutArry.length;i++){
					cut[i]=Integer.valueOf(cutArry[i]);
				}
			}
		}
		
	}

	public String getIcon() {
		return icon;
	}

	public String getDeviceSn() {
		return deviceSn;
	}
	
	public void writeFile() throws Exception{
		icon = this.uploadWrite(dir,cut);
	}

	public Long getUserId() {
		return userId;
	}

	

}
