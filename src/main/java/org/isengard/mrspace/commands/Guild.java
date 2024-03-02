package org.isengard.mrspace.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Guild extends Command{
    private static Mono<discord4j.core.object.entity.Guild> selectedGuild;
    @Override
    protected void help(boolean list) {
        System.out.println("To manage guilds. you can select a guild and leave a guild using this command.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("guild select [guild id]");
            System.out.println("guild leave [guild id]");
        }
    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        switch (args.length){
            case 0:
                help(false);
                break;
            case 1:
                System.out.println("Missing arguments.");
                help(false);
                break;
            case 2:
                switch (args[0].toLowerCase()){
                    case "leave":
                        try {
                            long id = Long.parseLong(args[1]);
                            leave(id, gateway);
                        } catch (NumberFormatException e){
                            System.out.println("Invalid server ID.");
                        }
                        break;
                    case "select":
                        try {
                            long id = Long.parseLong(args[1]);
                            select(id, gateway);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid server ID.");
                        }
                        break;
                    default:
                        System.out.println("Unknown argument '"+args[1]+"'.");
                        break;
                }
                break;

            default:
                System.out.println("Too many arguments.");
                help(false);
                break;
        }
    }

    public static Mono<discord4j.core.object.entity.Guild> getSelectedGuild(){
        return selectedGuild;
    }

    private void leave(long id, GatewayDiscordClient gateway){
        if (isInGuild(id, gateway)) {
            Mono<discord4j.core.object.entity.Guild> guild = gateway.getGuildById(Snowflake.of(id));
            guild.flatMap(discord4j.core.object.entity.Guild::leave).subscribe();
        } else {
            System.out.println("Guild id " + id + " not found.");
        }
    }

    private void select(long id, GatewayDiscordClient gateway){
        if (isInGuild(id, gateway)){
            selectedGuild = gateway.getGuildById(Snowflake.of(id));
            System.out.println("Selected guild " + id);
        } else {
            System.out.println("Guild id " + id + " not found.");
        }
    }

    private boolean isInGuild(long id, GatewayDiscordClient gateway){
        Flux<discord4j.core.object.entity.Guild> guilds = gateway.getGuilds();
        List<Long> guildIds = new ArrayList<>();
        guilds.map(guild -> guild.getId().asLong()).doOnNext(guildIds::add).blockLast();
        for (Long i : guildIds){
            if (id == i){
                return true;
            }
        }
        return false;
    }
}
