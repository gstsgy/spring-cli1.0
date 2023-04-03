package com.gstsgy.base.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static String read(String url, String filecode) {
        StringBuilder content = new StringBuilder();
        FileInputStream fileInputStream = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        try {
            fileInputStream = new FileInputStream(url);
            is = new InputStreamReader(fileInputStream, filecode);
            br = new BufferedReader(is);
            String tmp = br.readLine();
            while (tmp != null) {
                content.append("\n").append(tmp);
                tmp = br.readLine();
            }
            return content.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String read(InputStream inputStream, String filecode) {
        StringBuilder content = new StringBuilder();

        InputStreamReader is = null;
        BufferedReader br = null;
        try {
            is = new InputStreamReader(inputStream, filecode);
            br = new BufferedReader(is);
            String tmp = br.readLine();
            while (tmp != null) {
                content.append(tmp);
                tmp = br.readLine();
            }
            return content.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String read(String url) {
        return read(url, "UTF-8");
    }

    public static String read(InputStream inputStream) {
        return read(inputStream, "UTF-8");
    }

    public static List<String> readToList(String url, String filecode) {
        List<String> content = new ArrayList<>();
        FileInputStream fileInputStream = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        try {
            fileInputStream = new FileInputStream(url);
            is = new InputStreamReader(fileInputStream, filecode);
            br = new BufferedReader(is);
            String tmp = br.readLine();
            while (tmp != null) {
                content.add(tmp);
                tmp = br.readLine();
                // br.
                //br.
            }
            return content;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<String> readToList(String url) {
        return readToList(url, "UTF-8");
    }

    public static void write(String url, String filecode, String content) {
        FileOutputStream fos = null;
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(new File(url));
            ow = new OutputStreamWriter(fos, filecode);
            bw = new BufferedWriter(ow);
            bw.write(content);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void write(String url, String content) {
        write(url, "UTF-8", content);
    }

    public static void write(String url, InputStream inputStream) {
        write(url, "UTF-8", read(inputStream));
    }

   /* public static void main(String[] args) {
        String url = "./a.txt";
        String content = "testaaaaa";
        // new FileUtil().write(url, content);
        System.out.println(new FileUtil().read(url));
    }*/
}
