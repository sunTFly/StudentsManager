package com.arcsoft.arcfacedemo.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamToString {
    public static String readInputStream(InputStream instream) throws Exception {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer=new byte[1024];
        while ((len=instream.read(buffer))!=-1){
            outstream.write(buffer,0,len);
        }
        instream.close();
        outstream.close();
        byte[] result=outstream.toByteArray();
        String temp=new String(result);
        return temp;
    }
}