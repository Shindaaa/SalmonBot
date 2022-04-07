package eu.shindapp.salmonbot.listeners;

import eu.shindapp.salmonbot.utils.ConfigUtils;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class MessageListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent) {
            this.onMessageReceivedEvent((MessageReceivedEvent) genericEvent);
        }
    }

    private void onMessageReceivedEvent(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(ConfigUtils.getConfig("default_prefix"))) {
            if (!event.getAuthor().isBot()) {
                event.getChannel().sendMessage("This bot is on development and doesn't work perfect-fully!").queue();
            }
        }
    }

}
