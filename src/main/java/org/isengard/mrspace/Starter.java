package org.isengard.mrspace;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import discord4j.core.GatewayDiscordClient;
import discord4j.gateway.intent.Intent;
import discord4j.gateway.intent.IntentSet;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Starter {
    protected static Scanner input;
    public static void main(String[] args) {
        input = new Scanner(System.in);
        DiscordClient client;
        GatewayDiscordClient gateway;
        CommandHandler ch = new CommandHandler();
        String command;

        System.out.println("Please insert the bot token:");

        while (true) {
            try {
                gateway = DiscordClient.create(input.nextLine())
                        .gateway()
                        .setEnabledIntents(IntentSet.of(Intent.GUILD_MEMBERS))
                        .login()
                        .block();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Please insert a valid token!");
            }
        }

        assert gateway != null;

        gateway.on(ReadyEvent.class).subscribe(tmod -> {
            User self = tmod.getSelf();
            System.out.println("Logged in as " + self.getUsername());
            System.out.print("> ");
        });

        //noinspection InfiniteLoopStatement
        while (true){
            command = input.nextLine();
            ch.invoke(command, gateway);
            System.out.print("> ");
        }
    }
}