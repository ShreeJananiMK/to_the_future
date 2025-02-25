package com.snr.loginportal.controller;


import com.snr.loginportal.dto.LoginCred;
import com.snr.loginportal.service.implement.EventManagementServiceImpl;
import com.snr.loginportal.util.ApiResponse;
import com.snr.loginportal.util.JwtTokenGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    JwtTokenGeneration jwtTokenGeneration;
    @Value("${app.User_Name}")
    private String User_Name;
    @Value("${app.Password}")
    private String Password;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader(value = "Authorization", required = false) String token, @RequestBody LoginCred loginCred){
        logger.info("The Input  : ---- : {}",loginCred);
        // If a token is provided, validate it and return user details
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            String username = jwtTokenGeneration.extractUsername(token);

            if (username != null && username.equals(User_Name) && jwtTokenGeneration.validateToken(token, username)) {
                return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, "Token is valid", token, "Admin"));
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(HttpStatus.UNAUTHORIZED, "Invalid or expired token"));
            }
        }

        logger.info("The Input username : ---- : {}",loginCred.getUserName());
        logger.info("The Input password : ---- : {}",loginCred.getPassWord());
        if(loginCred.getUserName().equals(User_Name) && loginCred.getPassWord().equals(Password)){
        token = jwtTokenGeneration.generateToken(User_Name);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.ACCEPTED,"You are successfully logged in", token, "Admin"), HttpStatus.ACCEPTED);
        }
        else if (!loginCred.getUserName().equals(User_Name) && loginCred.getPassWord().equals(Password)){
            return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED,"Incorrect Username"),HttpStatus.UNAUTHORIZED);}
        else return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED,"Incorrect password"),HttpStatus.UNAUTHORIZED);
    }

}
