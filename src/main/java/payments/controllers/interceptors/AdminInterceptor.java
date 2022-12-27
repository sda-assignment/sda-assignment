package payments.controllers.interceptors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import payments.controllers.auth.Authenticator;
import payments.controllers.auth.Context;

public class AdminInterceptor implements HandlerInterceptor {
    private Authenticator authenticator;

    public AdminInterceptor(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        Context ctx = authenticator.getContextOrFail(authHeader);
        if (!ctx.isAdmin)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        request.setAttribute("context", ctx);
        return true;
    }

}
