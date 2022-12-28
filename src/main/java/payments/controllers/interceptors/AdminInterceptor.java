package payments.controllers.interceptors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import payments.controllers.auth.Context;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Context ctx = (Context) request.getAttribute("context");
        if (!ctx.isAdmin)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        request.setAttribute("context", ctx);
        return true;
    }

}
