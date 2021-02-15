package com.command;

import com.exception.WrongAmountOfArgumentsException;
import com.util.Console;

/**
 * 'help' command. Just for logical structure. Does nothing.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
