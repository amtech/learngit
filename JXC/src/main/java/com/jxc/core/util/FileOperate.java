package com.jxc.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @FileName: FileOperate.java
 * @Author: WuF
 * @Version: 1.0
 * @Date: 2013-2-25上午8:49:24
 * @Description:操作文件的工具类
 * @Others:
 * @FuntionList:
 * @History:
 * @Category:
 */
public class FileOperate {
    public FileOperate() {
    }

    /**
     * create folder
     * 
     * @param folderPath
     *            String c:/fqf
     * @return boolean
     */
    public static void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creat file
     * 
     * @param filePathAndName
     *            String c:/fqf.txt
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void newFile(String filePathAndName, StringBuffer fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent.toString();
            myFile.println(strContent);
            resultFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * creat file
     * 
     * @param filePathAndName
     *            String c:/fqf.txt
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void newFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);

            if (!myFilePath.exists()) {

                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            resultFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete file
     * 
     * @param filePathAndName
     *            String c:/fqf.txt
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete folder
     * 
     * @param filePathAndName
     *            String��c:/fqf
     * @param fileContent
     *            String
     * @return boolean
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all file
     * 
     * @param path
     *            String c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * copy one file
     * 
     * @param oldPath
     *            String c:/fqf.txt
     * @param newPath
     *            String f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath, String oldName, String newName) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            int bytesum = 0;
            int byteread = 0;
            if (oldPath.endsWith("/")) {

            } else {
                oldPath += "/";
            }

            if (newPath.endsWith("/")) {

            } else {
                newPath += "/";
            }

            File oldfile = new File(oldPath + oldName);
            if (oldfile.exists()) {
                FileInputStream inStream = new FileInputStream(oldPath + oldName);
                FileOutputStream fs = new FileOutputStream(newPath + newName);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;

                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * copy all file
     * 
     * @param oldPath
     *            String c:/fqf
     * @param newPath
     *            String f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/"
                                                                   + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cut file to new path
     * 
     * @param oldPath
     *            String c:/fqf.txt
     * @param newPath
     *            String d:/fqf.txt
     */
    public static void moveFile(String oldPath, String newPath, String oldName, String newName) {
        copyFile(oldPath, newPath, oldName, newName);
        delFile(oldPath + oldName);
    }

    /**
     * cut folder to new path
     * 
     * @param oldPath
     *            String c:/fqf.txt
     * @param newPath
     *            String d:/fqf.txt
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }
}