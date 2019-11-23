package cn.dintalk.util;

import java.io.*;

/**
 * @author Mr.song
 * @date 2019/10/10 19:44
 */
public class FileUtils {

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return File 对象
     */
    public static File getFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    /**
     * @param file File 对象
     * @return file对象的内容
     * @throws IOException 文件读取异常
     */
    public static StringBuilder readFile(File file) throws IOException {
        if (file == null) return null;
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), codeString(file)));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String s = bf.readLine();
            if (s == null) break;
            sb.append(s);
        }
        return sb;
    }

    /**
     * 获取原文件的编码格式
     * @param file
     * @return
     * @throws IOException
     */
    public static String codeString(File file) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII";
                break;
            default:
                code = "GBK";
        }
//        System.out.println("文件："+file.getName()+"|的编码格式为："+code);
        return code;
    }
}
