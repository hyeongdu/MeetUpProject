<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="user"><sec:authentication property="principal"/></c:set>
<!DOCTYPE html>
<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu"
	crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css"
	integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ"
	crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"
	integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"
	crossorigin="anonymous"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css"
	type="text/css" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>

<script type="text/javascript">
	$(document).ready(
			function() {
				$.datepicker.setDefaults($.datepicker.regional['ko']);
				$("#startDate1")
						.datepicker(
								{
									changeMonth : true,
									changeYear : true,
									nextText : '다음 달',
									prevText : '이전 달',
									dayNames : [ '일요일', '월요일', '화요일', '수요일',
											'목요일', '금요일', '토요일' ],
									dayNamesMin : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									monthNames : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									dateFormat : "yy/mm/dd",
									minDate : 0,
									maxDate : "+3m", // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
									onClose : function(selectedDate) {
										//시작일(startDate) datepicker가 닫힐때
										//종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
										$("#endDate1").datepicker("option",
												"minDate", selectedDate);
										$("#startDate2").datepicker("option","maxDate", selectedDate);
									}

								});
				$("#endDate1")
						.datepicker(
								{
									changeMonth : true,
									changeYear : true,
									nextText : '다음 달',
									prevText : '이전 달',
									dayNames : [ '일요일', '월요일', '화요일', '수요일',
											'목요일', '금요일', '토요일' ],
									dayNamesMin : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									monthNames : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									dateFormat : "yy/mm/dd",
									minDate : 0,
									maxDate : "+3m", // 선택할수있는 최대날짜, ( 0 : 오늘 이후 날짜 선택 불가)
									onClose : function(selectedDate) {
										// 종료일(endDate) datepicker가 닫힐때
										// 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정
										$("#startDate1").datepicker("option",
												"maxDate", selectedDate);
										$("#endDate2").datepicker("option","maxDate", selectedDate);
									}

								});
			});

	$(document).ready(
			function() {
				$.datepicker.setDefaults($.datepicker.regional['ko']);
				$("#startDate2")
						.datepicker(
								{
									changeMonth : true,
									changeYear : true,
									nextText : '다음 달',
									prevText : '이전 달',
									dayNames : [ '일요일', '월요일', '화요일', '수요일',
											'목요일', '금요일', '토요일' ],
									dayNamesMin : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									monthNames : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									dateFormat : "yy/mm/dd",
									minDate : 0,
									maxDate : "+3m", // 선택할수있는 최소날짜, ( 0 : 오늘 이후 날짜 선택 불가)
									onClose : function(selectedDate) {
										//시작일(startDate) datepicker가 닫힐때
										//종료일(endDate)의 선택할수있는 최소 날짜(minDate)를 선택한 시작일로 지정
										$("#endDate2").datepicker("option",
												"minDate", selectedDate);
									}

								});
				$("#endDate2")
						.datepicker(
								{
									changeMonth : true,
									changeYear : true,
									nextText : '다음 달',
									prevText : '이전 달',
									dayNames : [ '일요일', '월요일', '화요일', '수요일',
											'목요일', '금요일', '토요일' ],
									dayNamesMin : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									monthNames : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									dateFormat : "yy/mm/dd",
									minDate : 0,
									maxDate : "+3m", // 선택할수있는 최대날짜, ( 0 : 오늘 이후 날짜 선택 불가)
									onClose : function(selectedDate) {
										// 종료일(endDate) datepicker가 닫힐때
										// 시작일(startDate)의 선택할수있는 최대 날짜(maxDate)를 선택한 시작일로 지정
										$("#startDate2").datepicker("option",
												"maxDate", selectedDate);
									}

								});
			});
</script>

<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=97f32d99acd8c1d93c0887fa00241456&libraries=services"></script>
<script>

	function charge() {
		if(${classinfo.payment eq 'charge'}){
			$('#price')
			.html(
				'<input class="form-control" type="text" name="price" placeholder="금액을 입력하세요.(단위: 원)" value="${classinfo.price}">');
		}else{
			$('#price')
				.html(
					'<input class="form-control" type="text" name="price" placeholder="금액을 입력하세요.(단위: 원)">');
		}
		
	}
	function free() {
		$('#price')
				.html(
					'<input class="form-control" type="hidden" name="price" value="0">');
	}


	function offlineselect() {
		var type = $("#option1").val();
		console.log("tSpaceType:"+type);
		$('#locationselection').show();
		$('#zoom').hide();
		$('#map').hide();
		$('#locationsearch').hide();

		$('#locationselection')
				.html(
						'<div class="btn-group" data-toggle="buttons"><input type="radio" onclick="kakaomap();" name="tSpaceType" id="option3" value="locfix">장소지정</div>');
	}

	function kakaomap() {
		$('#locationsearch').show();
		$('#locationsearch')
				.html(
						'<p>모임주최자가 장소를 직접 지정.&nbsp;도로명주소를 검색하여 위치를 확인하신 후 상세주소를 입력해 주세요.</p>'
								+'<div id="searchdiv"><input type="text" class="form-control" id="searchWord" name="tSpace" placeholder="도로명 주소를 정확하게 입력하세요">'
								+ '<button type="button" class="btn btn-default pull-left" id="searchWordbtn" onclick="searchAction();">검색</button></div>'
								+ '<div id="searchdiv"><input type="text" class="form-control" name="tSpaceDetail" placeholder="상세주소를 입력하세요"></div>');

		$('#map').show();
		mapopen();
	}

	function recommand() {
		$('#locationsearch').show();
		$('#locationsearch').html('<p>모임 장소를 추후에 공지합니다.</p>');
		$('#map').hide();
	}

	function mapopen() {

		if (navigator.geolocation) {

			// GeoLocation을 이용해서 접속 위치를 얻어옵니다
			navigator.geolocation.getCurrentPosition(function(position) {

				var lat = position.coords.latitude, // 위도
				lon = position.coords.longitude; // 경도

				displayMarker(lat, lon);

			});
		}

	}

	function displayMarker(lat, lon) {

		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);

		var locPosition = new kakao.maps.LatLng(lat, lon);

		// 결과값으로 받은 위치를 마커로 표시합니다
		var marker = new kakao.maps.Marker({
			map : map,
			position : locPosition
		});

		// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		map.setCenter(locPosition);

	}

	function searchAction() {
		console.log('searchAction');
		var searchWord = $("#searchWord").val();
		console.log(searchWord);

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();

		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('\'' + searchWord + '\'', function(result,
				status) {

			// 정상적으로 검색이 완료됐으면 
			if (status === kakao.maps.services.Status.OK) {

				var lat = result[0].y
				var lon = result[0].x
				$('#exactlocation').val('exact');
				displayMarker(lat, lon);

			}else{
				alert('도로명 주소를 정확히 입력하지 않으면 제대로 된 장소가 등록되지 않을 수 있습니다.');
				$('#exactlocation').val('nonexact');
				$('#exactlocation').focus();
			}
		});
	}

	function zoom() {
		$('#zoom').show();
		$('#zoom')
				.html(
						'<a href="https://zoom.us/j/9666418146?pwd=R0g0d0d0eUdoQ0txSXdaN3dHNittUT09">ZOOM 접속</a>');
		$('#map').hide();
		$('#locationselection').hide();
		$('#locationsearch').hide();

	}
	function checkwrite(){
		if($('mName').val().length==0 ||$('mTel').val().length==0||$('mEmail').val().length==0){
			alert('모임 담당자 정보는 필수사항입니다.');
			$('mName').focus();
			return false;
		}
		if($('#tIntro').val().length==0){
			alert('모임 소개는 필수입니다.');
			$('tIntro').focus();
			return false;
		}
		if($('#startDate1').val().length==0 || $('#endDate1').val().length==0 || $('#startDate2').val().length==0 || $('#endDate2').val().length==0){
			alert('모임 기간 및 신청 기간을 입력해주세요.');
			return false;
		}
		if($('tMemnum').val().length==0){
			alert('모집인원은 필수사항입니다.');
			return false;
		}
		if($(':radio[name="tSpaceType"]:checked').length < 1){
			alert('모임 장소 유형을 선택해주세요');
			return false;
		}
		if($(':radio[name="payment"]:checked').length < 1){
			alert('모임 가격을 선택해주세요');
			return false;
		}
		else{
			var check_submit=confirm('클래스 수정을 완료하시겠습니까?');
			return check_submit;
		}
	}
</script>

<link href="/css/carousel.css" rel="stylesheet">
<link href="/css/writecss.css" rel="stylesheet">

<style>
.td{display:table-cell;vertical-align:middle;}
.td {vertical-align:center;}

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
	<!--  ********************테이블********************** -->
<div class="container">
<div class="row">
	
	<div style="margin:0 auto; width:1000px;">
		<div style="margin-bottom:20px"><span style="font-weight: bold; font-size: 1.5em;">클래스 개설하기</span></div>
		<form method="post" action="modwritedone" enctype="multipart/form-data" onSubmit="return checkwrite()">
			<table class="table table-bordered table-hover"
				style="text-align: center; border: 1px solid #dddddd;">
				<tbody>
					<tr>
						<td>제목</td>
						<td colspan="4">${classinfo.title }
							<input class="form-control" type="hidden" name="title" value="${classinfo.title }">
						</td>
					</tr>
					<tr>
						<td rowspan="4">기본정보</td>
						<td>카테고리/모임명</td>
						<td colspan="3">
<%-- 							<input class="form-control" type="text" name="tCategory" value="${classinfo.tcategory }"> --%>
							<select class="form-control" name="tCategory">
								<option value="교육">교육</option>
								<option value="문화">문화</option>
								<option value="예술">예술</option>
								<option value="여가">여가</option>
								<option value="기타">기타</option>						
							</select>
						</td>
					</tr>

					<tr>
					<td rowspan="3">문의/연락처</td>
						<td>담당자명</td>
						<td colspan="2"><input class="form-control" type="text" id="mName" name="mName" value="${classinfo.mname }"></td>
					</tr>
					
					<tr>
						<td>전화번호</td>
						<td colspan="2"><input class="form-control" type="text" id="mTel" name="mTel" value="${classinfo.mtel }"></td>
					</tr>
					
					<tr>
						<td>이메일</td>
						<td colspan="2"><input class="form-control" type="text" id="mEmail" name="mEmail" value="${classinfo.memail }"></td>
					</tr>
					<tr>
						<td>간단한 모임소개</td>
						<td colspan="4"><input class="form-control" type="text" id="tIntro" name="tIntro" value="${classinfo.tintro }"></td>

					</tr>
					<tr>
						<td style="width: 110px;"><h5>모임 상세 내용</h5></td>
						<td colspan="4"><textarea class="form-control" rows="10"
								id="editor1" name="tContent" maxlength="2048"
								placeholder="글 내용을 입력하세요.">${classinfo.tcontent }</textarea> <script>
									CKEDITOR.replace('editor1');
								</script></td>
					</tr>

					<tr>
						<td style="width: 110px;">
							<h5>파일 업로드</h5>
						</td>
						<td colspan="4">
							<div class="input-group col-xs-12">
<!-- 								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>  -->
								<input type="file" name="tFile" class="file" placeholder="파일을 업로드하세요."> 
							</div>
							<div class="text_wrapper" style="display:none;"><input type="text" id="tid" name="tid" value="${classinfo.tid }" /></div>
						</td>
					</tr>

					<tr>
						<td rowspan="7">모임 정보</td>
					</tr>

						<tr>
						<td>모임기간</td>
						<td colspan="3"><span class="glyphicon glyphicon-calendar"
							aria-hidden="true"></span><input type="text" id="startDate1"
							name="classStartDate" value="${fn:split(classstartdate,' ')[0]}"> ~ <span
							class="glyphicon glyphicon-calendar" aria-hidden="true"></span><input
							type="text" id="endDate1" name="classEndDate" value="${fn:split(classenddate,' ')[0]}"></td>
					</tr>
					<tr>
						<td>신청기간</td>
						<td colspan="3"><span class="glyphicon glyphicon-calendar"
							aria-hidden="true"></span><input type="text" id="startDate2"
							name="regStartDate" value="${fn:split(regstartdate,' ')[0]}"> ~ <span
							class="glyphicon glyphicon-calendar" aria-hidden="true"></span><input
							type="text" id="endDate2" name="regEndDate" value="${fn:split(regenddate,' ')[0]}"></td>
					</tr>
					<tr>
						<td>모집인원</td>
						<td colspan="3"><input class="form-control" type="text" name="tMemnum" value="${classinfo.tmemnum }"></td>
					</tr>
					<tr>
						<td>참가비</td>
						<c:choose>
						<c:when test="${classinfo.payment eq 'charge'}">
						<td colspan="3">
							<div class="btn-group" data-toggle="buttons">
								<input type="radio" onclick="charge();" name="payment" id="option1" value="charge" checked="checked">유료 
								<input type="radio" onclick="free();" name="payment" id="option2" value="free">무료
							</div>
							<div id="price"></div>
						</td>
						</c:when>
						<c:otherwise>
							<td colspan="3">
							<div class="btn-group" data-toggle="buttons">
								<input type="radio" onclick="charge();" name="payment" id="option1" value="charge">유료 
								<input type="radio" onclick="free();" name="payment" id="option2" value="free" checked="checked">무료
							</div>
							<div id="price"></div>
						</td>
						</c:otherwise>
						</c:choose>
					</tr>
					<c:choose>
					<c:when test="${classinfo.tspacetype eq 'locfix'}">
						<tr>
							<td colspan="4">
								<div class="btn-group" data-toggle="buttons">
									<input type="radio" onclick="offlineselect();" name="tSpaceType"
										id="option1" value="offline" checked="checked">오프라인 
									<input type="radio" onclick="zoom();"name="tSpaceType" 
										id="option2" value="online">온라인	
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" id="onoffselection">
								<div id="locationselection">
									<div class="btn-group" data-toggle="buttons">
										<input type="radio" onclick="kakaomap();" name="tSpaceType" id="option3" value="locfix">장소지정
									</div>
								</div>
								<div id="locationsearch">
									<p>모임주최자가 장소를 직접 지정.&nbsp;도로명주소를 검색하여 위치를 확인하신 후 상세주소를 입력해 주세요.</p>
									<div id="searchdiv"><input type="text" class="form-control" id="searchWord" name="tSpace" placeholder="도로명 주소를 정확하게 입력하세요" value="${classinfo.tspace }">
									<button type="button" class="btn btn-default pull-left" id="searchWordbtn" onclick="searchAction();">검색</button></div>
									<div id="searchdiv"><input type="text" class="form-control"  placeholder="상세주소를 입력하세요"></div>
								</div>
								<div id="zoom"></div>
								<div id="map" style="width: 100%; height: 350px;"></div>
								<div class="text_wrapper" style="display:none;"><input type="text" id="exactlocation" value="exact" /></div>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="4">
								<div class="btn-group" data-toggle="buttons">
									<input type="radio" onclick="offlineselect();" name="tSpaceType"
										id="option1" value="offline">오프라인 <input type="radio" onclick="zoom();"
										name="tSpaceType" id="option2" value="online" checked="checked">온라인	
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" id="onoffselection">
								<div id="locationselection"></div>
								<div id="locationsearch"></div>
								<div id="zoom"><a href="https://zoom.us/j/9666418146?pwd=R0g0d0d0eUdoQ0txSXdaN3dHNittUT09">ZOOM 접속</a></div>
								<div id="map" style="width: 100%; height: 350px;"></div>
								<div class="text_wrapper" style="display:none;"><input type="text" id="exactlocation" value="exact" /></div>
							</td>
						</tr>
					</c:otherwise>
					</c:choose>
					<tr>
						<td style="text-align: left;" colspan="5"><a
							href="viewopenclass" class="btn btn-default pull-right"
							type="submit">목록보기</a>&nbsp;&nbsp; <input
							class="btn btn-default pull-right" type="submit" value="입력">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
</div>
	<script>
		if(${classinfo.tspacetype eq 'offline'}){
			mapopen();
		}
		if(${classinfo.payment eq 'charge'}){
			charge();
		}else if(${classinfo.payment eq 'free'}){
			free();
		}
	</script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>

</body>
</html>