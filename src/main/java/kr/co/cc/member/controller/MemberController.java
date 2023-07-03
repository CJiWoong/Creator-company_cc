package kr.co.cc.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.cc.member.dao.MemberDAO;
import kr.co.cc.member.dto.MemberDTO;
import kr.co.cc.member.service.MemberService;
import kr.co.cc.member.service.SendEmailService;

@Controller
public class MemberController {
	
	@Autowired MemberService memberservice;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String home(Model model) {
		return "Login";
	}

	@RequestMapping(value="/join.go", method = RequestMethod.GET)
	public String joinForm(Model model) {
		return "JoinForm";
	}
	
	@PostMapping(value="/join.do")
	public ModelAndView join(MemberDTO dto) {
		logger.info("dto : " + dto.getUser_id());
		return memberservice.join(dto);
	}
	
	@RequestMapping(value="/main.go")
	public String main(Model model) {
		return "workHistoryList";
	}
	
	@RequestMapping(value="/login.do", method = RequestMethod.POST)
	public String login(String user_id, String password, Model model, HttpSession session, String id) {
		String page = "Login";
		
		if (memberservice.login(user_id,password,id) != null){
			page = "redirect:/main.go";
			id = memberservice.loginid(user_id);
			session.setAttribute("id", id);
			logger.info("id : " + id + "/ " + "user_id : " + user_id + "/ " + "password  : " + password);
		}else {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인해주세요");
		}
		
		return page;
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("id");
		logger.info("id 제거 실행되었나 ? ");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/idChk.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> idChk(@RequestParam String user_id) {
		logger.info("idChk-controller");
	    return memberservice.idChk(user_id);
	}
	
	
	
	
	
	@RequestMapping(value = "/findID.go", method = RequestMethod.GET)
	public String findID(Model model) {
			return "findID";
	}

	@RequestMapping(value="/sendMail.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> sendMail(@RequestParam HashMap<String, String> params) {
		logger.info("params : " + params);
		return memberservice.sendMail(params);
	}

	
	
	
	
	@RequestMapping(value = "/findPW.go")
	public String findPW(Model model) {
			return "findPw";
	}
	
	@RequestMapping(value="/sendPWMail.ajax", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> sendPWMail(@RequestParam HashMap<String, String> params) {
		logger.info("params : " + params);
		return memberservice.sendPWMail(params);
	}
	
	
	
//	// 비밀번호 찾기 페이지 요청
//	@GetMapping("/pw-find")
//	public ModelAndView find() {
//		return new ModelAndView("user/pw-find");
//	}
//	
//	// 비밀번호 찾기 요청
//	@PostMapping("pw-find")
//	public String findPw(@RequestBody MemberDAO login) {
//		return memberservice.findPw(login);
//	}
	


	
	
	
	
	
	
	@RequestMapping(value="/userinfo.go")
    public String userInfo(HttpSession session, Model model) {  
       
       String page = "login";      
       
       logger.info("로그인 세션 : "+session.getAttribute("loginId"));
       
        if(session.getAttribute("loginId") != null) {
        	MemberDTO dto = memberservice.userInfo(session.getAttribute("loginId"));             
        	model.addAttribute("user",dto);
            page = "userInfo";
        }
       
       return page;
    }
}
