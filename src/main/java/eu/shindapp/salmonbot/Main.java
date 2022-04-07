package eu.shindapp.salmonbot;

import eu.shindapp.salmonbot.client.SalmonClient;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        try {
            new SalmonClient().init();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
