/**
 * 切换录入内容可编辑性
 * 
 * @param expr 表达式：录入内容对象或范围
 * @param editable 是否可编辑
 */
function toggleEditable(expr, editable) {
	// 获取录入内容对象
	var _target = function(f) {
		return $(expr).filter(f).add($(expr).find(f));
	};

	// 切换校验标记显示
	var _toggleMarker = function(opt) {
		_target(".important:not(.toggle-exclude)").each(function() {
			validateMark(this, opt);
		});
	};
	
	// 切换录入内容包裹层
	var _toggleWrapper = function(ele, editable, label) {
		// 过滤不需切换显示的元素
		if($(ele).hasClass("toggle-exclude")) {
			return false;
		}
		
		// 本身隐藏的元素，显示空白内容
		if($(ele).css("display") == "none") {
			label = "";
		}
		
		var wrap = $(ele).parent(".toggle-wrap");
		if(editable) {
			if(wrap.size() == 1) {
				wrap.next("span.toggle-label").remove();
				$(ele).unwrap();
			}
		} else {
			if(wrap.size() == 0) {
				// 对于利用PIE.htc进行css3渲染的元素，将一并包裹，防止渲染出错
				$(ele).add($(ele).prev("css3-container")).wrapAll("<span class='toggle-wrap'></span>");
				wrap = $(ele).parent();
			}
			if(wrap.next("span.toggle-label").size() == 0) {
				wrap.after("<span class='toggle-label'>" + (label || "") + "</span>");
			}
		}
	};
	
	// 切换操作类元素
	var _toggleOperation = function(ele, editable) {
		// 过滤不需切换显示的元素
		if($(ele).hasClass("toggle-exclude")) {
			return false;
		}
		
		if($(ele).hasClass("toggle-reverse")) {
			_toggleWrapper(ele, !editable);

			if(editable) {
				$(ele).hide();
			} else {
				$(ele).show();
			}
		} else {
			_toggleWrapper(ele, editable);
		}
	};
	
	// 录入内容相关元素
	var text = _target("input:text");
	var textarea = _target("textarea");
	var select = _target("select");
	var checkable = _target(":checkbox, :radio");
	var hidden = _target("input[type=hidden]");
	var all = _target("input:text, textarea, select, :checkbox, :radio, input[type=hidden]");
	var operation =  _target(":button, :submit, :image, img, a");
	var other = _target(".toggle-include");
	
	if(editable) { // 允许编辑
		// 添加校验标记
		_toggleMarker("star");
		
		all.each(function() {
			_toggleWrapper(this, editable);
		});
		
		operation.each(function() {
			_toggleOperation(this, editable);
		});
		
		other.show();
	} else { // 禁用编辑
		// 清除校验标记
		_toggleMarker("clear");
		
		text.each(function() {
			_toggleWrapper(this, editable, $(this).val());
		});
		
		textarea.each(function() {
			_toggleWrapper(this, editable, $(this).text());
		});
		
		select.each(function() {
			_toggleWrapper(this, editable, $(this).find("option:selected").text());
		});
		
		checkable.each(function() {
			var label = "<input type='" + $(this).attr("type") + "' " + ($(this).is(":checked") ? "checked" : "") + " disabled />";
			_toggleWrapper(this, editable, label);
		});
		
		hidden.each(function() {
			_toggleWrapper(this, editable, "");
		});
		
		operation.each(function() {
			_toggleOperation(this, editable);
		});
		
		other.hide();
	}
}