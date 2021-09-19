package api.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.auth.dto.CustomUserDto;
import api.auth.util.BasicAuthExtractor;
import api.auth.util.JwtTokenUtil;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtTokenUtil jwtUtil;

	@Autowired
	AuthenticationManager authManager;

	@RequestMapping("/login")
	public ResponseEntity<CustomUserDto> login(
			@RequestHeader(name = "Authorization", required = true, value = "Authorization") String authorization) {
		try {
			final String[] values = BasicAuthExtractor.extract(authorization);

			Authentication authenticate = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(values[0], values[1]));

			CustomUserDto user = (CustomUserDto) authenticate.getPrincipal();
			user.setToken(jwtUtil.generateAccessToken(user));
			return ResponseEntity.ok().body(user);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
