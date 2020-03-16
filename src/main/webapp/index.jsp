<%--
  Created by IntelliJ IDEA.
  User: kamisama
  Date: 2020/3/16
  Time: 下午12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>lunit</title>
</head>
<body>

    <h1>LuNiT</h1>
    <p>light unit test for .tex, which is a component of AutoLatex</p>
    <a href="https://github.com/KingHaremIII/AutoLatex">Click Here for Details of AutoLatex</a>
    <hr/>
    <form action="file/upload" method="post" enctype="multipart/form-data">
        Select your structure configuration file:
        <input type="file" name="upload" width="120px"><br/>
        <input type="submit" value="upload your Structure.xml">
    </form>

</body>
</html>
