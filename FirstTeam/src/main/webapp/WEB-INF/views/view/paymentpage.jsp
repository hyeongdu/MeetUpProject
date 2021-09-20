<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<c:set var="user"><sec:authentication property="principal"/></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>ContentView</title>

    <!-- Bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
<script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
  <!-- jQuery -->
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
  <!-- iamport.payment.js -->
  <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<!-- Custom styles for this template -->
<link href="/css/carousel.css" rel="stylesheet">
<link href="/css/paytable.css" rel="stylesheet">
<style>
.applyclass {
	box-sizing: border-box;
	border: 5px solid gray;
	width:auto; height:auto;
	padding:7px;
	border-radius:0.4em;
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

<div class="container">
	<div class="row">
		<div style="margin:0 auto; width:1000px;">
		  <div style="margin-bottom:20px;"><span style="font-size: 2.0em;">결제하기</span></div>
          <div class="row placeholders" style="width: 100%; padding: 20px; border:1px solid black;">
	          <div class="row placeholders" style="width: 100%;">
	           <div style="float:left; width:30%;">
                 	<img src="/classimages/${classinfo.tfile}" width="200px" class="img-responsive" alt="프로필 이미지">
	           </div>
		       <div style="margin:0 auto;width:70%;">
					<h2 style="font-weight: bold;">${classinfo.title }</h2>
					<div>
						<p>${classinfo.tintro }</p>
					</div>
					<ul>
						<li><p><strong>모임기간</strong> : ${classinfo.classstartdate } ~ ${classinfo.classenddate }</p></li>
						<li><p><strong>모임장소</strong> : ${classinfo.tspace}</p></li>
					</ul>
				</div>
				<c:forEach var="uif" items="${userinfo }">
					<div style="display:none;"> 
						<input type="text" id="classid" name="classid" value="${classinfo.tid }"/>
						<input type="text" id="title" name="title" value="${classinfo.title}"/>
						<input type="text" id="classprice" name="classprice" value="${classinfo.price }"/>
						<input type="text" id="userid" name="userid" value="${uif.userid }"/>
						<input type="text" id="username" name="username" value="${uif.username }"/>
						<input type="text" id="useremail" name="useremail" value="${uif.useremail }"/>
					</div>
				</c:forEach>
				</div>
				<hr/>
         
		 <!--  <div class="row placeholders" style="width: 100%; margin:10px; padding: 20px; border:1px solid #d1d0cd;"> -->
	           <div style="margin:20px; width:90%;">
	           		<p><span style="font-size: 1.2em;">결제 클래스 정보</span></p>
               		<table class="group_table pay" style="margin:0 auto; width:100%;">
					    <thead>
					        <tr>
								<th scope="col" class="group_name">그룹명</th> 
								<th scope="col" class="price">금액</th>
								<th scope="col" class="members">신청인원</th>
								<th scope="col" class="price_applied">신청금액</th>
					        </tr>
					    </thead>
					    <tbody>
					        <tr>					         
						        <td class="group_name">${classinfo.title }</td>           
					        	<td class="price"> ${classinfo.price }원</td>      	
					        	<td class="members">1명</td>
					            <td class="price_applied">${classinfo.price }원</td>
					        </tr>		            		
					     </tbody>
		   			 </table>
				</div>
				<div class="attend_user" style="margin:20px;  width:90%;">
					<p><span style="font-size: 1.2em;">결제 신청자 정보</span></p>
					<c:forEach var="uif" items="${userinfo }">
			        <table style="margin:0 auto; width:100%;">
			        <colgroup>
			        	<col class="table_width head">
			        	<col class="table_width">
			        	<col class="table_width head">
			        	<col class="table_width">
			        </colgroup>
					<tr>
						<th scope="row" >아이디</th>
						<div class="text_wrapper" style="display:none;"><input type="text" id="idvalue" value="<sec:authentication property="principal.Username"/>" /></div>
						<td><strong><sec:authentication property="principal.Username"/></strong></td>
						<th scope="row" >이름</th>
						<td><span class="value_txt">${uif.username2}</span> </td>
					</tr>
					<tr>
						<th scope="row" >이메일</th>
						<td><span class="value_txt">${uif.useremail }</span></td>	
						<th scope="row" >휴대폰번호</th>
						<td><span class="value_txt">${uif.txtMobile1 } - ${uif.txtMobile2 } - ${uif.txtMobile3 }</span></td>		
					</tr>
						
					</table>
				</c:forEach>
          	</div>
          	<div class="attend_user" style="margin:20px;  width:90%;">
          		<p><span style="font-size: 1.2em;">결제 정보</span></p>
          		<table class="group_table pay" style="margin:0 auto; width:100%;">
	          		<tbody>
	          		<tr class="total_price">
					    <th scope="row" style="font-size:14px;">총 결제금액</th>
					    <td colspan="2" style="text-align:left;">
					    	<span class="blue_txt">${classinfo.price }</span><span>원</span>
						</td>
					</tr>
					</tbody>	
				</table>
          	</div>
          	</div>
          	<div class="row placeholders" style="width: 100%; padding: 20px;">
          		<div class="btn_wrap" style="margin:0 auto; width:30%;">
	                   <!-- 나중에결제 체크에 따라 결제하기 혹은 신청완료 표시 -->
	                <button type="button" class="btn btn-primary" style="width:250px;font-size:1.5em;" onclick="requestPay()">결제하기</button>
	            </div>
          	</div>            
        <%--   <div class="row featurette" >
			<hr />
			<table class="group_table pay">
		    <thead>
		        <tr>
					<th scope="col" colspan="4" style="text-align:center; font-size:1.0em">신청 정보</th>
		        </tr>
		    </thead>
		    <tbody>
		        <tr class="" data-group_name="스터디1기" data-group_members="1" data-group_price="80000">
			        <th scope="col" class="group_name">그룹명</th>  
			        <td class="group_name">${classinfo.title }</td>           
		        </tr>
		        <tr>
		        	<th scope="col" class="price">금액</th>
		        	<td class="price"> ${classinfo.price }원</td>		        	
		        </tr>
		        <tr>
		        	<th scope="col" class="members">신청인원</th>
		        	<td class="members">1명</td>
		        </tr>
		        <tr>
		        	<!-- 신청금액 유료일때만 블럭됨 -->
		            <th scope="col" class="price_applied">신청금액</th>
		            <td class="price_applied">${classinfo.price }원</td>
		        </tr>		            		
		     </tbody>
   			 </table>					
		  </div>
		  <div class="row featurette">
			<section class="attend_user">
			    <h4 class="title">신청자 정보 입력</h4>
			    <!-- 모집대상 본인인증 신청자 문구 -->
			        <p class="info_sub_title">개설자 요청에 의해 최초 1회에 한하여 본인인증 사용자만 신청 가능하며, 회원 정보와 연동됩니다.</p>
			    
			    <div class="edit_wrap">
			        <c:forEach var="uif" items="${userinfo }">
			        <table>
			            <colgroup>
			                <col class="table_width head">
			                <col class="table_width">
			                <col class="table_width head">
			                <col class="table_width">
			            </colgroup>
			            <tbody>
			            	
			                <tr>
			                    <th class="user_name" scope="row">이름</th>
			                    
			                    <td class="user_name"><span class="value_txt">${uif.username }</span></td>
			                    <th class="user_email" scope="row">이메일</th>
			                                        <!-- 인증 완료된 사용자의 경우 data-auth = true -->
			                    <td class="user_email" data-auth="false">
			                      <!-- 이메일 인증 완료 시작 -->
			                        <div class="auth_after">
			                            <span class="auth_complete">인증</span>
			                            <span class="value_txt">${uif.useremail }</span>
			                        </div>
			                        <!-- 이메일 인증 완료 끝 -->
			                    </td>
			                </tr>
			                
			
			                <tr>
			                    <th scope="row">아이디</th>
			                    <td>
			                        <span class="value_txt">${uif.userid}</span>
			                        <!-- <input type="text" name="company" maxlength="64" value=""> -->
			                    </td>
			                    <th scope="row">전화번호
			                        <span class="required_txt">(필수)</span>
			                    </th>
			                     <td class="user_phone " data-auth="false">
			                        <!-- 휴대전화 인증 완료 시작 -->
			                        <div class="auth_after">
			                            <span class="auth_complete">인증</span>
			                            <span class="value_txt">${uif.txtMobile1 } - ${uif.txtMobile2 } - ${uif.txtMobile3 }</span>
			                            <button type="button" class="btn_change_phone phone">변경</button>
			                        </div>
			                        <!-- 휴대전화 인증 완료 끝 -->
			                    </td>
			                    </tr>			
			                    <tr>
			                  </tr>
			            </tbody>
			        </table></c:forEach>
			    </div>
			    <!-- 회원일 때 + 소셜회원일 때 끝 -->
			</section>
		  </div>
		  <div class="row featurette">
		  	<section class="attend_payment_info">
			    <h4 class="title">결제정보</h4>
			
			    <!-- 나중에결제하기 : 개설자 통장에서는 없음 -->
			    
			    <div class="edit_wrap">
			    <table>
			    <tbody>
			    <tr class="total_price">
			    <th scope="row">총 결제금액</th>
			    <td colspan="2">
			        <span class="blue_txt">${classinfo.price }</span><span>원</span>
			    </td>
				</tr>
		                <!-- 개설자 통장입금인경우 시작 -->
						<!-- 입금 계좌가 같은 경우 -->
			    <!-- <tr class="payment_type host">
			        <th scope="row">결제방식</th>
			        <td colspan="2">
			            <strong class="sub_title">개설자 통장입금</strong>
			                <ul class="bank_info">
			                        <li>입금은행 : <strong> 국민은행</strong></li>
			                        <li>계좌번호 : <strong> 032211134504</strong></li>
			                        <li>예금주 : <strong> ㅇㅎㄱ</strong></li>
			                                    </ul>
			            <ul class="info_list">
			                <li>* 개설자 통장입금 방식의 모임은 참가비를 개설자 통장으로 직접 입금해야하며, <span class="required_txt">입금확인 및 환불은 신청마감 전까지 개설자에게 직접 요청</span>해야 합니다.</li>
			                <li>* 이후 계좌 정보는 신청내역 &gt; 결제하기 화면에서 확인 가능합니다.</li>
			            </ul>
			        </td>
			    </tr> -->
			
			    </tbody>
			    </table>
			    </div>
			</section>
		  </div>

	           <div class="row featurette">
	               <div class="btn_wrap">
	                   <!-- 나중에결제 체크에 따라 결제하기 혹은 신청완료 표시 -->
	                    <button onclick="requestPay()">결제하기</button>
	                   <a href="/event/239498" class="btn_cancel">취소하기</a>
	               </div>
	           </div> --%>
	</div>
</div>
</div>
	<script>
    function requestPay() {
      // IMP.request_pay(param, callback) 호출
     console.log("requestPay function()");
     var IMP = window.IMP; // 생략해도 괜찮습니다.
  	IMP.init("imp08600014"); // "imp00000000" 대신 발급받은 "가맹점 식별코드"를 사용합니다.
     
  	var user_phone=$('#txtMobile1').val()+'-'+$('#txtMobile2').val()+'-'+$('#txtMobile3').val();
  	var classid=$('#classid').val();
  	console.log(user_phone);
  	IMP.request_pay({ // param
          pg: "html5_inicis",
          pay_method: "card",
          merchant_uid: "merchant_"+new Date().getTime(),
          name: $('#title').val(),
          amount: $('#classprice').val(),
          buyer_email: $('#useremail').val(),
          buyer_name: $('#userid').val(),
          buyer_tel: user_phone
      }, function (rsp) { // callback
          	if (rsp.success) {
        	  $.ajax({
      			type : 'POST',
      			url : 'http://localhost:8081/view/insertpaydb',
      			data : {
      				imp_uid: rsp.imp_uid,
      	            merchant_uid: rsp.merchant_uid,
      	            classid : $('#classid').val(),
      	           	buyer_name: rsp.buyer_name,
      	           	title : rsp.name
      			},
      			dataType: 'json',
      			success : function(json) {
      				console.log(json);      				
      				if (json.code == "success") {
      					alert("결제 전달 성공");
      					window.location.replace("/mypage/viewapplyclass");
      				} else {
      					alert(json.desc);
      				}
      			}
      		});
              
          } else {
        	  alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
          }
      });
    }
  </script>
	<script src="https://code.jquery.com/jquery-1.12.4.min.js" integrity="sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ" crossorigin="anonymous"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

</body>
</html>