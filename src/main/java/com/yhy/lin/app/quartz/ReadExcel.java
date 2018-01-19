package com.yhy.lin.app.quartz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import jodd.util.StringUtil;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2018年18号 8:01:45
 */
public class ReadExcel {

	static int rNum = 0; // 行
	static int cNum = -1; // 列

	static boolean isNextRol = false;

	static String path = "C:\\Users\\Administrator\\Desktop\\a.xlsx";
	static String wPath = "C:\\Users\\Administrator\\Desktop\\b.xlsx";

	static InputStream wInputStream = null;
	static Workbook wWorkbook = null;

	public static void main(String[] args) throws Exception {
		p();
		// System.out.println("（)".indexOf("（"));
	}

	public static void p() throws Exception {

		openInput();

		InputStream inputStream = new FileInputStream(path);
		// InputStream inp = new
		// FileInputStream("C:/Users/H__D/Desktop/workbook.xls");

		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter formatter = new DataFormatter();
		for (Row row : sheet) {
			cNum = -1;
			if (isNextRol) {
				rNum++;
			}
			isNextRol = false;
			for (Cell cell : row) {
				if (rNum == 0) {
					rNum++;
					break;
				}

				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
				// 单元格名称
				String cellName = cellRef.formatAsString();

				if (cellName.contains("C") || cellName.contains("D") || cellName.contains("E")) {
					cNum++;
				} else {
					continue;
				}

				System.out.print(cellRef.formatAsString());
				System.out.print(" - ");

				// 通过获取单元格值并应用任何数据格式（Date，0.00，1.23e9，$ 1.23等），获取单元格中显示的文本
				String text = formatter.formatCellValue(cell);
				System.out.println(text);

				String v = getCellValue(cell);

				System.out.println("row:" + rNum + "  col:" + cNum);

				boolean a = false;
				
				String[] sArr = null;
				if (cellName.contains("D") || cellName.contains("E")) {
				} else if (compareVal(v, "（", 1)) {
					sArr = parse1(v);
					a = true;
				} else if (compareVal(v, "收", 1) && (compareVal(v, "（", 2))) {
					sArr = parse2(v);
					a = true;
				} else if (compareVal(v, "收", 1) && !isNumber(v) && v.indexOf("（") != -1) {
					sArr = parse3(v);
					a = true;
				}

				if (cellName.contains("C")) {
					if(a){
						for (int i = 0; i < sArr.length; i++) {
							modifyCell(sArr[i], rNum, cNum + i);
						}
					} else {
						modifyCell(v, rNum, cNum);
					}
					
				} else if (cellName.contains("D") || cellName.contains("E")) {
					if (cellName.contains("D")) {
						modifyCell(v, rNum, cNum + 6);
					} else {
						modifyCell(v, rNum, cNum + 7);
					}
				}
				a = false;
				isNextRol = true;
			}
		}
		wr(wWorkbook);
	}

	private static boolean isNumber(String v) {
		String str = v.substring(v.indexOf("（") + 2, v.indexOf("（") + 3);
		try {
			int num = Integer.valueOf(str);// 把字符串强制转换为数字
			return true;// 如果是数字，返回True
		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}
	
	public static boolean compareVal(String val, String sVal, int index) {
		if (!StringUtil.isNotEmpty(val) || val.indexOf(sVal) == -1) {
			return false;
		}
		if (val.indexOf(sVal) <= index) {
			return true;
		}
		return false;
	}

	public static String getCellValue(Cell cell) throws Exception {
		String v = "";
		// 获取值并自己格式化
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:// 字符串型
			v = cell.getRichStringCellValue().getString();
			System.out.println(v);
			break;
		case Cell.CELL_TYPE_NUMERIC:// 数值型
			if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
				// ，获取该cell的date值
				v = cell.getDateCellValue() + "";
				System.out.println(v);
			} else {
				// 纯数字
				v = cell.getNumericCellValue() + "";
				System.out.println(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:// 布尔
			v = cell.getBooleanCellValue() + "";
			System.out.println(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:// 公式型
			v = cell.getCellFormula() + "";
			System.out.println(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BLANK:// 空值
			System.out.println();
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			System.out.println();
			break;
		default:
			System.out.println();
		}
		return v;
	}

	public static void modifyCell(String val, int rNumt, int cNumt) throws Exception {
		// InputStream inp = new FileInputStream("workbook.xlsx");

		Sheet sheet = wWorkbook.getSheetAt(1);

		Row row = sheet.getRow(rNumt);
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(cNumt);
		} else {
			row = sheet.createRow(rNumt);
			cell = row.createCell(cNumt);
		}

		if (cell == null) {
			cell = row.createCell(cNumt);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(val);

	}

	public static String[] parse1(String val) {
		String name1 = "";
		String name2 = "";
		int a = val.indexOf("（");
		int b = val.indexOf("）");

		if (a > b) {
			a = val.indexOf("（");
		}

		try {
			name1 = val.substring(a + 1, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int c = val.indexOf("收");
		if (c == -1) {
			c = val.indexOf("）");
		}

		int d = val.indexOf("保证金", a + 1);
		if (d == -1) {
			d = val.indexOf("（", a + 1);
			if (d == -1) {
				d = val.indexOf(" ", a + 1);
			}
		}
		// int d = val.indexOf("(") == -1 ? val.indexOf("（") :
		// val.indexOf("(");
		try {
			name2 = val.substring(c + 1, d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String[] { name1, name2 };
	}

	public static String[] parse2(String val) {
		String name1 = "";
		String name2 = "";
		int a = val.indexOf("（");
		int b = val.indexOf("）");
		name1 = val.substring(a + 1, b);

		int c = val.indexOf("）");

		int d = val.indexOf("保证金", a + 1);
		if (d == -1) {
			d = val.indexOf("（", a + 1);
			if (d == -1) {
				d = val.indexOf(" ", a + 1);
			}
		}
		// int d = val.indexOf("(") == -1 ? val.indexOf("（") :
		// val.indexOf("(");
		try {
			name2 = val.substring(c + 1, d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String[] { name1, name2 };
	}
	
	public static String[] parse3(String val) {
		String name1 = "";
		String name2 = "";
		int a = val.indexOf("（");
		int b = val.indexOf("）");
		name2 = val.substring(a + 1, b);

		int c = val.indexOf("收");
		try {
			name1 = val.substring(c + 1, b);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String[] { name1, name2 };
	}

	public static void openInput() throws Exception {
		wInputStream = new FileInputStream(wPath);
		wWorkbook = WorkbookFactory.create(wInputStream);
	}

	public static void wr(Workbook workbook) throws Exception {
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(wPath);
		workbook.write(fileOut);
		fileOut.close();
	}

}
