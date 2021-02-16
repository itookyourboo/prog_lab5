package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.exception.NoAccessToFileException;
import com.exception.ScriptRecursionException;
import com.Main;

/**
 * Operates command input.
 */
public class Console {
    private final CommandManager commandManager;
    private final Scanner userScanner;
    private final Interactor interactor;
    private final List<String> scriptStack = new ArrayList<>();

    public Console(CommandManager commandManager, Scanner userScanner, Interactor interactor) {
        this.commandManager = commandManager;
        this.userScanner = userScanner;
        this.interactor = interactor;
    }

    /**
     * Mode for catching com.commands from user input.
     */
    public void interactiveMode() {
        String[] userCommand = {"", ""};
        int commandStatus;
        try {
            do {
                Console.print(Main.INPUT_COMMAND);
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != 2);
        } catch (NoSuchElementException exception) {
            Console.printerror("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            Console.printerror("Непредвиденная ошибка!");
        }
    }

    /**
     * Mode for catching com.commands from a script.
     * @param argument Its argument.
     * @return Exit code.
     */
    public int scriptMode(String argument) {
        String[] userCommand = {"", ""};
        int commandStatus;
        scriptStack.add(argument);
        try {
            File file = new File(argument);
            if (file.exists() && !file.canRead()) throw new NoAccessToFileException();
            Scanner scriptScanner = new Scanner(file);
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = interactor.getUserScanner();
            interactor.setUserScanner(scriptScanner);
            interactor.setFileMode();
            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                Console.println(Main.INPUT_COMMAND + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }   
                }
                commandStatus = launchCommand(userCommand);
            } while (commandStatus == 0 && scriptScanner.hasNextLine());
            interactor.setUserScanner(tmpScanner);
            interactor.setUserMode();
            if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                Console.println("Проверьте скрипт на корректность введенных данных!");
            return commandStatus;
        } catch (FileNotFoundException exception) {
            Console.printerror("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            Console.printerror("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            Console.printerror("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            Console.printerror("Непредвиденная ошибка!");
            System.exit(0);
        } catch (NoAccessToFileException e) {
            Console.printerror("Нет доступа к файлу");
        } finally {
            scriptStack.remove(scriptStack.size()-1);
        }
        return 1;
    }

    /**
     * Launchs the command.
     * @param userCommand Command to launch.
     * @return Exit code.
     */
    private int launchCommand(String[] userCommand) {
        String command = userCommand[0];
        String argument = userCommand[1];
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(argument)) return 1;
                break;
            case "info":
                if (!commandManager.info(argument)) return 1;
                break;
            case "show":
                if (!commandManager.show(argument)) return 1;
                break;
            case "add":
                if (!commandManager.add(argument)) return 1;
                break;
            case "update":
                if (!commandManager.update(argument)) return 1;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(argument)) return 1;
                break;
            case "clear":
                if (!commandManager.clear(argument)) return 1;
                break;
            case "save":
                if (!commandManager.save(argument)) return 1;
                break;
            case "execute_script":
                if (!commandManager.executeScript(argument)) return 1;
                else return scriptMode(argument);
            case "add_if_min":
                if (!commandManager.addIfMin(argument)) return 1;
                break;
            case "remove_greater":
                if (!commandManager.removeGreater(argument)) return 1;
                break;
            case "remove_lower":
                if (!commandManager.removeLower(argument)) return 1;
                break;
            case "remove_all_by_should_be_expelled":
                if (!commandManager.removeAllByShouldBeExpelled(argument)) return 1;
                break;
            case "count_by_group_admin":
                if (!commandManager.countByGroupAdmin(argument)) return 1;
                break;
            case "filter_greater_than_expelled_students":
                if (!commandManager.filterGreaterThanExpelledStudents(argument)) return 1;
            case "exit":
                if (!commandManager.exit(argument)) return 1;
                else return 2;
            default:
                if (!commandManager.noSuchCommand(command)) return 1;
        }
        return 0;
    }

    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error: toOut.toString() to Console
     * @param toOut Error to print
     */
    public static void printerror(Object toOut) {
        System.out.println("error: " + toOut);
    }

    /**
     * Prints formatted 2-element table to Console
     * @param element1 Left element of the row.
     * @param element2 Right element of the row.
     */
    public static void printtable(Object element1, Object element2) {
        System.out.printf("%-60s%-1s%n", element1, element2);
    }
}
