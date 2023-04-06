package com.gstsgy.permission.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class AuthWhiteUtil {
    private static List<String> urls = new ArrayList<>();

    static {
        urls.add("/auth/login");
    }

    public static void addUrl(String url){
        urls.add(url);
    }

    public static boolean matches(String url){
       return urls.stream().anyMatch(it -> Pattern.matches(it, url));
    }

    public static void main(String[] args) {
        System.out.println(matches("/"));
        System.out.println(matches("/v"));

        System.out.println(matches("/auth/login"));
    }
}
