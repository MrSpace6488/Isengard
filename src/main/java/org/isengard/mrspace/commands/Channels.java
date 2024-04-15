package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Category;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.entity.channel.VoiceChannel;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class Channels extends Command{
    @Override
    protected void help(boolean list) {
        System.out.println("Lists all channels of the selected guild. Also puts the ID and the type after the channel name.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("channels");
        }

    }

    private void printChannels(Channel channel) {
        String name;
        long id = channel.getId().asLong();
        String type = channel.getType().name();

        switch (channel.getType()) {
            case GUILD_TEXT:
                name = ((TextChannel) channel).getName();
                type = "(Text channel)";
                break;
            case GUILD_VOICE:
                name = ((VoiceChannel) channel).getName();
                type = "(Voice channel)";
                break;
            case GUILD_CATEGORY:
                name = ((Category) channel).getName();
                type = "(Category)";
                break;
            default:
                name = "Unknown";
                type = "(Unknown)";
                break;

        }
        System.out.println(name + " [" + id + "] " + type);
    }


    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {

        Mono<discord4j.core.object.entity.Guild> guild = Guild.getSelectedGuild();

        if (guild == null) {
            System.out.println("No guild selected. Please select a guild using the 'guild select' command");
            return;
        }
        guild.flatMapMany(discord4j.core.object.entity.Guild::getChannels)
                .subscribe(this::printChannels);
    }
}
