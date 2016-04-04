package com.careydevelopment.twitterautomation.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword{

 public static void main(String args[]) throws Exception {
  String cryptedPassword = new BCryptPasswordEncoder().encode("");
  System.out.println(cryptedPassword);
 }
}