package com.ym.springbootproject.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/8/17 17:18
 */
public class HttpSendUtil {

    public static String xmlPost(String urlStr, String xmlInfo) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
//            con.setRequestProperty("Pragma:", "no-cache");
//            con.setRequestProperty("Cache-Control", "no-cache");
            // 一定要设置报文格式，否则发送失败
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter out = null;
            try {
                out = new OutputStreamWriter(con.getOutputStream());
            } catch (ConnectException e) {
//                e.printStackTrace();
                return "Connection refused";
            }
//            System.out.println("urlStr=" + urlStr);
//            System.out.println("xmlInfo=" + xmlInfo);
            out.write(new String(xmlInfo.getBytes("ISO-8859-1")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
//                System.out.println(line);
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void readStringXml(String xml) {
        //Document：表示xml文档信息，是一个树形结构
        Document doc = null;
        try {

             /**
             读取并解析XML文档
             SAXReader就是一个管道，用一个流的方式，把xml文件读出来
             SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
             Document document = reader.read(new File("User.hbm.xml"));
             下面的是通过解析xml字符串的
             */
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            //Eelment：表示xml的元素结点，提供一些操作其子元素方法的，如文本、属性、名称空间等
            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            Iterator iter = rootElt.elementIterator("head"); // 获取根节点下的子节点head

            // 遍历head节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                String title = recordEle.elementTextTrim("title"); // 拿到head节点下的子节点title值
                System.out.println("title:" + title);
                Iterator iters = recordEle.elementIterator("script"); // 获取子节点head下的子节点script

                // 遍历Header节点下的Response节点
                while (iters.hasNext()) {
                    Element itemEle = (Element) iters.next();
                    String username = itemEle.elementTextTrim("username"); // 拿到head下的子节点script下的字节点username的值
                    String password = itemEle.elementTextTrim("password");
                    System.out.println("username:" + username);
                    System.out.println("password:" + password);
                }
            }
            Iterator iterss = rootElt.elementIterator("body"); ///获取根节点下的子节点body
            // 遍历body节点
            while (iterss.hasNext()) {

                Element recordEless = (Element) iterss.next();
                String result = recordEless.elementTextTrim("result"); // 拿到body节点下的子节点result值
                System.out.println("result:" + result);
                Iterator itersElIterator = recordEless.elementIterator("form"); // 获取子节点body下的子节点form

                // 遍历Header节点下的Response节点
                while (itersElIterator.hasNext()) {

                    Element itemEle = (Element) itersElIterator.next();
                    String banlce = itemEle.elementTextTrim("banlce"); // 拿到body下的子节点form下的字节点banlce的值
                    String subID = itemEle.elementTextTrim("subID");
                    System.out.println("banlce:" + banlce);
                    System.out.println("subID:" + subID);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    /**
     * 创建xml文件
     * @param file
     */
    public static void createDom4j(File file) {
        try {
            // 创建一个Document实例
            Document doc = DocumentHelper.createDocument();

            // 添加根节点
            Element root = doc.addElement("root");

            // 在根节点下添加第一个子节点
            Element oneChildElement= root.addElement("person").addAttribute("attr", "root noe");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person one child one");
            oneChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person one child two");

            // 在根节点下添加第一个子节点
            Element twoChildElement= root.addElement("person").addAttribute("attr", "root two");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child one")
                    .addText("person two child one");
            twoChildElement.addElement("people")
                    .addAttribute("attr", "child two")
                    .addText("person two child two");

            // xml格式化样式
            // OutputFormat format = OutputFormat.createPrettyPrint(); // 默认样式

            // 自定义xml样式
            OutputFormat format = new OutputFormat();
            format.setIndentSize(2);  // 行缩进
            format.setNewlines(true); // 一个结点为一行
            format.setTrimText(true); // 去重空格
            format.setPadText(true);
            format.setNewLineAfterDeclaration(false); // 放置xml文件中第二行为空白行

            // 输出xml文件
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(doc);
            System.out.println("dom4j CreateDom4j success!");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
