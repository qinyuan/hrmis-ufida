package qinyuan.lib.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import qinyuan.lib.file.FileFormat;

public abstract class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	abstract protected String getDiskFolder();

	abstract protected void handle(FileItem item);

	abstract protected void afterDoPost();

	protected void forward(String path) {
		try {
			RequestDispatcher requestDispatcher = this.getServletContext()
					.getRequestDispatcher(path);
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String getDiskFileName(FileItem fileItem) {
		String uploadFileName = fileItem.getName();
		uploadFileName = FileFormat.getLinStyleFileName(uploadFileName);
		int start = uploadFileName.lastIndexOf("/");
		String fileName = uploadFileName.substring(start + 1);
		return getDiskFolder() + fileName;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		this.response = response;

		request.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(getDiskFolder()));
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (!(item.isFormField() || item.getSize() == 0 || item
						.getName().isEmpty())) {
					OutputStream out = new FileOutputStream(new File(
							getDiskFileName(item)));
					InputStream in = item.getInputStream();
					int length = 0;
					byte[] buf = new byte[1024];
					while ((length = in.read(buf)) != -1) {
						out.write(buf, 0, length);
					}
					in.close();
					out.close();
					handle(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		afterDoPost();
	}
}
