package filters;

import constants.Keys;
import utils.CalendarReplaceUtil;
import utils.categories.SystemCategoryUtil;
import utils.db.hibernate.HibernateSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static constants.Keys.*;

@WebFilter(value = {ASTERISK})
public class ContextFilter implements Filter {

    private static long now;
    private final SystemCategoryUtil systemCategoryUtil = new SystemCategoryUtil();
    @Override
    public void init(FilterConfig filterConfig) {
        systemCategoryUtil.checkCategories();
        CalendarReplaceUtil.init();
        now = Timestamp.valueOf(LocalDateTime.now()).getTime();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        request.setAttribute(CONTEXT, request.getContextPath());
        request.setAttribute(Keys.NOW, now);


        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", request.getContextPath());
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
        CalendarReplaceUtil.shutdown();
    }
}
