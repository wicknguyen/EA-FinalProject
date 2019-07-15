package com.mum.web.entities;

public class Like extends Interaction {

    public Like() {
        this.interactionType=InteractionType.LIKE;
        this.icon = "like.icon";
    }
}
