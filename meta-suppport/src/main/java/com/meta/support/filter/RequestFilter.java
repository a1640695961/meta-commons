package com.meta.support.filter;

import com.meta.commons.model.SessionContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiong Mao
 * @date 2022/01/13 17:54
 **/
public class RequestFilter extends RequestContextFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String userId = request.getHeader(SessionContextHolder.SessionUser.USER_ID);
        if (StringUtils.isBlank(userId)) {
            userId = request.getParameter(SessionContextHolder.SessionUser.USER_ID);
        }

        String userName = request.getHeader(SessionContextHolder.SessionUser.USER_NAME);
        if (StringUtils.isBlank(userId)) {
            userName = request.getParameter(SessionContextHolder.SessionUser.USER_NAME);
        }

        SessionContextHolder.SessionUser sessionUser = new SessionContextHolder.SessionUser(userId, userName);
        SessionContextHolder.setSessionUser(sessionUser);

        super.doFilterInternal(request, response, filterChain);
    }
}
