import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int HP = 100;

    public static void main(String[] args) {

         Scanner scanner = new Scanner(System.in);
         Random random = new Random();

         int personX = 1;
         int personY = 5;
         int sizeBoard = 5;

         String person = "Гг";
         String monster = "М";
         String castle = "З";
         String empty = ".";

         System.out.println("Привет! Ты готов начать игру? (67/нет)");
         System.out.println("Количество HP: " + HP);
         System.out.println("Твой ответ :");

         String answer = scanner.next().trim();

         System.out.println("Получено: [" + answer + "]");

         if (answer.equalsIgnoreCase("67")) {
            System.out.println("Начинаем играть!");
         } else if (answer.equalsIgnoreCase("нет")) {
            System.out.println("Приходи ещё, я буду тебя ждать! :)");
            scanner.close();
            return;
         } else {
            System.out.println("Непонятен твой ответ. Введи 67 или нет");
            scanner.close();
            return;
         }

         int difficultGame;

         while (true) {
            System.out.println("Выбери сложность игры (от 1 до 5)");
            System.out.println("Я хочу сложность: ");


            if (scanner.hasNextInt()) {
                difficultGame = scanner.nextInt();

                if(difficultGame >=1 && difficultGame <=5) {
                    break;
                }
            } else {
                System.out.println("Ошибка! Введи целое число (ну пожалуйста)");
            }

            System.out.println("Число должно быть от 1 до 5.");   
        }
        
        // Поле
        String[][] board = new String[sizeBoard][sizeBoard];

        for (int y = 0; y < sizeBoard; y++) {
            for (int x = 0; x < sizeBoard; x++) {
                board[y][x] = empty;
            }
        }
        
        // размещение замка
        int castleX;
        int castleY;

        do { 
            castleX = random.nextInt(sizeBoard) + 1;
            castleY = random.nextInt(sizeBoard) + 1;
        } while (castleX == personX && castleY == personY);

        board[castleY - 1][castleX - 1] = castle;

        //  типо монстры на каникулах
        int countMonster = difficultGame * 2;

        for (int i = 0; i < countMonster; i++){
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

            System.out.println("\nКоличество HP: " + HP);

            System.out.print("   ");

            for (int x = 1; x <= sizeBoard; x++) {
                System.out.print(" " + x + " ");
            }

            System.out.println();

            // рисуем игровое поле
            for (int y = 1; y <= sizeBoard; y++) {
                System.out.print(y + "  ");

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
                "Введи новые координаты x и y через пробел"
            );
            System.out.println("Для выхода введи 0 0");
System.out.print("> ");

            if(!scanner.hasNextInt()) {
                System.out.println("Ошибка! Координаат x должна быть числом");
                scanner.next();
                continue;
            }

            int x = scanner.nextInt();

            if(!scanner.hasNextInt()) {
                System.out.println("Ошибка! Координаат y должна быть числом");
                scanner.next();
                continue;
            }

            int y = scanner.nextInt();

            // выход из игры
            if (x == 0 && y == 0) {
                System.out.println("Ты завершил(а) игру");
                break;
            }

            // Проверка границ поля
            if (x < 1 || x > sizeBoard || 
                y < 1 || y > sizeBoard) {

                System.out.println(
                    "Нельзя выйти за пределы поля!"
                    + "Используй координа ты от 1 до 5"
                );
                continue;
            }

            // Проверяем, что ход на одну клетку
            int distanceX = Math.abs(x - personX);
            int distanceY = Math.abs(y - personY);

            if (distanceX + distanceY != 1) {
                System.out.println(
                    "Неккоректный ход! Можно двигаться "
                    + "только на одну клетку по вертикали или горизонтали"
                );
                continue;
            }

            // Проверка клетки назначения
            String target = board[y - 1][x - 1];

            if (target.equals(monster)) {
                HP-= 20;

                System.out.println("Ты встретил монстра!");
                System.err.println("Потеряно 15HP ");

                // Убираем монмтра с поля
                board[y - 1][x - 1] = empty;

                System.out.println("Осталось HP: " + HP);
            } else if (target.equals(castle)) {
                personX = x;
                personY = y;

                System.out.println("Замок найден!");
                System.out.println("Поздравляю, ты победил!");

                gameOver = true;
                continue;
            }

            // Перемещаем Гг
            personX = x;
            personY = y;

            System.out.println(
                "Ход выполнен! Новые координаты: x = "
                + personX + ", y = " + personY
            );

            // Проверяем количество HP
            if (HP <= 0) {
                System.out.println("\nКоличество HP закончилось!");
                System.out.println("Победи монстра, чтобы остаться в игре");

                int a = random.nextInt(300);
                int b = random.nextInt(300);
                int trueAnswer = a + b;
                System.out.println("Реши пример: " + a + " + " + b + " = ?");
                int ans = scanner.nextInt();
            if (trueAnswer == ans) {
                System.out.println("Верно! Ты победил монстра");
                HP+= 30;
            }  else  {
                System.out.println("Ты проиграл эту битву! Игра закончилась");
                break;
                }
                //gameOver = true;
            }

            scanner.close();

            if (board[y - 1][x - 1].equals("  ")) {
                board[personY - 1][personX - 1] = "  ";
                personX = x;
                personY = y;
                System.out.println("Ход корректный; Новые координаты: x = "
                                   + personX + ", y =  " + personY);
            } else if (board[y - 1][x - 1].equals(castle)) {
                System.out.println("Вы прошли игру!");
                break;
            } else {
                System.out.println("Решите задачу.");
                if (taskMonster()) {
                    if (succes()) {
                        board[personY - 1][personX - 1] = "  ";
                        personX = x;
                        personY = y;
                } else {
                    HP-= 20;    
                }
}
        }

    }
    }   

    static void outputBoard(String[][] board, int live) {
        String leftBlock = "| ";
        String rightBlock = "|";
        String wall = "+ —— + —— + —— + —— + —— +";

    for (String[] row : board) {
        System.out.println(wall);
        for (String col : row) {
            System.out.print(leftBlock + col + " ");
        }
        System.out.println(rightBlock);
    }
    System.out.println(wall);

    System.out.println("Количество HP: \t" + HP + "\n"); // другой массив,спросить про переменные HP
    }

    public class Person {
        private int x, y;
        private String image = "\uD83E\uDDD9"; 
        private int HP = 100; 


        public boolean isMoveCorrect(int x, int y) {
        return this.x == x && Math.abs(this.y - y) == 1 || this.y == y && Math.abs(this.x - x) == 1;
        }

        Person(int sizeBoard) {
            Random random = new Random();
            x = 1 + random.nextInt(sizeBoard);
            y = sizeBoard;
        }

        Person(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }
        public int getY() {
            return y;
        }
        public int getLive() {
            return HP;
        }
        public String getImage(){
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void downLive() {
        if (HP <= 0)
            HP = 0;
        else
            HP-= 20;
        }

        void move(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // метод:
    public boolean taskMonster(int difficultGame){
        System.out.println("Решите задачу:");
        int x = r.nextInt(15);
        int y = r.nextInt(10);
        int z = r.nextInt(20);
        int trueAnswer = x * y - z;
        System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");
        Scanner sc = new Scanner(System.in);
        int ans = sc.nextInt();
        if (trueAnswer == ans) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }

        
    }



}
