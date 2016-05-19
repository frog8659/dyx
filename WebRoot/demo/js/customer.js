function rsize() {
	var k=$(window).height();// 浏览器当前窗口可视区域高度
  //判断框架主区高度
    $("#wrap").css({"height":k-150});
	$("#side").css({"height":k-150});
	$("#main").css({"height":k-150});
}
$(document).ready( function (){  
   rsize();
   window.onresize=rsize;
  //左侧标签上下切换
   $(".menu dt").click(function(){
		   $(this).next('dd').slideDown().end().siblings().next('dd').slideUp();//下拉展示效果
		   $(this).parent().find("dt").removeClass("current");//添加样式
		   $(this).addClass("current");	
		   $(this).parent().find("dd").removeClass("current");
		   $(this).next("dd").addClass("current");				 
	   });
   $(".menu li").click(function(){
		   $(this).parent().parent().parent().find("li").removeClass("current");//添加样式
		   $(this).addClass("current");			 
	   });

//表格隔行奇偶换色
$(".trColor tr").each(function(i){  
         var className = ["deep", ""][i % 2];  
         $(this).addClass(className);  
         $(this).hover(function(){  
                 $(this).removeClass(className);  
                 $(this).addClass("hover");
            },function(){  
				 $(this).removeClass("hover");  
                 $(this).addClass(className);  
          });  
    });  
//标签选项切换
  $(".tabs li").click(function(){
	          $(this).parent().find("li").removeClass("current"); 
	          $(this).addClass("current").siblings().removeClass(); 						  
	          var yy = $(this).attr("rel");
			  $(this).parent().parent().find(".info").removeClass("block"); 
	          $(this).parent().parent().find(".info."+yy).addClass("block"); 
	});	
/*---------*/
});
