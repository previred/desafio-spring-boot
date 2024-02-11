package com.moveapps.apigateway.infrastructure.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

//@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/security/users/register",
            "/api/security/users/token",
            "/actuator"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}