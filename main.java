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

        System.out.println("Привет! Готов начать игру? (ДА / НЕТ)");
        System.out.println("Количество жизней: " + personLive);

        String answer = scanner.nextLine().trim();

        if (!answer.equalsIgnoreCase("ДА")) {
            System.out.println("Приходи ещё, я буду тебя ждать!");
            scanner.close();
            return;
        }

        System.out.println("\nНачинаем игру!");

        // Выбор сложности
        int difficultGame;

        while (true) {
            System.out.println("Выбери сложность игры (от 1 до 5):");

            if (scanner.hasNextInt()) {
                difficultGame = scanner.nextInt();

                if (difficultGame >= 1 && difficultGame <= 5) {
                    break;
                }
            } else {
                scanner.next
();
            }

            System.out.println("Ошибка! Введи число от 1 до 5.");
        }

        // Игровое поле
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

        // Размещаем монстров в разных клетках
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

            // Отрисовка игрового поля
            System.out.println("\nКоличество жизней: " + personLive);
            System.out.println("Ход: " + step);

            System.out.print("   ");

            for (int x = 1; x <= sizeBoard; x++) {
                System.out.print(" " + x + "  ");
            }

            System.out.println();

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

            // Запрашиваем координаты следующего хода
            System.out.println(
                "\nТвоя позиция: x = " + personX
                + ", y = " + personY
            );

            System.out.println(
                "Введи новые координаты x и y (или 0 0 для выхода):"
            );

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Нужно ввести два целых числа.");
                scanner.next
();
                continue;
            }

            int x = scanner.nextInt();

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка! Введи координату y.");
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
            if (x < 1 || x > sizeBoard
                    || y < 1 || y > sizeBoard) {
                System.out.println("Нельзя выходить за пределы поля!");
                continue;
            }

            // Проверка: перемещение только на одну клетку
            int distanceX = Math.abs(x - personX);
            int distanceY = Math.abs(y - personY);

            if (distanceX + distanceY != 1) {
                System.out.println(
                    "Некорректный ход! Можно двигаться "
                    + "только на одну клетку по вертикали или горизонтали."
                );
                continue;
            }

            // Проверяем содержимое клетки
            String target = board[y - 1][x - 1];

            if (target.equals(monster)) {
                personLive--;

                System.out.println("Осторожно! Ты встретил монстра!");
                System.out.println("Осталось жизней: " + personLive);

                // Монстр исчезает после столкновения
                board[y - 1][x - 1] = empty;

            } else if (target.equals(castle)) {

                personX = x;
                personY = y;
                step++;

                System.out.println("\nПоздравляю! Ты нашёл замок!");
                System.out.println("Количество ходов: " + step);
                System.out.println("Ты победил!");
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

            // Проверяем жизни
            if (personLive <= 0) {
                System.out.println("\nЗакончились жизни!");
                System.out.println("Игра окончена. Ходов: " + step);
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

