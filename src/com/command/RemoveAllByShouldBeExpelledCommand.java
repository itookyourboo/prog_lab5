package com.command;

import com.exception.CollectionIsEmptyException;
import com.exception.WrongAmountOfArgumentsException;
import com.util.CollectionManager;
import com.util.Console;

/**
 * 'remove_all_by_should_be_expelled' command. Removes elements with sholdBeExpelled value user entered.
 */
public class RemoveAllByShouldBeExpelledCommand extends Command{
    private final CollectionManager collectionManager;

    public RemoveAllByShouldBeExpelledCommand(CollectionManager collectionManager) {
        super("remove_all_by_should_be_expelled <shouldBeExpelled>",
                "удалить из коллекции все элементы, значение поля shouldBeExpelled которого эквивалентно заданному");
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
            Long shouldBeExpelled = Long.parseLong(argument);
            collectionManager.removeAllByShouldBeExpelled(shouldBeExpelled);
            Console.println("Группы успешно удалены!");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return false;
    }
}
