@REM ===============================================================================
@REM SCRIPT   :	autoRunner.cmd
@REM AUTHOR   : Kyle
@REM Date     :	2013-8-14
@REM REV      :	1.0
@REM PLATFORM :	Windows
@REM ===============================================================================

@REM================================================================================
…Ë÷√»Ìº˛¬∑æ∂
SET HOME=E:\EasDirector
@REM================================================================================

SET JAVA_HOME=%HOME%\oracle-jdk
SET LIBPATH= .;%HOME%\lib\eas.director.jar;%HOME%\lib\log4j-1.2.12.jar;%HOME%\lib\org.mortbay.jetty.jar;%HOME%\lib\commons-logging-1.1.jar

%JAVA_HOME%\bin\java -Xms256m -Xmx768m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=36363 -classpath %LIBPATH% -DAPP_HOME=%HOME% com.kingdee.eas.bos.pureflex.manager.WebServer
