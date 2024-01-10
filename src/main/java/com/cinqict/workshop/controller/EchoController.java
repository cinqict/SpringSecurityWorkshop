package com.cinqict.workshop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cinqict.workshop.domain.Employee;
import com.cinqict.workshop.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/echo")
public class EchoController {

    private final EmployeeService employeeService;

    @GetMapping("")
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)request.getUserPrincipal();
        return token.getPrincipal().toString();
    }
}
