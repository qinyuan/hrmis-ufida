function Table(jDiv) {
	var self=this;
	var thickBorder = "4px solid black";
	var thinBorder = "1px solid darkgray";
	var clickRecallFunc = null;
	self.selectedRow = null;
	if (jDiv != null) {
		var index=0;
		jDiv.find('tr').each(function() {
			if (index == 0) {
				$(this).prepend('<th>选择</th>');
			} else {
				$(this).prepend('<td class="selectTd">' + index	+ '</td>');
			}
			index++;
		});
		jDiv.find('td.selectTd').attr('title', '单击选择')
		.mouseover(function() {
			$(this).css('cursor', 'pointer');
		}).click(function() {
			var tr = $(this).parent();
			var clickedRow = tr.get(0);
			if (self.selectedRow) {
				self.selectedRow.style.backgroundColor = "white";
				if (clickedRow == self.selectedRow) {
					self.selectedRow = null;
				} else {
					self.selectedRow = clickedRow;
					self.selectedRow.style.backgroundColor = "lime";
				}
			} else {
				self.selectedRow = clickedRow;
				self.selectedRow.style.backgroundColor = "lime";
			}
			if (clickRecallFunc != null) {
				clickRecallFunc(tr,self.selectedRow != null);
			}			
		});
		jDiv.find('table').css({
			"borderTop" : thickBorder,
			"borderBottom" : thickBorder,
			"borderCollapse" : "collapse",
			"margin" : "5px"
		});
		jDiv.find('th').css({
			"borderBottom" : "2px solid black",
			"borderLeft" : thinBorder,
			"borderRight" : thinBorder,
			"padding" : "4px"
		});
		jDiv.find('td').css({
			"border" : thinBorder,
			"padding" : "4px"
		});
		
		jDiv.find('tr').hover(function() {
			if(this!=self.selectedRow){
				this.style.backgroundColor = "yellow";
			}
		}, function() {
			if (this == self.selectedRow) {
				this.style.backgroundColor = "lime";
			} else {
				this.style.backgroundColor="white";
			}
		}).css('background-color','white');
	}

	this.click = function(func) {
		clickRecallFunc = func;
	};
}