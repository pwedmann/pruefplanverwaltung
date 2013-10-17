package org.bielefeld.epp.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/app/*")
public class LoginFilter implements Filter {
    private FilterConfig config;
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {  
        HttpServletRequest req = (HttpServletRequest) request;
        SessionManager auth = (SessionManager) req.getSession().getAttribute("sessionManager");
        
        if (auth != null && auth.isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}