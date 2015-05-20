package com.unistrong.tracker.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.imageio.ImageIO;

public class ImageUtil {
	
    private static Set<String> process=new ConcurrentSkipListSet<String>();//正在处理的图片
    
	 /**
     * 图片切割
     * @param imagePath  图片地址
     * @param x  目标切片坐标 X轴起点
     * @param y  目标切片坐标 Y轴起点
     * @param w  目标切片 宽度
     * @param h  目标切片 高度
     */
    public static String cutImage(String imagePath, int x ,int y ,int w,int h){
    	String ret=null;
        try {
        	
        	if(process.contains(imagePath)){
        		return "正在处理";
        	}else{
        		process.add(imagePath);
        	}
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(imagePath));
            int srcWidth = bi.getWidth();      // 源图宽度
            int srcHeight = bi.getHeight();    // 源图高度
            //若原图大小大于切片大小，则进行切割
            if (srcWidth >= w && srcHeight >= h) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, w, h);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制裁剪后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(imagePath));
            }else{
            	ret="参数有误不能大于原图宽高";
            }
        } catch (IOException e) {
            e.printStackTrace();
            ret="文件不存在"+e.getLocalizedMessage();
        }finally{
        	process.remove(imagePath);
        }
        return ret;
    }
   
    
    
    
    
    
}
