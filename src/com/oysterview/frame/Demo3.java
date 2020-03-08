package com.oysterview.frame;

import java.awt.BorderLayout;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;

import javax.swing.JFrame;


import com.oysterview.util.FileUtils;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.*;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;



public class Demo3 {
	static {
        try {
            Class claz = null;
            //6.5.1版本破解 兼容xp
//            claz =  Class.forName("com.teamdev.jxbrowser.chromium.aq");
            //6.21版本破解 默认使用最新的6.21版本
            System.setProperty("jxbrowser.chromium.sandbox", "true");
             claz =  Class.forName("com.teamdev.jxbrowser.chromium.ba");

            Field e = claz.getDeclaredField("e");
            Field f = claz.getDeclaredField("f");


            e.setAccessible(true);
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行jxbrowser破解程序时出现异常");
        }
    }
	public static void main(String[] args) {
	 	final String url = "http://m.baidu.com/";  
        final String title = "百度"; 
        final Browser browser = new Browser();  
        BrowserView view = new BrowserView(browser);  
  
        JFrame frame = new JFrame();  
      //禁用close功能
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);        
        //不显示标题栏,最大化,最小化,退出按钮
        //frame.setUndecorated(true);  
        frame.setSize(400, 600);
        frame.add(view, BorderLayout.CENTER);  
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        frame.setLocationByPlatform(true);  
        frame.setVisible(true);  
        String fileName = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\demo2.html";
		String html1 = FileUtils.readFileToString(new File(fileName));
//        browser.loadURL(url);  
        browser.loadHTML(html1);
        browser.addLoadListener(new LoadAdapter() {

			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {
				// TODO Auto-generated method stub
				super.onFinishLoadingFrame(event);
				
				String fileName = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\jquery-1.7.1.min.js";
				String fileName_easyUI = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\jquery.easyui.min.js";
				String fileName_datgrid = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\datagrid-scrollview.js";
				String fileName_demo = "E:\\Project\\Java\\SWT_Browser_JQuery\\html\\demo2\\demo2.js";
				String jqueryui = FileUtils.readFileToString(new File(fileName));
				String easyUI = FileUtils.readFileToString(new File(fileName_easyUI));
				String datgrid = FileUtils.readFileToString(new File(fileName_datgrid));
				String demo = FileUtils.readFileToString(new File(fileName_demo));
				
				
				
//				browser.executeJavaScript(jqueryui);
//				browser.executeJavaScript(easyUI);
//				browser.executeJavaScript(datgrid);
//				browser.executeJavaScript(demo);
			}
        	
        });
        
}

	
	
	
	
	
	
	
	
}
