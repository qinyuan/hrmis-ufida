package qinyuan.lib.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is class is used to read properties file
 * 
 * @author Administrator
 * 
 */
public class PFile {

	private static final String EXTEND_NAME = ".properties";

	private PFile() {
	}

	public static Map<String, String> parse(String fileName) {
		return parse(fileName, null);
	}

	public static Map<String, String> parse(String fileName,
			List<String> keyList) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (String line : readPFileAsStrArr(fileName)) {
				int splitIndex = line.indexOf('=');
				if (splitIndex > 0 && splitIndex < line.length() - 1) {
					String key = line.substring(0, splitIndex).trim();
					if (!map.containsKey(key)) {
						String value = line.substring(splitIndex + 1);
						if (keyList != null)
							keyList.add(key);
						map.put(key, value);
					}
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}

	private static String[] readPFileAsStrArr(String fileName) throws Exception {
		fileName = adjustFileName(fileName);
		String fullPath = FileUtil.getClassesPath() + fileName;
		return FileUtil.readAsStrArr(fullPath);
	}

	private static String adjustFileName(String fileName) {
		if (!fileName.endsWith(EXTEND_NAME)) {
			fileName += EXTEND_NAME;
		}
		return fileName;
	}
}