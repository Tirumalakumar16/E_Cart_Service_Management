package com.ktkapp.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {


    public static final List<String> openEndPoints = Arrays.asList(
            "/identity-app/api/register",
            "/eureka",
            "/identity-app/api/token"

    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndPoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
