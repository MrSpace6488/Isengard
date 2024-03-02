package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;
import org.isengard.mrspace.CommandHandler;

public class Help extends Command{
    @Override
    public void run(String[] args, GatewayDiscordClient gateway){
        if (args.length > 1) {
            System.out.println("Too many arguments");
            help(false);
        } else if (args.length == 0) {
            this.list();
        } else {
            Command c = CommandHandler.getCommand(args[0]);
            if (c == null){
                System.out.println("Command " + args[0] + " not found");
            } else {
                c.help(false);
            }
        }
    }
    @Override
    protected void help(boolean list) {
        System.out.println("Returns information about commands. Will return a list of all commands if no parameters are given.");
        if (!list){
            System.out.println();
            System.out.println("Usage:");
            System.out.println("help [command]");
        }
    }

    private void list(){
        String spacing = "                    ";
        for (Command c :CommandHandler.getCommands()){
            String name = c.getClass().getSimpleName().toLowerCase();
            System.out.print(name + spacing.substring(0,20- name.length()));
            c.help(true);
        }
    }
}
