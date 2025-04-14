package com.nuevospa.taskmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nuevospa.taskmanager.dto.LoginResponse;

public interface LoginService {

	LoginResponse login(String username, String password);


}
