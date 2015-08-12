package qinyuan.hrmis.action.demand;

import java.sql.SQLException;

import qinyuan.hrmis.dao.DemandDao;
import qinyuan.hrmis.dao.SimpleDemand;
import qinyuan.hrmis.dao.SimpleDemandDao;
import qinyuan.hrmis.domain.demand.DemandDetailTable;
import qinyuan.lib.web.SimpleAction;
import qinyuan.lib.web.html.ImageUtil;

public class DemandAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	private void getDemandDetailByDemandId() {
		int demandId = getInt("getDemandDetailByDemandId");
		if (demandId > 0) {
			DemandDetailTable t = new DemandDetailTable(demandId);
			setResult(t.toString().replace("null", ""));
		}
	}

	private void getDemandByCusId() {
		int cusId = getInt("getDemandByCusId");
		if (cusId <= 0) {
			return;
		}

		setResult("已开启：<br />");
		try {
			setResult(getSpans(SimpleDemandDao.getActiveInstancesByCusId(cusId)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setResult("<hr />已暂停：<br />");
		try {
			setResult(getSpans(SimpleDemandDao.getPauseInstancesByCusId(cusId)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setResult("<hr />已关闭：<br />");
		try {
			setResult(getSpans(SimpleDemandDao.getCloseInstancesByCusId(cusId)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setResult("<hr />");
	}

	private static String getSpans(SimpleDemand[] sds) throws SQLException {
		StringBuilder s = new StringBuilder();
		for (SimpleDemand sd : sds) {
			s.append("<p><span id='dem" + sd.getId() + "'>" + sd.getName()
					+ "</span>");
			if (DemandDao.isCommented(sd.getId())) {
				s.append(ImageUtil.getCommentImage("deRemark" + sd.getId()));
			}
			s.append("</p>");
		}
		return s.toString();
	}

	@Override
	protected void exec() throws Exception {
		getDemandByCusId();
		getDemandDetailByDemandId();
	}
}
