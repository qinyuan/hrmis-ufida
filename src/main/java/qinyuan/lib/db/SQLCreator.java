package qinyuan.lib.db;

import java.util.ArrayList;
import java.util.List;

public class SQLCreator {

	private List<String> fields = new ArrayList<String>();
	private List<String> whereClauses = new ArrayList<String>();
	private String table;

	public SQLCreator(String table) {
		this.table = table;
	}

	public SQLCreator addFields(String field) {
		fields.add(field);
		return this;
	}

	public SQLCreator addWhereClause(String whereClause) {
		whereClauses.add(whereClause);
		return this;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("SELECT ");

		for (String field : fields)
			s.append(field + ",");

		s.deleteCharAt(s.length() - 1);

		s.append(" FROM " + table);

		boolean first = true;
		for (String whereClause : whereClauses) {
			if (first) {
				s.append(" WHERE (" + whereClause + ")");
				first = false;
			} else {
				s.append(" AND (" + whereClause + ")");
			}
		}
		return s.toString();
	}
	
}
