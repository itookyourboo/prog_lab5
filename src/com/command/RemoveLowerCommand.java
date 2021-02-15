package com.command;

import com.model.StudyGroup;
import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.StudyGroupNotFoundException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.Console;
import com.util.Interactor;

import java.time.ZonedDateTime;

/**
 * 'lower_greater' command. Removes elements lower than user entered.
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public RemoveLowerCommand(CollectionManager collectionManager, Interactor interactor) {
        super("remove_lower {element}", "удалить из коллекции все элементы меньше заданного");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            StudyGroup studyGroupToFind = new StudyGroup(
                    -1,
                    interactor.askGroupName(),
                    interactor.askCoordinates(),
                    ZonedDateTime.now(),
                    interactor.askStudentsCount(),
                    interactor.askExpelledStudents(),
                    interactor.askShouldBeExpelled(),
                    interactor.askFormOfEducation(),
                    interactor.askGroupAdmin()
            );
            StudyGroup studyGroup = collectionManager.getByValue(studyGroupToFind);
            if (studyGroup == null) throw new StudyGroupNotFoundException();
            int collectionSize = collectionManager.collectionSize();
            collectionManager.removeLower(studyGroup);
            Console.print("Удалено групп: " + (collectionManager.collectionSize() - collectionSize));
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (StudyGroupNotFoundException exception) {
            Console.printerror("Группы с такими параметрами в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}