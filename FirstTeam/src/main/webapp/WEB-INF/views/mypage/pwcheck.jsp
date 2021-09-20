<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="user"><sec:authentication property="principal"/></c:set>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<link rel="stylesheet" href="/css/carousel.css">
<title>비밀번호 체크</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
function pwcheck(){
	var userid=$('#userid').val();
	var pw=$('#pw').val();
	
	console.log(userid+" : "+pw);
	$.ajax({
		type : 'POST',
		url : 'http://localhost:8081/mypage/pwcheck',
		data : {
			userid : userid,
			pw : pw
		},
		dataType: 'json',
		success : function(json) {
			console.log(json);
			if (json.code == "success") {
				console.log(json.desc);
				window.location.replace("/mypage/modifyuserinfo?userid="+userid);
			} else {
				console.log(json.desc);
/* 				$('#pwCheckMessage').html('비밀번호가 틀렸습니다.'); */
				alert(json.desc);
			}
		}
	});
}
</script>
<style>

.navbar .navbar-nav {
  display: inline-block;
  float: none;
}

#mainnav .navbar-collapse {
  text-align: center;
}

#mypagenav ul li{
	margin-right:20px;
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
	<div style="margin:0 auto; width:1000px;">
		<div style="margin-bottom:20px;"><span style="font-size: 2.0em;"> 비밀번호 확인 </span></div>
		<div class="row placeholders">
	          <!-- <div class="col-xs-12 col-sm-10 placeholder"> -->
	          <div style="margin:0 auto;width:100%;">
				<nav class="navbar navbar-default" id="mypagenav">
				  <div class="container-fluid">
				    <!-- Brand and toggle get grouped for better mobile display -->
				    <div class="navbar-header">
				      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				        <span class="sr-only">Toggle navigation</span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				      </button>
				    </div>			
				    <!-- Collect the nav links, forms, and other content for toggling -->
				    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				      <ul class="nav navbar-nav">
				      	<li><a href="/mypage/mp">마이페이지홈</a></li>
				        <li class="active"><a href="pwcheckview">회원정보수정</a></li>
				        <li><a href="viewapplyclass">신청한 클래스 조회</a></li>
				        <li><a href="viewopenclass">개설한 클래스 조회</a></li>
				        <li><a href="viewlikeclass">찜한 클래스 조회</a></li>
				     </ul>
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
				</nav>
			  </div>
		  </div>
		  <div class="row placeholders" style="width: 100%; padding: 20px;">
           <div style="margin:0 auto;width:60%;">
<%-- 				<form id="pwcheck">
					<table class="table" style="align:center;border:1px solid white;">
						<tr style="display:none;"><td><input type="text" id="userid" name="userid" value="<sec:authentication property="principal.Username"/>"/></td></tr>
						<tr>
							<th scope="row">비밀번호</th>
							<td><input type="password" id="pw" name="pw" size="20"></td>
							<td><input type="button" value="비밀번호 확인" onclick="pwcheck()"></td>
						</tr>
						<tr><th></th><td style="text-align: left;color: red; font-size: 0.8em" colspan="3" id="pwCheckMessage"></td></tr>
					</table>
				</form> --%>
				<div>
					<span style="width:20%;"><strong>비밀번호</strong></span>
					<span style="width:50%;"><input type="password" id="pw" name="pw" size="30"></span>
					<span style="width:10%;"><input type="button" value="비밀번호 확인" onclick="pwcheck()"></span>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

</body>
</html>