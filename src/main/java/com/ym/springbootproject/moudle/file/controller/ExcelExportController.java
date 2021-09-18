package com.ym.springbootproject.moudle.file.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ym.springbootproject.common.ResultBody;
import com.ym.springbootproject.common.core.AjaxResult;
import com.ym.springbootproject.common.utils.poi.ExcelUtil;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.mapper.PersonMapper;
import com.ym.springbootproject.moudle.sys.entity.SysRole;
import com.ym.springbootproject.moudle.sys.service.TestService;
import com.ym.springbootproject.utils.ExcelUtils;
import com.ym.springbootproject.utils.ExportExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO Excel文件导入、导出测试
 * @date 2021/7/6 11:21
 */
@Api(value = "Excel文件导入、导出",tags = {"Excel文件导入、导出接口"})
@RestController
@RequestMapping("/excel")
public class ExcelExportController {

    private static Logger log = LoggerFactory.getLogger(ExcelExportController.class);

    @Autowired
    private TestService testService;

    @Autowired
    PersonMapper personMapper;

    /**
     * 测试数据导出功能，使用的是若依框架导出
     * @return
     */
    @PostMapping("/export")
    @ApiOperation("角色数据导出，若依框架")
    public AjaxResult export() {
        List<SysRole> list = testService.selectSysRoleList();
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 导出人员信息，自己写的导出
     * @return
     */
    @GetMapping("/exportPerson")
    @ApiOperation("导出人员信息，自己写的导出")
    public void exportPerson(HttpServletResponse response){
        List<Map> list = personMapper.selectPerson();

        // 实体类转换为map
        List<LinkedHashMap<String, String>> result = new ArrayList<>();
        for (Map e : list) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("name", (String) e.get("name"));
            map.put("sec", (String) e.get("sex"));
            map.put("age", String.valueOf(e.get("age")));
            map.put("phone",(String) e.get("phone"));
            if(e.get("birthday") !=null){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                map.put("birthday",sf.format(e.get("birthday")));
            }else {
                map.put("birthday","");
            }
            map.put("email",(String) e.get("email"));
            map.put("qq",(String) e.get("qq"));
            map.put("wechat",(String) e.get("wechat"));
            map.put("address",(String) e.get("address"));
            result.add(map);
        }

        // 2.定义变量值 创建Excel文件
        // 定义文件名
        String fileName = "人员信息表.xls";
        // 定义表格标题
        String headString = "人员信息表";
        // 定义工作表表名
        //String sheetName = "人员信息表Sheet";
        // 文件本地保存路径
        String filePath = "E:\\";
        String[] thead = { "姓名", "性别", "年龄", "电话", "出生日期",
                "邮箱","QQ","微信", "地址" };
        // 定义每一列宽度
        int[] sheetWidth = { 3000, 3000, 3000, 3000, 4000, 5000, 5000, 5000,9000};

        HSSFWorkbook wb = new HSSFWorkbook(); // 创建Excel文档对象
        HSSFSheet sheet = wb.createSheet(); // 创建工作表
        // 3.生成表格
        // ①创建表格标题
        //ExportExcelUtil.createHeadTittle(wb, sheet, headString, 8);
        // ②创建表头
        ExportExcelUtil.createThead(wb, sheet, thead, sheetWidth);
        // ③填入数据
        ExportExcelUtil.createTable(wb, sheet, result);
        // 设置浏览器下载
        ExportExcelUtil.respOutPutExcel("人员信息导出",wb,response);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(new File(filePath + fileName));
            wb.write(fos);
            fos.close();
            System.out.println("导出excel成功");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     *  Excel数据导入功能
     * @param file
     * @param sjlx
     * @return
     */
    @PostMapping("/importExcelData")
    @ApiOperation("Excel数据导入解析")
    public ResultBody importExcelData(@RequestParam("file") MultipartFile file,@RequestParam("sjlx") String sjlx){

        List<LinkedHashMap<String, String>> mapList = ExcelUtils.readData(file);

        return ResultBody.ok(mapList);
    }

    /**
     *  使用easypoi导出框架,导出人员信息
     * @param response
     */
    @GetMapping("/easypoiExport")
    @ApiOperation("使用easypoi导出框架，导出人员信息")
    public void easypoiExport(HttpServletResponse response) {
        log.info("请求 exportExcel start ......");

        List<Person> list = personMapper.selectAll();

        try {
            // 设置响应输出的头类型及下载文件的默认名称
            String fileName = new String("人员信息测试".getBytes("utf-8"), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");

            //导出
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), Person.class, list);
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            log.info("文件下载成功！");;
            log.info("请求 exportExcel end ......");
        } catch (IOException e) {
            log.info("请求 exportExcel 异常：{}", e.getMessage());
        }
    }


    /**
     * 使用easypoi框架导入Excel数据
     * @param file
     */
    @GetMapping("/easypoiImport")
    @ApiOperation("使用easypoi框架导入Excel数据")
    public void easypoiImport(@RequestParam("file") MultipartFile file) {
        log.info("easypoi框架导入数据开始");
        List<Person> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), Person.class, new ImportParams());
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
