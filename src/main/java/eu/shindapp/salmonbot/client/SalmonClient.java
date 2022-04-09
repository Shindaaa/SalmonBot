package eu.shindapp.salmonbot.client;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import eu.shindapp.salmonbot.commands.SlashSuggestionCmd;
import eu.shindapp.salmonbot.listeners.MessageListener;
import eu.shindapp.salmonbot.utils.ConfigUtils;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class SalmonClient {

    EventWaiter eventWaiter = new EventWaiter();

    public void init() throws LoginException, InterruptedException {

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId(ConfigUtils.getConfig("client.ownerId"));
        builder.setCoOwnerIds(ConfigUtils.getConfig("client.coOwnerId"));
        builder.setPrefix(ConfigUtils.getConfig("client.prefix"));
        builder.setAlternativePrefix("<@!" + ConfigUtils.getConfig("client.id") + "> ");
        builder.setActivity(Activity.watching("play.shindapp.eu"));

        builder.forceGuildOnly(ConfigUtils.getConfig("client.mainGuildId"));
        builder.addSlashCommands(
                new SlashSuggestionCmd(eventWaiter)
        );

        CommandClient client = builder.build();

        JDABuilder jdaBuilder = JDABuilder.createDefault(ConfigUtils.getConfig("client.token"))
                .addEventListeners(client, eventWaiter)
                .addEventListeners(new MessageListener())
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.ACTIVITY);

        jdaBuilder.build().awaitReady();

    }
}
