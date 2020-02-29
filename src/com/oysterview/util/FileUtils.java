package com.oysterview.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
	public static byte[] readFileToByteArray(File file){
		byte[] fileContent = null;
		try {
			Long fileLength = file.length();
			fileContent = new byte[fileLength.intValue()];
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(fileContent);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileContent;
		
	}
	
	public static String readFileToString(File file){
		String readData = null;
		BufferedReader readbuf;
		FileReader readfile;
		StringBuilder sb = new StringBuilder();
		if(file.exists() && file.canRead())
		{
			try 
			{
				 readfile = new FileReader(file);
				 readbuf = new BufferedReader(readfile);
				while((readData = readbuf.readLine()) != null)
				{	
					sb.append(readData);
				}
				
				readbuf.close();
				readfile.close();
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("找不到文�??");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("文件读取异常");
			}
		}
		else
		{
			System.out.println("文件不存在或不允许读�??");
		}
		return sb.toString();
	}
	
	public static byte[] readFileToByteArray(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
	
}
