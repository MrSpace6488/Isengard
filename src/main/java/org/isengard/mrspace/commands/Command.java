package org.isengard.mrspace.commands;

import discord4j.core.GatewayDiscordClient;

public abstract class Command {
    protected abstract void help(boolean list);
    public abstract void run(String[] args, GatewayDiscordClient gateway);
}
