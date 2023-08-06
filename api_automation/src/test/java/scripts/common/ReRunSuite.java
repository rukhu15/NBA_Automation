package scripts.common;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import framework.utility.common.Utils;
import framework.utility.globalConst.FilePath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import scripts.TestInit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReRunSuite extends TestInit {

    /**
     * Get CCLog.csv data
     *
     * @return
     */
    private static HashMap<String, List<String>> getCurrentTestStatusCsv() {

        HashMap<String, List<String>> map = new HashMap<>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(FilePath.fileCurrentExecutionLog);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {
                String suite = row[1];
                String test = row[2];
                String dependentTest = row[3];
                if (map.containsKey(suite) && !map.get(suite).contains(test)) {
                    map.get(suite).add(test);
                } else {
                    List<String> tests = new ArrayList<>();
                    tests.add(test);
                    map.put(suite, tests);
                }
                if (!dependentTest.equals("") && !map.get(suite).contains(dependentTest)) {
                    map.get(suite).add(dependentTest);
                }
            }

            // Clear Log File
            FileWriter fw = new FileWriter(FilePath.fileCurrentExecutionLog, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
            System.out.println("CCLog.csv is cleared for next execution, please find the archive data under DailyLog > ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // can add more groups based on the xml been executed with groups.
  /*  @Test(priority = 151, groups = {TestingTag.SANITY}, dependsOnMethods = "initRerun")
    public void checkFailedSuite() {
        createReRunSuite(suiteName);
    }
*/

    /**
     * Create Re Run suite
     *
     * @param suiteFileName
     */
    public static void createReRunSuite(String suiteFileName) {
        String xmlFilePath = FilePath.dirFailSuitesPath + suiteFileName + "_FAIL.xml";

        try {

            // get the CClog.csv data
            HashMap<String, List<String>> map = getCurrentTestStatusCsv();

            // structure the xml file
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element suite = document.createElement("suite");
            document.appendChild(suite);
            suite.setAttribute("name", suiteFileName + "_FAIL");
            suite.setAttribute("verbose", "1");
            suite.setAttribute("configfailurepolicy", "continue");

            Element test = document.createElement("test");
            suite.appendChild(test);
            test.setAttribute("name", suiteFileName + "_FAIL");
            test.setAttribute("preserve-order", "true");
            test.setAttribute("configfailurepolicy", "continue");

            Element classes = document.createElement("classes");
            test.appendChild(classes);

            for (String suiteName : map.keySet()) {
                if (suiteName.equalsIgnoreCase("scripts.baseScripts.SIQBaseSetup"))
                    continue;

                Element nClass = document.createElement("class");
                classes.appendChild(nClass);
                nClass.setAttribute("name", suiteName);

                Element nMethod = document.createElement("methods");
                nClass.appendChild(nMethod);

                List<String> tests = map.get(suiteName);

                for (String testName : tests) {
                    Element nTest = document.createElement("include");
                    nMethod.appendChild(nTest);
                    nTest.setAttribute("name", testName);
                }

            }

            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File/ deleting the Current Execution Log File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } finally {
            Utils.clearCsv(FilePath.fileCurrentExecutionLog);
        }
    }
}
