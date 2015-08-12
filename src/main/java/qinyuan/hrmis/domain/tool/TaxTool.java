package qinyuan.hrmis.domain.tool;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import qinyuan.lib.file.PropertyUtil;
import qinyuan.lib.lang.MyMath;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class TaxTool {

	private int base;
	private int[] taxLevels;
	private int[] taxRates;
	private int[] calNums;
	private Map<String, String> map;

	public TaxTool() {
		map = PropertyUtil.parse("tax_rate");
		Set<String> set = map.keySet();
		int len = set.size();
		taxLevels = getTaxLevelsByKeySet(set);
		taxRates = new int[len - 1];
		calNums = new int[len - 1];
		String value;
		String[] strArr;
		for (int i = 0; i < taxLevels.length; i++) {
			value = map.get(String.valueOf(taxLevels[i]));
			strArr = value.split("_");
			taxRates[i] = Integer.parseInt(strArr[0]);
			calNums[i] = Integer.parseInt(strArr[1]);
		}
		strArr = map.get("more").split("_");
		taxRates[len - 2] = Integer.parseInt(strArr[0]);
		calNums[len - 2] = Integer.parseInt(strArr[1]);
		base = Integer.parseInt(map.get("base"));
	}

	public int getBase() {
		return base;
	}

	public int[] getTaxLevels() {
		return taxLevels;
	}

	public String getTaxLevelsStr() {
		return getArrayStr(taxLevels);
	}

	public int[] getTaxRates() {
		return taxRates;
	}

	public String getTaxRatesStr() {
		return getArrayStr(taxRates);
	}

	public int[] getCalNums() {
		return calNums;
	}

	public String getCalNumsStr() {
		return getArrayStr(calNums);
	}

	public String toTable() {
		Table t = new Table();
		TableRow tr = new TableRow(true);
		tr.add("应纳税所得额");
		tr.add("适用税率");
		tr.add("速算扣除数");
		t.addHead(tr);

		for (int i = 0; i <= taxLevels.length; i++) {
			tr = new TableRow();
			if (i == 0) {
				tr.add(taxLevels[i] + " 以下");
			} else if (i == taxLevels.length) {
				tr.add(taxLevels[i - 1] + " 以上");
			} else {
				tr.add(taxLevels[i - 1] + "~" + taxLevels[i]);
			}
			tr.add(taxRates[i] + "%");
			tr.add(calNums[i]);
			t.add(tr);
		}
		return t.toString();
	}

	private static String getArrayStr(int[] ints) {
		StringBuilder s = new StringBuilder();
		for (int i : ints) {
			s.append(i + ",");
		}
		s.deleteCharAt(s.length() - 1);
		return s.toString();
	}

	private static int[] getTaxLevelsByKeySet(Set<String> set) {
		int len = set.size();
		int[] taxLevels = new int[len - 2];
		int i = 0;
		for (String str : set) {
			if (MyMath.isNumeric(str))
				taxLevels[i++] = Integer.parseInt(str);
		}
		Arrays.sort(taxLevels);
		return taxLevels;
	}

	public static void main(String[] args) {
		TaxTool tt = new TaxTool();
		System.out.println(tt.getTaxLevelsStr());
		System.out.println(tt.getTaxRatesStr());
		System.out.println(tt.getCalNumsStr());
		System.out.println(tt.getBase());
		System.out.println(tt.toTable());
	}
}
