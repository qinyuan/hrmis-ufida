package qinyuan.lib.web;

import java.util.ArrayList;
import java.util.List;

public class HighChart {

	private String title;
	private String subTitle;
	private String[] categories;
	private String yTitle;
	private List<String> seriesNames = new ArrayList<String>();
	private List<double[]> seriesValues = new ArrayList<double[]>();

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public void setYTitle(String yTitle) {
		this.yTitle = yTitle;
	}

	public void addSeries(String name, double[] values) {
		seriesNames.add(name);
		seriesValues.add(values);
	}

	public void addSeries(String name, int[] values) {
		double[] dblValues = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			dblValues[i] = values[i];
		}
		addSeries(name, dblValues);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");

		if (title != null)
			s.append("title:{text:'" + title + "'},");

		if (subTitle != null)
			s.append("subtitle:{text:'" + subTitle + "'},");

		if (categories != null) {
			s.append("xAxis:{categories:[");
			for (String category : categories) {
				s.append("'" + category + "',");
			}
			deleteLastComma(s);
			s.append("]},");
		}

		if (yTitle != null)
			s.append("yAxis:{title:{text:'" + yTitle + "'}},");

		s.append("series:[");
		int seriesCount = Math.min(seriesNames.size(), seriesValues.size());
		for (int i = 0; i < seriesCount; i++) {
			s.append("{name:'" + seriesNames.get(i) + "',data:[");
			for (double value : seriesValues.get(i))
				s.append(value + ",");
			deleteLastComma(s);
			s.append("]},");
		}
		deleteLastComma(s);

		s.append("],");
		s.append("credits:{text:null,href:null}");
		s.append("}");
		return s.toString();
	}

	private static void deleteLastComma(StringBuilder s) {
		int index = s.length() - 1;
		if (s.charAt(index) == ',')
			s.deleteCharAt(index);
	}
}
