package com.gravypod.PodBot;

import org.jibble.pircbot.User;

/**
 * This is my main command class. Any classes than deal with Commands /should extend this.
 * The CommandClass is a util class for all commands.
 * All messages should be send from this class.
 * 
 * @author gravypod
 *
 */
public abstract class CommandClass {
	
	boolean notice = false;
	boolean ping = false;
	
	/**
	 * 
	 * Gets a pointer (A user name or command name)
	 * 
	 * @param args - Args that were used in this command.
	 * @param commandName - name of command that was used
	 * @return
	 * 
	 */
	public String getPointer(String[] args, String commandName) {
		
		String pointedMessage = commandName;
		
		if (args.length > 0)
		for (String argument : args) {
			
			pointedMessage = argument;
			
			if (pointedMessage.endsWith("@@")) {
				
				notice = true;
				continue;
				
			} else if (pointedMessage.endsWith("@")) {
				
				ping = true;
				continue;
				
			}
			
			pointedMessage = commandName;
			
		}
		
		if (ping) {
			pointedMessage.replace("@", "");
		} 
		
		if (notice) {
			pointedMessage.replace("@@", "");
		} 

		return pointedMessage;
		
	}
	
	/**
	 * 
	 * Send a message to a pointer. 
	 * 
	 * @param args - The args that were used in this message
	 * @param commandName - The command that was used in this message
	 * @param channel - Channel were the message was sent in
	 * @param message - The message you want to send.
	 * 
	 */
	public void sendResponce(String[] args, String commandName, String channel, String message) {
		
		String pointer = getPointer(args, commandName);
		
		pointer = pointer.replaceAll("@", "");
		
		if (notice) {
			System.out.println("Responce" + channel + " " + pointer + " " + message);
			BotStartup.botInstance.sendNotice(pointer, pointer + ": " + message);
			
		} else {
			System.out.println("Responce" + channel + " " + pointer + " " + message);
			BotStartup.botInstance.sendMessage(channel, pointer + ": " + message);
			
		}
		
	}
	
	/**
	 * 
	 * Send an array of messages to a pointer.
	 * 
	 * @param args - The args that were used in this message
	 * @param commandName - The command that was used in this message
	 * @param channel - Channel were the message was sent in
	 * @param messages - String[] of messages that you want to send
	 * 
	 */
	public void sendArrayResponce(String[] args, String commandName, String channel, String[] messages) {
		
		for (String message : messages) {
			System.out.println("ArratResponce" + channel + " " + message);
			sendResponce(args, commandName, channel, message);
		}
			
			
	}
	
	/**
	 * 
	 * Checks to see if a user is opped.
	 * 
	 * @param channel
	 * @param username
	 * @return
	 * 
	 */
	public boolean isUserOp(String channel, String username) {
		
		for (User user : BotStartup.botInstance.getUsers(channel))
			if (user.getNick().equalsIgnoreCase(username) && user.isOp())
				return true;
		
		sendResponce(CommandParse.args, CommandParse.command, channel, "You do not have permission to use that command!");
		
		return false;
		
	}
	
	/**
	 * 
	 * Checks to see if a user is voiced.
	 * 
	 * @param channel
	 * @param username
	 * @return
	 * 
	 */
	public boolean isUserVoice(String channel, String username) {

		for (User user : BotStartup.botInstance.getUsers(channel))
			if (user.getNick().equalsIgnoreCase(username) && user.hasVoice())
				return true;
		
		sendResponce(CommandParse.args, CommandParse.command, channel, "You do not have permission to use that command!");
		
		return false;
	}
		
}
