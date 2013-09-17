function logout(){
		$.ajax({
		  type:"get",
		  url:"./logout.action",
		  dataType:"json",
		  success:showLoginTips
		});
	}
	function showLoginTips(){
	}