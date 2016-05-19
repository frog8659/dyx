/**
 * 分页相关函数集
 */
var paging = paging || {};

/**
 * 分页相关函数扩展
 */
$.extend(true, paging, {
	/**
	 * 表单分页
	 */
	form: {
		/**
		 * 分页操作栏生成函数
		 * 
		 * @param container 分页容器选择器
		 */
		generator: function(container) {
			// 分页容器
			var cont = $(container);
			// 分页页码
			var numb = cont.data("numb");
			// 总计页数
			var page = cont.data("page");
			// 总计条数
			var total = cont.data("total");
			// 翻页函数
			var turn = cont.data("turn");
			// 当前页码
			var cur = Number(numb.val());

			// 总计页数小于1，无需显示分页操作栏
			if(page <= 1) {
				return false;
			}
			
			var html = "<dl class='page'><dt>";
			
			if(cur != 1) {
				html += "<a href='javascript:' rel='1'>首页</a>";
			} else {
				html += "首页";
			}
			
			html += " | ";
			
			if(cur > 1) {
				html += "<a href='javascript:' rel='" + (cur - 1) + "'>上一页</a>";
			} else {
				html += "上一页";
			}

			html += " | ";
			
			if(cur < page) {
				html += "<a href='javascript:' rel='" + (cur + 1) + "'>下一页</a>";
			} else {
				html += "下一页";
			}

			html += " | ";

			if(cur != page) {
				html += "<a href='javascript:' rel='" + page + "'>尾页</a>";
			} else {
				html += "尾页";
			}

			html += "&nbsp;&nbsp;&nbsp;&nbsp;";
			
			html += "共 " + total + " 条记录，当前第 " + cur + "/" + page + " 页，";
			
			html += "跳到第 <input type='text' class='jump' /> 页 <input type='button' value='GO' class='btnJump' />"; 
			
			html += "</dt></dl>";
			
			
			cont.html(html);
			
			// 点击链接将触发翻页事件
			cont.find("a[rel]").unbind("click").click(function() {
				// 触发翻页动作
				turn(container, $(this).attr("rel"));
				// 禁止浏览器默认行为
				return false;
			});
			
			// 点击跳转链接触发跳转事件
			cont.find(".btnJump").unbind("click").click(function() {
				// 触发翻页动作
				turn(container, $(".jump").val());
				// 禁止浏览器默认行为
				return false;
			});
		}
	}
});