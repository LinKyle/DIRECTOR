@REM ===============================================================================
@REM SCRIPT   :	autoRunner.cmd
@REM AUTHOR   : Kyle
@REM Date     :	2013-8-14
@REM REV      :	1.0
@REM PLATFORM :	Windows
@REM ===============================================================================

@REM================================================================================
@REM…Ë÷√»Ìº˛¬∑æ∂
SET HOME=E:\EasDirector
@REM================================================================================

SET JAVA_HOME=%HOME%\oracle-jdk
SET LIBDIR=%HOME%\lib
SET LIBPATH= .;%LIBDIR%\eas.director.jar;
@REM %LIBDIR%\log4j-1.2.12.jar;%LIBDIR%\org.mortbay.jetty.jar;%LIBDIR%\commons-logging-1.1.jar;%LIBDIR%\javax.servlet.jar;%LIBDIR%\javax.servlet.jsp.jar

%JAVA_HOME%\bin\java -Xms256m -Xmx768m  -classpath %LIBPATH% -DAPP_HOME=%HOME% com.kingdee.eas.bos.pureflex.manager.WebServer
