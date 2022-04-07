package eu.shindapp.salmonbot.client;

import eu.shindapp.salmonbot.listeners.MessageListener;
import eu.shindapp.salmonbot.utils.ConfigUtils;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class SalmonClient {

    public void init() throws LoginException, InterruptedException {

        JDABuilder jdaBuilder = JDABuilder.createDefault(ConfigUtils.getConfig("discord_token"));
        jdaBuilder.addEventListeners(new MessageListener());
        //... awaiting more parameters
        jdaBuilder.build().awaitReady();

    }
}
