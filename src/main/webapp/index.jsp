<%--
  Created by IntelliJ IDEA.
  User: kamisama
  Date: 2020/3/16
  Time: 下午12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>lunit</title>

    <script src="js/jquery.min.js"></script>

</head>
<body>

    <h1>LuNiT</h1>
    <p>light unit test for .tex, which is a component of AutoLatex</p>
    <a href="https://github.com/KingHaremIII/AutoLatex">Click Here for Details of AutoLatex</a>
    <hr/>
    <textarea id="uploadText" name="uploadText" rows="50" cols="80" placeholder="copy and paste your structure from Structure.xml"></textarea>
    <button id="upload">transfer</button>

    <script>
        // 页面加载，绑定单击事件
        $(function(){
            $("#upload").click(function(){
                var text = $('#uploadText').val();

                // alert("hello btn");
                // 发送ajax请求
                $.ajax({
                    // 编写json格式，设置属性和值
                    url:"file/ajaxTest",
                    contentType:"application/json;charset=UTF-8",
                    data:text,
                    dataType:"text",
                    type:"post",
                    success:function(paths){
                        var lenPaths = paths.length;
                        paths = paths.substring(1, lenPaths-1);
                        var splieted = paths.split(",");
                        for (let i=0;i<splieted.length;i++) {
                            var pathSingle = splieted[i].substring(1, splieted[i].length-1);
                            document.write('<input type="checkbox" name="path" value='+pathSingle+'/>');
                            document.write(pathSingle);
                            document.write('<br/>');
                            if ((i+1)%5 == 0) {
                                document.write("<hr/>");
                            }
                        }

                        document.write('<button id="process">submit</button>');

                        $(function(){
                            $("#process").click(function(){
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
                                apiContentStr = apiContentStr+paths;
                                // document.write(apiContentStr);
                                // 发送ajax请求
                                $.ajax({
                                    // 编写json格式，设置属性和值
                                    url:"file/ajaxTest2",
                                    contentType:"application/json;charset=UTF-8",
                                    data:apiContentStr,
                                    dataType:"text",
                                    type:"post",
                                    success:function(data){
                                        // data服务器端响应的json的数据，进行解析
                                        // alert(data);
                                        document.write("<h2>Result</h2>");
                                        document.write(
                                            '<textarea name="generatedText" rows="50" cols="80">' +
                                                data +
                                            '</textarea>');
                                    }
                                });

                            });
                        });
                    }
                });

            });
        });
    </script>

</body>
</html>
