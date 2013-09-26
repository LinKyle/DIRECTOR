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
	if(n()){
		switchTab(nextTabId);
	}
	
}

function doPre(preTabId,p){
	if(p()){
		switchTab(preTabId);
	}
}

function doTask(){
	//检测参数
	var checkTips = "温馨提示：\n";
	var easIp = $("input[name='easIp']").val();
	var storeServerIp = $("input[name='storeIp']").val();
	var storeServerUser = $("input[name='storeUser']").val();
	var storeServerPassword = $("input[name='storePassword']").val();
	if(checkInputEmpty(easIp)){
		checkTips += "步骤1 中的应用服务器 ip 不能为空。\n";
	}
	if(checkIpFormatError(easIp)){
		checkTips += "步骤1 中的应用服务器 ip 输入的格式不正确。\n";
	}
	if(checkInputEmpty(storeServerIp)){
		checkTips += "步骤2 中的存储服务器 ip 不能为空。 \n";
	}
	if(checkIpFormatError(storeServerIp)){
		checkTips += "步骤2 中的存储服务器 ip 输入的格式不正确。\n";
	}
	if(checkInputEmpty(storeServerUser)){
		checkTips += "步骤2 中的存储服务器 用户名 不能为空。\n";
	}
	if(checkInputEmpty(storeServerPassword)){
		checkTips += "步骤2 中的存储服务器 密码 不能为空。\n";
	}
	if(checkTips.length == "温馨提示：\n".length){
		doNext('taskProcess');
		doRealTask();
	}
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
	return true;
}

function checkIpFormatError(inputText){
	var ips = inputText.split(".");
	if(ips.length != 4){
		return true;
	}
	for(index in ips){
		if(ips[index] < 0 || ips[index] > 255){
			return true;
		}
	}
	return false;
}

function checkInputEmpty(inputText){
	if(inputText.length < 1){
		return true;
	}else{
		return false;
	}
}

