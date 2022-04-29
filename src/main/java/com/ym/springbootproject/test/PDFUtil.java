package com.ym.springbootproject.test;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author yangmeng
 * @Date 2022/3/16 16:12
 */
public class PDFUtil {

    public static void main(String[] args) throws Exception {

        test();

        System.out.println("success");

    }


    public static void test() throws IOException, DocumentException {
        // pdf模板
        String fileName = "E:/XDH/ttttttt_加水印.pdf";
        //读取pdf
        PdfReader reader = new PdfReader(fileName);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        //将要生成的目标PDF文件名称
        PdfStamper ps = new PdfStamper(reader, bos);

        //PdfContentByte under = ps.getUnderContent(1);

        //设置中文字体
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();

        fontList.add(bf);

        //取出报表模板中的所有字段
        AcroFields fields = ps.getAcroFields();

        fields.setSubstitutionFonts(fontList);

        //对表单数据进行赋值
        fillData(fields, ps, data());

        //必须要调用这个，否则文档不会生成的
        ps.setFormFlattening(true);

        ps.close();

        OutputStream fos = new FileOutputStream("E:/XDH/new.pdf");

        fos.write(bos.toByteArray());

        fos.flush();

        fos.close();

        bos.close();

    }

    public static void fillData(AcroFields fields, PdfStamper ps, Map<String, String> data) throws IOException, DocumentException {
        // 为字段赋值,注意字段名称是区分大小写的
        for (String key : data.keySet()) {
            if (key.equals("image")){
                /**
                 * 添加图片
                 */
                String imgpath = data.get(key);
                int pageNo = fields.getFieldPositions(key).get(0).page;
                Rectangle signRect = fields.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                // 读图片
                Image image = Image.getInstance(imgpath);
                // 获取操作的页面
                PdfContentByte under = ps.getOverContent(pageNo);
                // 根据域的大小缩放图片
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                // 添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            }else {
                String value = data.get(key);
                fields.setField(key, value);
            }
        }

    }

    public static Map<String, String> data() {

        Map<String, String> data = new HashMap<String, String>();

        data.put("name", "王强");

        data.put("idcard", "5303811111111111");

        data.put("phone", "18200000000");

        data.put("companyName", "XXXXXX公司");

        data.put("payTime", "2018年10月10日");

        //选择框的设置On为选中
        data.put("build", "On");

        data.put("house", "On");

        data.put("other", "Off");

//        data.put("building", "18208817655");
//
//        data.put("floor", "18208817655");

        data.put("first", "On");

//        data.put("again", "18208817655");

//        data.put("change", "18208817655");

        data.put("expireTime", "2018年10月10日");

        data.put("plate", "京A10101");

        data.put("amount", "768.25");

        return data;

    }

}
