package cn.dintalk.gui;

import cn.dintalk.util.FileUtils;
import cn.dintalk.util.IKSUtils;
import cn.dintalk.util.WebUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.song
 * @date 2019/10/13 10:09
 */
public class MyFrame extends JFrame {
    //1.定义字体及最终的 地址
    Font font = new Font("宋体", Font.PLAIN, 18);
    String location;
    //2.组件的定义 start

    //2.1文件来源下拉框
    JPanel jp1;
    JLabel jlb1;
    JComboBox jcb1;

    //2.2url输入框
    JPanel jp2;
    JLabel jlb2;
    JTextField jtf2;

    //2.3文件选择按钮及文件选择器
    JPanel jp3;
    JLabel jlb3;
    JTextField jtf3;
    JButton jButton3;
    JFileChooser jFileChooser;

    //2.4 统计结果及其滚动窗口
    JPanel jp4;
    JLabel jlb4;
    JScrollPane jsp4;
    JTextArea jta4;
    //2.5 确定按钮
    JPanel jp5;
    JButton jButton5;
    //2.组件的定义 end

    //3.构造函数
    public MyFrame() {
        //3.1 设置窗体属性
        this.setTitle("词频统计");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        //3.2组件初始化 start
        //下拉框选择
        jp1 = new JPanel();
        jlb1 = new JLabel("源文件来源：");
        String[] jg = {"网络", "本地"};
        jcb1 = new JComboBox(jg);

        // url输入框
        jp2 = new JPanel();
        jlb2 = new JLabel("网络资源URL:");
        jtf2 = new JTextField();
        jtf2.setPreferredSize(new Dimension(300, 30));

        // 浏览按钮及文件选择
        jp3 = new JPanel();
        jlb3 = new JLabel("本地文件选择：");
        jtf3 = new JTextField();
        jtf3.setPreferredSize(new Dimension(207, 30));
        jButton3 = new JButton();
        jButton3.setText("浏览");
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("请选择.txt的文本文件");
        jFileChooser.setPreferredSize(new Dimension(500, 300));
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt");
            }
            @Override
            public String getDescription() {
                return ".txt";
            }
        });
        //确定按钮
        jp5 = new JPanel();
        jButton5 = new JButton();
        jButton5.setText("确认");
        //统计结果
        jp4 = new JPanel();
        jlb4 = new JLabel("统计分析结果：");
        jta4 = new JTextArea();
        jsp4 = new JScrollPane(jta4);
        jsp4.setPreferredSize(new Dimension(600, 380));
        jta4.setLineWrap(true);
        jta4.setWrapStyleWord(true);
        //3.2组件初始化 end

        //3.3字体和位置设置 start
        jlb1.setFont(font);
        jcb1.setFont(font);
        jlb2.setFont(font);
        jtf2.setFont(font);
        jlb2.setFont(font);
        jlb3.setFont(font);
        jtf3.setFont(font);
        jButton3.setFont(font);
        jlb4.setFont(font);
        jta4.setFont(font);
        jButton5.setFont(font);
        //3.3字体和位置设置 end

        //3.4 添加组件 start
        jp1.add(jlb1);
        jp1.add(jcb1);
        jp2.add(jlb2);
        jp2.add(jtf2);
        jp3.add(jlb3);
        jp3.add(jtf3);
        jp3.add(jButton3);
        jp4.add(jlb4);
        jp4.add(jsp4);
        jp5.add(jButton5);
        this.add(jp1, BorderLayout.WEST);
        this.add(jp2, BorderLayout.WEST);
        this.add(jp3, BorderLayout.WEST);
        this.add(jp5, BorderLayout.WEST);
        this.add(jp4, BorderLayout.SOUTH);
        //3.4 添加组件 end

        //3.5 设置默认显示 start
        jp3.setVisible(false);
        this.setVisible(true);
        //3.5 设置默认显示 end

        //3.6 添加事件 start
        //下拉框选择事件
        jcb1.addItemListener(e -> {
            if (jcb1.getSelectedItem().equals("网络")) {
                jp3.setVisible(false);
                jp2.setVisible(true);
                jtf3.setText("");
            } else {
                jp2.setVisible(false);
                jp3.setVisible(true);
                jtf2.setText("");
            }
        });
        //浏览按钮点击事件
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = jFileChooser.showOpenDialog(null);
                if (jFileChooser.APPROVE_OPTION == returnVal) {
                    File file = jFileChooser.getSelectedFile();
                    jtf3.setText(file.getAbsolutePath());
                }
            }
        });
        //确认按钮添加时间
        jButton5.addActionListener(e -> {
            String urlText = jtf2.getText();
            String filePath = jtf3.getText();
            if (urlText != null && !"".equals(urlText)) {
                location = urlText;
            }
            if (filePath != null && !"".equals(filePath)) {
                location = filePath;
            }
            //处理逻辑
            try {
                showResult(location);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"请输入合法的URL地址或选择符合格式要求的文件");
            }
        });
        //3.6 添加事件 end
    }

    private void showResult(String location) throws Exception {
        Map<String,Integer> result;
        String content;
        if(location.startsWith("http")){
            String html = WebUtils.sendGet(location, null);
            content = WebUtils.parseHtmlToText(html);
        }else {
            File file = FileUtils.getFile(location);
            content = FileUtils.readFile(file).toString();
        }
        List<String> stringList = IKSUtils.getStringList(content);
        result = IKSUtils.wordCount(stringList);
        //结果封装
        StringBuilder sb = new StringBuilder();
        sb.append("词汇").append("\t\t\t\t\t");
        sb.append("出现次数").append("\r\n");
        sb.append("==============================================").append("\r\n");
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            String key = entry.getKey();
            sb.append(key);
            int step = Math.round(40 - key.length());
            for (int i = 0; i < step; i++) {
                sb.append(" ");
            }
            sb.append(entry.getValue()).append("\r\n");
        }
        jta4.setText(sb.toString());
    }
}
