package com.ym.springbootproject.utils;

import com.ym.springbootproject.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Meng Excel工具类
 * @Description: TODO
 * @date 2021/7/15 17:50
 */
public class ExcelUtils {

    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private final static String Excel_2003 = ".xls"; //2003 版本的Excel
    private final static String Excel_2007 = ".xlsx"; //2007 版本的Excel

    /**
     * Excel数据解析
     * @param file 上传的文件
     * @return
     */
    public static List<LinkedHashMap<String,String>> readData(MultipartFile file){
        Workbook workbook = null;

        try {
            workbook = ExcelUtils.getWorkbook(file.getInputStream(),file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(workbook == null){
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null){
            return null;
        }
        //TODO 行数
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        //TODO 列数
        Row row = sheet.getRow(firstRowNum);
        int firstCellNum = row.getFirstCellNum();
        int lastCellNum = row.getLastCellNum();
        log.info("Excel表格数据："+"行数:"+lastRowNum +","+"列数："+lastCellNum);

        List<LinkedHashMap<String,String>> mapList = new ArrayList<>();
        for(int i = firstRowNum; i<=lastRowNum;i++){
            LinkedHashMap<String,String> tempMap = new LinkedHashMap<>();
            if(sheet.getRow(i) == null){
                continue;
            }
            row = sheet.getRow(i);
            for(int j = firstCellNum;j<lastCellNum;j++){
                if(row.getCell(j) == null){
                    continue;
                }else {
                    Cell cell = row.getCell(j);
                    // TODO 获取Excel表格里的值
                    String value = ExcelUtils.getCellValue(cell).trim();
                    tempMap.put("column"+(j+1),value);
                }
            }
            mapList.add(tempMap);
        }

        List<LinkedHashMap<String, String>> resultList = ExcelUtils.deleteBlankCell(mapList, lastCellNum);
        return resultList;
    }

    /**
     * 获取文件的工作簿
     * @param input
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream input, String fileName) throws IOException {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
            if(Excel_2003.equals(fileType)){
                workbook = new HSSFWorkbook(input);
            }else if(Excel_2007.equals(fileType)){
                workbook = new HSSFWorkbook(input);
            }else {
                throw new IOException("解析文件格式有误！");
            }
       return workbook;

    }

    /**
     *  获取Excel表格里的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){
        String value = null;
        DecimalFormat df = new DecimalFormat("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(cell != null && !"".equals(cell)){
            CellType cellType = cell.getCellType();
            switch (cellType){
                case NUMERIC:
                case FORMULA:
                    if(DateUtil.isCellDateFormatted(cell)){
                        value = sdf.format(cell.getDateCellValue());
                    }else {
                        // 去除长数字double类型带有两位小数的
                        value = df.format(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case BLANK:
                    value = "";
                    break;
                case ERROR:
                    value = "错误字符";
                    break;
                case BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                default:
                    value = "字符无法辨认";
                    break;
            }
        }

        return value;
    }

    /**
     * 删除表格空白数据
     * @param dataList
     * @param lastCellNum
     * @return
     */
    public static List<LinkedHashMap<String,String>> deleteBlankCell(List<LinkedHashMap<String,String>> dataList,int lastCellNum){
       // TODO 定义返回结果集
        List<LinkedHashMap<String,String>> resultList = new ArrayList<>();
        // TODO 数据处理，（默认第一行为表头）如果有空表头，要去掉
        List<String> blankHead = new ArrayList<>();
        int nullIndex = 0;
        int notNullHeadSize = 0;
        for(int i = 0; i<dataList.size();i++){
            LinkedHashMap<String,String> tempMap = dataList.get(i);
            if(i == 0){
                for(Map.Entry<String,String> entry: tempMap.entrySet()){
                    if(StringUtils.isEmpty(entry.getValue())){
                        blankHead.add(entry.getKey());
                    }
                }
                //todo 如果没有空表头就进行下一次循环
              if(blankHead.size() == 0 ){
                  resultList.add(tempMap);
                  continue;
              }else{
                  for (String str : blankHead) {//如果有空表头，则删除空表头
                      tempMap.remove(str);
                  }
              }
                resultList.add(tempMap);
            }else {
                notNullHeadSize = lastCellNum - blankHead.size();
                for(String str : blankHead){// 删除空表头对应的数据
                    tempMap.remove(str);
                }
                nullIndex = 0;
                for(Map.Entry<String,String> entry: tempMap.entrySet()){
                    if(StringUtils.isEmpty(entry.getValue())){
                        nullIndex++;
                    }
                }
                if(nullIndex != notNullHeadSize){
                    resultList.add(tempMap);
                }
            }
        }
        return resultList;
    }

}
