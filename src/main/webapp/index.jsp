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
        // page loading, bind event.
        $(function(){
            $("#upload").click(function(){
                var text = $('#uploadText').val();
                // transfer ajax
                $.ajax({
                    url:"file/upload",
                    contentType:"application/json;charset=UTF-8",
                    data:text,
                    dataType:"text",
                    type:"post",
                    success:function(paths){
                        paths = paths.substring(1, lenPaths);
                        var splieted = paths.split(",");
                        var lenPaths = splieted.length-1;
                        for (let i=0;i<lenPaths;i++) {
                            var pathSingle = splieted[i].substring(1, splieted[i].length-1);
                            document.write('<input type="checkbox" name="path" value='+i+'>');
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
                                        apiContentStr += box[i].value+"/";
                                    }
                                }
                                if(apiContentStr == "" || apiContentStr.length == 0){
                                    alert("You must select at least one section! ");
                                    return;
                                }
                                var filename = splieted[lenPaths];
                                filename = filename.substring(1, filename.length-2);
                                apiContentStr = apiContentStr+filename;
                                // transfer ajax
                                $.ajax({
                                    url:"file/generate",
                                    contentType:"application/json;charset=UTF-8",
                                    data:apiContentStr,
                                    dataType:"text",
                                    type:"post",
                                    success:function(data){
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
