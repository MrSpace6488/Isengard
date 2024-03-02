package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;

public class Exit extends Command{

    @Override
    protected void help(boolean list) {
        System.out.println("Exists Isengard.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("exit");
        }
    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        System.exit(0);
    }
}
