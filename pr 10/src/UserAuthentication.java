import java.util.Scanner;

class UserAuthentication {
    private static final int MAX_USERS = 15;
    private static String[] usernames = new String[MAX_USERS];
    private static String[] passwords = new String[MAX_USERS];
    private static int userCount = 0;
    private static final String[] forbiddenPasswords = {"admin", "pass", "password", "qwerty", "ytrewq"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Аутентифікація");
            System.out.println("4 - Вийти");
            System.out.print("Ваш вибір: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            try {
                switch (choice) {
                    case 1:
                        addUser(scanner);
                        break;
                    case 2:
                        deleteUser(scanner);
                        break;
                    case 3:
                        authenticateUser(scanner);
                        break;
                    case 4:
                        System.out.println("Вихід...");
                        return;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addUser(Scanner scanner) throws Exception {
        if (userCount >= MAX_USERS) {
            throw new Exception("Досягнуто максимальну кількість користувачів.");
        }

        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();
        validateUsername(username);

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        validatePassword(password);

        usernames[userCount] = username;
        passwords[userCount] = password;
        userCount++;
        System.out.println("Користувач успішно доданий.");
    }

    private static void deleteUser(Scanner scanner) throws Exception {
        System.out.print("Введіть ім'я користувача для видалення: ");
        String username = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                usernames[i] = usernames[userCount - 1];
                passwords[i] = passwords[userCount - 1];
                usernames[userCount - 1] = null;
                passwords[userCount - 1] = null;
                userCount--;
                System.out.println("Користувача видалено.");
                return;
            }
        }
        throw new Exception("Користувача не знайдено.");
    }

    private static void authenticateUser(Scanner scanner) throws Exception {
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                System.out.println("Аутентифікація успішна!");
                return;
            }
        }
        throw new Exception("Невірне ім'я або пароль.");
    }

    private static void validateUsername(String username) throws Exception {
        if (username.length() < 5 || username.contains(" ")) {
            throw new Exception("Ім'я користувача має бути не менше 5 символів і не містити пробілів.");
        }
    }

    private static void validatePassword(String password) throws Exception {
        if (password.length() < 10 || password.contains(" ")) {
            throw new Exception("Пароль має містити не менше 10 символів і не мати пробілів.");
        }
        if (!password.matches(".*[!@#$%^&*()].*")) {
            throw new Exception("Пароль має містити хоча б один спеціальний символ.");
        }
        if (password.replaceAll("\\D", "").length() < 3) {
            throw new Exception("Пароль має містити принаймні 3 цифри.");
        }
        for (String forbidden : forbiddenPasswords) {
            if (password.toLowerCase().contains(forbidden)) {
                throw new Exception("Пароль містить заборонене слово.");
            }
        }
    }
}
