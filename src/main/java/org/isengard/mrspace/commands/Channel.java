package org.isengard.mrspace.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import reactor.core.publisher.Mono;

public class Channel extends Command{

    private static Mono<discord4j.core.object.entity.channel.Channel> selectedChannel;

    @Override
    protected void help(boolean list) {
        //TODO: help menu
    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        //TODO: run method of channel command
        String command = args[0].toLowerCase();
        switch (args.length){
            case 0:
                help(false);
                return;
            case 1:
                System.out.println("Missing arguments.");
                help(false);
                return;
            case 2:
                if ("rename".equals(command)) return;
            case 3:
                break;
            default:
                System.out.println("Too many arguments.");
                help(false);
                return;
        }

        switch (command){
            case "create":
                break;
            case "delete":
                try {
                    long id = Long.parseLong(args[1]);
                    delete(id, gateway);
                } catch (NumberFormatException e) {
                    System.out.println("Unknown argument '"+args[1]+"'.");
                }
                break;
            case "rename":
                break;
            case "select":
                break;
        }
    }

    private static void select(long selectedServer){
        //TODO: select channel method
    }

    private static void create(String name){
        //TODO: create channel method
    }

    private static void delete(long channelId, GatewayDiscordClient gateway) {
        //TODO: delete channel method
        Mono<discord4j.core.object.entity.channel.Channel> channel = gateway.getChannelById(Snowflake.of(channelId));
        channel.flatMap(discord4j.core.object.entity.channel.Channel::delete).subscribe();

    }

    private static void rename(long channelId, String channelName) {
        //TODO: rename channel method
    }
}
