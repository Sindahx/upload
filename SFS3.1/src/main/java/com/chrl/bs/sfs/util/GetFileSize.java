package com.chrl.bs.sfs.util;

/**
 * Created by Administrator on 2015/12/11.
 */
import  java.io.File;

public   class  GetFileSize
{
    // ตÝน้
    public static long  getFileSize(File f)
            throws  Exception
    {
        long  size =  0 ;
        File flist[] = f.listFiles();
        for  ( int  i =  0 ; i < flist.length; i++)
        {
            if  (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            } else
            {
                size = size + flist[i].length();
            }
        }
        return  size;
    }

    public   static   void  main(String args[])
    {
        GetFileSize g = new  GetFileSize();
        long  startTime = System.currentTimeMillis();
        try
        {
            File ff = new  File( "E:\\qycache" );
            long  l = g.getFileSize(ff);
            System.out.println("E:\\qycache size="  + l +  "  B" );
            l = l/1024/1024;
            System.out.println("E:\\qycache size="  + l +  "  MB" );
        } catch  (Exception e)
        {
            e.printStackTrace();
        }
        long  endTime = System.currentTimeMillis();
        System.out.println("total times = "  + (endTime - startTime) +  " ms." );
    }
}