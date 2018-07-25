package common.jwt;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isNotEmpty(token)){
            Map unsign = JWT.unsign(token, Map.class);
            Object oUserId =  unsign.get("userId");
            Object oStiStoreCode =  unsign.get("stiStoreCode");
            String userId = new String(oUserId.toString());
            String stiStoreCode = new String(oStiStoreCode.toString());
            request.setAttribute("userId",userId);
            request.setAttribute("stiStoreCode",stiStoreCode);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}