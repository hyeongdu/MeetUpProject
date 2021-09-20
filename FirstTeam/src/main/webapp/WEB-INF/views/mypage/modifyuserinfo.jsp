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

<title>회원정보 수정</title>

<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script>
//휴대폰번호에 번호만 입력
function pnCheckFunction(){
	var phone1 = $('#txtMobile2').val();
	var phone2 = $('#txtMobile3').val();
	
	if (isNaN(phone1)) {
		$('#pnCheckMessage').html('휴대전화 번호는 숫자만 입력하세요.');
	} else if(isNaN(phone2)){
		$('#pnCheckMessage').html('휴대전화 번호는 숫자만 입력하세요.');
	} 
	else {
		$('#pnCheckMessage').html('');
	}
}
  
function setEmailDomain(domain){
	$("#email_domain").val(domain);
}
	  //회원정보 수정 양식 체크
	function modifySubmitCheck(){
		 var email_id =$("#email_id").val();
		 var email_domain =$("#email_domain").val();
		 var phone1=$('#txtMobile1').val();
		 var phone2 = $('#txtMobile2').val();
		var phone3 = $('#txtMobile3').val(); 
		
		 if(!email_id){
		      alert("이메일을 입력해주세요");
		    $("#email_id").focus();
		    return;
		  }
		 if(!email_domain){
		      alert("도메인을 입력해주세요");
		    $("#email_domain").focus();
		    return;
		  }
		 //휴대전화번호 양식 체크
		 if(phone1.length!=0 || phone2.length!=0 || phone3.length!=0){
			 if(phone1.length==0 || phone2.length==0 || phone3.length==0){
				 alert("휴대전화번호를 정확하게 입력하세요1.");
				 return;
			 }
			 if(phone2.length!=4 || phone3.length!=4){
				 alert("휴대전화번호를 정확하게 입력하세요2.");
				 return;
			 }
		 }
		 
		//권한정보 동의사항 체크
		 if(!$("#check_3").is(":checked"))
		 {
			 $("#agree").val("nAgree");
		 }else {
			 $("#agree").val("Agree");
		 }

		submit_ajax();
		
	}
	  //회원정보수정 AJAX
	  function submit_ajax(){
			var queryString=$("#form_modifyinfo").serialize();
			var userid=$('#idvalue').val();			
			
			console.log(queryString);
			$.ajax({
				url:'http://localhost:8081/mypage/moduserinfoOk',
				type:'POST',
				data: queryString,
				dataType: 'text',
				success: function(json){
					console.log(json);
					var result=JSON.parse(json);
					if(result.code=="success"){
						alert(result.desc);
						window.location.replace("/mypage/mp?userid="+userid);
					}else{
						alert(result.desc);
					}
				}
			})
		}
</script>
<script>
function checkModal3()
{
	$("#check_3").prop("checked", true);	
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
<!-- Modal -->
<div class="modal fade" id="exampleModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
         <p>      
		회사는 참여신청 기능을 사용하지 않는 모임은 ‘외부접수’로 판단합니다.
		‘모임소개’ 또는 ‘모임상세정보’에 외부접수 방법으로 연결된 url이 존재할 경우 ‘외부접수’로 판단합니다.
		외부접수를 사용하는 모임은 ‘외부접수’ 로 분류하여 관리합니다.
		무료모임을 개설하고 ‘모임소개’ 또는 ‘모임상세정보’에 비용을 받는 것으로 확인되는 경우 ‘유료모임’으로 판단하며 ‘유료모임’으로 분류하여 관리 합니다.
		</p>
		<p>
		
		제 19 조 게시물에 대한 저작권
		이용자가 서비스 내에 게시한 게시물의 저작권은 게시한 이용자에게 귀속됩니다. 또한 회사는 게시자의 동의 없이 게시물을 상업적으로 이용할 수 없습니다. 다만, 비영리 목적인 경우는 그러하지 아니하며, 또한 서비스 내의 게재권을 갖습니다.
		이용자는 서비스를 이용하여 취득한 정보를 임의 가공, 판매하는 행위 등 서비스에 게재된 자료를 상업적으로 사용할 수 없습니다.
		이용자는 회원이 게시하거나 등록하는 서비스 내의 내용물, 게시 내용에 대해 제18조 제1항의 각 호에 해당한다고 판단되는 경우 사전통지 없이 수정, 삭제하거나 이동 또는 등록을 거부할 수 있습니다.
		</p>
		<p>
		제 20 조 서비스 내 링크에 대한 보증
		회사가 제공하는 서비스 내에는 다양한 배너와 링크(Link)를 포함하고 있습니다. 많은 경우 타 사이트의 페이지와 연결되어 있으며, 이는 광고주와의 계약관계에 의하거나 제공받은 콘텐츠의 출처를 밝히기 위한 조치입니다. 서비스 내에 포함되어 있는 링크를 클릭하여 타 사이트의 페이지로 옮겨갈 경우 해당 사이트의 개인정보보호정책은 회사와 무관하므로 새로 방문한 사이트의 정책을 검토하셔야 합니다.
		</p>
		<p>
		제 21 조 정보의 제공
		회사는 회원에게 서비스 이용에 필요가 있다고 인정되는 각종 정보에 대해서 전자우편이나 서신우편 등의 방법으로 회원에게 제공할 수 있습니다.
		회사는 서비스 개선 및 회원 대상의 서비스 소개 등의 목적으로 회원의 동의 하에 추가적인 개인 정보를 요구할 수 있습니다.
		</p>
		<p>
		제 22 조 광고게재 및 광고주와의 거래
		회사가 이용자에게 서비스를 제공할 수 있는 서비스 투자기반의 일부는 광고게재를 통한 수익으로부터 나옵니다. 이용자는 서비스 이용 시 노출되는 광고게재에 대해 동의합니다.
		회사는 서비스상에 게재되어 있거나 본 서비스를 통한 광고주의 판촉활동에 회원이 참여하거나 교신 또는 거래를 함으로써 발생하는 손실과 손해에 대해 책임을 지지 않습니다.
		</p>
		<p>
		제 5 장 손해배상 및 기타사항
		제 23 조 손해배상 등
		회사는 법령상 허용되는 한도 내에서 서비스와 관련하여 본 약관에 명시되지 않은 어떠한 구체적인 사항에 대한 약정이나 보증을 하지 않습니다. 또한, 회사는 개설자가 제공하거나 이용자가 작성하는 등의 방법으로 서비스에 게재된 정보, 자료, 사실의 허위성, 위법성이 객관적으로 확인된 경우를 제외하고 신뢰도, 정확성 등에 대해서는 보증을 하지 않으며, 그와 관련하여 회사의 고의 또는 중과실이 있는 경우를 제외하고 어떠한 책임도 지지 않습니다.
		회사는 회사의 과실로 인하여 이용자가 손해를 입게 될 경우 본 약관 및 법령에 따라 이용자의 손해를 배상하겠습니다. 다만 회사는 아래와 같은 손해에 대해서는 책임을 부담하지 않습니다.
		회사는 천재지변, 전쟁 및 기타 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 대한 책임이 면제됩니다.
		회사는 기간통신 사업자가 전기통신 서비스를 중지하거나 정상적으로 제공하지 아니하여 손해가 발생한 경우 책임이 면제됩니다.
		회사는 서비스용 설비의 보수, 교체, 정기점검, 공사 등 부득이한 사유로 발생한 손해에 회사의 고의 또는 중과실이 있는 경우를 제외하고 책임이 면제됩니다.
		회사는 이용자의 귀책사유로 인한 서비스 이용의 장애 또는 손해에 대하여 회사의 고의 또는 중과실이 있는 경우를 제외하고 책임을 지지 않습니다.
		회사는 이용자의 컴퓨터 오류에 의해 손해가 발생한 경우, 또는 이용자가 신상정보 및 전자우편 주소를 부실하게 기재하여 손해가 발생한 경우 책임을 지지 않습니다.
		회사는 이용자가 서비스를 이용하여 기대하는 이익을 얻지 못하거나 상실한 것에 대하여 책임을 지지 않습니다.
		회사는 이용자가 서비스를 이용하면서 얻은 자료로 인한 손해에 대하여 회사의 고의 또는 중과실이 있는 경우를 제외하고 책임을 지지 않습니다.
		회사는 이용자가 서비스를 이용하며 다른 이용자로 인해 입게 되는 정신적 피해에 대하여 보상할 책임을 지지 않습니다.
		회사는 이용자 상호간 및 이용자와 제3자 상호 간에 서비스를 매개로 발생한 분쟁에 대해 개입할 의무가 없으며, 이로 인한 손해를 배상할 책임도 없습니다.
		</p>
		<p>
		제 24 조 분쟁의 해결
		회사와 이용자는 서비스와 관련하여 발생한 분쟁을 원만하게 해결하기 위하여 필요한 노력을 합니다.
		회사는 이용자로부터 제출되는 불만사항 및 의견을 우선적으로 그 사항을 처리합니다. 다만, 신속한 처리가 곤란한 경우에는 이용자에게 그 사유와 처리 일정을 즉시 통보합니다.
		제 25 조 재판권 및 준거법
		이 약관에 명시되지 않은 사항은 대한민국 관계법령과 상관습에 따릅니다.
		회사의 정액 서비스 회원 및 기타 유료 서비스 이용 회원의 경우 회사가 별도로 정한 약관 및 정책에 따릅니다.
		서비스 이용으로 발생한 분쟁에 대해 소송이 제기되는 경우 민사소송법상의 주소지를 관할하는 법원을 합의관할로 합니다.
		부칙
		책임 한계와 법적고지
		회사는 회원 및 일반 이용자들이 서비스(http://www.onoffmix.com)에서 제공하는 각종 정보와 서비스를 이용하는데 있어 유의하여야 할 사항들을 다음과 같이 고지 합니다.
		</p>
		<p>
		
		회사가 제공하는 서비스에 관한 저작권 및 기타 지적재산권은 회사에 속합니다.
		이용자는 회사가 제공하는 서비스를 무단으로 전재하거나 회사의 사전동의 없이 복제, 전송, 출판, 배포, 방송 기타 방법에 의하여 이용하거나 제3자에게 이용하게 하여서는 안됩니다.
		회사는 회원들에 대한 서비스 제고를 위해 외부의 전문 업체들과 협력, 다양한 정보와 상품 컨텐츠를 제공받고 있습니다.
		회사 및 정보제공자가 제공하는 이들 정보는 오류가 있을 수 있고, 서비스가 지연될 수도 있으며, 이러한 정보의 오류나 서비스의 지연에 대해 회사는 회사의 고의 또는 중과실이 있는 경우를 제외하고 어떠한 책임도 지지 않습니다.
		회사가 직접 주최/주관하는 모임/행사 이외에 회원이 개설한 모임/행사의 컨텐츠 및 상품은 회사의 통제 권한 밖에서 제작되었습니다.
		회사의 통제 권한 밖에서 제작된 컨텐츠의 경우 컨텐츠의 내용이 제3 자에 의하여 개발되었으므로 회사의 책임여부와는 무관하며, 사이트 내에 수록된 자료의 정확성이나, 상표법, 저작권법 등의 관계법령의 준수 여부에 대하여 회사의 고의 또는 중과실이 있는 경우를 제외하고 어떠한 책임도 지지 않습니다.
		회사의 결제수단을 이용하지 않는 모임은 회사의 통제 권한 밖에서 진행되는 행사 입니다.
		회사는 모임 개설자와 모임 참석자간의 사고가 발생하지 않게 하기 위해서 회사의 결제수단을 사용하는 경우 행사종료일 +7일 이후에 정산이 되는 것을 원칙으로 하고 있습니다. 개설자통장입금을 통해서 참석자가 모임 개설자의 계좌번호로 직접 입금을 한 경우에는 회사의 통제 권한이 닿지 못하는 관계로 보호해드릴 수 없습니다.
		이용자는 회사의 서비스를 통하여 욕설, 비방, 외설, 폭력적 내용의 표현, 허위정보 유포, 바이러스 등의 유포 등의 행위를 하여서는 안됩니다.
		이러한 내용을 담고 있거나 담고 있을 것이라고 의심되는 게시물 또는 링크 등은 사전통보 없이 수정 또는 삭제 조치될 수 있으며, 그 정도에 따라 회원탈퇴 및 기소 등의 조치가 취해질 수도 있습니다.
		이용자는 다량의 정보를 전송하거나 동일한 또는 유사한 내용의 정보를 반복적으로 게시하여 서비스의 안정적인 운영을 방해하거나, 광고성 정보를 수신자의 의사에 반하여 전자우편으로 지속적으로 전송 또는 게시판에 게시하여 다른 이용자의 서비스 이용을 방해하는 행위를 하여서는 안됩니다. 이러한 내용을 담고 있거나 담고 있을 것이라고 의심되는 게시물이나 링크 등은 사전통보 없이 수정 또는 삭제 조치될 수 있으며, 그 정도에 따라 회원탈퇴 및 기소 등의 조치가 취해질 수도 있습니다.
		</p>
		<p>
		이용자는 서비스 또는 서비스에 연결되어 있는 서버나 네트워크를 방해 또는 간섭하거나, 서비스에 관련된 네트워크의 요건, 절차, 정책 또는 규정을 준수하지 않는 등의 방법으로 업무나 서비스의 운영을 방해하여서는 안됩니다.
		
		
		온오프믹스의 “책임한계와 법적고지”에 관하여 의문사항이 있으시면 연락 바랍니다.
		
		문의 : (02) 6080-5579 (webmaster@onoffmix.com)
		
		
		[부칙] (2007. 10. 20)
		(시행일) 본 약관은 2007년 10월 20일부터 시행합니다.
		[부칙] (2010. 06. 18)
		(시행일) 본 약관은 2010년 06월 18일부터 시행합니다.
		[부칙] (2013. 02. 14)
		(시행일) 본 약관은 2013년 02월 14일부터 시행합니다.
		[부칙] (2014. 04. 10)
		(시행일) 본 약관은 2014년 04월 10일부터 시행합니다.
		[부칙] (2014. 06. 02)
		전자금융거래이용약관이 삭제 되었습니다.
		(시행일) 본 약관은 2014년 06월 02일부터 시행합니다.
		[부칙] (2017. 06. 14)
		(시행일) 본 약관은 2017년 06월 14일부터 시행합니다.
		[부칙] (2019. 11. 07)
		(시행일) 본 약관은 2019년 11월 07일부터 시행합니다.
		</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기 </button>
        <button type="button" class="btn btn-primary" onclick= "checkModal3()" data-bs-dismiss="modal">동의 </button>
      </div>
    </div>
  </div>
</div>

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
	            <li><a href="#">회원정보 수정</a></li>
	            <li><a href="#">비밀번호 변경</a></li>
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
   <div style="margin-bottom:20px;"><span style="font-size: 2.0em;"> 회원 정보 수정 </span></div>
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

      <div class="row placeholders">       
	     <form id="form_modifyinfo" >
			<c:forEach var="user" items="${userinfo }">
			<table class="table" width="800px">
			<tr style="display:none;"><td><input type="text" id="userid" name="userid" value="<sec:authentication property="principal.Username"/>"/></td></tr>
			<tr>
				<td style="width: 120px; background-color: #ededed;"><h5>아이디</h5></td>
				<div class="text_wrapper" style="display:none;"><input type="text" id="idvalue" value="<sec:authentication property="principal.Username"/>" /></div>
				<td><strong><sec:authentication property="principal.Username"/></strong></td>
			</tr>
			<tr>
				<td style="width: 120px; background-color: #ededed;"><h5>이름</h5></td>
				<td>${user.username2} </td>
			</tr>
			<tr>
				<td style="width: 120px; background-color: #ededed;"><h5>닉네임</h5></td>
				<td>${user.username}</td>		
			</tr>
			<tr><td style="width: 120px; background-color: #ededed;"><h5>휴대전화 번호</h5></td>
				<td>
					<c:choose>
					<c:when test="${user.txtMobile1 eq '010'}">
						<select id="txtMobile1"  name="txtMobile1">
						    <option value="">::선택::</option>
						    <option value="011" >011</option>
						    <option value="010" selected="selected">010</option>
						</select>-
					</c:when>
					<c:when test="${user.txtMobile1 eq '011'}">
						<select id="txtMobile1"  name="txtMobile1">
						    <option value="">::선택::</option>
						    <option value="011" selected="selected">011</option>
						    <option value="010" >010</option>
						</select>-
					</c:when>
					<c:otherwise>
						<select id="txtMobile1"  name="txtMobile1">
					    <option value="">::선택::</option>
					    <option value="011" >011</option>
					    <option value="010">010</option>
						</select>-
					</c:otherwise></c:choose>
					<input  onkeyup="pnCheckFunction();" type="text" id="txtMobile2" name="txtMobile2" value="${user.txtMobile2 }" size="4" />-
					<input  onkeyup="pnCheckFunction();" type="text" id="txtMobile3"  name="txtMobile3" value="${user.txtMobile3 }" size="4" />
				</td>
				<td style="text-align: left;color: red; font-size: 0.8em" colspan="3" id="pnCheckMessage">				
				</td>
			</tr>
			<!-- <tr><th></th><td style="text-align: left;color: red; font-size: 0.8em" colspan="3" id="pnCheckMessage"></td></tr> -->
			<tr>
			<td style="width: 120px; background-color: #ededed;"><h5>이메일</h5></td> 
			<td><input type="text" id="email_id" name="email_id" class="form_w200" value="${user.email_id }" title="이메일 아이디" placeholder="이메일" maxlength="18" > @ 
			<input type="text" id="email_domain" name="email_domain" class="form_w200" value="${user.email_domain }" title="이메일 도메인" placeholder="이메일 도메인" maxlength="18"> 
			<select class="select" title="이메일 도메인 주소 선택" onclick="setEmailDomain(this.value);return false;">
			    <option value="">-선택-</option>
			    <option value="naver.com">naver.com</option>
			    <option value="gmail.com">gmail.com</option>
			    <option value="hanmail.net">hanmail.net</option>
			    <option value="hotmail.com">hotmail.com</option>
			    <option value="korea.com">korea.com</option>
			    <option value="nate.com">nate.com</option>
			    <option value="yahoo.com">yahoo.com</option>
			</select>
			</td>
			</tr>

			<tr>
				<td style="width: 120px; background-color: #ededed;">
				<h5>성별</h5>
				</td>
				<c:choose>
				<c:when test="${user.usergender eq 'man'}">
				<td colspan="2">
				<input type="radio" name="usergender" value="man" checked="checked">남 &nbsp;
				<input type="radio" name="usergender" value="woman">여<br>
				</td>
				</c:when>
				<c:when test="${user.usergender eq 'woman'}">
				<td colspan="2">
				<input type="radio" name="usergender" value="man">남 &nbsp;
				<input type="radio" name="usergender" value="woman" checked="checked">여<br>
				</td>
				</c:when>
				<c:otherwise>
				<td colspan="2">
				<input type="radio" name="usergender" value="man">남 &nbsp;
				<input type="radio" name="usergender" value="woman">여<br>
				</td></c:otherwise>				
				</c:choose>
			</tr>			
			<!-- <tr><td style="width: 110px; background-color: #ededed;"><h5>생년월일 </h5></td><td><input type="text" id="address" name="address" size="50" value=""></td></tr> -->
			<tr>
				<td style="width: 120px; background-color: #ededed;"><h5>마켓팅 수신동의</h5></td>
				<c:choose>
				<c:when test="${user.marketcheck eq 'y'}">
				<td colspan="2">
					  <input type="checkbox" id="check_3" class="normal" checked="checked">
					  <label for="check_3">(선택)마케팅 수신 동의 </label>
				</td>
				</c:when>
				<c:otherwise>
				<td colspan="2">
					  <input type="checkbox" id="check_3" class="normal">
					  <label for="check_3">(선택)마케팅 수신 동의 </label>
					  <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#exampleModal3">
				 	 	내용보기
					  </button>
				</td>
				</c:otherwise>
				</c:choose>
				
				<input type="hidden" value="agree" name="agree" id="agree">
			</tr>
			<th colspan="2" align="right"><input type="button" onclick="modifySubmitCheck()" value="회원정보수정"></th>
			</table></c:forEach>
			</form>	
			</div>
        </div>        
</div>
</div>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</body>
</html>