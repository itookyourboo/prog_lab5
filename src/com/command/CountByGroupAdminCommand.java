package com.command;

import com.model.Person;
import com.exception.CollectionIsEmptyException;
import com.exception.IncorrectInputInScriptException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.Console;
import com.util.Interactor;


/**
 * 'count_by_group_admin_command' command. Prints amount of groups with the same admin
 */
public class CountByGroupAdminCommand extends Command{
    private final CollectionManager collectionManager;
    private final Interactor interactor;

    public CountByGroupAdminCommand(CollectionManager collectionManager, Interactor interactor) {
        super("count_by_group_admin {element}",
                "вывести количество элементов, значение поля groupAdmin которых равно заданному");
        this.collectionManager = collectionManager;
        this.interactor = interactor;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Person admin = new Person(
                    interactor.askAdminName(),
                    interactor.askWeight(),
                    interactor.askPassportID(),
                    interactor.askLocation()
            );
            long count = collectionManager.countByGroupAdmin(admin);
            Console.println("Количество групп: " + count);
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (IncorrectInputInScriptException exception) {
            Console.printerror("Не удалось выполнить скрипт.");
        }
        return false;
    }
}
