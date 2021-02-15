package com.command;

import java.time.ZonedDateTime;

import com.model.StudyGroup;
import com.exception.IncorrectInputInScriptException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.Console;
import com.util.Interactor;

/**
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command {
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public AddCommand(CollectionManager collectionManager, Interactor interactor) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.interactor = interactor;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            collectionManager.addToCollection(
                    new StudyGroup(
                            collectionManager.generateNextId(),
                            interactor.askGroupName(),
                            interactor.askCoordinates(),
                            ZonedDateTime.now(),
                            interactor.askStudentsCount(),
                            interactor.askExpelledStudents(),
                            interactor.askShouldBeExpelled(),
                            interactor.askFormOfEducation(),
                            interactor.askGroupAdmin()
                    )
            );
            Console.println("Группа успешно добавлена!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
