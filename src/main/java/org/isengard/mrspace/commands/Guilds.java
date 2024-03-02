package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        if (org.isengard.mrspace.commands.Guild.getSelectedGuild() != null){
            System.out.println("Selected guild:");
            Mono<Guild> sGuild = org.isengard.mrspace.commands.Guild.getSelectedGuild();
            sGuild.subscribe(guild -> System.out.println(guild.getName() +" ["+ guild.getId().asLong() + "]"));
            System.out.println();
        }
        System.out.println("Guilds:");
        Flux<Guild> guilds = gateway.getGuilds();
        guilds.subscribe(guild -> System.out.println(guild.getName() +" ["+ guild.getId().asLong() + "]"));
    }
}
