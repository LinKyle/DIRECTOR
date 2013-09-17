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

function doNext(nextTabId,n){
	switchTab(nextTabId);
	n();
}

function doPre(preTabId,p){
	switchTab(preTabId);
	p();
}

function doTask(){
	//检测参数
	var easIp = "";
	var storeServerIp = "";
	var storeServerUser = "";
	var storeServerPassword = "";
	
	doNext('taskProcess');
	doRealTask();
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

function doNothing(){
}

