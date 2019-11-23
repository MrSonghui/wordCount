package cn.dintalk.util;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Mr.song
 * @date 2019/10/10 21:12
 */
public class IKSUtils {

    /**
     * 对文本进行分词
     * @param text
     * @return
     * @throws Exception
     */
    public static List<String> getStringList(String text) throws Exception{
        //独立Lucene实现
        StringReader re = new StringReader(text);
        IKSegmenter ik = new IKSegmenter(re, true);
        Lexeme lex;
        List<String> s = new ArrayList<>();
        while ((lex = ik.next()) != null) {
            s.add(lex.getLexemeText());
        }
        return s;
    }

    /**
     * 统计词频
     * @param wordList
     * @return
     */
    public static Map<String,Integer> wordCount(List<String> wordList){
        if (wordList == null) return null;
        Map<String,Integer> result = new HashMap<>();
        for (String s : wordList) {
            Integer count = result.get(s);
            if (count ==  null){
                result.put(s,1);
            }else {
                result.put(s,++count);
            }
        }
        //按照次数排序
        result = result
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return result;
    }
}
