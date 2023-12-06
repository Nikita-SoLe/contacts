import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        TreeMap<String, String> contact = new TreeMap<>();

        while (true) {
            System.out.println("Введите Номер или Имя.\n" +
                            "LIST - посмотреть книгу.");
            String input = scan.nextLine();
            if (!input.isEmpty())
                contact.putAll(mainContact(input, contact));
            else {
                System.out.println("Не может быть пустым.");
            }
        }

    }

    static TreeMap<String, String> mainContact(String input, TreeMap<String, String> contact) {

        if (input.equals("LIST")) {
            printAllContact(contact);
        } else if (input.matches("\\d+")) {
            String number = getCorrectName(input);

            if (!contact.containsValue(number)) {
                contact.putAll(addContactNum(number, contact));
                System.out.println("Контакт добавлен.");
            } else {
                System.out.printf("%s: %s\n", getContactName(number, contact), number);
            }
        } else {
            String name = getName(input);
            if (!contact.containsKey(name)) {
                contact.putAll(addContactName(name, contact));
                System.out.println("Контакт добавлен.");
            } else {
                System.out.printf("%s: %s\n", name, contact.get(name));
            }
        }
        return contact;
    }

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

    private static String getName(String str) {
        Matcher match = Pattern.compile("[ a-zA-ZА-Яа-яёЁ]+").matcher(str);

        match.find();
        return match.group();
    }

    private static String getNumber(String str) {
        Matcher match = Pattern.compile("[0-9]{10,16}").matcher(str);

        match.find();
        return match.group();
    }

    private static TreeMap<String, String> addContactNum(String number, TreeMap<String, String> map) {
        System.out.println("Пожалуйста введите имя контакта.");
        String name = getName(scan.nextLine());
        map.put(name, number);
        return map;
    }

    private static TreeMap<String, String> addContactName(String name, TreeMap<String, String> map) {
        System.out.println("Пожалуйста введите номер");
        String number = getCorrectName(scan.nextLine());
        map.put(name, number);
        return map;
    }

    private static String getContactName(String number, TreeMap<String, String> map) {
        String name = "";
        for (String elem : map.keySet()) {
            if (map.get(elem).equals(number)) {
                name = elem;
            }
        }
        return name;
    }

    private static void printAllContact(TreeMap<String, String> map) {

        if (map.isEmpty()) {
            System.out.println("Книга пуста");
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }

}