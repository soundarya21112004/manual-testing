package com.eagle.mas.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class JwtExceptionHandler {
    public JwtExceptionHandler(){
        System.out.println("JwtExceptionHandler CONSTructro clalled");
    }
    @ExceptionHandler(JwtException.class)
    public String handleJwtException(JwtException ex){

        System.out.println("Exception Handled by Handler class");
        return "redirect:/logout";
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
//        logger.error("Request: " + req.getRequestURL() + " raised " + ex);
        System.out.println("Inside exceptionhandler");
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
//    @ExceptionHandler({SQLException.class,DataAccessException.class})
//    public String databaseError() {
//        // Nothing to do.  Returns the logical view name of an error page, passed
//        // to the view-resolver(s) in usual way.
//        // Note that the exception is NOT available to this view (it is not added
//        // to the model) but see "Extending ExceptionHandlerExceptionResolver"
//        // below.
//        return "databaseError";
//    }

}
