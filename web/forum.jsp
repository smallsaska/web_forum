<%@ page import="services.Comment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.common.Attribute" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1"/>
  <title>Saska First web</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style1.css"/>
  <script type="text/javascript" src="http://www.google.com/jsapi"></script>
</head>
<body>
  <form class = "create" action="<c:url value='${pageContext.request.contextPath}/theme/create'/>" method="get">
    <input type="text"  name ="theme" placeholder="Enter a theme title of new theme"/>
    <input type="submit" name ="create" value="Create new theme" />
    <% if (request.getAttribute(Attribute.IFTHEMENAMEISUNIQUE)!=null) {%>
    <label style="color: red" ><%out.print(request.getAttribute(Attribute.IFTHEMENAMEISUNIQUE).toString());%></label>
    <% } %>
  </form>
  <form class = "find" action="<c:url value='${pageContext.request.contextPath}/theme/find'/>" method="get">
    <input type="text" name = "theme" placeholder="Enter a theme title"/>
    <input type="submit" name ="find" value="Find the theme"/>
  </form>
  <form class="add" action ="${pageContext.request.contextPath}/comment/create" style="display: block" method="post">
       <label>
         <%
           String theme = request.getAttribute(Attribute.THEME_NAME).toString();
           out.print(theme);
         %>
       </label>
    <table border="1" width="750px">
      <%
        List<Comment> comments = (List<Comment>)request.getAttribute(Attribute.COMMENTLIST);
        for (Comment comment : comments){
      %>
      <tr>
        <td>
          <%
            out.print(comment.getUserLoginName());
          %>
        </td>
        <td>
          <%
            out.print(comment.getText());
          %>
        </td>
      </tr>
      <%}%>
    </table>
  <%if ((Boolean)request.getAttribute(Attribute.NOTHEME)){%>
    <input type="text" id="new_comment" name = "comment_text" disabled>
    <input type="submit" id="add_new_comment_button" disabled/>
  <%}
  else {%>
    <input type="hidden" name="theme" value="<%out.print(request.getParameter(Attribute.THEME_NAME.toString()));%>" />
    <input type="hidden" name="loginName" value="<%out.print(request.getSession().getAttribute(Attribute.LOGINNAME.toString()));%>" />
    <input type="text" name="comment_text" id="new_comment">
    <input type="submit" id="add_new_comment_button"/>
  <%}%>
  </form>
</body>
</html>