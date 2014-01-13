package qinyuan.hrmis.domain.tool;

import java.util.Map;
import qinyuan.lib.file.PropertyUtil;

public class SalaryTool {

	private Map<String, String> map = PropertyUtil.parse("salary_cal");

	public double getEndowmentRate() {
		String str = map.get("endowmentRate");
		return toDouble(str);
	}

	public double getMedicareFare() {
		return toDouble(map.get("medicareFare"));
	}

	public double getMedicareRate() {
		return toDouble(map.get("medicareRate"));
	}

	public double getIdlenessRate() {
		return toDouble(map.get("idlenessRate"));
	}

	public double getFundsRate() {
		return toDouble(map.get("fundsRate"));
	}

	public double getFundsBase() {
		return toDouble(map.get("fundsBase"));
	}

	public double getSecurityBase() {
		return toDouble(map.get("securityBase"));
	}

	private static double toDouble(String str) {
		return Double.parseDouble(str);
	}
}