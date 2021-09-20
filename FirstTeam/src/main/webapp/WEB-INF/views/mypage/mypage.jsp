<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="user"><sec:authentication property="principal"/></c:set>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<link rel="stylesheet" href="/css/carousel.css">
<title>MyPage</title>
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>

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
<!-- /. modal -->
<div class="modal fade" id="profileModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <form method="post" action="/mypage/profileupdateaction" enctype="multipart/form-data">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">프로필 사진 수정</h4>
	      </div>
	      <div class="modal-body">  
	      	<div class="row placeholders">  
	        <div style="float:left; width:30%;margin:20px;position:relative;display:inline-block;">
	        	<c:forEach var="dto" items="${userinfo }">
	               <p><img src="/image/${dto.userprofile }" width="200px" class="img-responsive" alt="프로필 이미지"></p>
	            </c:forEach>
	        </div>
	        <div style="float:left; position:relative;display:inline-block;margin-left:10px;margin-top:20px;">	        	
	        	<p>마이페이지의 프로필 사진을 변경해보세요.</p>
	        	<p>업로드한 사진은 가로 200px 원본 비율에 맞춰<br/> 등록됩니다.</p>
	        	<br/>
	        	<input type="file" name="files" class="file">        	
	        </div>
	        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
	        <button class="btn btn-primary" type="submit" value="등록">사진 등록</button>
	      </div>
    </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->
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
          <div style="margin-bottom:20px;"><span style="font-size: 2.0em;"> 마이페이지 </span></div>
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
				      	<li class="active"><a href="/mypage/mp">마이페이지홈</a></li>
				        <li><a href="pwcheckview">회원정보수정</a></li>
				        <li><a href="viewapplyclass">신청한 클래스 조회</a></li>
				        <li><a href="viewopenclass">개설한 클래스 조회</a></li>
				        <li><a href="viewlikeclass">찜한 클래스 조회</a></li>
				     </ul>
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
				</nav>
			  </div>
		  </div>
          <div class="row placeholders" style="width: 100%; padding: 20px; border:1px solid black;">
           <div style="float:left; width:30%;">
      			<c:forEach var="dto" items="${userinfo }">
                	<img src="/image/${dto.userprofile }" style="margin:0 auto;width:200px" class="img-responsive" alt="프로필 이미지">
                </c:forEach>
             	<div style="margin:0 auto; width:115px;margin-top:10px;">
             		<span class="text-muted"><button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#profileModal">프로필 사진 변경</button></span>
             	</div>
            </div>
            <!-- <div class="col-xs-12 col-sm-6 placeholder" style="position:relative; left:50px;">  -->
            <div style="float:left; margin:0 auto;">
            <c:forEach var="dto" items="${userinfo }">
	            <h4>회원 정보</h4>
	            <form method="post" >
					<table class="table table-bordered" style="width:400px;">
					<tr>
						<th scope="row" >아이디</th>
							<div class="text_wrapper" style="display:none;"><input type="text" id="idvalue" value="<sec:authentication property="principal.Username"/>" /></div>
						<td><strong><sec:authentication property="principal.Username"/></strong></td>
					</tr>
					<tr>
						<th scope="row" >이름</th>
						<td>${dto.username2} </td>
					</tr>
					<tr>
						<th scope="row" >닉네임</th>
						<td>${dto.username}</td>		
					</tr>
					<tr>
						<th scope="row" >성별</th>
						<td>${dto.usergender}</td>		
					</tr>
					<tr>
						<th scope="row" >이메일</th>
						<td>${dto.useremail }</td>	
					</tr>
					<tr>
						<th scope="row" >휴대폰번호</th>
						<td>${userphone}</td>		
					</tr>
						
					</table>
				</form>	
			</c:forEach>			
            </div>
            <div style="overflow:hidden; padding:20px; height:340px; margin:0 auto;">
	            <ul class="list-group" style="margin:auto;padding-top:80px;">
					  <li class="list-group-item">
					    <span class="badge"><a style="color:white;" href="viewapplyclass">${joinclass}</a></span>
					    신청한 클래스
					  </li>
					  <li class="list-group-item">
					    <span class="badge"><a style="color:white;" href="viewopenclass">${openclass }</a></span>
					    
					    개설한 클래스
					  </li>
					  <li class="list-group-item">
					    <span class="badge"><a style="color:white;" href="viewlikeclass">${likecount }</a></span>
					    찜한 클래스
					  </li>
				</ul>
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