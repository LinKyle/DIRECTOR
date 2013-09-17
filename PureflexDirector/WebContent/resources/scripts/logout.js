function logout(){
		$.ajax({
		  type:"get",
		  url:"./logout.action",
		  dataType:"json",
		  success:gotoLoginPage
		});
	}
function gotoLoginPage(result){
	if(result.success){
		window.location.replace("login.html");
	}
}