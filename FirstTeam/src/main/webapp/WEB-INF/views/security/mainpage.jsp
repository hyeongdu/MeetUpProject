<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ page import="com.study.springboot.oauth2.SessionUser" %>   
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="user"><sec:authentication property="principal"/></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>secmainpage</title>
    <!-- Bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous" >
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<!-- Custom styles for this template -->
<link rel="stylesheet" href="/css/carousel.css">
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
	      	
	      	<ul class="nav navbar-nav navbar-right">
	        <li class="dropdown">
	        <li><a href="#" class="point "><img src="http://www.icounseling.co.kr/images/icon_mypage.jpg" alt=""></a></li>
	        <li>
	        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><sec:authentication property="principal.Username"/>님 환영합니다<span class="caret"></span></a>	        
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

    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->
<div class="container">
<div class="row">  
    <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class=""></li>
        <li data-target="#myCarousel" data-slide-to="1" class=""></li>
        <li data-target="#myCarousel" data-slide-to="2" class="active"></li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div class="item">
          <img class="first-slide" src="https://cfile1.onoffmix.com/attach/x9zrIVdy4eASnqaRCXYpLWEhogPMGiZU" alt="First slide">
          <div class="container">
            <div class="carousel-caption">
              <h1></h1>
              <p></p>
<!--               <p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p> -->
            </div>
          </div>
        </div>
        <div class="item">
          <img class="second-slide" src="https://cfile1.onoffmix.com/attach/73OktDlEIRWqjgZz1SfoHdnVhYuv8CTB" alt="Second slide">
          <div class="container">
            <div class="carousel-caption">           
            </div>
          </div>
        </div>
        <div class="item active">
          <img class="third-slide" src="https://cfile1.onoffmix.com/attach/V0POg8H9yLcNBWXuqvlahEwStrTCeUxK" alt="Third slide">
          <div class="container">
            <div class="carousel-caption">
              
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div><!-- /.carousel -->  
        <div style="margin:0 auto; width:750px;">
		    <div class="input-group">
                <div class="input-group-btn search-panel">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    	<span id="search_concept">Filter by</span> <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                      <li><a href="#contains">Contains</a></li>
                      <li><a href="#its_equal">It's equal</a></li>
                      <li><a href="#greather_than">Greather than ></a></li>
                      <li><a href="#less_than">Less than < </a></li>
                      <li class="divider"></li>
                      <li><a href="#all">Anything</a></li>
                    </ul>
                </div>
                <input type="hidden" name="search_param" value="all" id="search_param">         
                <input type="text" class="form-control" name="x" placeholder="검색어로 밋업 찾기">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search"></span></button>
                </span>
            </div>
        </div>
        <br/>
        
    <div class="container marketing" style="margin:20px;">
	    <hr/>
	    <div style="margin-bottom:20px"><span style="font-weight: bold; font-size: 1.5em;">밋업 개설 모임</span></div>

      <!-- Three columns of text below the carousel -->
      <div class="row">
       <c:forEach items="${rblist}" var="dto" varStatus="status">
        <div class="col-lg-3">
        <div class="thumbnail" style="width: 250px; height: 350px; ">         
          <img src="<c:url value='/classimages/${dto.tfile }'/>" alt="Class Image" style="width:150px;height:200px">
          <div id="classviewinfo"> 
	          <div id="view_classtitle"><strong>${dto.title}</strong></div>          
	          <p>  
	          <c:if test="${dto.payment eq 'charge' }">
	          &nbsp;유료 [<img src="<c:url value='/images/won.png' />" height="10px;" > ${dto.price }]
	          </c:if>
	          <c:if test="${dto.payment eq 'free' }">
	          &nbsp;무료
	          </c:if>
	          </p>
	          <div id="viewdetails"><p><a class="btn btn-default" href="/view/classview?tid=${dto.tid }" role="button">클래스 상세보기 »</a></p></div>
        </div>
        </div>
        </div><!-- /.col-lg-4 -->
        </c:forEach>
        </div><!-- /.col-lg-4 -->
     </div><!-- /.row -->
	</div>
	</div>

	<script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</body>
</html>