package com.boxamazing.service.excel;

import java.io.OutputStream;
import java.util.Vector;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
/**
 * @类功能说明：简单创建Excel的实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @作者：He Chaojie
 * @创建时间：2013-8-2 下午1:04:40
 * @版本：V1.0
 */

public class SimpleCreateExcel implements CreateExcel {
	
	@Override
	public WritableWorkbook create(AbstractSheet abstractSheet , OutputStream os) {
		
		SimpleSheet simpleSheet = (SimpleSheet)abstractSheet;
		WritableWorkbook writableWorkbook = null;
		
		try {
			//创建工作表格
			writableWorkbook = Workbook.createWorkbook(os);
           
            // 生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = writableWorkbook.createSheet(simpleSheet.getSheetName()==null?"":simpleSheet.getSheetName().trim(), 0);
            
            //标题头
            String[] colNames ;
            if(simpleSheet.getColNames() != null){
            	colNames = simpleSheet.getColNames();
            	for( int i = 0; i < colNames.length ; i++){
            		//列----->行
            		Label label = new Label(i, 0, colNames[i].trim());
            		sheet.addCell(label);
            	}
            }
            
            //数据
            Vector<String[]> datas ;
            if(simpleSheet.getDatas() != null){
            	datas = simpleSheet.getDatas();
            	//多少行
            	for(int i = 0 ;i<datas.size();i++){
            		String[] str = datas.get(i);
            		//多少列
            		for( int j =0 ; j< str.length ;j++){
            			Label label = new Label(j,i+1, str[j].trim());
            			sheet.addCell(label);
            		}
            	}
            }
        } catch (Exception e) {
        	log.error("----error---导出Excel文件出错------");
        }
		return writableWorkbook;
	}
}
