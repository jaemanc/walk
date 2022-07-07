package com.org.walk.util;

import java.io.File;

public class Utils {

    public static long getFolderSize(File folder) {
        File[] files = folder.listFiles();
        long length = 0;
        int count = files.length;
        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            } else {
                length += getFolderSize(files[i]);
            }
        }

        System.out.println(  " 소스 사이즈 결과 : " + length);
        return length;
    }
}
