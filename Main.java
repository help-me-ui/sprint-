import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in
);
        Random random = new Random();

        // Основные параметры игры
        int step = 0;
        int personX = 1;
        int personY = 5;
        int personLive = 3;
        int sizeBoard = 5;

        String person = "Г";
        String monster = "М";
        String castle = "З";
        String empty = "·";

        // Приветствие
        System.out.println("Привет! Ты готов начать игру? (67 / НЕТ)");
        System.out.println("Количество жизней: " + personLive);
        System.out.print("Твой ответ: ");

        // Считываем ответ
        String answer = scanner.next
().trim();

        System.out.println("Получено: [" + answer + "]");

        if (answer.equalsIgnoreCase("67")) {
            System.out.println("Начинаем играть!");
        } else if (answer.equalsIgnoreCase("НЕТ")) {
            System.out.println("Приходи ещё, я буду тебя ждать!");
            scanner.close();
            return;
        } else {
            System.out.println("Не понял ответ. Введи 67 или НЕТ.");
            scanner.close();
            return;
        }

        // Выбор сложности
        int difficultGame;

        while (true) {
            System.out.println("\nВыбери сложность игры (от 1 до 5):");

            if (scanner.hasNextInt()) {
                difficultGame = scanner.nextInt();

                if (difficultGame >= 1 && difficultGame <= 5) {
                    break;
                }
            } else {
                System.out.println("Ошибка! Введи целое число.");
                scanner.next
();
            }

            System.out.println("Число должно быть от 1 до 5.");
        }

        // Создаём игровое поле
        String[][] board = new String[sizeBoard][sizeBoard];

        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = empty;
            }
        }

        // Размещаем замок
        int castleX;
        int castleY;

        do {
            castleX = random.nextInt(sizeBoard) + 1;
            castleY = random.nextInt(sizeBoard) + 1;
        } while (castleX == personX && castleY == personY);

        board[castleY - 1][castleX - 1] = castle;

        // Размещаем монстров
        int countMonster = difficultGame * 2;

        for (int i = 0; i < countMonster; i++) {
            int monsterX;
            int monsterY;

            do {
                monsterX = random.nextInt(sizeBoard) + 1;
                monsterY = random.nextInt(sizeBoard) + 1;
            } while (
                !board[monsterY - 1][monsterX - 1].equals(empty)
                || (monsterX == personX && monsterY == personY)
            );

            board[monsterY - 1][monsterX - 1] = monster;
        }

        boolean gameOver = false;

        // Главный игровой цикл
        while (!gameOver) {

            // Выводим количество жизней и ходов
            System.out.println("\nЖизни: " + personLive);
            System.out.println("Номер хода: " + step);

            // Рисуем заголовок координат
            System.out.print("   ");

            for (int x = 1; x <= sizeBoard; x++) {
                System.out.print(" " + x + "  ");
            }

            System.out.println();

            // Рисуем игровое поле
            for (int y = 1; y <= sizeBoard; y++) {
                System.out.print(y + "  ");

                for (int x = 1; x <= sizeBoard; x++) {
                    if (x == personX && y == personY) {
                        System.out.print("[" + person + "]");
                    } else {
                        System.out.print("[" + board[y - 1][x - 1] + "]");
                    }
                }

                System.out.println();
            }

            // Ввод координат
            System.out.println(
                "\nТвоя позиция: x = " + personX
                + ", y = " + personY
            );

            System.out.println(
                "Введи новые координаты x и y через пробел."
            );
            System.out.println("Для выхода введи: 0 0");
            System.out.print("> ");

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Координата x должна быть числом.");
                scanner.next
();
                continue;
            }

            int x = scanner.nextInt();

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Координата y должна быть числом.");
                scanner.next
();
                continue;
            }

            int y = scanner.nextInt();

            // Выход из игры
            if (x == 0 && y == 0) {
                System.out.println("Ты завершил игру.");
                break;
            }

            // Проверка границ поля
            if (x < 1 || x > sizeBoard ||
                y < 1 || y > sizeBoard) {

                System.out.println(
                    "Нельзя выйти за пределы поля! "
                    + "Используй координаты от 1 до 5."
                );
                continue;
            }

            // Проверяем, что ход сделан на одну клетку
            int distanceX = Math.abs(x - personX);
            int distanceY = Math.abs(y - personY);

            if (distanceX + distanceY != 1) {
                System.out.println(
                    "Некорректный ход! Можно двигаться "
                    + "только на одну клетку по вертикали или горизонтали."
                );
                continue;
            }

            // Проверяем клетку назначения
            String target = board[y - 1][x - 1];

            if (target.equals(monster)) {
                personLive--;

                System.out.println("Ты встретил монстра!");
                System.out.println("Потеряна одна жизнь.");

                // Убираем монстра с поля
                board[y - 1][x - 1] = empty;

                System.out.println("Осталось жизней: " + personLive);

            } else if (target.equals(castle)) {

                personX = x;
                personY = y;
                step++;

                System.out.println("\nТы нашёл замок!");
                System.out.println("Поздравляю, ты победил!");
                System.out.println("Количество ходов: " + step);

                gameOver = true;
                continue;
            }

            // Перемещаем персонажа
            personX = x;
            personY = y;
            step++;

            System.out.println(
                "Ход выполнен! Новые координаты: x = "
                + personX + ", y = " + personY
            );

            // Проверяем количество жизней
            if (personLive <= 0) {
                System.out.println("\nЗакончились жизни!");
                System.out.println("Игра окончена.");
                System.out.println("Количество ходов: " + step);

                gameOver = true;
            }
        }

        scanner.close();
    }
}

// if (board[y - 1][x - 1].equals("  ")) {
//     board[personY - 1][personX - 1] = "  ";
//     personX = x;
//     personY = y;
//     step++;
//     System.out.println("Ход корректный; Новые координаты: " + personX + ", " + personY +
//                                     "\nХод номер: " + step);
// } else if (board[y - 1][x - 1].equals(castle)) {
//     System.out.println("Вы прошли игру!");
//     break;
// } else {
//     int a = random.nextInt(100);
//     int b = random.nextInt(100);
//     int trueAnswer = a + b;
//     System.out.println("Реши пример: " + a + " + " + b + " = ?");
//     int ans = scanner.nextInt();
//     if (trueAnswer == ans) {
//         System.out.println("Верно! Ты победил монстра :)");
//     } else {
//         System.out.println("Ты проиграл эту битву! :(");
//         personLive--;
//     }
// }

