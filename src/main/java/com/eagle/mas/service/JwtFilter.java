package com.eagle.mas.service;

import com.eagle.mas.model.Userdetails;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JwtFilter  extends OncePerRequestFilter {
    @Autowired
    private TokenManager tokenManager;


    @Override

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//RedirectAttributes redirectAttributes = null;

//
//
//            String tokenHeader = request.getHeader("Authorization");
//            String username = null;
//            String token = null;
//            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//                token = tokenHeader.substring(7);
//                try {
//                    username = tokenManager.getUsernameFromToken(token);
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Unable to get JWT Token");
//                } catch (ExpiredJwtException e) {
//                    System.out.println("JWT Token has expired");
//                }
//            } else {
//                System.out.println("Bearer String not found in token");
//            }
//        HttpSession session = request.getSession();
//               session.getAttribute("userdetails");
//
//            if (null != username ) {
//                Userdetails userdetails = (Userdetails) session.getAttribute("userdetails");
//                if (tokenManager.validateJwtToken(token, userdetails)) {
//
//                }
//                else {
//
//                }
//            }
//            filterChain.doFilter(request, response);
//        }
//    }


        String tokenHeader = request.getHeader("Authorization");


        ArrayList<String> url = new ArrayList<>();

        url.add("/leveloneSubmit");
        url.add("/saveMVSL1Result");
        url.add("/leveloneSearchByName");
        url.add("/levelOneSearch");
        url.add("/levelTwoSearch");
        url.add("/leveltwodetails");
        url.add("/leveltwoSearchByName");
        url.add("/saveMVSL2Result");
        url.add("/levelthreeSearch");
        url.add("/levelthreedetails");
        url.add("/levelthreeSearchByName");
        url.add("/saveMVSL3Result");


        for (String checkpath : url) {
            if (request.getRequestURI().equals(checkpath)) {
                System.out.println("url-if :" + checkpath);
                Cookie[] cookies = request.getCookies();

                for (Cookie cookie : cookies) {


                    if (cookie.getName().contains("Authorization")) {
                        System.out.println(cookie.getValue());
                        tokenHeader = cookie.getValue();

                    }

                    else {
                        continue;
                    }
                }

                    String token = null;


                    if (tokenHeader != null) {
                        token = tokenHeader;
                        try {
                            HttpSession session = request.getSession();

                            session.getAttribute("userdetails");
                            Userdetails userdetails = (Userdetails) session.getAttribute("userdetails");
                            if (tokenManager.validateJwtToken(token, userdetails)) {
                                filterChain.doFilter(request, response);
                                System.out.println("Token valid");
                                return;
                            }
                            else {
                                  response.sendRedirect("/redirectlogin");

                            }
                        }
                        catch (IllegalArgumentException e) {
                             e.printStackTrace();
                            System.out.println("Token not valid");


                        }
                        catch (ExpiredJwtException e) {
                            response.sendRedirect("/redirectlogin");
                             e.printStackTrace();
                            System.out.println("JWT Token has expired");
                            return;
                        }
                    }
                    else
                    {
                        System.out.println("Bearer String not found in token");
                    }


                }

        }

        filterChain.doFilter(request, response);

    }
}





