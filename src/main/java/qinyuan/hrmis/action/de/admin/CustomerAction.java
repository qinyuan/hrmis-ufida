package qinyuan.hrmis.action.de.admin;

import qinyuan.lib.web.SimpleAction;
import static qinyuan.hrmis.dao.CustomerDao.*;

public class CustomerAction extends SimpleAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void exec() throws Exception {
		addNewDepenCustomer();
		deleteCus();
		addSubCustomer();
		setCusSuperior();
		setDependentCus();
		mdfCustomerName();
	}

	private void addNewDepenCustomer() throws Exception {
		String cusName = getString("newDepenCusName");
		if (!empty(cusName)) {
			if (exist(cusName)) {
				setResult("“" + cusName + "”已存在");
			} else {
				add(cusName);
			}
		}
	}

	private void addSubCustomer() throws Exception {
		String subCusName = getString("subCusName");
		int supCusId = getInt("supCusId");
		if ((!empty(subCusName)) && supCusId > 0) {
			add(subCusName, supCusId);
		}
	}

	private void mdfCustomerName() throws Exception {
		int id = getInt("mdfCustomerId");
		String name = getString("mdfCustomerName");
		if (id > 0 && !empty(name)) {
			mdfName(id, name);
		}
	}

	private void deleteCus() throws Exception {
		int cusId = getInt("delCusId");
		if (cusId > 0) {
			if (isUsed(cusId)) {
				setResult("不能删除存在职位需求的客户");
			} else if (isSuperior(cusId)) {
				setResult("该客户已经存在子客户，不能删除");
			} else {
				delete(cusId);
			}
		}
	}

	private void setCusSuperior() throws Exception {
		int subCusId = getInt("subCusId");
		int supCusId = getInt("supCusId");
		if (subCusId > 0 && supCusId > 0) {
			mdfSuperior(subCusId, supCusId);
		}
	}

	private void setDependentCus() throws Exception {
		int cusId = getInt("depenCusId");
		if (cusId > 0) {
			mdfSuperior(cusId);
		}
	}
}
