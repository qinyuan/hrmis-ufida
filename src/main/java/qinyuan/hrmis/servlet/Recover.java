package qinyuan.hrmis.servlet;

import org.apache.commons.fileupload.FileItem;

import qinyuan.hrmis.domain.data.HRMISRecover;
import qinyuan.lib.file.PFile;
import qinyuan.lib.web.Upload;

public class Recover extends Upload {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getDiskFolder() {
		return PFile.parse("data").get("dataFolder");
	}

	@Override
	protected void handle(FileItem item) {
		String diskFileName = getDiskFileName(item);
		HRMISRecover recover = new HRMISRecover();
		if (recover.recover(diskFileName)) {
			request.setAttribute("result", "数据库恢复成功");
		} else {
			request.setAttribute("result", "数据库恢复失败： " + recover.getErrorInfo());
		}
	}

	@Override
	protected void afterDoPost() {
		forward("/data/recover.jsp");
	}
}
