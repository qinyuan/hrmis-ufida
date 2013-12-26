package qinyuan.lib.office;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtil {

	public static void createDocByXml(String xmlFileName, String docFileName)
			throws Exception {
		createDocByXml(xmlFileName, docFileName, new HashMap<String, Object>());
	}

	public static void createDocByXml(String xmlFileName, String docFileName,
			Map<String, Object> replaceMap) throws Exception {
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		File file = new File(xmlFileName);
		if (!file.isFile()) {
			throw new RuntimeException("invalid file " + file.getAbsolutePath());
		}
		configuration.setDirectoryForTemplateLoading(file.getParentFile());
		Template t = configuration.getTemplate(file.getName());
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(docFileName), "UTF-8"));
		t.process(replaceMap, out);
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws Exception {
	}
}
