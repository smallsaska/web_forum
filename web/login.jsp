<%@ page import="com.common.Attribute" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1"/>
    <title>Saska First web</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
</head>
<body>

<div id="wrap">
    <div id="regbar">
        <div id="navthing">
            <h2><a href="#" id="loginform">Login</a> | <a href="#" id="registerform">Register</a></h2>


            <div class="login">
                <div class="arrow-up"></div>
                <div class="formholder">
                    <div class="randompad">
                        <fieldset>
                            <form class="" action="${pageContext.request.contextPath}/login" method="post">
                                <label name="loginName">Login name</label>
                                <input type="text" name="loginName_l" placeholder="Login name"/>
                                <label name="password">Password</label>
                                <input type="password" name="password_l" placeholder="Password"/>
                                <% if (request.getAttribute(Attribute.ERRORREASON) != null) {%>
                                <div style="color: red">
                                    <%= request.getAttribute(Attribute.ERRORREASON)%>
                                </div>
                                <% } %>
                                <input type="submit" value="Log in"/>
                            </form>
                        </fieldset>

                    </div>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="r">
                    <div class="arrow-up"></div>
                    <div class="formholder">
                        <div class="randompad">
                            <fieldset>
                                <label name="loginName">Login name</label>
                                <input type="text" id="loginName_r" name="loginName_r" placeholder="Login name"/>
                                <label name="loginName">First name</label>
                                <input type="text" id="firtsName_r" name="firstName_r" placeholder="First name"/>
                                <label name="loginName">Last name</label>
                                <input type="text" id="lastName_r" name="lastName_r" placeholder="Last name"/>
                                <label name="password">Password</label>
                                <input type="password" id="password_r" name="password_r" placeholder="Password"/>
                                <label name="password">Repeat password</label>
                                <input type="password" id="password_r_r" name="password_r_r" placeholder="Password"/>
                                <% if (request.getAttribute(Attribute.IFUSERNAMEISUNIQUE)!=null) {%>
                                <label style="color: red" ><%out.print(request.getAttribute(Attribute.IFUSERNAMEISUNIQUE).toString());%></label>
                                <% } %>
                                <input type="submit" value="Register"/>


                            </fieldset>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src='http://codepen.io/assets/libs/fullpage/jquery.js'></script>

<script src="js/index.js"></script>

</body>
</html>