package kr.go.rastech.commons.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * <pre>
 *  엑셀파일을 생성하여 결과를 저장한다. 
 * 
 * </pre>
 * @FileName : ExcelDownUtil.java
 * @package  : kr.go.ncmiklib.commons.utils
 * @author   : wonki0138
 * @date     : 2018. 4. 3.
 * 
 */
public class ExcelDownUtil {


	/**
	 * <pre>
	 *  과제 참여율 관리데이터를 excel 다운로드 한다.
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 3. 
	 * @param response
	 * @param dataList
	 * @param colinfoList
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public Object excelWrite(HttpServletResponse response, List dataList, ArrayList colinfoList, String filename, String title) throws IOException, SQLException , NullPointerException
	{
 	
 		//String colnames, String colinfos,
 		//String[] col_names = colnames.toLowerCase().split(";");
 		//String[] col_info = colinfos.split(";");
 		
 		String tm = getStringFromDate(new Date(), "yyyy-MM-dd-HH:mm");
 		
 		HSSFWorkbook wb = new HSSFWorkbook();  
		// 「sheet1」라는 이름의 워크시트를 표시하는 오브젝트 생성 
		HSSFSheet sheet1 = wb.createSheet(title);  
				
		// 행의 작성  
		/************************엑셀 스타일 지정****************************/
		HSSFCellStyle style_title = wb.createCellStyle();
		HSSFCellStyle style_header = wb.createCellStyle();
		HSSFCellStyle style_data_l = wb.createCellStyle();
		HSSFCellStyle style_data_c = wb.createCellStyle();
		HSSFCellStyle style_data_r = wb.createCellStyle();
		
		
		HSSFCellStyle style_data_sum = wb.createCellStyle();
		HSSFCellStyle style_sum_center = wb.createCellStyle();
		HSSFCellStyle style_sum_right = wb.createCellStyle();
		
		style_data_l.setWrapText(true);
		style_data_c.setWrapText(true);
		style_data_r.setWrapText(true);
		
		HSSFFont font = wb.createFont();
	
		HSSFFont font_title = wb.createFont();
		
		font_title.setFontHeightInPoints((short)12);
		font_title.setBoldweight((short)700);
		 
		font.setBoldweight((short)700);
		
		style_data_l.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data_l.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data_l.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data_l.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data_l.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_data_l.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		style_data_c.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data_c.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data_c.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data_c.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data_c.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_data_c.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		style_data_r.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data_r.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data_r.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data_r.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data_r.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_data_r.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setFont(font_title);		
		style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						
		style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style_header.setFillBackgroundColor(HSSFColor.AQUA.index);
		style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_header.setFont(font);		
		
	
		style_data_sum.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style_data_sum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style_data_sum.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style_data_sum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style_data_sum.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
	    style_data_sum.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    style_data_sum.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

	    // 소계 스타일 설정
	    style_sum_center.cloneStyleFrom(style_data_sum);
	    style_sum_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	    style_sum_right.cloneStyleFrom(style_data_sum);
	    style_sum_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		/************************엑셀 스타일 지정****************************/
		
		
		
		
		/************************엑셀 정보 출력****************************/
		HSSFRow row_title = sheet1.createRow(2);
		row_title.setHeight((short)600);
		HSSFCell cell_title= row_title.createCell(0);
		cell_title.setCellValue(title);
		cell_title.setCellStyle(style_title);
		sheet1.addMergedRegion(new CellRangeAddress(2,2,0,(colinfoList.size() - 1)));
		
		
		HSSFRow row_date = sheet1.createRow(3);
		HSSFCell cell_date= row_date.createCell(0);
		
		cell_date = row_date.createCell(0);
		cell_date.setCellValue("Date");
		cell_date = row_date.createCell(1);
		cell_date.setCellValue(tm);
		/************************엑셀 정보 출력****************************/
		
		
		HSSFRow row0 = sheet1.createRow(5);
		HSSFCell cell = null;
		for(int k=0; k < colinfoList.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList.get(k);
			row0.setHeight((short)500);
			cell = row0.createCell(k);
			sheet1.setColumnWidth(k, colmap.get("COL_SIZE") != null ? Integer.parseInt(colmap.get("COL_SIZE").toString()):4000);
			cell.setCellValue((String)colmap.get("COL_INFO"));
			cell.setCellStyle(style_header);
		}			
		for(int i=0; i < dataList.size(); i++)
		{
			row0 = sheet1.createRow(i + 6);
			row0.setHeight((short)900);
			
			Map article = (HashMap)dataList.get(i);
			boolean isSumRow = false;
			
			for(int j=0; j < colinfoList.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList.get(j);
				cell =  row0.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")));
				if(obj == null){
					cell.setCellValue("");
					cell.setCellStyle(style_data_c);
				}else {
					if(obj instanceof java.sql.Clob){
						StringBuffer strOut = new StringBuffer();
						 String str = "";
						 Clob clob = (java.sql.Clob)obj;
						 BufferedReader br = new BufferedReader(clob.getCharacterStream());
						 while ((str = br.readLine()) != null) {
							 strOut.append(str);
						 }
						 cell.setCellValue(strOut.toString());
					}else{
						if(StringUtils.equals(obj.toString(), "소        계")){
							isSumRow = true;
						}
						// 월 참여율 데이터는 double형으로 구성
						if(NumberUtils.isNumber(obj.toString())) {
							double numVal = Double.parseDouble(obj.toString());
							cell.setCellValue(numVal);
						}else {
							cell.setCellValue(obj.toString());
						}
						
					}
					
					if (StringUtil.nvl(colmap.get("COL_INFO")).indexOf("월") != -1){
			           
			                cell.setCellStyle(style_data_r);
					}else if(StringUtil.nvl(colmap.get("COL_INFO")).indexOf("과제명") != -1 ){
						cell.setCellStyle(style_data_l);
					}else {
						cell.setCellStyle(style_data_c);
					}

	            
				}
				
			}	
			
			// 합계행을 구성한다 . 
			if (isSumRow) {
			    for (int j = 0; j < colinfoList.size(); j++) {
			    	  HSSFCell cellSum = row0.getCell(j);
			          if (cellSum != null) {
			              if (cellSum.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			            	
			            	  cellSum.setCellStyle(style_data_sum);
			              } else {
			            	
			            	  cellSum.setCellStyle(style_sum_center);
			                  
			              }
			          }			    				    	
			    }
			}
			
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(filename + "_" + tm + ".xls","UTF-8"));
		response.addHeader("Content-description", "EventAttendees");
		response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		ServletOutputStream stream = response.getOutputStream();
		wb.write(stream);
		stream.close();

		return null;
	}
	
	
	/**
	 * <pre>
	 *  Query 질의 결과를 엑셀파일로 저장함. 
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 3. 
	 * @param response
	 * @param dataList
	 * @param colinfoList
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public Object excelMultiWrite(HttpServletResponse response, List<List<HashMap>> dataList, List<List<HashMap>> colinfoList, String filename, String[] title) throws IOException, SQLException , NullPointerException
	{
		

		
		String tm = getStringFromDate(new Date(), "yyyy-MM-dd - HH:mm");
		
		HSSFWorkbook wb = new HSSFWorkbook();  
		// 「sheet1」라는 이름의 워크시트를 표시하는 오브젝트 생성 
		
		ServletOutputStream stream = null;
		
		try{
			
			for ( int cnt=0;cnt < dataList.size();cnt++) {
				
				List list = dataList.get(cnt);
				List col = colinfoList.get(cnt);
				
				HSSFSheet sheet1 = wb.createSheet(title[cnt]);  
				
				// 행의 작성  
				/************************엑셀 스타일 지정****************************/
				HSSFCellStyle style_title = wb.createCellStyle();
				HSSFCellStyle style_data = wb.createCellStyle();
				HSSFCellStyle style_header = wb.createCellStyle();
				
				HSSFFont font = wb.createFont();
				HSSFFont font_title = wb.createFont();
				
				font_title.setFontHeightInPoints((short)12);
				font_title.setBoldweight((short)700);
				
				font.setBoldweight((short)700);
				
				style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style_title.setFont(font_title);		
				style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
				style_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				
				
				style_data.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style_data.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style_data.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style_data.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style_data.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				
				style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				
				style_header.setFillBackgroundColor(HSSFColor.AQUA.index);
				style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style_header.setFont(font);		
				/************************엑셀 스타일 지정****************************/
				
				
				
				
				/************************엑셀 정보 출력****************************/
				HSSFRow row_title = sheet1.createRow(2);
				row_title.setHeight((short)600);
				HSSFCell cell_title= row_title.createCell(0);
				cell_title.setCellValue(title[cnt]);
				cell_title.setCellStyle(style_title);
				sheet1.addMergedRegion(new CellRangeAddress(2,2,0,(colinfoList.size() - 1)));
				
				
				HSSFRow row_date = sheet1.createRow(3);
				HSSFCell cell_date= row_date.createCell(0);
				
				cell_date = row_date.createCell(0);
				cell_date.setCellValue("Date");
				cell_date = row_date.createCell(1);
				cell_date.setCellValue(tm);
				/************************엑셀 정보 출력****************************/
				
				
				HSSFRow row0 = sheet1.createRow(5);
				HSSFCell cell = null;
				
				for(int k=0; k < col.size(); k++)
				{
					HashMap colmap = (HashMap)col.get(k);
					row0.setHeight((short)500);
					cell = row0.createCell(k);
					sheet1.setColumnWidth(k, (short)4000);
					cell.setCellValue((String)colmap.get("COL_INFO"));
					cell.setCellStyle(style_header);
				}			
				
				for(int i=0; i < list.size(); i++)
				{
					row0 = sheet1.createRow(i + 6);
					row0.setHeight((short)400);
					Map article = (HashMap)list.get(i);
					
					for(int j=0; j < col.size(); j++)
					{
						HashMap colmap = (HashMap)col.get(j);
						cell =  row0.createCell(j);
						Object obj = article.get(((String)colmap.get("COL_NAME")));
						if(obj == null){
							cell.setCellValue("");
						}else {
							cell.setCellValue(obj.toString());
						}
						cell.setCellStyle(style_data);
					}				
					
				}
			
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(filename + "_" + tm + ".xls","UTF-8"));
			response.addHeader("Content-description", "EventAttendees");
			response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
			stream = response.getOutputStream();
			wb.write(stream);
			
		
		}finally{
			stream.flush();
			stream.close();
		}
		
		return null;
	}
	

	
	
	/**
	 *  Date 를 String으로 변경...
	 *  @param dt : Date 
	 *  @param format : ex) "yyyy-MM-dd HH:mm:SS"
	 */
	public String getStringFromDate(Date dt, String format)
	{
		SimpleDateFormat smp = new SimpleDateFormat(format);
		return smp.format(dt);
	}
	
	/**
	 * <pre>
	 * 질의 결과를 엑셀파일로 저장함. 
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 3. 
	 * @param response
	 * @param colinfoReportList
	 * @param dataList
	 * @param colinfoList
	 * @param dataList3
	 * @param colinfoList3
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public boolean excelReportWrite(HttpServletResponse response , ArrayList colinfoReportList ,  List dataList, ArrayList colinfoList,String filename, String title) throws IOException , SQLException , NullPointerException 
	{
 	
 		//String colnames, String colinfos,
 		//String[] col_names = colnames.toLowerCase().split(";");
 		//String[] col_info = colinfos.split(";");
 		
 		String tm = getStringFromDate(new Date(), "yyyy년MM월dd일-HH시mm분");
 		
 		HSSFWorkbook wb = new HSSFWorkbook();  
		// 「sheet2」라는 이름의 워크시트를 표시하는 오브젝트 생성 
		HSSFSheet sheet1 = wb.createSheet("보고서 정보 양식");  
		HSSFSheet sheet2 = wb.createSheet("주제 코드 정보");  
		
		// 행의 작성  
		/************************엑셀 스타일 지정****************************/
		HSSFCellStyle style_title = wb.createCellStyle();
		HSSFCellStyle style_data = wb.createCellStyle();
		HSSFCellStyle style_header = wb.createCellStyle();
		
		HSSFFont font = wb.createFont();
		HSSFFont font_title = wb.createFont();
		
		font_title.setFontHeightInPoints((short)12);
		font_title.setBoldweight((short)700);
		 
		font.setBoldweight((short)700);
		
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setFont(font_title);		
		style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		
		style_data.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						
		style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style_header.setFillBackgroundColor(HSSFColor.AQUA.index);
		style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_header.setFont(font);		
		/************************엑셀 스타일 지정****************************/
		
		
		
		
		/************************Sheet1 엑셀 정보 출력****************************/
		
		HSSFRow row_report = sheet1.createRow(0);
		HSSFCell cell_report = null;
		
		for(int k=0; k < colinfoReportList.size(); k++)
		{
			HashMap colmap_report = (HashMap)colinfoReportList.get(k);
			row_report.setHeight((short)500);
			cell_report = row_report.createCell(k);
			if( k == 1  ||  k == 2  ){
				sheet1.setColumnWidth(k, (short)14000);
			}else if( k == 4 ){
				sheet1.setColumnWidth(k, (short)10000);
			}else{
				sheet1.setColumnWidth(k, (short)6000);
			}
			
			cell_report.setCellValue((String)colmap_report.get("COL_INFO_REPORT"));
			cell_report.setCellStyle(style_header);
		}			
		
		
		/************************Sheet2 엑셀 정보 출력 str ****************************/

		HSSFRow row0 = sheet2.createRow(0);
		HSSFCell cell = null;
		
		for(int k=0; k < colinfoList.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList.get(k);
			row0.setHeight((short)500);
			cell = row0.createCell(k);
			sheet2.setColumnWidth(k, (short)8000);
			cell.setCellValue((String)colmap.get("COL_INFO"));
			cell.setCellStyle(style_header);
		}			
		
		for(int i=0; i < dataList.size(); i++)
		{
			row0 = sheet2.createRow(i + 1);
			row0.setHeight((short)400);
			Map article = (HashMap)dataList.get(i);
			
			for(int j=0; j < colinfoList.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList.get(j);
				cell =  row0.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell.setCellValue("");
				}else{ 
					cell.setCellValue(obj.toString());
				}
				cell.setCellStyle(style_data);
			}				
			
		}
		/************************Sheet2 엑셀 정보 출력 end ****************************/
		
	
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(filename + "_" + tm + ".xls","UTF-8"));
		response.addHeader("Content-description", "EventAttendees");
		response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		ServletOutputStream stream = response.getOutputStream();
		wb.write(stream);
		stream.close();
		
		return true;
	}
	
	/**
	 * <pre>
	 * 질의 결과를 엑셀파일로 저장함. 
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 3. 
	 * @param response
	 * @param colinfoReportList
	 * @param dataList
	 * @param colinfoList
	 * @param dataList3
	 * @param colinfoList3
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public void excelEdsWrite(HttpServletResponse response , ArrayList colinfoList ,  List dataList ,String filename, String title) throws IOException , SQLException , NullPointerException 
	{
 	
 		//String colnames, String colinfos,
 		
 		//String[] col_names = colnames.toLowerCase().split(";");
 		//String[] col_info = colinfos.split(";");
 		
 		String tm = getStringFromDate(new Date(), "yyyy년MM월dd일_HH시mm분");
 		
 		HSSFWorkbook wb = new HSSFWorkbook();  
		// 「sheet2」라는 이름의 워크시트를 표시하는 오브젝트 생성 
		HSSFSheet sheet1 = wb.createSheet("전자자원");  
		//HSSFSheet sheet2 = wb.createSheet("주제 코드 정보");  
		
		// 행의 작성  
		/************************엑셀 스타일 지정****************************/
		HSSFCellStyle style_title = wb.createCellStyle();
		HSSFCellStyle style_data = wb.createCellStyle();
		HSSFCellStyle style_header = wb.createCellStyle();
		
		HSSFFont font = wb.createFont();
		HSSFFont font_title = wb.createFont();
		
		font_title.setFontHeightInPoints((short)12);
		font_title.setBoldweight((short)700);
		 
		font.setBoldweight((short)700);
		
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setFont(font_title);		
		style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		
		style_data.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						
		style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style_header.setFillBackgroundColor(HSSFColor.AQUA.index);
		style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_header.setFont(font);		
		/************************엑셀 스타일 지정****************************/
		
		
		
		
		/************************Sheet1 엑셀 정보 출력****************************/
		
		HSSFRow row = sheet1.createRow(0);
		HSSFCell cell = null;
		
		for(int k=0; k < colinfoList.size(); k++)
		{
			HashMap colmap= (HashMap)colinfoList.get(k);
			row.setHeight((short)500);
			cell = row.createCell(k);
			
			if( k == 3  ||  k == 5  ){ 
				sheet1.setColumnWidth(k, (short)6000);
			}else if( k == 10 ){  
				sheet1.setColumnWidth(k, (short)6000);
			}else if( k == 13){ 
				sheet1.setColumnWidth(k, (short)6000);
			}else{
				sheet1.setColumnWidth(k, (short)6000);
			}
			
			cell.setCellValue((String)colmap.get("COL_INFO"));
			cell.setCellStyle(style_header);
		}			
		for(int i=0; i < dataList.size(); i++)
		{
			row = sheet1.createRow(i + 1);
			row.setHeight((short)400);
			Map article = (HashMap)dataList.get(i);
			
			for(int j=0; j < colinfoList.size(); j++)
			{

				HashMap colmap = (HashMap)colinfoList.get(j);
				cell =  row.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell.setCellValue("");
				}else{ 
					cell.setCellValue(obj.toString());
				}
				cell.setCellStyle(style_data);
			}				
			
		}
			
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(filename + "_" + tm + ".xls","UTF-8"));
		response.addHeader("Content-description", "EventAttendees");
		response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		ServletOutputStream stream = response.getOutputStream();
		wb.write(stream);

		stream.close();

	}
	
	/**
	 * <pre>
	 * 질의 결과를 엑셀파일로 저장함. 
	 *
	 * </pre>
	 * @author : wonki0138
	 * @date   : 2018. 4. 3. 
	 * @param response
	 * @param colinfoReportList
	 * @param dataList
	 * @param colinfoList
	 * @param dataList3
	 * @param colinfoList3
	 * @param filename
	 * @param title
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@SuppressWarnings("unchecked")
	public boolean excelStatsWrite(HttpServletResponse response ,  List statsXlsList_1, ArrayList colinfoList_1, List statsXlsList_2, ArrayList colinfoList_2, List statsXlsList_3, ArrayList colinfoList_3, List statsXlsList_4, ArrayList colinfoList_4, List statsXlsList_5, ArrayList colinfoList_5, List statsXlsList_6, ArrayList colinfoList_6,List statsXlsList_7, ArrayList colinfoList_7,String filename, String title) throws IOException , SQLException , NullPointerException 
	{
 	
 		//String colnames, String colinfos,
 		
 		//String[] col_names = colnames.toLowerCase().split(";");
 		//String[] col_info = colinfos.split(";");
 		
 		String tm = getStringFromDate(new Date(), "yyyy년MM월dd일-HH시mm분");
 		
 		HSSFWorkbook wb = new HSSFWorkbook();  
		// 「sheet2」라는 이름의 워크시트를 표시하는 오브젝트 생성 
		
		HSSFSheet sheet1 = wb.createSheet("01 기관 그룹별 로그인 건 수");  
		HSSFSheet sheet2 = wb.createSheet("02 일별 로그인 건 수");  
		HSSFSheet sheet3 = wb.createSheet("03 시간별 로그인 건 수");  
		HSSFSheet sheet4 = wb.createSheet("04 기관 그룹별 로그인 건 수(월별)");  
		HSSFSheet sheet5 = wb.createSheet("05 포털 회원 가입 건 수(탈퇴 제외)");  
		HSSFSheet sheet6 = wb.createSheet("06 포털 회원 가입 건 수(탈퇴 포함)");  //순서 바뀜 
		HSSFSheet sheet7 = wb.createSheet("07 국립벼원 로그인 건 수");  
		// 행의 작성  
		/************************엑셀 스타일 지정****************************/
		HSSFCellStyle style_title = wb.createCellStyle();
		HSSFCellStyle style_data = wb.createCellStyle();
		HSSFCellStyle style_header = wb.createCellStyle();
		
		HSSFFont font = wb.createFont();
		HSSFFont font_title = wb.createFont();
		
		font_title.setFontHeightInPoints((short)12);
		font_title.setBoldweight((short)700);
		 
		font.setBoldweight((short)700);
		
		style_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_title.setFont(font_title);		
		style_title.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		style_title.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_title.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		
		style_data.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_data.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_data.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						
		style_header.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style_header.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style_header.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style_header.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style_header.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style_header.setFillBackgroundColor(HSSFColor.AQUA.index);
		style_header.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style_header.setFont(font);		
		/************************엑셀 스타일 지정****************************/
		
		
		
		
		/************************Sheet1 엑셀 정보 출력****************************/
		
		HSSFRow row1 = sheet1.createRow(0);
		HSSFCell cell1 = null;
		
		for(int k=0; k < colinfoList_1.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_1.get(k);
			row1.setHeight((short)500);
			cell1 = row1.createCell(k);
			sheet1.setColumnWidth(k, (short)8000);
			cell1.setCellValue((String)colmap.get("COL_INFO"));
			cell1.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_1.size(); i++)
		{
			row1 = sheet1.createRow(i + 1);
			row1.setHeight((short)400);
			Map article = (HashMap)statsXlsList_1.get(i);
			
			for(int j=0; j < colinfoList_1.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_1.get(j);
				cell1 =  row1.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell1.setCellValue("");
				}else{ 
					cell1.setCellValue(obj.toString());
				}
				cell1.setCellStyle(style_data);
			}				
			
		}
		
		
		/************************Sheet2 엑셀 정보 출력 str ****************************/
		HSSFRow row2 = sheet2.createRow(0);
		HSSFCell cell2 = null;
		
		for(int k=0; k < colinfoList_2.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_2.get(k);
			row2.setHeight((short)500);
			cell2 = row2.createCell(k);
			sheet2.setColumnWidth(k, (short)8000);
			cell2.setCellValue((String)colmap.get("COL_INFO"));
			cell2.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_2.size(); i++)
		{
			row2 = sheet2.createRow(i + 1);
			row2.setHeight((short)400);
			Map article = (HashMap)statsXlsList_2.get(i);
			
			for(int j=0; j < colinfoList_2.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_2.get(j);
				cell2 =  row2.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell2.setCellValue("");
				}else{ 
					cell2.setCellValue(obj.toString());
				}
				cell2.setCellStyle(style_data);
			}				
			
		}	
		
		/************************Sheet2 엑셀 정보 출력 end ****************************/
		/************************Sheet3 엑셀 정보 출력 str ****************************/
		HSSFRow row3 = sheet3.createRow(0);
		HSSFCell cell3 = null;
		
		for(int k=0; k < colinfoList_3.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_3.get(k);
			row3.setHeight((short)500);
			cell3 = row3.createCell(k);
			sheet3.setColumnWidth(k, (short)8000);
			cell3.setCellValue((String)colmap.get("COL_INFO"));
			cell3.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_3.size(); i++)
		{
			row3 = sheet3.createRow(i + 1);
			row3.setHeight((short)400);
			Map article = (HashMap)statsXlsList_3.get(i);
			
			for(int j=0; j < colinfoList_3.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_3.get(j);
				cell3 =  row3.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell3.setCellValue("");
				}else{ 
					cell3.setCellValue(obj.toString());
				}
				cell3.setCellStyle(style_data);
			}				
			
		}
		
		/************************Sheet3 엑셀 정보 출력 end ****************************/
		/************************Sheet4 엑셀 정보 출력 str ****************************/
		HSSFRow row4 = sheet4.createRow(0);
		HSSFCell cell4 = null;
		
		for(int k=0; k < colinfoList_4.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_4.get(k);
			row4.setHeight((short)500);
			cell4 = row4.createCell(k);
			sheet4.setColumnWidth(k, (short)8000);
			cell4.setCellValue((String)colmap.get("COL_INFO"));
			cell4.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_4.size(); i++)
		{
			row4 = sheet4.createRow(i + 1);
			row4.setHeight((short)400);
			Map article = (HashMap)statsXlsList_4.get(i);
			
			for(int j=0; j < colinfoList_4.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_4.get(j);
				cell4 =  row4.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell4.setCellValue("");
				}else{ 
					cell4.setCellValue(obj.toString());
				}
				cell4.setCellStyle(style_data);
			}				
			
		}	
		
		/************************Sheet4 엑셀 정보 출력 end ****************************/
		/************************Sheet5 엑셀 정보 출력 str ****************************/
		HSSFRow row5 = sheet5.createRow(0);
		HSSFCell cell5 = null;
		
		for(int k=0; k < colinfoList_5.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_5.get(k);
			row5.setHeight((short)500);
			cell5 = row5.createCell(k);
			sheet5.setColumnWidth(k, (short)8000);
			cell5.setCellValue((String)colmap.get("COL_INFO"));
			cell5.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_5.size(); i++)
		{
			row5 = sheet5.createRow(i + 1);
			row5.setHeight((short)400);
			Map article = (HashMap)statsXlsList_5.get(i);
			
			for(int j=0; j < colinfoList_5.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_5.get(j);
				cell5 =  row5.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell5.setCellValue("");
				}else{ 
					cell5.setCellValue(obj.toString());
				}
				cell5.setCellStyle(style_data);
			}				
			
		}
		
		/************************Sheet5 엑셀 정보 출력 end ****************************/
		/************************Sheet6 엑셀 정보 출력 str ****************************/
		HSSFRow row6 = sheet6.createRow(0);
		HSSFCell cell6 = null;
		
		for(int k=0; k < colinfoList_6.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_6.get(k);
			row6.setHeight((short)500);
			cell6 = row6.createCell(k);
			sheet6.setColumnWidth(k, (short)8000);
			cell6.setCellValue((String)colmap.get("COL_INFO"));
			cell6.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_6.size(); i++)
		{
			row6 = sheet6.createRow(i + 1);
			row6.setHeight((short)400);
			Map article = (HashMap)statsXlsList_6.get(i);
			
			for(int j=0; j < colinfoList_6.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_6.get(j);
				cell6 =  row6.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell6.setCellValue("");
				}else{ 
					cell6.setCellValue(obj.toString());
				}
				cell6.setCellStyle(style_data);
			}				
			
		}	
		
		/************************Sheet6 엑셀 정보 출력 end ****************************/
		
		/************************Sheet7 엑셀 정보 출력 str ****************************/
		HSSFRow row7 = sheet7.createRow(0);
		HSSFCell cell7 = null;
		
		for(int k=0; k < colinfoList_7.size(); k++)
		{
			HashMap colmap = (HashMap)colinfoList_7.get(k);
			row7.setHeight((short)500);
			cell7 = row7.createCell(k);
			sheet7.setColumnWidth(k, (short)8000);
			cell7.setCellValue((String)colmap.get("COL_INFO"));
			cell7.setCellStyle(style_header);
		}			
		
		for(int i=0; i < statsXlsList_7.size(); i++)
		{
			row7 = sheet7.createRow(i + 1);
			row7.setHeight((short)400);
			Map article = (HashMap)statsXlsList_7.get(i);
			
			for(int j=0; j < colinfoList_7.size(); j++)
			{
				HashMap colmap = (HashMap)colinfoList_7.get(j);
				cell7 =  row7.createCell(j);
				Object obj = article.get(((String)colmap.get("COL_NAME")).toUpperCase());
				if(obj == null){
					cell7.setCellValue("");
				}else{ 
					cell7.setCellValue(obj.toString());
				}
				cell7.setCellStyle(style_data);
			}				
			
		}
		/************************Sheet7 엑셀 정보 출력 end ****************************/
	
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+java.net.URLEncoder.encode(filename + "_" + tm + ".xls","UTF-8"));
		response.addHeader("Content-description", "EventAttendees");
		response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		ServletOutputStream stream = response.getOutputStream();
		wb.write(stream);
		stream.close();
		
		return true;
	}
}
