$(function() {
	getCheckBoxes().each(function() {
		persistStatus(this.id);
	});

	$('#selectAll').click(function() {
		setCheckBoxStatus(this.checked);
	});

	$('#exportDailyButton').aclick(function() {
		exportReportByType("daily");
	});
	$('#exportWeeklyButton').aclick(function() {
		exportReportByType("weekly");
	});
	function exportReportByType(type) {
		var href = "export-excel?type=" + type;

		var recruiterId = getRecruiterId();
		if (recruiterId > 0) {
			href += "&recruiterId=" + recruiterId;
		}

		var startDate = getStartDate();
		if (startDate == "") {
			alert("开始时间未设置");
			return;
		} else {
			href += "&startDate=" + startDate;
		}

		var endDate = getEndDate();
		if (endDate == "") {
			alert("结束时间未设置");
			return;
		} else {
			href += "&endDate=" + endDate;
		}

		$selectedCheckBoxes = getCheckBoxes().filter(function() {
			return this.checked && this.id != 'selectAll';
		}).each(function() {
			href += "&" + this.id + "=true";
		});

		window.open(href);
	}
	function getCheckBoxes() {
		return $('#columnsSelectDiv').find('input:checkbox[id]');
	}
	function setCheckBoxStatus(checked) {
		getCheckBoxes().each(function() {
			if (this.checked != checked) {
				$(this).trigger('click');
			}
		});
	}
});