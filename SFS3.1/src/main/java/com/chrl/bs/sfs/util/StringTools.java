package com.chrl.bs.sfs.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2015/11/26.
 */
public class StringTools {

    public static String replaceAll(String old ,String str1, String str2){
        while (old.contains(str1)){
            old = old.replace(str1,str2);
        }
        return old;
    }

    public static String replaceAll2Web(String old){
        old = replaceAll(old,"//","/");
        old = replaceAll(old,"\\\\","\\");
        old = old.replace("\\","/");
        return old;
    }

    /**
     * ×ª»»uri
     * @param uri
     * @return
     */
    public static String sanitize(String uri) {
        uri = replaceAll2Web(uri);
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                throw new Error();
            }
        }
        uri = uri.replace('/', File.separatorChar);
        if (uri.contains(File.separator + '.') || uri.contains('.' + File.separator) || uri.startsWith(".") || uri.endsWith(".")) {
            return null;
        }
        return uri;
    }
}
