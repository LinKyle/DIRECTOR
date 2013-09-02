<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EAS专家部署系统</title>
<link rel="stylesheet" href="resources/css/reset.css" type="text/css" media="screen" />
<link rel="stylesheet" href="resources/css/style.css" type="text/css" media="screen" />


<script type="text/javascript" src="resources/scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="resources/scripts/uba.js"></script>

</head>
<body>
<div id="body-wrapper">
  <!-- Wrapper for the radial gradient background -->
  <div id="sidebar">
    <div id="sidebar-wrapper">
      <div id="title">EAS专家部署系统</div>
      <ul id="main-nav">
		<!-- Add the class "current" to current menu item -->
        <li> <a href="version.jsp" class="nav-top-item current">EAS安装</a> </li>
        <li> <a href="funciton.jsp" class="nav-top-item">并发数调整</a></li>
        <li> <a href="button.jsp" class="nav-top-item">虚拟机配置查看</a></li>
      </ul>
      <!-- End #main-nav -->
    </div>
  </div>
  <!-- End #sidebar -->
  <div id="main-content">
    <!-- Main Content Section with everything -->
    <!-- Page Head -->
    <div class="content-box">
      <!-- Start Content Box -->
      <div class="content-box-header">
        <ul class="content-box-tabs">
           <li><a id="link1" class="default-tab current" onclick="clickTab1()">1.虚拟配置</a></li>
           <li><a id="link2" onclick="clickTab2()">2.应用环境配置</a></li> 
           <li><a id="link3" onclick="clickTab1()">3.数据中心配置</a></li>
        </ul>
        <div class="clear"></div>
      </div>
      <!-- End .content-box-header -->
      <div class="content-box-content">
	  	<div class="tab-content default-tab" id="tab1">
	  			
        </div>
        <!-- End #tab1 -->
       <div class="tab-content" id="tab2">
       		
       </div>
        <!-- End #tab2 -->
        <div class="tab-content" id="tab3">
        </div>
        <!-- End #tab3 -->
      </div>
      <!-- End .content-box-content -->
    </div>
    <!-- End .content-box -->
    <div id="footer"> <small>
      <!-- Remove this notice or replace it with whatever you want -->
      &#169; Copyright 2013 Kingdee | Powered by <a href="#">BOS</a> </small> 
	  </div>
    <!-- End #footer -->
  </div>
  <!-- End #main-content -->
</div>
</body>
</html>