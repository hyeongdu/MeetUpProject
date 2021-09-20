<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="user"><sec:authentication property="principal"/></c:set>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

<title>비밀번호 체크</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
function pwcheck(){
	var tid=$('#tid').val();
	var pw=$('#pw').val();
	$.ajax({
		type : 'POST',
		url : 'http://localhost:8081/mypage/pwcheck2',
		data : {
			pw : pw
		},
		dataType: 'json',
		success : function(json) {
			console.log(json);
			if (json.code == "success") {
				window.location.replace("/deleteclasspage?tid="+tid);
			} else {
				$('#pwCheckMessage').html('비밀번호가 틀렸습니다.');
			}
		}
	});
}
</script>
<style>
	.sidebar {
    position: fixed;
    top: 51px;
    bottom: 0;
    left: 0;
    z-index: 1000;
    display: block;
    padding: 20px;
    overflow-x: hidden;
    overflow-y: auto;
    background-color: #f5f5f5;
    border-right: 1px solid #eee;
}
</style>
</head>
<body>
<nav class="navbar navbar-default" style="background-color:#F3F9F9;">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">TeamProject</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/security/mainpage">HOME <span class="sr-only">(current)</span></a></li>
        <li><a href="#">Link</a></li>
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
	        <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.Username"/> 님 환영합니다<span class="caret"></span></a>	        
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
<div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="/modifyuserinfo?userid=<sec:authentication property="principal.Username"/>">회원정보수정 <span class="sr-only">(current)</span></a></li>
            <li><a href="#">비밀번호 변경</a></li>
            <li><a href="#">Analytics</a></li>
            <li><a href="#">Export</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="">Nav item</a></li>
            <li><a href="">Nav item again</a></li>
            <li><a href="">One more nav</a></li>
            <li><a href="">Another nav item</a></li>
            <li><a href="">More navigation</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="">Nav item again</a></li>
            <li><a href="">One more nav</a></li>
            <li><a href="">Another nav item</a></li>
          </ul>
</div>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
<div>
<p><h2>개설한 클래스 삭제를 위해 비밀번호를 입력해주세요.</h2></p>
<form id="pwcheck">
<table class="table" style="align:center">
	<tr style="display:none;"><td><input type="text" id="userid" name="userid" value="<sec:authentication property="principal.Username"/>"/></td></tr>
	<tr style="display:none;"><td><input type="text" id="tid" name="tid" value="${tid }"/></td></tr>
	<tr><th scope="row">비밀번호 확인 </th>
		<td><input type="password" id="pw" name="pw" size="20"></td>
	</tr>
	<tr><th></th><td style="text-align: left;color: red; font-size: 0.8em" colspan="3" id="pwCheckMessage"></td></tr>
	<tr><td><input type="button" value="비밀번호 확인" onclick="pwcheck()"></td></tr>
</table></form></div>
</div>
</div>
</div>
</body>
</html>