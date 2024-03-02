package org.isengard.mrspace;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import discord4j.core.GatewayDiscordClient;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        DiscordClient client;
        GatewayDiscordClient gateway;

        System.out.println("Please insert the bot token:");

        while (true) {
            try {
                client = DiscordClient.create(input.next());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Please insert a valid token!");
            }
        }

        gateway = client.login().block();
        assert gateway != null;

        gateway.on(ReadyEvent.class).subscribe(tmod -> {
            User self = tmod.getSelf();
            System.out.println("Logged in as " + self.getUsername());
        });

    }
}