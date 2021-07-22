package com.shahin.restfulwebservices.configuration.security;

public enum ApplicationUserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    POST_READ("post:read"),
    POST_WRITE("post:write");
    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission =permission;
    }

    public String getPermission() {
        return permission;
    }

}
