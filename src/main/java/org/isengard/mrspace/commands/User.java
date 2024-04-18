package org.isengard.mrspace.commands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class User extends Command{

    private static Member selectedUser;
    private static GatewayDiscordClient gateway;


    @Override
    protected void help(boolean list) {

    }

    @Override
    public void run(String[] args, GatewayDiscordClient gateway) {
        if (User.gateway == null){
            User.gateway = gateway;
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
                break;
            case "nick":
                try {
                     nick(Long.parseLong(args[1]), args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID.");
                }
                break;
            case "role":
                if ("add".equalsIgnoreCase(args[1])){
                    try {
                        addRole(Long.parseLong(args[3]),Long.parseLong(args[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }
                } else if ("remove".equalsIgnoreCase(args[1])){
                    try {
                        removeRole(Long.parseLong(args[3]),Long.parseLong(args[2]));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID.");
                    }

                } else {
                    System.out.println("Unknown argument '"+args[1]+"'.");
                }
        }


    }

    private void select(long userID){
        selectedUser = getUser(userID);
    }

    private void nick(long userID, String name){
        Member m;
        if ((m = getUser(userID)) == null) {
            return;
        }
        m.edit().withNicknameOrNull(name).block();
    }

    private void addRole(long userId, long roleId){
        Member m;
        if ((m = getUser(userId)) == null) {
            return;
        }

        Role r;
        if ((r = getRole(roleId)) == null){
            return;
        }

        m.addRole(r.getId()).block();

    }

    private void removeRole(long userId, long roleId){
        Member m;
        if ((m = getUser(userId)) == null) {
            return;
        }

        Role r;
        if ((r = getRole(roleId)) == null){
            return;
        }

        m.removeRole(r.getId()).block();
    }

    private void ban(long userId, String reason){

    }

    private void kick(long userId, String reason) {

    }

    private void timeout(long userId, String reason, short length){

    }

    private static Member getUser(long id) {
        return gateway.getMemberById(Guild.getSelectedGuild().getId(), Snowflake.of(id))
                .onErrorResume(error -> {
                    System.out.println("Error retrieving member: " + error.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
    public static Member getSelectedUser() {
        return selectedUser;
    }

    private static Role getRole(long id){
        return gateway.getRoleById(Guild.getSelectedGuild().getId(), Snowflake.of(id))
                .onErrorResume(error -> {
                    System.out.println("Error retrieving role: " + error.getMessage());
                    return Mono.empty();
                })
                .blockOptional()
                .orElse(null);
    }
}
