package com.eagle.mas.security.jwt;

import com.eagle.mas.model.TokenDetails;
import com.eagle.mas.model.Userdetails;
import com.eagle.mas.repository.TokenRepository;
import com.eagle.mas.repository.UserdetailsRepository;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilterChain extends OncePerRequestFilter {
    private static final String START = "Bearer ";
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserdetailsRepository userdetailsRepository;
    public JwtFilterChain(HandlerExceptionResolver handlerExceptionResolver) {
    }

    @Value("${base.context.path}")
    private String basePath ;


    public String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("Authorization".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // No token found in cookies
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        log.info("REQUEST URI : "+ request.getRequestURI());
        if (request.getRequestURI().equals(basePath+"login") || request.getRequestURI().equals(basePath) ||
                request.getRequestURI().startsWith(basePath+"plugins") || request.getRequestURI().startsWith(basePath+"dist") ||
                request.getRequestURI().equals(basePath+"favicon.ico") || request.getRequestURI().startsWith(basePath+"forgotPasswordDetails") ||
                request.getRequestURI().startsWith(basePath+"forgotPasswordController") ||
                request.getRequestURI().startsWith(basePath+"redirectLogin") ||
                request.getRequestURI().startsWith(basePath+"redirectlogin") ||
                request.getRequestURI().startsWith(basePath+"loginPage") ||
                request.getRequestURI().startsWith(basePath+"changePasswordDetails")
        ) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String token = extractTokenFromCookies(request);
                String userName;

                if (token != null) {
                    log.info("_______________________started checking the token ___________________________");
                    System.out.println(token);
                    userName = jwtService.extractUsername(token);
                    System.out.println("AUTHENTICATION username: "+userName);
                    System.out.println("AUTHENTICATION: "+SecurityContextHolder.getContext().getAuthentication());
                    if (userName != null) {
                        Userdetails user = userdetailsRepository.findUserdetailsByEmail(userName);
                        if (user != null) {
                            TokenDetails tokenDetails = tokenRepository.findByToken(token);
                            boolean flag;
                            if (tokenDetails.getToken().isEmpty() || tokenDetails.isExpired() || tokenDetails.isRevoked()) {
                                flag = false;
                            } else {
                                flag = true;
                            }
                            if (jwtService.validateToken(token, user) && flag) {
                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                System.out.println(authenticationToken.getPrincipal());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            } else {
                                log.info("_______________________ Invalid token ___________________________");
                                throw new JwtException("INVALID  TOKEN");
                            }
                        } else {
                            log.info("_______________________ No User Register in db ___________________________");
                            throw new JwtException("INVALID TOKEN");
                        }
                    } else {
                        log.info("_______________________ No User Found with this token ___________________________");
                        throw new JwtException("INVALID TOKEN");
                    }
                } else {
                    log.info("_______________________ Header does not contain token please check ______" + request.getSession().getId() + " :" + request.getRemoteAddr());
                    throw new JwtException("AUTHENTICATION  TOKEN  IS EMPTY");
                }
            } catch (Exception ex) {
                log.info("EXCEPTION OCCURS  : " + ex.getMessage());
                response.sendRedirect(request.getContextPath() + "/redirectLogin");
                return;
            }
            filterChain.doFilter(request, response);
        }
    }

}
