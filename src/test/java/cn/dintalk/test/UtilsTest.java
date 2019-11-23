package cn.dintalk.test;

import cn.dintalk.util.FileUtils;
import cn.dintalk.util.IKSUtils;
import cn.dintalk.util.WebUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.song
 * @date 2019/10/10 21:23
 */
public class UtilsTest {

    @Test
    public void testGetFile(){
        File file = FileUtils.getFile("C:\\Users\\Administrator\\Desktop\\中国人大\\java程序设计\\bk140a_201911 (1).doc");
        System.out.println(file.getName());
    }

    @Test
    public void testReadFile(){
        File file = FileUtils.getFile("C:\\Users\\Administrator\\Desktop\\中国人大\\java程序设计\\wordTest.txt");
        try {
            StringBuilder stringBuilder = FileUtils.readFile(file);
            System.out.println("文件内容start:=======");
            System.out.println(stringBuilder);
            System.out.println("文件内容end:=======");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetWordList(){
//        String text = "我不认为我是一个合格的学生，可是我真得爱学习";
        String text = "i don't think that i am a good student";
        try {
            List<String> stringList = IKSUtils.getStringList(text);
            System.out.println("list是："+stringList);
            Map<String, Integer> stringIntegerMap = IKSUtils.wordCount(stringList);
            System.out.println("词频统计结果是:"+stringIntegerMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWebUtils(){
        String s = WebUtils.sendGet("https://www.cnblogs.com/tigerlion/p/11182810.html", null);
//        System.out.println("请求的响应结果是：" + s);
        System.out.println("解析后是：=================");
        s = WebUtils.parseHtmlToText(s);
        try {
            List<String> stringList = IKSUtils.getStringList(s);
            System.out.println("字符串列表："+stringList);
            System.out.println("统计后是："+IKSUtils.wordCount(stringList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
