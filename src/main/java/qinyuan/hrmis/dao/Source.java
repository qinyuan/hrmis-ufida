package qinyuan.hrmis.dao;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.domain.resume.source.SourceMapDao;
import qinyuan.lib.web.JsUtil;

public class Source {

	private int id;
	private String name;
	private String idPattern;
	private boolean deletable;
	private String hrefPrefix;
	private String hrefSuffix;
	private String sign;
	private boolean downloaded;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdPattern() {
		return idPattern;
	}

	public void setIdPattern(String idPattern) {
		this.idPattern = idPattern;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public String getHrefPrefix() {
		return hrefPrefix;
	}

	public void setHrefPrefix(String hrefPrefix) {
		this.hrefPrefix = hrefPrefix;
	}

	public String getHrefSuffix() {
		return hrefSuffix;
	}

	public void setHrefSuffix(String hrefSuffix) {
		this.hrefSuffix = hrefSuffix;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean isDownloaded() {
		return downloaded;
	}

	public void setDownloaded(boolean downloaded) {
		this.downloaded = downloaded;
	}

	public String toJsObject() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		map.put("sign", sign);
		map.put("downloaded", String.valueOf(downloaded));

		String mapObj = SourceMapDao.getInstance(id).toJsObject();
		return JsUtil.toJsObject(map).replace("}", ",\"map\":" + mapObj + "}");
	}
}