package com.until;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;

public class WriteText {
	public void addFile(String imgStr, String name,String dirName) {
		//String path = "D:\\stuimg\\" + dirName + "\\" + name + ".txt";
		String path = "/usr/java/" + dirName + "/" + name + ".txt";
		PrintStream stream = null;
		try {

			stream = new PrintStream(path);// 写入的文件path
			stream.print(imgStr);// 写入的字符串
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String redFile(String name,String dirName) throws IOException {
		//String filePath = "D:\\stuimg\\" + dirName + "\\" + name + ".txt";
		String filePath = "/usr/java/" + dirName + "/" + name + ".txt";
		StringBuffer sb = new StringBuffer();
		readToBuffer(sb, filePath);
		return sb.toString();
	}
	 public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
	        InputStream is = new FileInputStream(filePath);
	        String line; // 用来保存每行读取的内容
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        line = reader.readLine(); // 读取第一行
	        while (line != null) { // 如果 line 为空说明读完了
	            buffer.append(line); // 将读到的内容添加到 buffer 中
	            buffer.append("\n"); // 添加换行符
	            line = reader.readLine(); // 读取下一行
	        }
	        reader.close();
	        is.close();
	    }
}