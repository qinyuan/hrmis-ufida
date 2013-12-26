package qinyuan.hrmis.domain.resume.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import qinyuan.lib.file.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class Test {
	private Configuration configuration = null;

	public Test() throws IOException {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
		configuration.setDirectoryForTemplateLoading(new File("d:/freemarker"));
	}

	public void createDoc() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tel", "testTel\r\ntestTel\r\ntestTel");
		map.put("gender", "testGender");
		map.put("name", "testName");
		try {
			Template t = configuration.getTemplate("test2.xml");
			File outFile = new File("D:/outFileDoc.doc");
			Writer out = null;
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "utf-8"));
			System.out.println(map);
			t.process(map, out);
			out.flush();
			out.close();
			FileUtil.show("d:/outFileDoc.doc");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Test().createDoc();
	}

}