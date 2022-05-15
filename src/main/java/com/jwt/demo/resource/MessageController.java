package com.jwt.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.demo.model.AuthRequest;
import com.jwt.demo.model.AuthResponse;
import com.jwt.demo.service.MyUserDetailService;
import com.jwt.demo.util.JWTUtil;

@RestController
public class MessageController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private MyUserDetailService service;

	@Autowired
	private JWTUtil jwtUtil;

	@GetMapping("/message")
	@ResponseBody
	public String message() {
		return "Hello from a secure page!!";
	}

	@PostMapping("/authenticate")
	@ResponseBody
	public AuthResponse authenticate(@RequestBody AuthRequest request) throws BadCredentialsException {
		manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

		final UserDetails userDetails = service.loadUserByUsername(request.getUserName());

		final String jwt = jwtUtil.generateToken(userDetails);

		return new AuthResponse(jwt);

	}

}
