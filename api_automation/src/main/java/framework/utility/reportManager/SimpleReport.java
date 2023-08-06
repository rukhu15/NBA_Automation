package framework.utility.reportManager;

import framework.entity.ReportObject;
import framework.utility.common.DateAndTime;
import framework.utility.globalConst.FilePath;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.testng.annotations.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleReport {

    @Test
    public void publishReport() throws Exception {
        HashMap<String, List<ReportObject>> reportMap = new HashMap<>();
        List<String> uniqueTestId = new ArrayList<>();
        List<String> uniqueDates = new ArrayList<>();
        List<String> rDirList = new ArrayList<>();
        rDirList.add(FilePath.dirReports);

        for (String rDirPath : rDirList) {
            // get latest directory
            File dir[] = new File(rDirPath).listFiles(File::isDirectory);
            List<File> weekReportDir = new ArrayList<>();
            Date fromDate = new SimpleDateFormat("MM/dd/yyyy").parse(DateAndTime.getDateMMddyyyy(-30));
            for (File rDir : dir) {
                if (rDir.getName().equalsIgnoreCase("suiteRunReport"))
                    continue;

                if (new SimpleDateFormat("yyyy-MM-dd").parse(rDir.getName()).compareTo(fromDate) >= 0)
                    weekReportDir.add(rDir);

                if (weekReportDir.size() > 10)
                    break;
            }

            // now iterate on dir, get all html files, parse them, store in hashmap, create a simple report
            for (File rDir : weekReportDir) {

                File reports[] = rDir.listFiles(File::isFile);
                for (File report : reports) {
                    // get reporter object[][]
                    List<ReportObject> reportObject = getSimpleReportObject(report.getAbsolutePath());
                    // add append to the HashMap
                    for (ReportObject oReport : reportObject) {
                        // add to unique date List
                        if (!uniqueDates.contains(rDir.getName()))
                            uniqueDates.add(rDir.getName());

                        oReport.exeDate = rDir.getName();
                        // add test to unique Test List
                        if (!uniqueTestId.contains(oReport.id))
                            uniqueTestId.add(oReport.id);

                        if (reportMap.keySet().contains(oReport.id)) {
                            // check if date is different
                            boolean uniqueDate = true;
                            for (ReportObject dObj : reportMap.get(oReport.id)) {
                                if (dObj.exeDate.equalsIgnoreCase(oReport.exeDate)) {
                                    uniqueDate = false;
                                    break;
                                }
                            }
                            if (!uniqueDate) {
                                reportMap.get(oReport.id).get(0).status += ">" + oReport.status;
                                reportMap.get(oReport.id).get(0).runCount++;
                            } else {
                                reportMap.get(oReport.id).add(oReport);
                            }

                        } else {
                            List<ReportObject> tList = new ArrayList<>();
                            tList.add(oReport);
                            reportMap.put(oReport.id, tList);
                        }
                    }
                }
            }
        }


        // write the header
        clearSheet(FilePath.fileRegressionData, 0, "Execution");
        clearSheet(FilePath.fileRegressionData, 1, "Summary");
        FileOutputStream fileOut = null;
        InputStream inp = null;

        try {
            inp = new FileInputStream(new File(FilePath.fileRegressionData));
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            sheet.createFreezePane(1, 1);
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 40 * 256);
            sheet.setColumnWidth(3, 45 * 256);

            CellStyle style_1 = wb.createCellStyle();
            style_1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style_1.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style_1.setAlignment(CellStyle.ALIGN_CENTER);
            style_1.setWrapText(true);
            style_1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style_1.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            Font normal = wb.createFont();
            normal.setFontHeightInPoints((short) 9);
            normal.setColor(IndexedColors.BLUE_GREY.getIndex());
            style_1.setFont(normal);

            CellStyle style_2 = wb.createCellStyle();
            style_2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style_2.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style_2.setAlignment(CellStyle.ALIGN_LEFT);
            style_2.setWrapText(true);
            style_2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style_2.setFont(normal);

            CellStyle stylePass = wb.createCellStyle();
            stylePass.setFillPattern(CellStyle.SOLID_FOREGROUND);
            stylePass.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            stylePass.setBorderRight(HSSFCellStyle.BORDER_THIN);
            stylePass.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            stylePass.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            stylePass.setAlignment(CellStyle.ALIGN_CENTER);
            stylePass.setFillForegroundColor(HSSFColor.GREEN.index);
            Font fontPass = wb.createFont();
            fontPass.setColor(HSSFColor.WHITE.index);
            fontPass.setFontHeightInPoints((short) 9);
            stylePass.setFont(fontPass);
            stylePass.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            stylePass.setWrapText(true);

            CellStyle styleFail = wb.createCellStyle();
            styleFail.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleFail.setAlignment(CellStyle.ALIGN_CENTER);
            styleFail.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleFail.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleFail.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            styleFail.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            styleFail.setFillForegroundColor(HSSFColor.RED.index);
            Font fontFail = wb.createFont();
            fontFail.setFontHeightInPoints((short) 9);
            fontFail.setColor(HSSFColor.WHITE.index);
            styleFail.setFont(fontFail);
            styleFail.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleFail.setWrapText(true);

            CellStyle styleOther = wb.createCellStyle();
            styleOther.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleOther.setAlignment(CellStyle.ALIGN_CENTER);
            styleOther.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            styleOther.setBorderRight(HSSFCellStyle.BORDER_THIN);
            styleOther.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            styleOther.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
            styleOther.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            Font fontOther = wb.createFont();
            fontOther.setFontHeightInPoints((short) 9);
            fontOther.setColor(IndexedColors.DARK_BLUE.getIndex());
            styleOther.setFont(fontOther);
            styleOther.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            styleOther.setWrapText(true);

            writeReportDataToExcel(sheet, 0, 0, "#", style_1);
            writeReportDataToExcel(sheet, 0, 1, "SUITE", style_1);
            writeReportDataToExcel(sheet, 0, 2, "TEST ID", style_1);
            writeReportDataToExcel(sheet, 0, 3, "TEST DESCRIPTION", style_1);
            writeReportDataToExcel(sheet, 0, 4, "STABILITY", style_1);
            writeReportDataToExcel(sheet, 0, 5, "AVG EXECUTION", style_1);

            // dashborad object
            HashMap<String, int[]> dMap = new HashMap<>();
            int counter = 6;

            for (String sDate : uniqueDates) {
                if (!dMap.keySet().contains(sDate))
                    dMap.put(sDate, new int[]{0, 0, 0, 0});

                writeReportDataToExcel(sheet, 0, counter, sDate, style_1);
                counter++;
            }

            // write to excel report
            int rowCounter = 1;
            for (String tcId : uniqueTestId) {
                writeReportDataToExcel(sheet, rowCounter, 0, rowCounter + "", style_1);
                List<ReportObject> rObject = reportMap.get(tcId);
                writeReportDataToExcel(sheet, rowCounter, 1, rObject.get(0).suite, style_2);
                writeReportDataToExcel(sheet, rowCounter, 2, tcId, style_2);
                writeReportDataToExcel(sheet, rowCounter, 3, rObject.get(0).desc, style_2);

                // print date wise status
                int columnCounter = 6;
                for (String sDate : uniqueDates) {

                    boolean isFound = false;
                    for (ReportObject rObj : rObject) {
                        if (rObj.exeDate.equalsIgnoreCase(sDate)) {
                            isFound = true;
                            String status = "";
                            if (rObj.status.contains(">"))
                                status = rObj.status.replace(">", "\n");
                            else
                                status = rObj.status;

                            if (status.contains("pass") && status.substring(status.length() - 4).equalsIgnoreCase("pass")) {
                                writeReportDataToExcel(sheet, rowCounter, columnCounter, status, stylePass);
                                dMap.get(sDate)[0] += 1; // increment Pass Counter
                            } else if (status.contains("fail") && status.substring(status.length() - 4).equalsIgnoreCase("fail")) {
                                writeReportDataToExcel(sheet, rowCounter, columnCounter, status, styleFail);
                                dMap.get(sDate)[1] += 1; // increment Pass Counter
                            } else if (status.contains("skip") && status.substring(status.length() - 4).equalsIgnoreCase("skip")) {
                                writeReportDataToExcel(sheet, rowCounter, columnCounter, status, styleOther);
                                dMap.get(sDate)[2] += 1; // increment Pass Counter
                            } else {
                                writeReportDataToExcel(sheet, rowCounter, columnCounter, status, styleOther);
                                dMap.get(sDate)[3] += 1; // increment Other Counter
                            }

                            columnCounter++;
                            break;
                        }
                    }
                    if (!isFound)
                        columnCounter++;
                }

                // get Stability and avg execution time
                String stability = "";
                int executionTime = 0;
                int executionCount = 0;
                for (ReportObject rObj : rObject) {
                    stability += ">" + rObj.status;
                    executionTime += Integer.parseInt(rObj.exeTime);
                    executionCount += rObj.runCount;
                }

                double stabilityPerc;
                if (!stability.contains("pass"))
                    stabilityPerc = 0;
                else {
                    double num = stability.split("pas").length - 1;
                    double denom = executionCount;
                    stabilityPerc = Math.round((num / denom) * 100);
                }

                String avgExecution = "";
                if (executionTime == 0)
                    avgExecution = "0";
                else
                    avgExecution = executionTime / executionCount + "";

                if (stabilityPerc <= 75) {
                    writeReportDataToExcel(sheet, rowCounter, 4, stabilityPerc + "", styleFail);
                } else {
                    writeReportDataToExcel(sheet, rowCounter, 4, stabilityPerc + "", stylePass);
                }

                writeReportDataToExcel(sheet, rowCounter, 5, avgExecution, style_1);

                rowCounter++;

            }


            Sheet sheet1 = wb.getSheetAt(1);
            sheet1.setColumnWidth(0, 25 * 256);
            writeReportDataToExcel(sheet1, 0, 0, "Execution Date", style_1);
            writeReportDataToExcel(sheet1, 0, 1, "PASS", style_1);
            writeReportDataToExcel(sheet1, 0, 2, "FAIL", style_1);
            writeReportDataToExcel(sheet1, 0, 3, "SKIP", style_1);
            writeReportDataToExcel(sheet1, 0, 4, "No RUN", style_1);
            int dCounter = 1;
            for (String sDate : dMap.keySet()) {
                int[] eData = dMap.get(sDate);
                writeReportDataToExcel(sheet1, dCounter, 0, sDate, style_1);
                writeReportDataToExcel(sheet1, dCounter, 1, String.valueOf(eData[0]), stylePass);
                writeReportDataToExcel(sheet1, dCounter, 2, String.valueOf(eData[1]), styleFail);
                writeReportDataToExcel(sheet1, dCounter, 3, String.valueOf(eData[2]), styleOther);
                writeReportDataToExcel(sheet1, dCounter, 4, String.valueOf(eData[3]), styleOther);
                dCounter++;
            }


            fileOut = new FileOutputStream(new File(FilePath.fileRegressionData));
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null)
                fileOut.close();
        }
    }

    private static List<ReportObject> getSimpleReportObject(String filePath) throws IOException {

        List<String> arrStringToExclude = Arrays.asList(
                "setup",
                "teardown",
                "pre-condition",
                "condition",
                "PreRequisite",
                "pre"
        );

        cleanUpSimpleReport(FilePath.fileRegressionData);
        ExtentReader extentReader = new ExtentReader();
        List<ReportObject> reporterData = new ArrayList<>();
        try {
            String FileContent = extentReader.readFile(filePath);
            Document doc = Jsoup.parse(FileContent, "", Parser.xmlParser());


            Iterator var6 = doc.select("li[class^='test displayed active has-leaf']").iterator();
            int counter = 0;

            while (var6.hasNext()) {
                Element e = (Element) var6.next();
                String moduleName = e.select("span[class='test-name']").text();
                boolean noTestFound = true;
                for (Iterator var9 = e.select("div[class='collapsible-header']").iterator(); var9.hasNext(); ++counter) {
                    Element f = (Element) var9.next();

                    String tcId = f.children().get(0).select("h5").text();
                    String exTime = f.children().get(2).select("span").text();
                    String status = f.children().get(3).select("span").text();
                    String desc = f.children().get(4).select("div").text();

                    ReportObject obj = new ReportObject(moduleName, tcId, desc, status, "10", null);
                    reporterData.add(obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reporterData;

    }

    private static int getTimeInSeconds(String strTime) {

        //0h 0m 18s+557ms
        if (strTime.indexOf("s+") > 0) {
            String[] arrTime = strTime.split(" ");
            int timeh = Integer.parseInt(arrTime[0].replaceAll("[^0-9 ]", "").replaceAll(" +", " ").trim()) * 360;
            int timem = Integer.parseInt(arrTime[1].replaceAll("[^0-9 ]", "").replaceAll(" +", " ").trim()) * 60;
            int times = Integer.parseInt(arrTime[2].split("s+")[0]);
            return timeh + timem + times;
        }
        return 0;
    }

    private static void cleanUpSimpleReport(String filename) throws IOException {
        InputStream inp = null;
        FileOutputStream fileOut = null;
        try {
            inp = new FileInputStream(filename);
            XSSFWorkbook wbx = new XSSFWorkbook(inp);
            XSSFSheet sheet = wbx.getSheetAt(0);
            int lastRowUsed = sheet.getLastRowNum() + 1;

            for (int i = 9; i <= lastRowUsed; i++) { // skip the report details

                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            fileOut = new FileOutputStream(filename);
            wbx.write(fileOut);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inp != null) inp.close();
            if (fileOut != null) fileOut.close();
        }
    }

    public static void writeReportDataToExcel(Sheet sheet, int iRow, int iColumn, String text, CellStyle style) {
        Row row = sheet.getRow(iRow);
        if (row == null) {
            sheet.createRow(iRow);
        }

        Cell cell = sheet.getRow(iRow).getCell(iColumn);
        if (cell == null) {
            sheet.getRow(iRow).createCell(iColumn);
            cell = sheet.getRow(iRow).getCell(iColumn);
            cell.setCellStyle(style);
        }

        cell.setCellValue("" + text);
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
}
