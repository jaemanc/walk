package com.org.walk;

import com.org.walk.util.Utils;

import java.io.*;

public class FileDonwloaderTest {

    public static void main(String[] args) {
        //String srcDirPath = "C:\\Users\\user\\Desktop\\테스트용 영상 및 이미지\\디렉토리 카피 테스트용 1";
        String srcDirPath = "C:\\Users\\admin\\Desktop\\다운로드 소스";
        //String tgtDirPath = "C:\\Users\\user\\Desktop\\테스트용 영상 및 이미지\\디렉토리 카피 타겟";
        String tgtDirPath = "C:\\Users\\admin\\Desktop\\다운로드 타겟";

        File srcDir = new File(srcDirPath);
        File tgtDir = new File(tgtDirPath);

        Copy(srcDir, tgtDir);
    }

    public static void Copy(File srcDir, File tgtDir) {

        boolean isDirectory = srcDir.isDirectory();

        String[] srcList = srcDir.list();

        // directory 단위이므로 영상여부 체크 없음.

        boolean is_cancel = false;

        try {
            int len = 0;
            byte[] buf = new byte[1024];
            int rates = 0;

            System.out.println(" 카피 시작! ");

            File finalTgtDir = tgtDir;
            File[] target_file = srcDir.listFiles();

            // 폴더 사이즈를 구할 경우 하위파일을 모두 탐색하고 파일 크기 합을 구하지 않는다면
            // directory의 사이즈를 4096으로 고정해서 뱉어낸다.
            long expectedBytes = Utils.getFolderSize(srcDir);
            System.out.println(" 소스 파일 사이즈 : " + expectedBytes);
            long totalBytesCopied = 0;

            for (File file : target_file) {
                File temp = new File(tgtDir.getAbsolutePath() + File.separator + file.getName());
                if (file.isDirectory()) {
                    temp.mkdir();
                    Copy(file, temp);
                } else {

                    FileInputStream fis = null;
                    FileOutputStream fos = null;
                    try {
                        fis = new FileInputStream(file);
                        fos = new FileOutputStream(temp);
                        byte[] b = new byte[1024];
                        while ((len = fis.read(b)) != -1) {
                            totalBytesCopied += len;

                            fos.write(b, 0, len);

                            rates = (int) Math.floor(( totalBytesCopied * 1.0 / expectedBytes) * 1000) / 10;

                            System.out.println(" 파일 별 사이즈 : " + totalBytesCopied + " / 원본 dir 사이즈 : " + expectedBytes);
                            System.out.println(" 진행률 : " + rates);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fis.close();
                            fos.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }

        } catch (Exception e) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pinrtStream = new PrintStream(out);
            e.printStackTrace(pinrtStream);
            System.out.println(out.toString());
        }

    }
}