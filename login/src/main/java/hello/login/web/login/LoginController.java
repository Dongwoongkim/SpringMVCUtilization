package hello.login.web.login;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm)
    {
        return "login/loginForm";
    }
//
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
//                        HttpServletResponse response)
//    {
//        if(bindingResult.hasErrors())
//        {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//
//        // 실패
//        if(loginMember == null)
//        {
//            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 쿠키 set
//        Cookie idCookie = new Cookie("memberId",String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);
//
//        // 성공
//        return "redirect:/";
//    }

//    @PostMapping("/login")
//    public String loginV2(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
//                          HttpServletResponse response)
//    {
//        if(bindingResult.hasErrors())
//        {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(),loginForm.getPassword());
//
//        // 실패
//        if(loginMember == null)
//        {
//            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//        // 세션관리자를 통해 세션생성 -> 회원데이터 보관
//        sessionManager.createSession(loginMember,response);
//
//        // 성공
//        return "redirect:/";
//    }

//    @PostMapping("/login")
//    public String loginV3(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
//                          HttpServletRequest request, HttpServletResponse response)
//    {
//        // Field, 타입 오류 검증
//        if(bindingResult.hasErrors())
//        {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(loginForm.getLoginId(),loginForm.getPassword());
//
//        // 실패
//        if(loginMember == null)
//        {
//            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
//            return "login/loginForm";
//        }
//
//
//        // 성공처리
//        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
//        HttpSession session = request.getSession(true);
//
//        // 세션에 로그인 회원 정보 보관
//        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);
//
//        return "redirect:/";
//    }
//

    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request, HttpServletResponse response)
    {
        // Field Error
        if(bindingResult.hasErrors())
        {
            return "login/loginForm";
        }

        // Field Error pass
        Member loginMember = loginService.login(loginForm.getLoginId(),loginForm.getPassword());

        // Object Error
        if(loginMember == null)
        {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }


        // 성공처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);

        // 세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);

        return "redirect:" + redirectURL;
    }



//
//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response)
//    {
//        expireCookie(response,"memberId");
//        return "redirect:/";
//    }
//
//    private void expireCookie(HttpServletResponse response, String cookieName) {
//        Cookie cookie = new Cookie(cookieName, null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }
//
//    @PostMapping("/logout")
//    public String logoutV2(HttpServletRequest request)
//    {
//        sessionManager.expire(request);
//        return "redirect:/";
//    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request)
    {
       HttpSession session = request.getSession(false);
       if(session!=null)
       {
           session.invalidate(); // 세션 삭제
       }
       return "redirect:/";
    }
//

}
