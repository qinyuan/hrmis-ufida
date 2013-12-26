package qinyuan.hrmis.domain.remark;

import java.sql.SQLException;

public class CustomerRemarkList {

	private int cusId;
	private int userId;

	public CustomerRemarkList(int cusId, int userId) {
		this.cusId = cusId;
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		CustomerRemark[] drs;
		try {
			drs = CustomerRemark.getInstancesByCusId(cusId);
			String addLink = "<p><a href='#add'>添加备注</a></p>";
			if (drs.length == 0) {
				s.append("<p><b>尚未添加备注...</b></p>");
			} else {
				s.append(addLink);
				for (CustomerRemark dr : drs) {
					s.append(getRemark(dr, userId));
				}
			}
			s.append(addLink);
			return s.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			return "<h1>数据库操作失败</h1>";
		}
	}

	private String getRemark(CustomerRemark dr, int userId) {
		String s = "<div class='remark'>";
		s += "<div class='remarkCreator'>" + dr.getUsername() + "</div>";
		s += "<div class='remarkContent'>" + dr.getContent() + "</div>";
		s += "<div class='createTime'>" + dr.getCreateTime();
		if (dr.getUserId() == userId) {
			s += " <a href='#mdf' id='mdf" + dr.getId() + "'>修改</a>";
			s += " <a href='#del' id='del" + dr.getId() + "'>删除</a>";
		}
		s += "</div>";
		s += "</div>";
		return s;
	}
}
