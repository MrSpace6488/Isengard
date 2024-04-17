package org.isengard.mrspace.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import reactor.core.publisher.Mono;

public class Channel extends Command{

    private static discord4j.core.object.entity.channel.Channel selectedChannel;

    @Override
    protected void help(boolean list) {
        //TODO: help menu
    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        //TODO: run method of channel command
        switch (args.length){
            case 0:
                help(false);
                return;
            case 1:
                System.out.println("Missing arguments.");
                help(false);
                return;
            case 2:
                if ("rename".equalsIgnoreCase(args[0])) return;
            case 3:
                break;
            default:
                System.out.println("Too many arguments.");
                help(false);
                return;
        }

        switch (args[0].toLowerCase()){
            case "create":
                break;
            case "delete":
                try {
                    long id = Long.parseLong(args[1]);
                    delete(id, gateway);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid channel ID.");
                }
                break;
            case "rename":
                try {
                    long id = Long.parseLong(args[1]);
                    rename(id, args[1], gateway);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid channel ID.");
                }
                break;
            case "select":
                try {
                    long id = Long.parseLong(args[1]);
                    select(id, gateway);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid channel ID.");
                }
        }
    }

    private static void select(long id, GatewayDiscordClient gateway){
        selectedChannel = getChannel(id, gateway);
    }

    private static void create(String name){
        //TODO: create channel method
    }

    private static void delete(long channelId, GatewayDiscordClient gateway) {
        discord4j.core.object.entity.channel.Channel c;
        if ((c = getChannel(channelId, gateway)) != null){
            c.delete();
        }
    }

    
    //TODO: rename channel method
    private static void rename(long channelId, String channelName, GatewayDiscordClient gateway) {
    }

    public static discord4j.core.object.entity.channel.Channel getSelectedChannel() {
        return selectedChannel;
    }

    private static discord4j.core.object.entity.channel.Channel getChannel(long id, GatewayDiscordClient gateway) {
        return gateway.getChannelById(Snowflake.of(id))
                .onErrorResume(error -> {
                    System.out.println("Error retrieving channel: " + error.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
}
