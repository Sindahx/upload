package cn.refine.filter;

import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  * Created by mazhenhua on 2016/12/27.  *  * 过滤器  
 */
//@Component
@WebFilter(filterName = "sessionFilter", urlPatterns = { "*.html" })
public class SessionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		// session不存在 准备跳转失败
		HttpSession session = httpRequest.getSession(true);
		if (session.getAttribute("userInfo") == null) {
//			if (!url.endsWith("index0.html")) {
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/");
				return;
//			httpRequest.getRequestDispatcher("/index0.html").forward(httpRequest, httpResponse);
//			}
		} else {
			session.setAttribute("userInfo", session.getAttribute("userInfo"));
		}
		chain.doFilter(httpRequest, httpResponse);
		return;
	}

	@Override
	public void destroy() {
	}
}