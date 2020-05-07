package userservice.interceptor;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import userservice.utils.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ServiceInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(ServiceInterceptor.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String getURL = request.getRequestURI();
        String mehtodType = request.getMethod();
        String authToken = request.getHeader("token");
        if(authToken!=null && !authToken.isEmpty()){//Private Urls
            if(!jwtTokenUtil.isTokenExpired(authToken))
                return true;
        }else{//Public URL
            if(("/login").equalsIgnoreCase(getURL) && "POST".equalsIgnoreCase(mehtodType))
                return true;
        }
        //Token has experied or no token in header
        response.sendError(401);
        return false;
    }
}
