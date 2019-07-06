<%--
  Created by IntelliJ IDEA.
  User: pengy
  Date: 2019/7/5
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>服务器进程信息</title>
</head>
<body>
<pre>
    <%
        for(Map.Entry<Thread,StackTraceElement[]> stackTrace:
            Thread.getAllStackTraces().entrySet()){
            Thread thread = stackTrace.getKey();
            StackTraceElement[] stack = stackTrace.getValue();
            if(thread.equals(Thread.currentThread())) continue;
            out.print("\n线程："+thread.getName()+"\n");
            for(StackTraceElement element:stack){
                out.print("\t"+element+"\n");
            }
        }
    %>
</pre>
</body>
</html>
