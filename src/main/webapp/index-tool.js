$(function() {
	decorateTable($('#taxTableDiv table'));
	//---------------update------------------//
	function update() {
		setEndowment();
		setMedicare();
		setIdleness();
		setFunds();
		setDeduction();
		setSalaryAfterTax();
	}
	update();

	// -------------endowment-----------------//
	function setEndowment() {
		$('#endowment').text(getEndowment());
	}
	function getEndowment() {
		return Math.round(getSecurityBase() * getEndowmentRate()) / 100;
	}
	function getEndowmentRate() {
		return $('#endowmentRate').val();
	}
	$('#endowmentRate').keyup(update);

	// --------------medicare----------------//
	function setMedicare() {
		$('#medicare').text(getMedicare());
	}
	function getMedicare() {
		return Math.round(getSecurityBase() * getMedicareRate()
				+ getMedicareFare() * 100) / 100;
	}
	function getMedicareRate() {
		return $('#medicareRate').val();
	}
	function getMedicareFare() {
		return $('#medicareFare').val();
	}
	$('#medicareRate').keyup(update);
	$('#medicareFare').keyup(update);

	// -------------idleness-----------------//
	function setIdleness() {
		$('#idleness').text(getIdleness());
	}
	function getIdleness() {
		return Math.round(getSecurityBase() * getIdlenessRate()) / 100;
	}
	function getIdlenessRate() {
		return $('#idlenessRate').val();
	}
	$('#idlenessRate').keyup(update);

	// -------------funds--------------------//
	function setFunds() {
		$('#funds').text(getFunds());
	}
	function getFundsRate() {
		return $('#fundsRate').val();
	}
	function getFunds() {
		return Math.round(getFundsBase() * getFundsRate()) / 100;
	}
	$('#fundsRate').keyup(update);

	// --------------deduction---------------//
	function setDeduction() {
		$('#deduction').text(new Number(getDeduction()).toFixed(2));
	}
	function getDeduction() {
		return getEndowment() + getMedicare() + getIdleness() + getFunds();
	}

	// --------------base--------------------//
	function getSecurityBase() {
		return $('#securityBase').val();
	}
	function getFundsBase() {
		return $('#fundsBase').val();
	}
	$('#securityBase').keyup(update);
	$('#fundsBase').keyup(update);

	// =============salaryBeforeTax================//
	$('#salaryBeforeTax').keyup(setSalaryAfterTax);
	function getSalaryBeforeTax() {
		return $('#salaryBeforeTax').val();
	}
	function setSalaryAfterTax(){
		var salaryBeforeTax = getSalaryBeforeTax();
		var deduction = getDeduction(salaryBeforeTax);
		var taxedSalary = salaryBeforeTax - deduction;
		var totalTax, salaryAfterTax;
		if (taxedSalary > 0) {
			totalTax = getTax(taxedSalary);
			salaryAfterTax = taxedSalary - totalTax;
		} else {
			totalTax = 0;
			salaryAfterTax = 0;
		}
		$('#totalTax').text(new Number(totalTax).toFixed(2));
		$('#salaryAfterTax').text(new Number(salaryAfterTax).toFixed(2));
	}

	// ======================tax====================//
	function getTaxLevels() {
		return $('#taxLevels').val().split(',');
	}
	function getTaxRates() {
		return $('#taxRates').val().split(',');
	}
	function getCalNums() {
		return $('#calNums').val().split(',');
	}
	function getTaxBase() {
		return $('#taxBase').val();
	}
	function getTax(beforeTax) {
		var taxLevels = getTaxLevels();
		var taxRates = getTaxRates();
		var calNums = getCalNums();
		var levelsCount = taxRates.length;
		var maxLevel = taxLevels[levelsCount - 2];
		var minLevel = taxLevels[0];
		var taxedSalary = beforeTax - getTaxBase();
		if (taxedSalary < 0) {
			return 0;
		} else if (taxedSalary <= minLevel) {
			return taxedSalary * taxRates[0] / 100 - calNums[0];
		} else if (taxedSalary >= maxLevel) {
			return taxedSalary * taxRates[levelsCount - 1] / 100
					- calNums[levelsCount - 1];
		} else if (taxedSalary) {
			for ( var i = 1; i < levelsCount; i++) {
				if (taxedSalary >= taxLevels[i - 1]
						&& taxedSalary <= taxLevels[i])
					return taxedSalary * taxRates[i] / 100 - calNums[i];
			}
		}
	}
});