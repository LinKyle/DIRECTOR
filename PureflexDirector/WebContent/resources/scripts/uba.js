$(document).ready(function(){
		
		$("#main-nav li .nav-top-item").hover(
			function () {
				$(this).stop().animate({ paddingRight: "25px" }, 200);
			}, 
			function () {
				$(this).stop().animate({ paddingRight: "15px" });
			}
		);
		
		$('.content-box .content-box-content div.tab-content').hide(); // Hide the content divs
		$('ul.content-box-tabs li').hide();
		$('ul.content-box-tabs li a.default-tab').addClass('current'); // Add the class "current" to the default tab
		$('.content-box-content div.default-tab').show(); // Show the div with class "default-tab"
		$('ul.content-box-tabs li a.default-tab').parent().show();
		
		if($('#cache').attr('isFirst') == "true"){
			$('#AlreadyInstallDesc').hide();
			$('#concurrentPage').hide();
			$('#FirstInstallDesc').show();
		}else{
			$('#FirstInstallDesc').hide();
			$('#concurrentPage').show();
			$('#AlreadyInstallDesc').show();
		}
		
});
var tablist = new Array("installDesc","virtualSetting","storeSetting","settingReport","taskProcess");
function openTab2(){
	$('#link1').removeClass('current');
	$('#link2').addClass('current');
	$('#link2').parent().show();
	$('#tab1').hide();
	$('#tab2').show();
}

function clickTab1(){
	if(!$('#link1').hasClass("current")){
		$('#link2').removeClass('current');
		//$('#link1').parent().siblings().hide();
		$('#link1').addClass('current');
		$('#tab1').siblings().hide();
		$('#tab1').show();
	}
}

function clickTab2(){
	if(!$('#link2').hasClass("current")){
		$('#link1').removeClass('current');
		//$('#link3').parent().hide();
		$('#link2').addClass('current');
		$('#tab2').siblings().hide();
		$('#tab2').show();
	}
}

function switchTab(switchTabId){
	for (index in tablist)
	{
		var tabId = tablist[index];
		if(tabId == switchTabId){
			$('#'+tabId+'Link').addClass("current");
			$('#'+tabId+'Link').parent().show();
			$('#'+tabId).addClass("default-tab");
			$('#'+tabId).siblings().hide();
			$('#'+tabId).show();
		}else{
			$('#'+tabId+'Link').removeClass("current");
			$('#'+tabId).removeClass("default-tab");
		}
	}
}

//传来下个的tabId,以及要执行的任务方法
function doNext(nextTabId){
	
	switchTab(nextTabId);
}

function doPre(preTabId){
	switchTab(preTabId);
}

function doTaskSuccess(){
	$('#cache').attr('isFirst',"false");
	$('#AlreadyInstallDesc').show();
	$('#installDesc').hide();
	$('#concurrentPage').show();
}

function gotoConcurrentPage(){
	window.location.replace("concurrent.html");
}


