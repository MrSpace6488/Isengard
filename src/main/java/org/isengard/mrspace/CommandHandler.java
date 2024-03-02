package org.isengard.mrspace;

import discord4j.core.GatewayDiscordClient;
import org.isengard.mrspace.commands.*;

import java.util.ArrayList;

public class CommandHandler {
    private final static ArrayList<Command> commands = new ArrayList<>();
    public CommandHandler() {
        if (commands.isEmpty()){
            commands.add(new Help());
            commands.add(new Exit());
            commands.add(new Guilds());
            commands.add(new Guild());
        }
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static Command getCommand(String name){
        for (Command c : commands){
            if (c.getClass().getSimpleName().toLowerCase().equals(name)) return c;
        }
        return null;
    }

    protected void invoke(String s, GatewayDiscordClient gateway){
        String[] completeCommand = s.split(" ");
        String[] args = new String[completeCommand.length-1];
        System.arraycopy(completeCommand, 1, args, 0, args.length);

        Command runningCommand = getCommand(completeCommand[0].toLowerCase());
        if (runningCommand != null){
            runningCommand.run(args, gateway);
        } else {
            System.out.println("Command not found. Run help to see all commands.");
        }
    }

}
