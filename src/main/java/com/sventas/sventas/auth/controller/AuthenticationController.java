package com.sventas.sventas.auth.controller;

import com.sventas.sventas.auth.dto.AuthenticationRequest;
import com.sventas.sventas.auth.dto.AuthenticationResponse;
import com.sventas.sventas.auth.service.JwtUtils;
import com.sventas.sventas.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private JwtUtils jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> singIn(@RequestBody AuthenticationRequest authRequest)throws Exception{

        UserDetails userDetails;//new
        try{
            //this.authenticationManager.authenticate(
              //      new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));


            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
           userDetails = (UserDetails) auth.getPrincipal();
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }
       //final UserDetails userDetails = this.userDetailsCustomService
         //      .loadUserByUsername(authRequest.getUsername());//new

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
