/**
 * 
 */
package com.tcs.demo.recipe.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tcs.demo.recipe.bean.User;
import com.tcs.demo.recipe.service.UserService;
import com.tcs.demo.recipe.util.EncryptionUtil;




/**
 * Home page redirection controller
 * @author Dhiraj
 *
 */

@RestController
public class MvcController {

	@Autowired
	UserService userService;

	@GetMapping({"/","/home"})
	public ModelAndView  returnHomePage(Principal user,HttpServletResponse response) throws UnsupportedEncodingException, GeneralSecurityException{

		User sessionUser = userService.getUserByLoginId(user.getName());	
		response.addCookie(new Cookie("ed", sessionUser.getUsrId().toString()));
		String encodedBasic = Base64.getEncoder() 
				.encodeToString((sessionUser.getUsrLoginId()+":"+EncryptionUtil.decrypt(sessionUser.getUsrPassword())) .getBytes());
		response.addCookie(new Cookie("ba",encodedBasic)); // basic authentication header value of encoded username:password 
		ModelAndView mv = new ModelAndView ("redirect:home.html");
		return mv;
	}
}
