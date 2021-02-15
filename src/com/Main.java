package com;

import java.util.Scanner;

import com.command.*;
import com.util.CollectionManager;
import com.util.CommandManager;
import com.util.Console;
import com.util.FileManager;
import com.util.Interactor;

/**
 * Main application class. Creates all instances and runs the program.
 */
public class Main {
    public static final String INPUT_COMMAND = "$ ";
    public static final String INPUT_INFO = "> ";

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            final String envVariable = "LAB5";

            Interactor interactor = new Interactor(userScanner);
            FileManager fileManager = new FileManager(envVariable);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                    new HelpCommand(),
                    new InfoCommand(collectionManager),
                    new ShowCommand(collectionManager),
                    new AddCommand(collectionManager, interactor),
                    new UpdateCommand(collectionManager, interactor),
                    new RemoveByIdCommand(collectionManager),
                    new ClearCommand(collectionManager),
                    new SaveCommand(collectionManager),
                    new ExitCommand(),
                    new ExecuteScriptCommand(),
                    new AddIfMinCommand(collectionManager, interactor),
                    new RemoveGreaterCommand(collectionManager, interactor),
                    new RemoveLowerCommand(collectionManager, interactor),
                    new RemoveAllByShouldBeExpelledCommand(collectionManager),
                    new CountByGroupAdminCommand(collectionManager, interactor),
                    new FilterGreaterThanExpelledStudentsCommand(collectionManager, interactor)
            );
            Console console = new Console(commandManager, userScanner, interactor);
            console.interactiveMode();
        }
    }
}
