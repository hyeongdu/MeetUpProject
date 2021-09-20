package com.study.springboot;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.study.springboot.service.IBoardService;
import com.study.springboot.service.IClassService;
import com.study.springboot.service.IMypageService;
import com.study.springboot.service.IPaymentService;
import com.study.springboot.service.IUserService;

@Controller
public class MyController {
	@Autowired
	IUserService us;
	@Autowired
	IBoardService bs;
	@Autowired
	IClassService cs;
	@Autowired
	IMypageService ms;
	@Autowired
	IPaymentService ps;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String root() throws Exception {
		return "redirect:security/mainpage";
	}

	@RequestMapping("/view/calendartest")
	public String CalendarTest() {
		return "/view/calendartest";
	}

	@RequestMapping("/security/loginpage")
	public String loginPage() {
		return "/security/login";
	}
	@RequestMapping("/mypage/mypage")
	public String myPage() {
		return "/mypage/mypage";
	}
//
//	@RequestMapping("/mypage/modifyuserinfo")
//	public String modifyUserinfo() {
//		return "/mypage/modifyuserinfo";
//	}

	@RequestMapping("/security/joinForm")
	public String joinForm() {
		return "/security/joinForm";
	}

	@RequestMapping("/security/main")
	public String mainForm() {
		return "/security/main";
	}

	@RequestMapping("mypage/pwcheckview")
	public String pwCheckView() {
		return "mypage/pwcheck";
	}

	@RequestMapping("view/meetupwriteview")
	public String meetupwriteview() throws Exception {

		return "view/writeview";
	}

	@RequestMapping("mypage/pwcheck2view")
	public String pwCheck2View(HttpServletRequest request) {
		String tid = request.getParameter("classid");
		String data = "mypage/pwcheck2?tid=" + tid;

		return data;
	}

	// sns 회원가입시 sns 이메일값을 가지고 회원가입 페이지로 이동
	@RequestMapping("/security/snsjoin")
	public String snsJoinForm(@RequestParam("userid") String userid, @RequestParam("authority") String authority,
			Model model) {
		System.out.println("snsJoinForm 무사입성");

		model.addAttribute("userid", userid);
		model.addAttribute("authority", authority);
		return "/security/snsjoin";
	}

	// kakao 회원가입시 kakao res.id값을 가지고 회원가입 페이지로 이동
	@RequestMapping("/security/kakaojoin")
	public String kakaoJoinForm(@RequestParam("userid") String userid, @RequestParam("authority") String authority,
			Model model) {
		System.out.println("kakaoJoinForm ");

		model.addAttribute("userid", userid);
		model.addAttribute("authority", authority);
		return "/security/kakaojoin";
	}

	@RequestMapping("/security/checkid")
	public @ResponseBody String joinIDCheck(HttpServletRequest request) {
		System.out.println("joinIDcheck 들어옴");

		String userId = request.getParameter("userID");
		int nResult = us.IDcheck(userId);
		System.out.println("idcheck: " + nResult);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"fail\",\"desc\":\"아이디가 이미 존재합니다.\"}";
		} else {
			json = "{\"code\":\"success\",\"desc\":\"사용가능한 아이디입니다.\"}";
		}

		return json;

	}

	// mypage로 들어갈 때 session에 저장된 회원아이디를 통해 db 정보를 갖고 접속함
	@RequestMapping("/mypage/mp")
	public ModelAndView myPage(HttpServletRequest request) {
		System.out.println("mypage mapping 들어옴");

		ModelAndView mv = new ModelAndView();
		mv = ms.goMypage();
		return mv;
	}

	@RequestMapping("/security/join")
	public @ResponseBody String join(HttpServletRequest request, Model model) {
		System.out.println("join()");

		String userId = request.getParameter("userID");
		String userpw = request.getParameter("userPassword1");
		String username = request.getParameter("userName");
		String username2 = request.getParameter("userName2");
		String useremail = request.getParameter("usereMail");
		String authority = request.getParameter("authority");
		String marketCheck = request.getParameter("marketCheck");

		System.out.println(userId);
		userpw = passwordEncoder.encode(userpw);
		System.out.println(userpw);

		Map<String, String> map = new HashMap<String, String>();
		map.put("id", userId);
		map.put("pw", userpw);
		map.put("name", username);
		map.put("name2", username2);
		map.put("email", useremail);
		map.put("authority", authority);
		map.put("userprofile", "profileImage.png");
		// 마켓팅 동의 체크여부 확인
		if ((marketCheck).equals("nAgree")) {
			marketCheck = "n";
		} else if ((marketCheck).equals("Agree")) {
			marketCheck = "y";
		}

		System.out.println(marketCheck);

		map.put("check", marketCheck);

		int nResult = us.join(map);
		System.out.println("insert userInfo: " + nResult);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\",\"desc\":\"회원가입에 성공하였습니다.\"}";
		} else {
			json = "{\"code\":\"fail\",\"desc\":\"오류로 회원가입에 실패하였습니다.\"}";
		}

		return json;
	}

	// 회원정보 수정시 비밀번호확인
	@RequestMapping("mypage/pwcheck")
	public @ResponseBody String pwcheck(HttpServletRequest request) {
		System.out.println("pwcheck()");

		String pw = request.getParameter("pw");

		int nResult = us.pwCheck(pw);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\",\"desc\":\"비밀번호가 일치합니다.\"}";
		} else {
			json = "{\"code\":\"fail\",\"desc\":\"비밀번호가 틀렸습니다.\"}";
		}
		return json;
	}

	// 개설한 클래스 삭제시 비밀번호확인
	@RequestMapping("mypage/pwcheck2")
	public @ResponseBody String pwcheck2(HttpServletRequest request) {
		System.out.println("pwcheck2()");

		String pw = request.getParameter("pw");

		int nResult = us.pwCheck(pw);

		String json = "";
		if (nResult == 1) {
			json = "{\"code\":\"success\",\"desc\":\"비밀번호가 일치합니다.\"}";
		} else {
			json = "{\"code\":\"fail\",\"desc\":\"비밀번호가 틀렸습니다.\"}";
		}
		return json;
	}

	@RequestMapping("mypage/modifyuserinfo")
	public ModelAndView modifyUserInfo(HttpServletRequest request) {
		System.out.println("modifyuserinfo()");

		ModelAndView mv = new ModelAndView();

		mv.addObject("userinfo", us.getUserInf());
		mv.setViewName("mypage/modifyuserinfo");

		return mv;
	}

	// 회원정보수정
	@RequestMapping("mypage/moduserinfoOk")
	public @ResponseBody String modifyUserInfoOk(HttpServletRequest request) {
		System.out.println("moduserinfoOk()");

		String userid = request.getParameter("userid");
		String phone1 = request.getParameter("txtMobile1");
		String phone2 = request.getParameter("txtMobile2");
		String phone3 = request.getParameter("txtMobile3");
		String email_id = request.getParameter("email_id");
		String email_domain = request.getParameter("email_domain");
		String usergender = request.getParameter("usergender");
		String marketCheck = request.getParameter("agree");

		// 마켓팅 동의 체크여부 확인
		if ((marketCheck).equals("nAgree")) {
			marketCheck = "n";
		} else if ((marketCheck).equals("Agree")) {
			marketCheck = "y";
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("txtMobile1", phone1);
		map.put("txtMobile2", phone2);
		map.put("txtMobile3", phone3);
		map.put("email_id", email_id);
		map.put("email_domain", email_domain);
		map.put("usergender", usergender);
		map.put("marketcheck", marketCheck);

		int nResult = us.modifyUserInfo(map);
		String json = "";

		if (nResult == 0) {
			json = "{\"code\":\"fail\",\"desc\":\"회원정보 수정에 실패하였습니다.\"}";
		} else {
			json = "{\"code\":\"success\",\"desc\":\"회원정보 수정에 성공하였습니다.\"}";
		}

		return json;
	}

	@RequestMapping("mypage/profileupdate")
	public String profileupdate() {
		return "/mypage/profileupdate";
	}

	@PostMapping("mypage/profileupdateaction")
	public ModelAndView uploadSingle(HttpServletRequest request) throws Exception {
		System.out.println("mypage/profileupdateaction");
		int size=1024*1024*10;
		String file="";
		String orifile="";

		String basePath=ResourceUtils
				.getFile("classpath:static/image/").toPath().toString();
		MultipartRequest multi=new MultipartRequest(request ,basePath,size, 
										"UTF-8", new DefaultFileRenamePolicy());
		Enumeration files=multi.getFileNames();
		String str=(String)files.nextElement();
		
		file=multi.getFilesystemName(str);
		orifile=multi.getOriginalFileName(str);
		System.out.println("file: "+file+", orifile: "+orifile);

		ModelAndView mv = new ModelAndView();

		us.profileUpdate(file);
		mv.setViewName("redirect:mp");

		return mv;
	}

	@RequestMapping("security/mainpage")
	public ModelAndView secMainPage(HttpServletRequest request) {
		System.out.println("secMainPage()");

		ModelAndView mv = new ModelAndView();
		mv = bs.AllBoardInfo();
		mv.setViewName("security/mainpage");

		return mv;
	}

	@RequestMapping("view/classview")
	public ModelAndView classContentView(HttpServletRequest request) {
		System.out.println("classContentView()");

		String tid = request.getParameter("tid");
		System.out.println("tid: "+tid);
		ModelAndView mv = new ModelAndView();
		
		mv = bs.viewClassInfo(tid);

		return mv;
	}

	@RequestMapping("view/location")
	public @ResponseBody String locationList(HttpServletRequest request, Model model) throws Exception {
		System.out.println("view/location");
		String tid = request.getParameter("tid");
		System.out.println(tid);

		String json = cs.locationRecommand(tid);

		return json;

	}

	// 클래스 신청하기
	@RequestMapping("view/applyclass")
	public @ResponseBody String applyClass(HttpServletRequest request) {
		System.out.println("view/applyclass");
		String tid = request.getParameter("tid");
		String title = request.getParameter("title");
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String address = request.getParameter("address");
		System.out.println(tid + title + lat + lon + address);
		int nResult = cs.applyClass(tid, title, lat, lon, address);


		String data = "redirect:classview?tid=" + tid;

		return data;
	}

	// 전체 클래스 조회
	@RequestMapping("view/allclassview")
	public ModelAndView ViewClass(HttpServletRequest request) {
		System.out.println("view/allclassview");

		ModelAndView mv = new ModelAndView();
		mv = bs.AllBoardInfo();

		mv.setViewName("view/classview");
		return mv;
	}

	// 신청한 클래스 목록보기
	@RequestMapping("mypage/viewapplyclass")
	public ModelAndView viewApplyClass(HttpServletRequest request) {
		System.out.println("mypage/viewapplyclass()");

		ModelAndView mv = new ModelAndView();

		mv = cs.getApplyClassInfo();

		mv.setViewName("mypage/viewapplyclass");
		return mv;
	}

	// 좋아요 클래스 목록보기
	@RequestMapping("mypage/viewlikeclass")
	public ModelAndView viewLikeClass(HttpServletRequest request) {
		System.out.println("mypage/viewlikeclass()");

		ModelAndView mv = new ModelAndView();

		mv = ms.getLikeClassInfo();
		mv.setViewName("mypage/viewlikeclass");
		return mv;
	}

	// 개설 클래스 목록보기
	@RequestMapping("mypage/viewopenclass")
	public ModelAndView viewOpenClass(HttpServletRequest request) {
		System.out.println("mypage/viewopenclass()");

		ModelAndView mv = new ModelAndView();

		mv = ms.getOpenClassInfo();
		mv.setViewName("mypage/viewopenclass");
		return mv;
	}

	// 클래스 신청 취소
	@RequestMapping("mypage/quitclass")
	public @ResponseBody String quitClass(HttpServletRequest request) {
		System.out.println("quitClass()");
		String userid = request.getParameter("userid");
		String classid = request.getParameter("classid");

		String json = cs.quitClass(userid, classid);

		return json;

	}

	// 클래스 좋아요 버튼
	@RequestMapping("view/like")
	public String doLike(HttpServletRequest request) {
		String tid = request.getParameter("tid");
		String classtitle = request.getParameter("title");

		int Result = cs.doLike(tid, classtitle);
		String data = "redirect:classview?tid=" + tid;

		return data;
	}

	@RequestMapping("view/undolike")
	public String undoLike(HttpServletRequest request) {
		String classid = request.getParameter("tid");

		int nResult = cs.undoLike(classid);
		String data = "redirect:classview?tid=" + classid;

		return data;
	}

	@RequestMapping("view/fixaddress")
	public String fixaddress(HttpServletRequest request) {
		System.out.println("view/fixaddress");
		String classid = request.getParameter("tid");
		String address = request.getParameter("address");

		bs.fixaddress(classid, address);
		String data = "redirect:classview?tid=" + classid;

		return data;
	}

	// 마이페이지에서 좋아요 취소하기
	@RequestMapping("mypage/undolikeclass")
	public @ResponseBody String undoLikeClass(HttpServletRequest request) {
		System.out.println("undolikeClass()");

		String classid = request.getParameter("classid");
		int nResult = cs.undoLike(classid);

		String json = "{\"code\":\"fail\"}";
		if (nResult == 1) {
			json = "{\"code\":\"success\"}";
		}

		return json;
	}

	// 클래스 삭제
	@RequestMapping("mypage/deleteclasspage")
	public ModelAndView deleteClassPage(HttpServletRequest request) {
		System.out.println("deleteclassPage()");

		String tid = request.getParameter("tid");
		ModelAndView mv = new ModelAndView();
		mv = ms.doDeleteClass(tid);
		mv.setViewName("mypage/deleteclass");
		return mv;
	}
	//개설한 클래스 수정하기
	@RequestMapping("mypage/modwrite")
	public ModelAndView ModifyClass(HttpServletRequest request) {
		System.out.println("deleteclassPage()");

		String tid = request.getParameter("tid");
		ModelAndView mv = new ModelAndView();
		mv=ms.modifyClass(tid);
		mv.setViewName("mypage/modwriteview");
		return mv;
	}

	// 클래스 등록
	@PostMapping("view/meetupwrite")
	public String meetupwrite(HttpServletRequest request, Model model)
			throws Exception {
		System.out.println("/meetupwrite");
		bs.meetUpWrite(request);
		
		return "redirect:allclassview";
	}
	//클래스 등록내용 수정
	@PostMapping("mypage/modwritedone")
	public String modClasswrite(HttpServletRequest request, Model model) throws Exception {
		System.out.println("/modwritedone");
		bs.modWrite(request);
		
		return "redirect:viewopenclass";
	}

	// 결제페이지로 이동
	@RequestMapping("view/paymentpage")
	public ModelAndView viewPaymentPage(HttpServletRequest request) {
		System.out.println("viewPaymentPage()");

		String tid = request.getParameter("tid");
		ModelAndView mv = new ModelAndView();

		mv = ps.getClassInfo(tid);

		return mv;
	}

	// 결제 정보 db 입력
	@RequestMapping("view/insertpaydb")
	public @ResponseBody String insertPayInfoDB(HttpServletRequest request) {
		System.out.println("insertPayInfoDB()");

		String merchant_id = request.getParameter("merchant_uid");
		String imp_uid = request.getParameter("imp_uid");
		String classid = request.getParameter("classid");
		String title = request.getParameter("title");
		String buyer_name = request.getParameter("buyer_name");

		System.out.println(merchant_id + ", " + imp_uid + ", " + "," + classid + ", " + buyer_name);

		int nResult1 = ps.insertPayInfoDb(buyer_name, classid, merchant_id);
		// db에 결제여부를 넣어야함.

		String json = "";
		if (nResult1 == 2) {
			json = "{\"code\":\"success\",\"desc\":\"결제에 성공하였습니다.\"}";
		} else {
			json = "{\"code\":\"fail\",\"desc\":\"결제에 실패하였습니다.\"}";
		}

		return json;
	}

	// 클래스 담당자 채팅 문의페이지 이동
	@RequestMapping("chat/chatpage")
	public ModelAndView goChatPage(HttpServletRequest request) {
		System.out.println("chat/gochatpage()");
		String classid=request.getParameter("classid");
		ModelAndView mv = new ModelAndView();
		
		String classman=cs.checkManId(classid);
		mv.addObject("classid", classid );
		mv.addObject("manId", classman);
		mv.addObject("manprofimage",us.getProfileImage2(classman));
		mv.addObject("profileimage", us.getProfileImage());
		mv.setViewName("chat/chatpage");

		return mv;
	}

	// 클래스 개설자가 해당 클래스 채팅창 목록 보기 페이지 이동
	@RequestMapping("chat/chatlistview")
	public String goChatListPage(HttpServletRequest request, Model model) {
		System.out.println("chat/chatlistview()");
		String tid = request.getParameter("tid");

		model.addAttribute("classid", tid);
		return "/chat/classchatlist";
	}

	// 클래스 담당자가 개별 클래스 채팅 입장
	 @RequestMapping("chat/managerclasschat")
	   public String goManagerChatPage(HttpServletRequest request, Model model) {
	      System.out.println("goManagerChatPage()");
	      String classid=request.getParameter("classid");
	      String memid=request.getParameter("memid");
	      
	      System.out.println("classid:"+request.getParameter("classid"));
	      model.addAttribute("classid", classid);
	      model.addAttribute("manprofimg", us.getProfileImage());
	      
	      System.out.println("memid: "+memid);
	      String memprofimg=us.getProfileImage2(memid);
	      model.addAttribute("memid", memid);
	      model.addAttribute("memprofimg", memprofimg);
	      return "/chat/managerclasschat";
	   }
}
