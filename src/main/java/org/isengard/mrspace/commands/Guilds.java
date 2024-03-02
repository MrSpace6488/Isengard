package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import reactor.core.publisher.Flux;

public class Guilds extends Command{
    @Override
    protected void help(boolean list) {
        System.out.println("Lists all guilds (servers) the bot is in. Also puts the ID after the guild name.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("guilds");
        }
    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        Flux<Guild> guilds = gateway.getGuilds();
        guilds.subscribe(guild -> System.out.println(guild.getName() +" ["+ guild.getId().asLong() + "]"));
    }
}
