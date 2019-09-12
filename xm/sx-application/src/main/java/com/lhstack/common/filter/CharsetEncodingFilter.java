package com.lhstack.common.filter;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import com.lhstack.utils.ClassPathResourceUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Properties;

@WebFilter(value = "/*")
public class CharsetEncodingFilter implements Filter {
    private static Properties properties = ClassPathResourceUtils.getProperties("encoding.properties");
    private static Log log = LogFactory.get(CharsetEncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(properties.getProperty("req.charset"));
        res.setContentType(properties.getProperty("resp.contextCharset"));
        res.setCharacterEncoding(properties.getProperty("resp.charset"));
        if(log.isEnabled(Level.INFO)){
            log.info("req-charset -> {},resp-charset -> {},resp-contextCharset -> {}",properties.getProperty("req.charset"),properties.getProperty("resp.contextCharset"),properties.getProperty("resp.charset"));
        }
        chain.doFilter(req,res);
    }

    @Override
    public void destroy() {

    }
}
