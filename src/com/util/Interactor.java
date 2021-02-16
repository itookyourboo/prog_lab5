package com.util;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.model.Coordinates;
import com.model.FormOfEducation;
import com.model.Location;
import com.model.Person;
import com.exception.IncorrectInputInScriptException;
import com.exception.MustBeNotEmptyException;
import com.exception.NotInBoundsException;
import com.Main;

/**
 * Asks a user a studyGroup's value.
 */
public class Interactor {
    private final int MIN_X = -512;
    private final int MIN_WEIGHT = 0;
    private final int MIN_PASSPORT_ID_LENGTH = 4;
    private final int MAX_LOCATION_NAME_LENGTH = 272;
    private final int MIN_STUDENTS_COUNT = 0;
    private final int MIN_EXPELLED_STUDENTS = 0;
    private final int MIN_SHOULD_BE_EXPELLED = 0;
    private Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

    private Scanner userScanner;
    private boolean fileMode;

    public Interactor(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     *
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets studyGroup asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets studyGroup asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the studyGroup's name.
     *
     * @param inputTitle title of input.
     * @param minLength  min length of string
     * @param maxLength  max length of string
     * @return name
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName(String inputTitle, int minLength, int maxLength) throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Console.println(inputTitle);
                Console.print(Main.INPUT_INFO);
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                if (name.length() <= minLength) throw new NotInBoundsException();
                if (name.length() >= maxLength) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror(String.format("Длина строки не входит в диапазон (%d; %d)", minLength, maxLength));
            }
        }
        return name;
    }

    /**
     * Asks a user the studyGroup's X coordinate.
     *
     * @param withLimit set bounds for X
     * @return studyGroup's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askX(boolean withLimit) throws IncorrectInputInScriptException {
        String strX = "";
        int x;
        while (true) {
            try {
                if (withLimit)
                    Console.println("Введите координату X > " + MIN_X + ":");
                else
                    Console.println("Введите координату X:");
                Console.print(Main.INPUT_INFO);
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Integer.parseInt(strX);
                if (withLimit && x <= MIN_X) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                Console.printerror("Координата X должна быть в диапазоне (" + (withLimit ? MIN_X : Integer.MIN_VALUE)
                        + ";" + Integer.MAX_VALUE + ")!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strX).matches())
                    Console.printerror("Координата X должна быть в диапазоне (" + (withLimit ? MIN_X : Integer.MIN_VALUE)
                            + ";" + Integer.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the studyGroup's Y coordinate.
     *
     * @return StudyGroup's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askY() throws IncorrectInputInScriptException {
        String strY = "";
        long y;
        while (true) {
            try {
                Console.println("Введите координату Y:");
                Console.print(Main.INPUT_INFO);
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Long.parseLong(strY);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strY).matches())
                    Console.printerror("Координата Y должна быть в диапазоне (" + Long.MIN_VALUE
                            + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the studyGroup's coordinates.
     *
     * @return StudyGroup's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        int x = askX(true);
        long y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the studyGroup's form of education
     *
     * @return StudyGroup's form of education
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public FormOfEducation askFormOfEducation() throws IncorrectInputInScriptException {
        String strFormOfEducation;
        FormOfEducation formOfEducation;
        while (true) {
            try {
                Console.println("Список форм обучения - " + FormOfEducation.nameList());
                Console.println("Введите форму обучения:");
                Console.print(Main.INPUT_INFO);
                strFormOfEducation = userScanner.nextLine().trim();
                if (fileMode) Console.println(strFormOfEducation);
                formOfEducation = FormOfEducation.valueOf(strFormOfEducation.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Форма обучения не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Формы обучения нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return formOfEducation;
    }

    /**
     * Asks a user the studyGroup's student count
     *
     * @return StudyGroup's students count
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Integer askStudentsCount() throws IncorrectInputInScriptException {
        String strStudentsCount = "";
        int studentsCount;
        while (true) {
            try {
                Console.println("Введите количество студентов:");
                Console.print(Main.INPUT_INFO);
                strStudentsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strStudentsCount);
                studentsCount = Integer.parseInt(strStudentsCount);
                if (studentsCount <= MIN_STUDENTS_COUNT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strStudentsCount).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_STUDENTS_COUNT + ";" + Integer.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_STUDENTS_COUNT);
            }
        }
        return studentsCount;
    }

    /**
     * Asks a user the studyGroup's expelled students count
     *
     * @return StudyGroup's count of expelled students
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Long askExpelledStudents() throws IncorrectInputInScriptException {
        String strExpelledStudents = "";
        long expelledStudents;
        while (true) {
            try {
                Console.println("Введите количество отчисленных студентов:");
                Console.print(Main.INPUT_INFO);
                strExpelledStudents = userScanner.nextLine().trim();
                if (fileMode) Console.println(strExpelledStudents);
                expelledStudents = Integer.parseInt(strExpelledStudents);
                if (expelledStudents <= MIN_EXPELLED_STUDENTS) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strExpelledStudents).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_EXPELLED_STUDENTS + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_EXPELLED_STUDENTS);
            }
        }
        return expelledStudents;
    }

    /**
     * Asks a user the studyGroup's admin
     *
     * @return Person [Admin]
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Person askGroupAdmin() throws IncorrectInputInScriptException {
        String name = askAdminName();
        long weight = askWeight();
        String passportID = askPassportID();
        Location location = askLocation();
        return new Person(name, weight, passportID, location);
    }

    /**
     * Asks a user the studyGroup's count of should be expelled students
     *
     * @return StudyGroup's count of should be expelled students
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Integer askShouldBeExpelled() throws IncorrectInputInScriptException {
        String strShouldBeExpelled = "";
        int shouldBeExpelled;
        while (true) {
            try {
                Console.println("Введите количество студентов, которые должны быть отчислены:");
                Console.print(Main.INPUT_INFO);
                strShouldBeExpelled = userScanner.nextLine().trim();
                if (fileMode) Console.println(strShouldBeExpelled);
                shouldBeExpelled = Integer.parseInt(strShouldBeExpelled);
                if (shouldBeExpelled <= MIN_SHOULD_BE_EXPELLED) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strShouldBeExpelled).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_SHOULD_BE_EXPELLED + ";" + Integer.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество студентов должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_SHOULD_BE_EXPELLED);
            }
        }
        return shouldBeExpelled;
    }

    /**
     * Asks a user the admin's name
     *
     * @return Person's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askAdminName() throws IncorrectInputInScriptException {
        return askName("Введите имя админа группы:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the location's name
     *
     * @return Location's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askLocationName() throws IncorrectInputInScriptException {
        return askName("Введите имя локации:", 0, MAX_LOCATION_NAME_LENGTH);
    }

    /**
     * Asks a user the studyGroup's name
     *
     * @return StudyGroup's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askGroupName() throws IncorrectInputInScriptException {
        return askName("Введите имя группы:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the person's weight
     *
     * @return Person's weight
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public long askWeight() throws IncorrectInputInScriptException {
        String strWeight = "";
        long weight;
        while (true) {
            try {
                Console.println("Введите вес студента:");
                Console.print(Main.INPUT_INFO);
                strWeight = userScanner.nextLine().trim();
                if (fileMode) Console.println(strWeight);
                weight = Integer.parseInt(strWeight);
                if (weight <= MIN_WEIGHT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strWeight).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_WEIGHT + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Вес должен быть представлен числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_WEIGHT);
            }
        }
        return weight;
    }

    /**
     * Asks a user the admin's passport ID
     *
     * @return Person's passportID
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askPassportID() throws IncorrectInputInScriptException {
        return askName("Введите ID паспорта:", 4, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the admin's location
     *
     * @return Person's location
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Location askLocation() throws IncorrectInputInScriptException {
        int x = askX(false);
        int y = (int) askY();
        String name = askLocationName();
        return new Location(x, y, name);
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(Main.INPUT_INFO);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                Console.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}
