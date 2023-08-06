package framework.utility.common;

import framework.utility.globalConst.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * Get Table as Array - use this method for creating a Data Provider
     *
     * @param FilePath  - TestData file path
     * @param sheetName - 0 for Sheet 1
     * @return
     * @throws Exception
     */
    public static Object[][] getTableArray(String FilePath, int sheetName) throws Exception {
        DataFormatter formatter = new DataFormatter();
        String[][] tabArray = null;

        try {

            FileInputStream inp = new FileInputStream(FilePath);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(sheetName);
            int startRow = 1;
            int startCol = 0;
            int ci, cj;

            // get total roles
            int totalRows = sheet.getLastRowNum();
            Row r = sheet.getRow(1);

            if (r == null) {
                logger.info("The target Excel Sheet is Empty");
                return null;
            }

            // get total number of columns
            int totalCols = r.getLastCellNum();

            tabArray = new String[totalRows][totalCols];
            ci = 0;
            for (int i = startRow; i <= totalRows; i++, ci++) {
                cj = 0;
                for (int j = startCol; j < totalCols; j++, cj++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    if (cell == null) {
                        tabArray[ci][cj] = "";
                    } else {
                        tabArray[ci][cj] = formatter.formatCellValue(cell);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Could not read the Excel sheet");
            e.printStackTrace();
        }

        return (tabArray);
    }

    /**
     * Delete a Excel FIle
     *
     * @param filePath
     */
    public static void deleteExcel(String filePath) {

        File file = new File(filePath);
        if (file.delete()) {
            logger.info(file.getName() + " is deleted!");
        } else {
            logger.info("Delete operation is failed.");
        }

    }

    public static void clearSheet(String fileName, int sheetIndex, String newSheetName) throws Exception {
        FileInputStream inp = new FileInputStream(new File(fileName));
        Workbook wb = WorkbookFactory.create(inp);
        wb.removeSheetAt(sheetIndex);
        wb.createSheet(newSheetName);
        wb.setSheetOrder(newSheetName, sheetIndex);
        FileOutputStream output = new FileOutputStream(new File(fileName));
        wb.write(output);
        output.close();
    }


    private static final String[] salesSQLHeader = {
            "GEO_CODE",
            "CHANNEL",
            "SALESFORCE",
            "PRODUCT",
            "UNITDEF",
            "MARKETDEF",
            "DATA_PERIOD",
            "Sales",
            "COUNTRY_CODE",
            "Organization",
            "FIELD2",
            "FIELD3",
            "VALUE1",
            "VALUE2"
    };

    public static void writeSalesSQLFileHeader(String fileName) throws IOException {
        writeHeader(fileName, salesSQLHeader);
    }

    /**
     * Write Header
     *
     * @param filePath   : Path to the file
     * @param headerList List of the header of the File
     */
    private static void writeHeader(String filePath, String[] headerList) throws IOException {
        FileOutputStream fileOut = null;
        InputStream inpx = null;
        try {
            clearSheet(filePath, 0, "salesSQL");
            logger.info("Writing file headers " + filePath);
            inpx = new FileInputStream(new File(filePath));
            XSSFWorkbook wbx = new XSSFWorkbook(inpx);
            XSSFSheet sheet = wbx.getSheetAt(0);

            // create row
            XSSFRow row = sheet.getRow(0);
            if (row == null) {
                sheet.createRow(0);
                row = sheet.getRow(0);
            }

            for (int i = 0; i < headerList.length; i++) {
                row.createCell(i);
                XSSFCell cell = row.getCell(i);
                cell.setCellValue(headerList[i].trim() + "");
            }

            fileOut = new FileOutputStream(new File(filePath));
            wbx.write(fileOut);
            Thread.sleep(250);
        } catch (Exception e) {
            Assertion.markAsFailure("Failed to write Header for file: " + filePath);
            e.printStackTrace();
        } finally {
            if (fileOut != null)
                fileOut.close();

            if (inpx != null)
                inpx.close();

        }
    }

    /**
     * Write Data to Excel
     *
     * @param filePath
     * @param iColumn
     * @param value
     * @throws IOException
     */
    public static void writeDataToExcel(String filePath, int iRow, int iColumn, String value) throws IOException {
        FileOutputStream fileOut = null;
        FileInputStream inpx = null;
        try {
            inpx = new FileInputStream(new File(filePath));
            XSSFWorkbook wbx = new XSSFWorkbook(inpx);
            XSSFSheet sheet = wbx.getSheetAt(0);

            XSSFRow row = sheet.getRow(iRow);
            if (row == null)
                sheet.createRow(iRow);

            row = sheet.getRow(iRow);
            row.createCell(iColumn);
            XSSFCell cell = row.getCell(iColumn);
            cell.setCellValue("" + value);

            fileOut = new FileOutputStream(new File(filePath));
            wbx.write(fileOut);

        } catch (Exception ex) {
            Assertion.markAsFailure("Failed to write data");
            ex.printStackTrace();
        } finally {
            if (fileOut != null)
                fileOut.close();

            if (inpx != null)
                inpx.close();
        }
    }


    public static boolean deleteFilesForPathByPrefix(final String path, final String prefix) {
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), prefix + "*")) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                Files.delete(newDirectoryStreamItem);
            }
            Utils.putThreadSleep(Constants.MAX_WAIT_TIME);
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getExcelLastRow(String fileName, int... index) throws IOException {
        int sheetId = (index.length > 0) ? index[0] : 0;
        InputStream inp = null;
        try {
            int val = 0;
            inp = new FileInputStream(fileName);
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(sheetId);
            int maxRowNo = sheet.getLastRowNum();
            val = maxRowNo;
            return val + 1;
        } catch (InvalidFormatException ex) {
            return 0;
        } catch (IOException ex) {
            return 0;
        } finally {
            inp.close();
        }
    }

}