function login(){
		//禁用登录按钮，转动菊花
		$("#Submit").removeClass();
		$("#Submit").addClass("login_button_disabled");
		$("#Submit").attr("disabled","disabled");
		$("#loading").removeAttr("style");
		var user = $("#user").val();
		var password = $("#password").val();
		$.ajax({
		  type:"POST",
		  url:"./login.action",
		  dataType:"json",
		  data:"password="+password+"&user="+user,
		  success:showLoginTips
		});
	}
	function showLoginTips(loginResult){
		if(loginResult.success){
			window.location.href="\index.html";
		}else{
			$('#loginTip').text(loginResult.tips);
		}
		// 恢复登录按钮，去掉菊花
		$("#Submit").removeClass();
		$("#Submit").addClass("login_button");
		$("#Submit").removeAttr("disabled");
		$("#loading").attr("style","display:none");
	}