package com.chrl.bs.sfs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cat on 2014/9/25.
 */
public class Test {



    public String  readResource(String filePath) {
        long fileLength = 0;
        final int BUFFER_SIZE = 0x300000;// 3M的缓冲
        StringBuffer stringBuffer = new StringBuffer();

        File file = new File(filePath);
        fileLength = file.length();
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r")
                    .getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
                            fileLength);// 读取大文件

            byte[] dst = new byte[BUFFER_SIZE];// 每次读出3M的内容

            for (int offset = 0; offset < fileLength; offset += BUFFER_SIZE) {
                if (fileLength - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++)
                        dst[i] = inputBuffer.get(offset + i);
                } else {
                    for (int i = 0; i < fileLength - offset; i++)
                        dst[i] = inputBuffer.get(offset + i);
                }
                // 将得到的3M内容给Scanner，这里的XXX是指Scanner解析的分隔符
                Scanner scan = new Scanner(new ByteArrayInputStream(dst));

                while (scan.hasNext()) {
                    // 这里为对读取文本解析的方法
                    stringBuffer.append(scan.next());
                }
                scan.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    public static void main(String[] args)
    {
        Test sp = new Test();
        String text = sp.readResource("d:\\4003.json");
        System.out.println();
    }
}
