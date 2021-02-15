package com.command;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.Interactor;
import com.util.CollectionManager;
import com.util.Console;

/**
 * 'filter_greater_than_expelled_students' command.
 */
public class FilterGreaterThanExpelledStudentsCommand extends Command {
    private final CollectionManager collectionManager;

    public FilterGreaterThanExpelledStudentsCommand(CollectionManager collectionManager, Interactor interactor) {
        super("filter_greater_than_expelled_students <expelledStudents>", "вывести элементы, значение поля expelledStudents которых больше заданного");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long expelledStudents = Long.parseLong(argument);
            String filteredInfo = collectionManager.greaterThanExpelledStudentsFilteredInfo(expelledStudents);
            if (!filteredInfo.isEmpty()) {
                Console.println(filteredInfo);
                return true;
            } else Console.println("В коллекции нет групп с таким значением expelledStudents!");
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return false;
    }
}
