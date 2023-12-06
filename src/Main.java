import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    // Создаем объект Scanner для считывания ввода пользователя
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        // TreeMap для хранения контактов (ключ - имя, значение - номер)
        TreeMap<String, String> contact = new TreeMap<>();

        while (true) {
            System.out.println("Введите Номер или Имя.\n" +
                    "LIST - посмотреть книгу.");
            String input = scan.nextLine();
            if (!input.isEmpty())
                // Обработка ввода пользователя и добавление контакта
                contact.putAll(mainContact(input, contact));
            else {
                System.out.println("Не может быть пустым.");
            }
        }

    }

    // Метод для обработки ввода пользователя и добавления/вывода контактов
    static TreeMap<String, String> mainContact(String input, TreeMap<String, String> contact) {
        // Если введено "LIST", выводим все контакты
        if (input.equals("LIST")) {
            printAllContact(contact);
        }
        // Если введен номер телефона
        else if (input.matches("\\d+")) {
            String number = getCorrectName(input);

            // Проверка, есть ли такой номер в списке контактов
            if (!contact.containsValue(number)) {
                contact.putAll(addContactNum(number, contact));
                System.out.println("Контакт добавлен.");
            } else {
                System.out.printf("%s: %s\n", getContactName(number, contact), number);
            }
        }
        // Если введено имя
        else {
            String name = getName(input);
            // Проверка, есть ли такое имя в списке контактов
            if (!contact.containsKey(name)) {
                contact.putAll(addContactName(name, contact));
                System.out.println("Контакт добавлен.");
            } else {
                System.out.printf("%s: %s\n", name, contact.get(name));
            }
        }
        return contact;
    }

    // Метод для получения корректного номера телефона от пользователя
    private static String getCorrectName(String str) {
        if (str.matches("\\d{10,16}")) {
            str = getNumber(str);
            return str;
        } else {
            System.out.println("Введенный номер не корректен\n" +
                    "Номер телефона должен быть \n" +
                    "от 10 до 16 чисел");
            return getCorrectName(scan.nextLine());
        }
    }

    // Метод для извлечения имени из ввода пользователя
    private static String getName(String str) {
        Matcher match = Pattern.compile("[ a-zA-ZА-Яа-яёЁ]+").matcher(str);

        match.find();
        return match.group();
    }

    // Метод для извлечения номера телефона из ввода пользователя
    private static String getNumber(String str) {
        Matcher match = Pattern.compile("[0-9]{10,16}").matcher(str);

        match.find();
        return match.group();
    }

    // Метод для добавления контакта по номеру телефона
    private static TreeMap<String, String> addContactNum(String number, TreeMap<String, String> map) {
        System.out.println("Пожалуйста введите имя контакта.");
        String name = getName(scan.nextLine());
        map.put(name, number);
        return map;
    }

    // Метод для добавления контакта по имени
    private static TreeMap<String, String> addContactName(String name, TreeMap<String, String> map) {
        System.out.println("Пожалуйста введите номер");
        String number = getCorrectName(scan.nextLine());
        map.put(name, number);
        return map;
    }

    // Метод для получения имени контакта по номеру телефона
    private static String getContactName(String number, TreeMap<String, String> map) {
        String name = "";
        for (String elem : map.keySet()) {
            if (map.get(elem).equals(number)) {
                name = elem;
            }
        }
        return name;
    }

    // Метод для вывода списка всех контактов
    private static void printAllContact(TreeMap<String, String> map) {

        if (map.isEmpty()) {
            System.out.println("Книга пуста");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }

}
