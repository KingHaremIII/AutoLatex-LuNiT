<%--
  Created by IntelliJ IDEA.
  User: kamisama
  Date: 2020/3/17
  Time: 上午9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>a</title>
    <script src="js/jquery.min.js"></script>

    <script>
        // 页面加载，绑定单击事件
        $(function(){
            $("#btn").click(function(){
                var box = document.getElementsByName("path");
                var objArray = box.length;
                var apiContentStr="";

                for(var i=0;i<objArray;i++){
                    if(box[i].checked == true){
                        apiContentStr += box[i].value+"$";
                    }
                }
                if(apiContentStr == "" || apiContentStr.length == 0){
                    alert("You must select at least one section! ");
                    return;
                }
                apiContentStr = apiContentStr.substring(0, apiContentStr.length-1);
                document.write(apiContentStr);

                // alert("hello btn");
                // 发送ajax请求
                $.ajax({
                    // 编写json格式，设置属性和值
                    url:"file/ajaxTest",
                    contentType:"application/json;charset=UTF-8",
                    data:apiContentStr,
                    dataType:"text",
                    type:"post",
                    success:function(data){
                        // data服务器端响应的json的数据，进行解析
                        alert(data);
                    }
                });

            });
        });
    </script>
</head>
<body>
    <h2>select the sections you want to test. </h2>
    <hr/>
    <!-- List all sections for selection -->
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
            var xhttp = new XMLHttpRequest();
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
            document.write(apiContentStr)
            xhttp.open("POST", "/file/tmp", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send("result="+apiContentStr)
        }
    </script>
    <button id="btn">发送ajax的请求</button>
</body>
</html>
