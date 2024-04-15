package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.Channel;

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

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        if (Guild.getSelectedGuild() != null) {
            Guild.getSelectedGuild().flatMapMany(discord4j.core.object.entity.Guild::getChannels)
                    .subscribe(channel -> {
                        System.out.print(channel.getName() + " [" + channel.getId().asLong() + "] ");
                        switch (channel.getType().name()){
                            case "GUILD_CATEGORY":
                                System.out.println("(Category)");
                                break;
                            case "GUILD_TEXT":
                                System.out.println("(Text channel)");
                                break;
                            case "GUILD_VOICE":
                                System.out.println("(Voice channel)");
                                break;
                            default:
                                System.out.println("(Unknown)");
                                break;
                        }
                    });
        } else {
            System.out.println("No guild selected. Please select a guild using the 'guild select' command");
        }
    }
}
