package com.example.SocialNetwork.service;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class FieldValidatorService {
    // Check password for at least 1 upper case, 1 lower case, 1 number, minimum 5 length, maximum 40, and no spaces
    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{5,40}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(password).matches();
    }

    // Check the name for the user doesn't have spaces and is maximum 20 characters length and no @
    public static boolean isValidUsername(String username) {
        String regex = "^(?!.*[@\\s]).{1,20}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(username).matches();
    }

    // Check the email is valid
    public static boolean isValidEmail(String email) {
        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    // Check the alias doesn't only have spaces and has characters
    public static boolean isValidAlias(String alias) {
        String regex = "^(?!\\s+$).{1,20}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(alias).matches();
    }
}
