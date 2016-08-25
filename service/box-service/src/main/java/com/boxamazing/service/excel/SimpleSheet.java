package com.boxamazing.service.excel;

import java.util.Vector;

/**
 * @类功能说明：简单sheet实体,继成抽象出来的sheet
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @作者：He Chaojie
 * @创建时间：2013-8-2 上午10:50:43
 * @版本：V1.0
 */
public class SimpleSheet extends AbstractSheet {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	private Integer rowCount;
	private Integer colCount;
	private String[] rowNames;
	private String[] colNames;
	private Vector<String[]> datas;
	private String sheetName;
	private String excelName;

	public SimpleSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @类名：SimpleExcel.java
	 * @描述：列名，数据集合
	 * @param colNames
	 * @param datas
	 */
	public SimpleSheet(String[] colNames, Vector<String[]> datas) {
		super();
		this.colNames = colNames;
		this.datas = datas;
	}

	/**
	 * @类名：SimpleExcel.java
	 * @描述： 列名，行名，数据集合
	 * @param colNames
	 * @param rowNames
	 * @param datas
	 */
	public SimpleSheet(String[] colNames, String[] rowNames,
			Vector<String[]> datas) {
		super();
		this.rowNames = rowNames;
		this.colNames = colNames;
		this.datas = datas;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getColCount() {

		return colCount;
	}

	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}

	public String[] getRowNames() {
		return rowNames;
	}

	public void setRowNames(String[] rowNames) {
		this.rowNames = rowNames;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public Vector<String[]> getDatas() {
		return datas;
	}

	public void setDatas(Vector<String[]> datas) {
		this.datas = datas;
	}
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

}
