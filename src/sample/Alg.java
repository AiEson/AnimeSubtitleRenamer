package sample;

import com.sun.istack.internal.Nullable;
import sun.net.www.URLConnection;

import java.io.*;
import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.List;


public class Alg {
    private final static String[] allTypeVideo = {"drc", "dsm", "dsv", "dsa", "dss", "vob", "ifo", "d2v", "flv", "fli", "flc", "flic",
            "ivf", "mkv", "mpg", "mpeg", "mpe", "m1v", "m2v", "mpv2", "mp2v", "dat", "ts", "tp", "tpr", "pva", "pss", "mp4", "m4v", "m4p",
            "m4b", "3gp", "3gpp", "3g2", "3gp2", "ogm", "mov", "qt", "amr", "ratdvd", "rt", "rp", "smi", "smil", "rm", "ram", "rmvb", "rpm",
            "roq", "swf", "smk", "bik", "wmv", "wmp", "wm", "asf", "avi", "asx", "m3u", "pls", "wvx", "wax", "wmx", "mpcpl"};
    private final static String[] allTypeAss = {"srt", "ssa", "ass", "sup"};

    /**
     * Get the Mime Type from a File
     *
     * @param fileName 文件名
     * @return 返回MIME类型
     * thx https://www.oschina.net/question/571282_223549
     * add by fengwenhua 2017年5月3日09:55:01
     */
    private static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }

    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(str.indexOf(strStart) + strStart.length(), str.replace(strStart, "").indexOf(strEnd) + strStart.length());
        return result;
    }

    public static void autoGetRule(List<String> str, int leng){
        char[] ch = str.get(0).toCharArray();
        int ii=0;
        for(int i = ii;i<=ch.length-leng;i++)
        {
            String getLeng="";
            for(int j=0;j<leng;j++)
            {
                getLeng+=Character.toString(ch[i+j]);
            }
            System.out.println(getLeng);
            //panduan shuzi
            if(isNumeric(getLeng))
            {
                String nextLeng="";
                char[] ch2 = str.get(1).toCharArray();
                System.out.println(str.get(1));
                for(int j=0;j<leng;j++)
                {
                    nextLeng+=Character.toString(ch2[(i)+(j)]);
                }
                if(isNumeric(nextLeng))
                {
                    if(getLeng.equals(nextLeng))
                    {
                        ii += 1;
                        System.out.println("Err");
                    }
                    else
                    {
                        Value.rule1 = str.get(1).substring(0,str.get(1).indexOf(nextLeng));
                        Value.rule2 = str.get(1).substring(str.get(1).indexOf(nextLeng)+leng,str.get(1).indexOf(nextLeng)+1+leng);
                        System.out.println(Value.rule1);
                        System.out.println(Value.rule2);
                        break;
                    }
                }
            }

        }
    }
    public static boolean isNumeric(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * 根据文件后缀名判断 文件是否是视频文件
     *
     * @param fileName 文件名
     * @return 是否是视频文件
     */
    public static boolean isVideoFile(String fileName) {
        boolean ret = false;
        String[] pro = fileName.split("\\.");
        String end = pro[pro.length - 1];
        for (int i = 0; i < allTypeVideo.length; i++) {
            if (end.equals(allTypeVideo[i])) {
                ret = true;
                break;
            }
        }
        return ret;
    }


    public static void copy(File f, File nf, boolean flag) throws Exception {
        // 判断是否存在
        if (f.exists()) {
            // 判断是否是目录
            if (f.isDirectory()) {
                if (flag) {
                    // 制定路径，以便原样输出
                    nf = new File(nf + "/" + f.getName());
                    // 判断文件夹是否存在，不存在就创建
                    if (!nf.exists()) {
                        nf.mkdirs();
                    }
                }
                flag = true;
                // 获取文件夹下所有的文件及子文件夹
                File[] l = f.listFiles();
                // 判断是否为null
                if (null != l) {
                    for (File ll : l) {
                        // 循环递归调用
                        copy(ll, nf, flag);
                    }
                }
            } else {
                System.out.println("正在复制：" + f.getAbsolutePath());
                System.out.println("到：" + nf.getAbsolutePath() + "\\" + f.getName());
                // 获取输入流
                FileInputStream fis = new FileInputStream(f);
                // 获取输出流
                FileOutputStream fos = new FileOutputStream(nf + "/" + f.getName());
                byte[] b = new byte[1024];
                // 读取文件
                while (fis.read(b) != -1) {
                    // 写入文件，复制
                    fos.write(b);
                }
                fos.close();
                fis.close();
            }
        }
    }

    public static String getEnd(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }

    public static boolean isAssFile(String fileName) {
        boolean ret = false;
        String[] pro = fileName.split("\\.");
        String end = pro[pro.length - 1];
        for (int i = 0; i < allTypeAss.length; i++) {
            if (end.equals(allTypeAss[i])) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static boolean isNumeric0(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }
}
