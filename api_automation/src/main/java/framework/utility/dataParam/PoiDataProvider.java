package framework.utility.dataParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Prateek Sethi
 *
 *
 */
public class PoiDataProvider {
	
	private Map<String, Map<String, List<Map<String, String>>>> contents = 
			new HashMap<String, Map<String, List<Map<String, String>>>>();
	
	private FormulaEvaluator formulaEv = null;
	
	public Map<String, Map<String, List<Map<String, String>>>> loadContents(
			File dataFile) throws Exception {
		
		try {
			
			InputStream dataIn = new FileInputStream(dataFile);
			Workbook dataWorkbook = WorkbookFactory.create(dataIn);
			formulaEv = dataWorkbook.getCreationHelper().createFormulaEvaluator();
			
			for (int i = 0; i < dataWorkbook.getNumberOfSheets(); i++) {
				Sheet sheet = dataWorkbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				System.out.println("Processing Sheet: " + sheetName);
				
				// construct the attributes array
				// the first row contains attributes except the first column
				// which is ignored (it's the header for the notations)
				int columnCount = 0;
				List<String> attributes = new ArrayList<String>();
				for (Iterator<Cell> ite = sheet.getRow(0).cellIterator(); 
															ite.hasNext();) {
					Cell cell = ite.next();
					if (Cell.CELL_TYPE_BLANK != cell.getCellType()) {
						attributes.add(cell.getStringCellValue());
						++columnCount;
					}
				}
				attributes.remove(0);
				
				// fetch the unique notations used in the sheet
				// starting from second row
				List<String> notations = new ArrayList<String>();
				boolean skipFirstRow = true;
				for (Row row : sheet) {
					if (skipFirstRow) { skipFirstRow = false; continue; }
					Cell cell = row.getCell(0);
					if (cell != null) {
						String notation = cell.getStringCellValue();
						if (notation != null && !notation.isEmpty()) {
							if (! notations.contains(notation)) {
								notations.add(notation);
							}
						}
					}
				}

				// fetch the data into contents
				Map<String, List<Map<String, String>>> notationEntities =
					new HashMap<String, List<Map<String,String>>>();
				for (String notation : notations) {
					List<Map<String, String>> entities = null;
					if (notationEntities.containsKey(notation)) {
						entities = notationEntities.get(notation);
					} else {
						entities = new ArrayList<Map<String, String>>();
						notationEntities.put(notation, entities);
					}
					skipFirstRow = true;
					for (Row row : sheet) {
						if (skipFirstRow) { skipFirstRow = false; continue; }
						List<Cell> data = new ArrayList<Cell>();
						// warning: do not engineer with iterator: can't be 
						// used since it skips empty cells
						for (int j = 0; j < columnCount; j++) {
							Cell cell = row.getCell(j);
							data.add(cell);
						}
						if (data.size() > 0) {
							Map<String, String> fixture = 
								new HashMap<String, String>();
							Cell notationCell = data.get(0);
							if (notationCell != null) {
								String cellNotation = notationCell
													.getStringCellValue();
								if (notation.equals(cellNotation)) {
									for (int j = 1; j < data.size(); j++) {
										String fKey = attributes.get(j - 1);
										String fValue = getCellStringValue(
												data.get(j));
										fixture.put(fKey, fValue);
									}
									entities.add(fixture);
								}	
							}
						}
					}
				}
				
				contents.put(sheetName, notationEntities);
			}
			
			dataIn.close();
			
		} catch (Exception e) {
			
			throw new Exception(e);
		}
		
		return contents;
	}
	
private String getCellStringValue(Cell cell) throws Exception {
		
		String fValue = null;
		CellValue cValue = formulaEv.evaluate(cell);
		
		if (cValue != null) {
			switch (cValue.getCellType()) {
			
			case Cell.CELL_TYPE_ERROR:
				throw new Exception(String.format(
						"Error cell found - Sheet: %s, Row:%d, Column:%d, Code: %d", 
						cell.getSheet().getSheetName(),
						cell.getRowIndex() + 1, cell.getColumnIndex() + 1,
						cell.getErrorCellValue()));
				
			case Cell.CELL_TYPE_STRING:
				fValue = cValue.getStringValue();
				break;
				
			case Cell.CELL_TYPE_NUMERIC:
				String dataFmt = cell.getCellStyle().getDataFormatString();
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double dv = cValue.getNumberValue();
					Date date = HSSFDateUtil.getJavaDate(dv);
					fValue = new CellDateFormatter(dataFmt).format(date);
				} else {
					CellFormat cf = CellFormat.getInstance(dataFmt);
					fValue = cf.apply(cell).text;
				}
				break;
			
			case Cell.CELL_TYPE_BOOLEAN:
				fValue = new Boolean(cValue.getBooleanValue()).toString();
				break;
			
			default:
				fValue = cValue.getStringValue();
				break;
			}	
		}
		
		if (fValue == null) {
			fValue = "";
		} else {
			fValue = fValue.trim();
		}
		
		return fValue;
	}

}
