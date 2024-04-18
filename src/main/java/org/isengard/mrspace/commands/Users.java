package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Users extends Command{
    @Override
    protected void help(boolean list) {
        System.out.println("Lists all users of the selected guild. Also puts the ID the username name.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("channels");
        }

    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {

        discord4j.core.object.entity.Guild guild;
        if ((guild = Guild.getSelectedGuild()) == null){
            System.out.println("No guild selected. Please select a guild using the 'guild select' command");
            return;
        }

        Flux<Member> members = guild.getMembers();
        members.flatMap(this::printMembers).blockLast();
    }

    private Mono<Void> printMembers(Member member){
        System.out.println(member.getDisplayName() + " " + member.getId().asString());
        return Mono.empty();
    }
}
