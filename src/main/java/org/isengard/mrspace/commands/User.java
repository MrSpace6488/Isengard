package org.isengard.mrspace.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Member;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class User extends Command{

    private static Member selectedUser;
    private GatewayDiscordClient gateway;


    @Override
    protected void help(boolean list) {

    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        if (this.gateway == null){
            this.gateway = gateway;
        }
        if (Guild.getSelectedGuild() == null){
            System.out.println("No guild selected.");
            return;
        }

        List<String> args2 = Collections.singletonList("select");
        List<String> args3 = Arrays.asList("nick", "ban", "kick");
        List<String> args4 = Arrays.asList("role", "timeout");

        List<String> allArgs = new ArrayList<>(args2);
        allArgs.addAll(args3);
        allArgs.addAll(args4);

        if (args.length == 0){
            help(false);
            return;
        }
        if (!allArgs.contains(args[0].toLowerCase())){
            System.out.println("Unknown argument '"+args[0]+"'.");
            return;
        }

        switch (args.length){
            case 1:
                System.out.println("Missing arguments.");
                help(false);
                return;
            case 2:
                if (args2.contains(args[0].toLowerCase())) break;
                System.out.println("Missing arguments.");
                return;
            case 3:
                if (args3.contains(args[0].toLowerCase())) break;
                if (args2.contains(args[0].toLowerCase())) {
                    System.out.println("Too many arguments.");
                    return;
                }
                System.out.println("Missing arguments.");
                return;
            case 4:
                if (!args4.contains(args[0].toLowerCase())){
                    System.out.println("Too many arguments.");
                }
                break;
            default:
                System.out.println("Too many arguments.");
                help(false);
                return;
        }

        switch (args[0].toLowerCase()) {
            case "select":
                try {
                    select(Long.parseLong(args[1]));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }
        }


    }

    private void select(long userID){
        selectedUser = getUser(userID, gateway);
    }

    private void nick(long userID, String name){

    }

    private void addRole(long userId, long roleId){

    }

    private void removeRole(long userId, long roleId){

    }

    private void ban(long userId, String reason){

    }

    private void kick(long userId, String reason) {

    }

    private void timeout(long userId, String reason, short length){

    }

    private static Member getUser(long id, GatewayDiscordClient gateway) {
        return gateway.getMemberById(Guild.getSelectedGuild().getId(), Snowflake.of(id))
                .onErrorResume(error -> {
                    System.out.println("Error retrieving channel: " + error.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
    public static Member getSelectedUser() {
        return selectedUser;
    }
}
