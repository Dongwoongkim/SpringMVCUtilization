package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId";

    // key - uuid , value - 객체
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    // 세선 생성
    public void createSession(Object value, HttpServletResponse response)
    {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    // 세션 조회
    public Object getSession(HttpServletRequest request)
    {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie==null)
        {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName)
    {
        if(request.getCookies()==null)
        {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    // 세션 만료
    public void expire(HttpServletRequest request)
    {
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie!=null)
        {
            sessionStore.remove(sessionCookie.getValue());
        }
    }


}
