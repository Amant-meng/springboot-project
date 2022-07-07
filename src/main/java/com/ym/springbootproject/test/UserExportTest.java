package com.ym.springbootproject.test;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import com.ym.springbootproject.moudle.sys.entity.Person;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @Description TODO easypoi 一对多导出测试
 * @Author yangmeng
 * @Date 2022/6/23 12:28
 */
@RestController
@RequestMapping("/test")
public class UserExportTest {

    @GetMapping("/easypoiExport")
    public void test(HttpServletResponse response) throws Exception {
        List<CourseEntity> courseEntities = new ArrayList<>();

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setId("11111");
        courseEntity.setName("语文课");
        TeacherEntity teacher = new TeacherEntity();
        teacher.setId("tesnks");
        teacher.setName("张三老师");
        courseEntity.setMathTeacher(teacher);

        List<UserEntity> userList = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setId("110");
        user.setName("小王");
        user.setBirthday(new Date());
        user.setSex(1);
        user.setRegistrationDate(new Date());

        UserEntity user1 = new UserEntity();
        user1.setId("120");
        user1.setName("小花");
        user1.setBirthday(new Date());
        user1.setSex(2);
        user1.setRegistrationDate(new Date());

        userList.add(user);
        userList.add(user1);

        List<Hobby> hobbyList = new ArrayList<>();
        Hobby hobby = new Hobby();
        hobby.setId("11110");
        hobby.setName("篮球");
        hobby.setText("喜欢喝别人一起打篮球");

        Hobby hobby1 = new Hobby();
        hobby1.setId("11110");
        hobby1.setName("唱歌");
        hobby1.setText("喜欢去KTV唱歌");

        Hobby hobby2 = new Hobby();
        hobby2.setId("11110");
        hobby2.setName("跳舞");
        hobby2.setText("喜欢看美女跳舞");

        hobbyList.add(hobby);
        hobbyList.add(hobby1);
        hobbyList.add(hobby2);

        courseEntity.setStudents(userList);
        courseEntity.setHobby(hobbyList);

        courseEntities.add(courseEntity);
        // 设置响应输出的头类型及下载文件的默认名称
        String fileName = new String("人员信息测试".getBytes("utf-8"), "ISO-8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), CourseEntity.class, courseEntities);
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
    }


}
