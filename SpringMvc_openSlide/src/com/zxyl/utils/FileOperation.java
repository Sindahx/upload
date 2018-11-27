package com.zxyl.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 文件的相关操作类
 *
 * @author Kiritor
 */
public class FileOperation {
    private static String contentPath;
    private static String filePath;
    private static File[] fileList = null;// 保存文件列表,过滤掉目录

    public FileOperation() {

    }

    /** 构造函数的参数是一个目录 */
    public FileOperation(String path) {
        File file = new File(path);
        if (file.isDirectory())
            this.contentPath = path;
        else
            this.filePath = path;
    }
    /**获取文件列表*/
    public static File[] getFiles() {
        if (contentPath == null) {

            File file = new File(filePath);
            fileList = new File[1];
            fileList[0] = file;
            return fileList;
        }
        fileList = new File(contentPath).listFiles(new FileFilter() {

            /**使用过滤器过滤掉目录*/
            public boolean accept(File pathname) {
                if(pathname.isDirectory())
                {
                    return false;
                }else
                    return true;
            }
        });
        return fileList;
    }

    /** 对当前目录下的所有文件进行排序 */
    public static File[] sort() {
        getFiles();
        Arrays.sort(fileList, new FileComparator());
        return fileList;
    }

    public static void tree(File f, int level) {
        String preStr = "";
        for(int i=0; i<level; i++) {
            preStr += "    ";
        }
        File[] childs = f.listFiles();
        //返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
        for(int i=0; i<childs.length; i++) {
            System.out.println(preStr + childs[i].getName());
            if(childs[i].isDirectory()) {
                tree(childs[i], level + 1);
            }
        }
    }

    // 提供一个"比较器"
    static class FileComparator implements java.util.Comparator<File> {
        public int compare(File o1, File o2) {
            // 按照文件名的字典顺序进行比较
            return o1.getName().compareTo(o2.getName());
        }

    }
}
