package com.web.webserver.cs.request;

import java.util.Set;

public record RegisterRequest(String email, String password, Set<String> role) {
}
