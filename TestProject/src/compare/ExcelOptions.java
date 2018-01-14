package compare;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import entity.CompareField;

public class ExcelOptions {
	//2222
	private static String[] fields = null;
	
	private static boolean isSuccessfuly = false;

	public static void main(String[] input) {
		System.out.println(input.length);
		for (int i = 0; i < input.length; i++) {
			System.out.println(input[i]);
		}
		
		if(input == null || input.length < 3){
			System.out.println("您输入的参数数量错误，输入的excel，对比的excel，输出的excel路径");
		}
		
		String inputFilePath = input[0];
		String compareFilePath = input[1];
		String fieldSpild = input[2];
		if(fieldSpild != null){
			fields = fieldSpild.split(",");
		}
		
		String inputfileType = inputFilePath.substring(inputFilePath.lastIndexOf(".")+1);
		String inputfileWithOutType = inputFilePath.substring(0,inputFilePath.lastIndexOf("."));
		String comparefileType = compareFilePath.substring(compareFilePath.lastIndexOf(".")+1);
		String comparefileWithOutType = compareFilePath.substring(0,compareFilePath.lastIndexOf("."));
		
		//init workbook
		InputStream inputStream = null;
		//InputStream compareStream = null;
		OutputStream outputStream = null;
		OutputStream compareOutStream = null;
		Workbook inputWorkbook = null;
		Workbook compareWorkbook = null;
		try {
			
			inputStream = new FileInputStream(inputFilePath);
			//compareStream = new FileInputStream(compareFilePath);
			
			inputWorkbook = initWorkbook(inputfileType,inputStream);
			inputStream = new FileInputStream(compareFilePath);
			compareWorkbook = initWorkbook(comparefileType,inputStream);
			
		
			if(inputWorkbook == null || compareWorkbook == null){
				System.out.println("inputWorkbook or compareWorkbook is null ");
				return;
			}
			
			//processing compare generate output excel
			processingCompare(inputWorkbook,compareWorkbook);
			
			if(isSuccessfuly){
				outputStream = new FileOutputStream(inputfileWithOutType+"_gen."+inputfileType);
				//compareOutStream = new FileOutputStream(compareFilePath);
				inputWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				outputStream = new FileOutputStream(comparefileWithOutType+"_gen."+comparefileType);
				compareWorkbook.write(outputStream);
				outputStream.flush();
				outputStream.close();
				//compareOutStream.flush();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("throw error,message is " + e.getMessage() +" . return function.");
			isSuccessfuly = false;
			return;
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("throw error,message is " + e.getMessage() +" . return function.");
			isSuccessfuly = false;
			return;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("throw error,message is " + e.getMessage() +" . return function.");
			isSuccessfuly = false;
			return;
		}finally{
			if(isSuccessfuly){
				try {
					outputStream.close();
					//compareOutStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	private static Workbook initWorkbook(String fileType, InputStream is) throws IOException{
		if(is == null || fileType == null || fileType.equals("")) return null;
		Workbook workbook = null;
		if(fileType.equalsIgnoreCase("xlsx")){
			workbook = new XSSFWorkbook(is);
		}else if(fileType.equalsIgnoreCase("xls")){
			workbook = new HSSFWorkbook(is);
		}else{
			System.out.println("can not enter without xlsx and xls filePath");
		}
		
		return workbook;
	}
	
	/**
	 * processing logic
	 * 处理excel 对比数据
	 * 
	 */
	
	private static void processingCompare(Workbook inputWorkbook,Workbook compareWorkbook){
		if(inputWorkbook == null || compareWorkbook == null) return;
		Sheet inputSheet = inputWorkbook.getSheetAt(0);
		Sheet compareSheet = compareWorkbook.getSheetAt(0);
		
		Map<String,List<CompareField[]>> inputFieldListMap = getCellsFormat(inputSheet);
		Map<String,List<CompareField[]>> compareFieldListMap = getCellsFormat(compareSheet);
		
		List<CompareField[]> inputFieldList = null;
		List<CompareField[]> compareFieldList = null;
		boolean isDifferent = false;
		//List<Integer> 
		
		for(String key : inputFieldListMap.keySet()){
			inputFieldList = inputFieldListMap.get(key);
			
			for(CompareField[] inputRowFields : inputFieldList){
				//int inRow =0;
				boolean[] compareResult = null;
				boolean[] destResult = null;
				int[] destRowCount = new int[2];
				isDifferent = false;
				int j = 0;
				if(compareFieldListMap.containsKey(key)){
					compareFieldList = compareFieldListMap.get(key);
					for(Iterator<CompareField[]> it = compareFieldList.iterator(); it.hasNext();){
						CompareField[] compareRowFields = it.next();
						
						if(Arrays.equals(inputRowFields, compareRowFields)){
							//set ok
							for(int i = 0;i < inputRowFields.length ;i ++){
								CellStyle style = inputWorkbook.createCellStyle();
								CellStyle style1 = compareWorkbook.createCellStyle();
								//set it is ok
								setCellStyle(inputSheet,inputRowFields[i],style,1,false,null);
								setCellStyle(compareSheet,compareRowFields[i],style1,1,false,null);
							}
							
							//boolean isOk = true;
							//compare all fields
							/**/
							//test
							//remove
							it.remove();
							
							//the last element need remove map key
							if(compareFieldList.size() == 0)
								compareFieldListMap.remove(key);
							//compareFieldList.remove(compareRowFields);
							isDifferent = false;
							j++;
							break;
							
						}else{
							
							isDifferent = true;
							int count = 0;
							compareResult = new boolean[fields.length];
							for(int i = 0;i < inputRowFields.length ;i ++){
								if(inputRowFields[i].getField() != null && compareRowFields[i].getField() != null &&
										inputRowFields[i].getField().equals(compareRowFields[i].getField())){
									compareResult[i] = true;
								}else{
									compareResult[i] = false;
									count ++;
								}
							}
							
							
							if(destResult == null || count < destRowCount[1]){
								destResult = compareResult;
								destRowCount[0] = j;
								destRowCount[1] = count;
								
							}
							
							
							if(j+1 == compareFieldList.size()){
								boolean isFirst = true;
								for(int i = 0;i < inputRowFields.length ;i ++){
									CellStyle style = inputWorkbook.createCellStyle();
									CellStyle style1 = compareWorkbook.createCellStyle();
									compareRowFields =  compareFieldList.get(destRowCount[0]);
									if(destResult[i] == true){
										//set it is ok
										setCellStyle(inputSheet,inputRowFields[i],style,1,false,null);
										setCellStyle(compareSheet,compareRowFields[i],style1,1,false,null);
									}else{
										//set error
										if(isFirst){
											isFirst = false;
											setCellStyle(inputSheet,inputRowFields[i],style,2,true,compareRowFields[0].getDestX());
											setCellStyle(compareSheet,compareRowFields[i],style1,2,true,inputRowFields[0].getDestX());
										}else{
											setCellStyle(inputSheet,inputRowFields[i],style,2,false,null);
											setCellStyle(compareSheet,compareRowFields[i],style1,2,false,null);
										}
										
									}
								}
								
							}
							
							
						}
						j++;
						
					}
				
					if(isDifferent){
						//remove
						compareFieldList.remove(compareFieldList.get(destRowCount[0]));
						//the last element need remove map key
						if(compareFieldList.size() == 0)
							compareFieldListMap.remove(key);
					}
				
				}else{
					//fill cell 没有匹配项 设置黄灯
					boolean flag = true;
					for(CompareField field : inputRowFields){
						CellStyle style = inputWorkbook.createCellStyle();
						//warning
						setCellStyle(inputSheet,field,style,6,flag,null);
						flag = false;
					}
					/*for(CompareField[] compareRowFields : compareFieldList){
						for(CompareField field : compareRowFields){
							CellStyle style = compareWorkbook.createCellStyle();
							//warning
							setCellStyle(compareSheet,field,style,6);
						}
					}*/
				}
				
				
			}
			
		}
		
		
		//
		if(compareFieldListMap != null && !compareFieldListMap.isEmpty()){
			for(String key : compareFieldListMap.keySet()){
				if(compareFieldListMap.get(key) == null) continue;
				compareFieldList = compareFieldListMap.get(key);
				
				for(CompareField[] compareRowFields : compareFieldList){
					boolean flag = true;
					for(CompareField field : compareRowFields){
						CellStyle style = compareWorkbook.createCellStyle();
						//warning
						setCellStyle(compareSheet,field,style,6,flag,null);
						flag = false;
					}
				}
			}
		}
		isSuccessfuly = true;
		/*try {
			((SXSSFSheet) inputSheet).flushRows();
			((SXSSFSheet) compareSheet).flushRows();
		} catch (IOException e) {
			e.printStackTrace();
			isSuccessfuly = false;
		}*/
		
	}
	
	
	
	private static void setCellStyle(Sheet sheet,CompareField field,CellStyle style,int flag,boolean isCreatRow,Integer setRow){
		if(sheet == null || field == null || style == null) return;
		
		Row row = sheet.getRow(field.getDestX());
		if(row == null) return;
		Cell cell = row.getCell(field.getDestY());
		if(cell == null) return;
		if(isCreatRow){
			if(setRow != null)
				//最末尾添加对比的row信息
				row.createCell(row.getLastCellNum() + 1).setCellValue("比对第" + (setRow+1) +"行");
			else
				//最末尾添加对比的row信息
				row.createCell(row.getLastCellNum() + 1).setCellValue("没有更多匹配项");
		}
			
		
		
		if(flag == 1){
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
		}else if(flag == 2){
			style.setFillForegroundColor(IndexedColors.RED.index);
		}else{
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		}
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		
	}
	
	/**
	 * get cell list
	 * 
	 */
	
	private static Map<String,List<CompareField[]>> getCellsFormat(Sheet sheet){
		Map<String,List<CompareField[]>> keyRowFieldMap = new HashMap<String,List<CompareField[]>>();
		
		if(sheet == null || sheet.getLastRowNum() < 1) return keyRowFieldMap;
		//get first title
		Row row = sheet.getRow(sheet.getFirstRowNum());
		Cell cell = null;
		/*int destGoodsNoCellColumn = -1;
		int destCostPriceCellColumn = -1;
		int destMarketPriceCellColumn = -1;
		int destSellPriceCellColumn = -1;
		int destNumberCellColumn = -1;
		int destStatusCellColumn = -1;
		int destOrderNoCellColumn = -1;*/
		Map<String,Integer> columnMap =  new HashMap<String,Integer>();
		//find dest cell
		for(int i = 0; i < row.getLastCellNum(); i ++){
			cell = row.getCell(i);
			if(cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING){
				for(String field: fields){
					if(cell.getStringCellValue().trim().equalsIgnoreCase(field)){
						columnMap.put(field, cell.getColumnIndex());
					}
				}
				/*if(cell.getStringCellValue().trim().equalsIgnoreCase(goodsNoT)){
					//set dest cell
					destGoodsNoCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(costPriceT)){
					destCostPriceCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(marketPriceT)){
					destMarketPriceCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(sellPriceT)){
					destSellPriceCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(numberT)){
					destNumberCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(statusT)){
					destStatusCellColumn = i;
				}else if(cell.getStringCellValue().trim().equalsIgnoreCase(orderNoT)){
					destOrderNoCellColumn = i;
				}*/
			}
		}
		CompareField[] rowField = null;
		List<CompareField[]> rowFileds = null;
		CompareField field = null;
		
		//j=1 no need compare
		for(int j = 1; j < sheet.getLastRowNum()+1; j++){
			rowField = new CompareField[fields.length];
			
			row = sheet.getRow(j);
			if(row == null) continue;
			//get dest cell value add to list
			for(int k =0; k < fields.length; k++){
				if(!columnMap.containsKey(fields[k])){
					//the first field is key ,cannot to be null
					if(k == 0){
						break;
					}else{
						continue;
					}
				}
				field = new CompareField();
				cell = row.getCell(columnMap.get(fields[k]));
				try{
					if(cell != null){
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							field.setField(new BigDecimal(cell.getNumericCellValue()+"").setScale(4,   BigDecimal.ROUND_HALF_UP));
						}else{
							field.setField(cell.getStringCellValue());
						}
					}else{
						if(k == 0) break;
						field.setField(null);
					}
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("get cell value error, message is : "+ e.getMessage());
					//the first field is key ,cannot to be null
					if(k == 0){
						break;
						
					}else{
						field.setField(null);
						//continue;
					}
				}
				field.setDestX(j);
				field.setDestY(columnMap.get(fields[k]));
				field.setKey(fields[k]);
				rowField[k] = field;
			}
			
			if(rowField[0] != null && rowField[0].getField() != null && rowField[0].getField().toString() != "" ){
				if(keyRowFieldMap.containsKey(rowField[0].getField().toString())){
					keyRowFieldMap.get(rowField[0].getField().toString()).add(rowField);
					//cellListMap.get(orderNo).add(tradedGoods);
				}else{
					rowFileds = new LinkedList<CompareField[]>();
					rowFileds.add(rowField);
					keyRowFieldMap.put(rowField[0].getField().toString(), rowFileds);
				}
			}
			
			
		}
		
		return keyRowFieldMap;
	} 
	
}
