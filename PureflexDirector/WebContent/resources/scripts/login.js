function check_username(form) {
    var name = $("input[name='adminName']").val();
    if (name != "") {
    	if(name != "admin"){
    		$("#checkadminName").html("<img src='resources/images/check_error.gif'>用户名不正确"); 
    		return false;
    	}else{
    		$("#checkadminName").html("<img src='resources/images/check_right.gif'>"); 
    		return 1;
    	}
    } else {
	    $("#checkadminName").html("<img src='resources/images/check_error.gif'>请输入用户名"); 
		return false;
    }
}
function check_password(form) {
    var password = $("input[name='passWord']").val();
    if (password == "") {
        $("#checkpassWord").html("<img src='resources/images/check_error.gif'>请输入密码"); 
		return false;
    } else {
    	$("#checkpassWord").html("<img src='resources/images/check_right.gif'>"); 
        return 1;
    } 
}


function checkform(form) {
	$("#msg").html("");
    var result = [];
    result[0] = check_username(form);
    result[1] = check_password(form);
    if (result[0] == 1 && result[1] == 1) {
    	login(form);
        return true;
    } else {
        return false;
    }
}

function login(form){
	var name = $("input[name='adminName']").val();
	var password = $("input[name='passWord']").val();
	var url = document.location.href;
	var ip = url.substring(7,url.lastIndexOf(":"));
	$.ajax({
		  type:"POST",
		  url:"login",
		  data:"name="+name+"&password="+password+"&ip="+ip,
		  datatype:"xml",
		  success:callLoginback
		  });
}

function callLoginback(xml){
	var issuccess = $(xml).find("success").text();
	if(issuccess == "1"){
		if($(xml).find("guide").text() == "on"){
			window.location.replace("jsp/guide.jsp");
		}else{
			var url = document.location.href;
			var ip = url.substring(7,url.lastIndexOf(":"));
			if($(xml).find("IP").text() != ip){
				alert("您上次修改了加速器IP，需要重启加速器使改动生效，以保证管理系统的正常使用");
			}
			window.location.replace("jsp/main.jsp");
		}
	}else{
		$("#msg").html($(xml).find("msg").text());
		$("#msg").css("color","red");
	}
}