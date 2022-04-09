package eu.shindapp.salmonbot.commands;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import eu.shindapp.salmonbot.enums.Errors;
import eu.shindapp.salmonbot.enums.Responses;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SlashSuggestionCmd extends SlashCommand {

    EventWaiter waiter;

    public SlashSuggestionCmd(EventWaiter waiter) {
        this.name = "suggestion";
        this.help = "Permet aux membres du discord de créer des suggestions.";
        this.botPermissions = new Permission[]{Permission.ADMINISTRATOR};
        this.guildOnly = true;
        this.waiter = waiter;

        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "titre", "Le titre de votre suggestion (Exemple: Ajout du mod ...)").setRequired(true));
        data.add(new OptionData(OptionType.STRING, "contenu", "La description de votre suggestion.").setRequired(true));
        this.options = data;
    }

    @Override
    protected void execute(SlashCommandEvent slashCommandEvent) {
        UUID confirm_button = UUID.randomUUID();
        UUID cancel_button = UUID.randomUUID();
        slashCommandEvent.deferReply(true).queue();

        String title = slashCommandEvent.getOption("titre").getAsString();
        String content = slashCommandEvent.getOption("contenu").getAsString();

        slashCommandEvent.getHook().sendMessageEmbeds(
                new EmbedBuilder()
                        .setColor(0xb400ff)
                        .setAuthor(Responses.SUGGESTION_WAITING_TITLE.getContent(), null, slashCommandEvent.getGuild().getSelfMember().getUser().getAvatarUrl())
                        .setDescription(String.format(Responses.SUGGESTION_WAITING_DESC.getContent(), title, content))
                        .build()
        ).addActionRow(
                Button.success(confirm_button.toString(), "confirmer").withEmoji(Emoji.fromMarkdown("⚠"))
        ).addActionRow(
                Button.danger(cancel_button.toString(), "annuler").withEmoji(Emoji.fromMarkdown("↩"))
        ).queue(success -> {

            waiter.waitForEvent(ButtonClickEvent.class, e -> e.getMember().getId().equals(slashCommandEvent.getMember().getId()) && e.getChannel().getId().equals(slashCommandEvent.getChannel().getId()), e -> {
                e.deferReply(true).queue();
                MessageChannel suggestionChannel = e.getGuild().getTextChannelById("959527127739428895");

                if (e.getComponentId().equals(confirm_button.toString())) {

                    if (suggestionChannel == null) {
                        e.getHook().sendMessageEmbeds(
                                new EmbedBuilder()
                                        .setColor(0xff000b)
                                        .setDescription(Errors.SUGGESTION_CHANNEL_IS_NULL.getContent())
                                        .build()
                        ).queue();
                        return;
                    }

                    try {
                        suggestionChannel.sendMessageEmbeds(
                                new EmbedBuilder()
                                        .setColor(0xb400ff)
                                        .setAuthor(Responses.SUGGESTION_MAIN_TITLE.getContent(), null, e.getMember().getUser().getAvatarUrl())
                                        .addField(Responses.SUGGESTION_AUTHOR.getContent(), e.getMember().getUser().getName(), true)
                                        .addField(Responses.SUGGESTION_AUTHOR_TITLE.getContent(), title, true)
                                        .addField(Responses.SUGGESTION_DESC.getContent(), content, false)
                                        .build()
                        ).queue(suggestion -> {
                            suggestion.addReaction("a:aayes:726735611414839356").queue();
                            suggestion.addReaction("a:aano:726735731153829928").queue();
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        e.getHook().sendMessageEmbeds(
                                new EmbedBuilder()
                                        .setColor(0xff000b)
                                        .setDescription(String.format(Errors.GLOBAL_TRY_CATCH_ERROR.getContent(), ex.getClass().getName(), ex.getMessage()))
                                        .build()
                        ).queue();
                        return;
                    }

                    e.getHook().sendMessageEmbeds(
                            new EmbedBuilder()
                                    .setColor(0xb400ff)
                                    .setDescription(String.format(Responses.SUGGESTION_FINAL.getContent(), e.getMember().getUser().getName(), suggestionChannel.getName()))
                                    .build()
                    ).queue();
                }

                if (e.getComponentId().equals(cancel_button.toString())) {
                    e.getHook().sendMessageEmbeds(
                            new EmbedBuilder()
                                    .setColor(0xb400ff)
                                    .setDescription(String.format(Responses.GLOBAL_CMD_CANCEL.getContent(), e.getMember().getUser().getName()))
                                    .build()
                    ).queue();
                }

            }, 15, TimeUnit.MINUTES, () -> slashCommandEvent.getHook().sendMessageEmbeds(
                    new EmbedBuilder()
                            .setColor(0xff000b)
                            .setDescription(String.format(Responses.GLOBAL_CMD_TIME_OUT.getContent(), slashCommandEvent.getMember().getUser().getName()))
                            .build()
            ).queue());

        });
    }
}
