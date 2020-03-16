<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/5/4
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>LuNiT Selector</title>
</head>
<body>

    <h2>select the sections you want to test. </h2>

    <hr/>
    <!-- List all sections for selection -->
<%--    <c:forEach items="${paths}" var="path">--%>
<%--        ${path}<br/>--%>
<%--    </c:forEach>--%>

    <script type="text/javascript">
        var i = 0
        document.write('<ul>');
        <c:forEach items="${paths}" var="path">
            i = i + 1;
            var splited = "${path}".split("/")
            var lenTmp = splited.length
            var addSpace = ""
            for (var spaceInt=0;spaceInt<lenTmp-2;spaceInt++) {
                addSpace = addSpace + "----------------"
            }

            var pathName = splited[lenTmp-1]
            document.write('<li>');
            document.write('<input type="checkbox" name="path" value="${path}"/>'+addSpace+pathName);
            document.write('</li>');
            if (i%5 == 0) {
                document.write('<hr/>');
            }
        </c:forEach>
        document.write('</ul>');
    </script>
    <input type="button" onclick="nextStep()" value="Submit"/>

    <script>
        function nextStep(){
            var box = document.getElementsByName("path");
            var objArray = box.length;
            var apiContentStr="";

            for(var i=0;i<objArray;i++){
                if(box[i].checked == true){
                    apiContentStr += box[i].value+"\\";
                }
            }
            if(apiContentStr == "" || apiContentStr.length == 0){
                alert("You must select at least one section! ");
                return;
            }
            apiContentStr = apiContentStr.substring(0, apiContentStr.length-1);
            // document.write(apiContentStr)
            $('#MainForm').attr('action', apiContentStr);
            $('#MainForm').submit();
        }
    </script>

</body>
</html>
