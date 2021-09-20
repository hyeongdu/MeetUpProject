<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<c:set var="user"><sec:authentication property="principal"/></c:set>      
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<link rel="stylesheet" href="/css/carousel.css">
<title>Insert title here</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
function undolikecheck(){
	const result=confirm("해당 클래스의 좋아요를 취소하시겠습니까?");
	if(result){
		undolikeajax();
	}
}

function undolikeajax(){
	var classid = $('#classid').val();
	$.ajax({
		type : 'POST',
		url : 'http://localhost:8081/mypage/undolikeclass',
		data : {
			classid: classid
		},
		dataType: 'json',
		success : function(json) {
			console.log(json);
			if (json.code == "success") {
				alert("좋아요를 취소하였습니다."); 
				window.location.replace("viewlikeclass");
			} else {
				alert("좋아요 취소에 실패하였습니다.(알수없는 오류)");
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
          <div style="margin-bottom:20px;"><span style="font-size: 2.0em;"> 찜한 클래스 </span></div>
          <div class="row placeholders">
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
				        <li><a href="pwcheckview">회원정보수정</a></li>
				        <li><a href="viewapplyclass">신청한 클래스 조회</a></li>
				        <li><a href="viewopenclass">개설한 클래스 조회</a></li>
				        <li class="active"><a href="viewlikeclass">찜한 클래스 조회</a></li>
				     </ul>
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
				</nav>
			  </div>
		  </div>
		<div class="row placeholders" id="classmodule">  
		<c:choose>
		<c:when test="${fn:length(likeclassinfo) eq 0}"> 
			<div class="row placeholders" style="margin:0 auto;width:100%;text-align:center;">
				<h4>찜한 클래스가 없습니다.</h4>
	       </div>
       	</c:when>
       	<c:otherwise>
       	<c:forEach var="lci" items="${likeclassinfo }">  
	       <div class="row placeholders">            
	         <div id="classimage">
	           <img src="/classimages/${lci.tfile }" class="img-responsive"
								alt="Generic placeholder thumbnail">
	         </div>
	         <div id="classinfo">
	         	<div>
	         		<h3><a href="/view/classview?tid=${lci.tid }">${lci.title }</a></h3>
	         		${lci.tintro }
	         	</div>	
	         	<div id="class_control_btn">
	         		<div>
	         			<form id="undolikeform">
	         				<input type="hidden" id="classid" name="classid" value="${lci.tid }">
	         				<button class="btn btn-default btn-sm" onClick="undolikecheck()">좋아요취소</button>
	         			</form>
	         		</div>
	         	</div>	
	         </div>          
	       </div>
       </c:forEach> </c:otherwise></c:choose>        
</div>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

</body>
</html>