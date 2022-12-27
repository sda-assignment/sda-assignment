package payments.controllers.interceptors;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import payments.controllers.auth.Authenticator;

public class AuthInterceptor implements HandlerInterceptor {
    private Authenticator authenticator;

    public AuthInterceptor(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        request.setAttribute("context", authenticator.getContextOrFail(authHeader));
        return true;
    }

}
