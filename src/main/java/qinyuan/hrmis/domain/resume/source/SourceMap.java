package qinyuan.hrmis.domain.resume.source;

import java.util.HashMap;

import qinyuan.lib.web.JsUtil;

public class SourceMap {

	private HashMap<String, String> map = new HashMap<String, String>();
	private int sourceId;

	SourceMap(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void add(String sourceSelector, String targetSelector) {
		map.put(sourceSelector, targetSelector);
	}

	public String toJsObject() {
		return JsUtil.toJsObject(map);
	}
}
