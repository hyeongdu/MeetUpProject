<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<c:set var="user"><sec:authentication property="principal"/></c:set>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous" >
<!-- <link href="/webjars/bootstrap/5.0.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous"> -->
<link rel="stylesheet" href="/css/carousel.css">
<link rel="stylesheet" href="/css/login.css">
<title>LoginFORM</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<style>

.navbar .navbar-nav {
  display: inline-block;
  float: none;
}

.navbar .navbar-collapse {
  text-align: center;
}

</style>
</head>
<body>
 <nav class="navbar navbar-default" style="background-color:#F4F8FB;">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
		<a class="navbar-brand" href="/security/mainpage"><span style="font-style: italic ; font-weight: bold; font-size: 1.5em;">MeetUp</span> <span class="sr-only">(current)</span></a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="/security/mainpage">HOME</a></li>
        <li><a href="/view/allclassview" >전체 클래스 목록보기</a></li>
        <li><a href="/view/meetupwriteview">클래스 개설하기</a></li>
      </ul>
	      <c:choose>
	      <c:when test="${user eq 'anonymousUser'}">
	     	<ul class="nav navbar-nav navbar-right">
	        <li><a href="/security/loginpage">로그인</a></li>
	       	<li><a id="gnbJoin" href="/security/joinForm" >회원가입</a></li>
	      	</ul>
	      </c:when>
	      <c:otherwise> 
	      	<c:set var="user"><sec:authentication property="principal.Username"/></c:set>
	      	<ul class="nav navbar-nav navbar-right">
	        <li class="dropdown">
	        <li><a href="#" class="point "><img src="http://www.icounseling.co.kr/images/icon_mypage.jpg" alt=""></a></li>
	        <li>
	        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.Username"/> 님 환영합니다<span class="caret"></span></a>	        
	         	<ul class="dropdown-menu">
	            <li><a href="/mypage/mp">마이페이지 이동</a></li>
	            <li><a href="/mypage/pwcheckview">회원정보 수정</a></li>
	            <li role="separator" class="divider"></li>
	            <li><a href="/logout">로그아웃</a></li>
	          </ul></li>
	        </li>
			</ul>
	      </c:otherwise>
	      </c:choose>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="container-fluid">
<div class="row">
	<div style="margin:0 auto; width:700px;">
		<div id="coverall">
			<div id="loginform">
				<form action="<c:url value="j_spring_security_check" />" method="post" id="login-form" class="login-form" autocomplete="off" role="main">
					<div style="margin-left:30px;margin-top:20px;"><span style="font-size: 2.0em;"><strong> 로그인 </strong></span></div>
					<div class="loginform">
						<div class="loginid">
				   			<input type='text' id="loginid" name="j_username" class="text" placeholder="아이디를 입력하세요" tabindex="1" required />
				   		</div>
				   		<div class="loginpw">
				   			<input type='password' id="loginpw" name="j_password" class="text" placeholder="비밀번호를 입력하세요" tabindex="2" required />   				
			   			</div>
			   			<c:if test="${param.error !=null}">
					      	<div class="errorcheckmsg">${error_message}</div>
					    </c:if> 
			   			<div><input type="submit" value="Log In" /></div>
			   			<hr style="width:350px;"/>
			   			<!-- <div id="naverlogin"><input type="button" value="Naver LOG IN" style="background:#38E13A" onClick="location.href='/oauth2/authorization/naver'"></div>
						<div id="kakaologin"><input type="button" value="Kakao LOG IN" style="background:#EDEC1D" onClick="location.href='/oauth2/authorization/kakao'"></div>
						<div id="googlelogin"><input type="button" value="GOOGLE LOG IN" style="background:#EF5144" onClick="location.href='/oauth2/authorization/google'"></div> -->
						<div class="recjoin">아직 회원이 아니신가요? &nbsp; <a href="/security/joinForm">회원가입</a></div>
					</div>					
				</form>
			</div>

		</div>
		
	</div>
</div>
</div>
</body>
</html>