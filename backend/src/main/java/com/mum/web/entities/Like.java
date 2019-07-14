package com.mum.web.entities;

import java.time.LocalDate;

public class Like extends Interaction {

    public Like() {

        this.interactionType=InteractionType.LIKE;
        this.icon = "like.icon";
    }
}
