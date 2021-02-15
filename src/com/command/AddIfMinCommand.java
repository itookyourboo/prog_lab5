package com.command;

import java.time.ZonedDateTime;

import com.model.StudyGroup;
import com.exception.IncorrectInputInScriptException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.Console;
import com.util.Interactor;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public AddIfMinCommand(CollectionManager collectionManager, Interactor interactor) {
        super("add_if_min {element}", "добавить новый элемент, если его значение меньше, чем у наименьшего");
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
            StudyGroup studyGroupToAdd = new StudyGroup(
                    collectionManager.generateNextId(),
                    interactor.askGroupName(),
                    interactor.askCoordinates(),
                    ZonedDateTime.now(),
                    interactor.askStudentsCount(),
                    interactor.askExpelledStudents(),
                    interactor.askShouldBeExpelled(),
                    interactor.askFormOfEducation(),
                    interactor.askGroupAdmin()
            );
            if (collectionManager.collectionSize() == 0 || studyGroupToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(studyGroupToAdd);
                Console.println("Группа успешно добавлена!");
                return true;
            } else Console.printerror("Значение группы больше, чем значение наименьшей из групп!");
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
