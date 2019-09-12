package com.lhstack.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomFilter {
    boolean doFilter(HttpServletRequest request, HttpServletResponse response,Object authorizedUser);
}
