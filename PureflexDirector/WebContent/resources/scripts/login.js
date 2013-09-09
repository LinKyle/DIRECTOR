function login(){
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
			window.location.href="\index.jsp";
		}else{
			$('#loginTip').text(loginResult.tips);
		}
	}