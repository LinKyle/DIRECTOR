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
		
		$.ajax({
		  type:"get",
		  url:"./concurrentNum.action",
		  dataType:"json",
		  success:adjustPage
		});
});

function adjustPage(result){
	if(result.concurrentNum == "false"){
		$('#AlreadyInstallDesc').hide();
		$('#concurrentPage').hide();
		$('#FirstInstallDesc').show();
//		$('#compliteBtn').hide();
	}else{
		$('#FirstInstallDesc').hide();
		$('#concurrentPage').show();
		$('#AlreadyInstallDesc').show();
	}
}

var tablist = new Array("installDesc","taskProcess");

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
	showTab(switchTabId);
}

function showTab(tabName){
	if(tabName == "settingReport"){
		$.ajax({
			  type:"get",
			  url:"./systemSetting.action",
			  dataType:"json",
			  success:refreshSettingPage
			});
	}else if(tabName == "taskProcess"){
		checkBeforeDoTask();
	}
}

function refreshSettingPage(result){
	$("#showServerType").text(result.appServerType);
	$("#showEasVersion").text(result.easVersion);
	$("#showEasHome").text(result.easHome);
	$("#showClusterHttpPort").text(result.clusterHttpPort);
	$("#showClusterRpcPort").text(result.clusterRpcPort);
	$("#showOracleHome").text(result.oracleHome);
	$("#showOracleSID").text(result.oracleSID);
	$("#showOraclePort").text(result.oraclePort);
	$("#showOracleSysPSW").text(result.oracleSysPSW);
	
	$("#showEasID").text($("input[name='easIp']").val());
	$("#showStoreServerIp").text($("input[name='storeIp']").val());
	
	var concurrentNum = $("input[name='concurrent']:checked").val();
	var instanceNum = 0;
	if(concurrentNum == "ls300"){
		instanceNum = result.concurrent_ls300;
	}else if(concurrentNum == "gt300ls600"){
		instanceNum = result.concurrent_gt300ls600;
	}else if(concurrentNum == "gt600ls800"){
		instanceNum = result.concurrent_gt600ls800;
	}
	$("#showInstanceNum").text(instanceNum);
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
	var isShowTips = false;
	var easIp = $("input[name='easIp']").val();
	var gateway = $("input[name='gateway']").val();
	var storeServerIp = $("input[name='storeIp']").val();
	var storeServerUser = $("input[name='storeUser']").val();
	var storeServerPassword = $("input[name='storePassword']").val();
	if(checkInputEmpty(easIp)){
		checkTips += "步骤1 中的应用服务器 ip 不能为空。\n";
		isShowTips = true;
	}else if(checkIpFormatError(easIp)){
		checkTips += "步骤1 中的应用服务器 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(gateway)){
		checkTips += "步骤1 中的网关 ip 不能为空。\n";
		isShowTips = true;
	}else if(checkIpFormatError(gateway)){
		checkTips += "步骤1 中的网关 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(storeServerIp)){
		checkTips += "步骤2 中的存储服务器 ip 不能为空。 \n";
		isShowTips = true;
	}else if(checkIpFormatError(storeServerIp)){
		checkTips += "步骤2 中的存储服务器 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(storeServerUser)){
		checkTips += "步骤2 中的存储服务器 用户名 不能为空。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(storeServerPassword)){
		checkTips += "步骤2 中的存储服务器 密码 不能为空。\n";
		isShowTips = true;
	}
	if(isShowTips){
		alert(checkTips);
	}else{
		doNext('taskProcess',doNothing);
		doRealTask();
	}
}

function collectDeployParam(deployType){
	var easIp = $("input[name='easIp']").val();
	var gateway = $("input[name='gateway']").val();
	var storeServerIp = $("input[name='storeIp']").val();
	var storeServerUser = $("input[name='storeUser']").val();
	var storeServerPassword = $("input[name='storePassword']").val();
	var concurrentNum = $("input[name='concurrent']:checked").val();
	
	var postData = "easIp="+easIp+"&gateway="+gateway+"&storeServerIp="+storeServerIp+
	  "&storeServerUser="+storeServerUser+"&storeServerPassword="
	  +storeServerPassword+"&concurrentNum="+concurrentNum+"&deployType="+deployType;
	
	return postData;
}

function doRealTask(){
	$("#deployEasVmStatus").html("<img alt='任务中' src='./resources/images/loading.gif'>");
	var createEasWorkloadData = collectDeployParam("eas");
	$.ajax({
		  type:"POST",
		  url:"./deployVM.action",
		  dataType:"json",
		  data:createEasWorkloadData,
		  success:doAfterDeployEasWorkload
		});
}

function doAfterDeployEasWorkload(deployResult){
	if(deployResult.isSuccess){
		$("#deployEasVmStatus").html("<img alt='完成' src='./resources/images/start.png'>");
		$("#deployOraVmStatus").html("<img alt='任务中' src='./resources/images/loading.gif'>");
		var createOraWorkloadDate = collectDeployParam("ora");
		$.ajax({
			  type:"POST",
			  url:"./deployVM.action",
			  dataType:"json",
			  data:createOraWorkloadDate,
			  success:doAfterDeployOraWorkload
			});
	}else{
		$("#deployEasVmStatus").text(deployResult.tips);
	}
}

function doAfterDeployOraWorkload(deployResult){
	if(deployResult.isSuccess){
		$("#deployOraVmStatus").html("<img alt='完成' src='./resources/images/start.png'>");
		$('#concurrentPage').show();
		//		$('#compliteBtn').show();
	}else{
		$("#deployOraVmStatus").text(deployResult.tips);
	}
}


function checkBeforeDoTask(){
	//检查storeServer 是否可用
	//禁用所有tab		
	$("#installDescLink").attr("onclick","return false;");
	$("#virtualSettingLink").attr("onclick","return false;");
	$("#storeSettingLink").attr("onclick","return false;");
	$("#settingReportLink").attr("onclick","return false;");
	$("#taskProcessLink").attr("onclick","return false;");
}

function doTaskSuccess(){
	
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

function checkPageOneInput(){
	var checkTips = "温馨提示：\n";
	var isShowTips = false;
	var easIp = $("input[name='easIp']").val();
	var gateway = $("input[name='gateway']").val();
	if(checkInputEmpty(easIp)){
		checkTips += "应用服务器 ip 不能为空。\n";
		isShowTips = true;
	}else if(checkIpFormatError(easIp)){
		checkTips += "应用服务器 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(gateway)){
		checkTips += "网关 ip 不能为空。\n";
		isShowTips = true;
	}else if(checkIpFormatError(gateway)){
		checkTips += "网关 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(isShowTips){
		alert(checkTips);
		return false;
	}else{
		return true;
	}
}

function checkPageTwoInput(){
	var checkTips = "温馨提示：\n";
	var isShowTips = false;
	var storeServerIp = $("input[name='storeIp']").val();
	var storeServerUser = $("input[name='storeUser']").val();
	var storeServerPassword = $("input[name='storePassword']").val();
	if(checkInputEmpty(storeServerIp)){
		checkTips += "存储服务器 ip 不能为空。 \n";
		isShowTips = true;
	}else if(checkIpFormatError(storeServerIp)){
		checkTips += "存储服务器 ip 输入的格式不正确。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(storeServerUser)){
		checkTips += "存储服务器 用户名 不能为空。\n";
		isShowTips = true;
	}
	if(checkInputEmpty(storeServerPassword)){
		checkTips += "存储服务器 密码 不能为空。\n";
		isShowTips = true;
	}
	if(isShowTips){
		alert(checkTips);
		return false;
	}else{
		return true;
	}
}

