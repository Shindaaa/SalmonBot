package eu.shindapp.salmonbot.listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

public class MessageListener implements EventListener {

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof MessageReceivedEvent) {
            this.onBotSendMessage((MessageReceivedEvent) genericEvent);
        }
    }

    public void onBotSendMessage(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
    }
}
