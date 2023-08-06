package framework.utility.dataParam;

import java.io.File;
import java.util.List;
import java.util.Map;
import framework.utility.globalConst.ConfigInput;

/**
 * @author Prateek Sethi
 *
 *
 */
public class DataSource {
private String dataFile = null;
	
	// The excel sheet containing the data.  
	private String worksheetName = null;
	private static long workBookReadTime= -1;
	
	/**
	 * {
	 * 	worksheetName : {
	 * 		dataNotation : [ {entity}, ...],
	 * 		...
	 * 	},
	 * 	...
	 * }
	 */
	private static Map<String, Map<String, List<Map<String, String>>>> workbookContents = null;
	
	public <T> DataSource(Class<T> klass) {

		worksheetName = klass.getSimpleName();
		try {
			loadWorkBookContents();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public <T> DataSource(String worksheetName) {
		
		this.worksheetName = worksheetName;
		try {
			loadWorkBookContents();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

public List<Map<String, String>> fetchMapList(String dataNotation) {
	return workbookContents.get(worksheetName).get(dataNotation);
}

@SuppressWarnings("unchecked")
private void loadWorkBookContents() throws Exception {
	
	if (workbookContents == null) {
		
		Class<PoiDataProvider> klass = null;
		try {
			klass = (Class<PoiDataProvider>) Class.forName(
					ConfigInput.dataProviderClassName);
		} catch (ClassNotFoundException e) {
			throw new Exception("Could not load " + 
					ConfigInput.dataProviderClassName, e);
		}
		
		try {
			PoiDataProvider provider = klass.newInstance();
			workBookReadTime=System.currentTimeMillis();
			workbookContents = provider.loadContents(new File(dataPath()));
		} catch (Exception e) {
			throw new Exception("Failed to load workbook contents", e);
		} 
	}
}

private String dataPath() {
	
	String dataSourceFile=null;
	try {
		dataSourceFile = getDataFile();
	
	if ( ! (new File(dataSourceFile)).isAbsolute() ) {
		
		String configuredDataDir = getConfiguredDataDir();
		if ( (new File(configuredDataDir)).isAbsolute() ) {
			dataSourceFile = String.join(configuredDataDir,dataSourceFile, File.separator);
		} else {
			String str=String.join(File.separator,configuredDataDir,dataSourceFile);
			System.out.println(str);
			
			dataSourceFile=String.join(File.separator,System.getProperty("user.dir"),"src","main","resources",
					configuredDataDir,dataSourceFile);

			
			System.out.println(dataSourceFile);
		}
	}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return dataSourceFile;
}

private String getDataFile() throws Exception {

	if (dataFile == null) {
		dataFile = ConfigInput.fileName;
		if (dataFile == null) {
			throw new Exception("data.provider.file JVM property is undefined - " +
					"do not know data file");
		}
		if ( !(dataFile.endsWith(".xls")||dataFile.endsWith(".xlsx")) ) {
			dataFile = String.join(
					dataFile, "xls"	
			, "."); 
		}
	}
	return dataFile;
}

private static String getConfiguredDataDir() {
	
	String configuredDir = ConfigInput.dataDir;

	if (configuredDir == null) {
		configuredDir = "TestData";
	}
	
	return configuredDir;
}
	
}
