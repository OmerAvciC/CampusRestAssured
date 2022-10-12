package Attestations;

import org.apache.commons.lang3.RandomStringUtils;

public class Attestations {

    private  String username;
    private  String password;
    private  String rememberMe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }


    public static String RandomString(){
        return RandomStringUtils.randomAlphabetic(8);
    }


}
