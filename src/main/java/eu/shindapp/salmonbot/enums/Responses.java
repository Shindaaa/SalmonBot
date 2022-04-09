package eu.shindapp.salmonbot.enums;

public enum Responses {

    SUGGESTION_WAITING_TITLE("Suggestion"),
    SUGGESTION_WAITING_DESC("Merci d'avoir voulu contribuer à l'amélioration du serveur !\n\nVoici un récapitulatif de ta suggestion:\nTitre: `%s`\nSuggestion: `%s`\n\nSi tout est bon pour toi clique sur le bouton `Confirmer`en dessous de ce message."),
    SUGGESTION_MAIN_TITLE("Nouvelle suggestion !"),
    SUGGESTION_AUTHOR_TITLE("Titre:"),
    SUGGESTION_AUTHOR("Auteur:"),
    SUGGESTION_DESC("Description de la suggestion:"),
    SUGGESTION_FINAL("`%s`, votre suggestion a été envoyé dans le salon `%s`"),

    GLOBAL_CMD_CANCEL("`%s`, vous venez `d'annuler` la commande."),
    GLOBAL_CMD_TIME_OUT("`%s`, vous avez trop long... la commande a été annulée.");

    String content;

    Responses(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
