import java.io.*;
import java.util.*;

public class QuizGame {
    private static final String QUESTIONS_FILE = "questions.dat";
    private static final String RAITING_FILE = "raiting.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в викторину!");
        System.out.print("Введите ваше имя: ");
        String playerName = scanner.nextLine();
        Questions questions = loadQuestions();
        if (questions == null) {
            System.out.println("Не удалось загрузить вопросы.");
            return;
        }
        int score = 0;
        score += askQuestion(questions.question1, questions.response1, questions.goodResponseIndex1, scanner);
        score += askQuestion(questions.question2, questions.response2, questions.goodResponseIndex2, scanner);
        score += askQuestion(questions.question3, questions.response3, questions.goodResponseIndex3, scanner);
        saveResult(playerName, score);
        showRaiting();
    }

    private static Questions loadQuestions() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(QUESTIONS_FILE))) {
            return (Questions) ois.readObject();
        } catch (Exception e) {
            System.out.println("Ошибка загрузки вопросов: " + e.getMessage());
            return null;
        }
    }

    private static int askQuestion(String question, String[] responses, int correctIndex, Scanner scanner) {
        System.out.println("\n" + question);
        for (int i = 0; i < responses.length; i++) {
            System.out.println((i + 1) + ". " + responses[i]);
        }
        System.out.print("Ваш ответ (1-3)" + "): ");
        int answer = scanner.nextInt();
        scanner.nextLine();
        if (answer - 1 == correctIndex) {
            System.out.println("Правильно!");
            return 1;
        } else {
            System.out.println("Неправильно! Правильный ответ: " + (correctIndex + 1));
            return 0;
        }
    }

    private static void saveResult(String playerName, int score) {
        Raiting raiting = loadRaiting();
        if (raiting == null) {
            raiting = new Raiting();
        }
        raiting.addGame(new Game(playerName, score, new Date()));
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(RAITING_FILE))) {
            oos.writeObject(raiting);
        } catch (Exception e) {
            System.out.println("Ошибка сохранения рейтинга: " + e.getMessage());
        }
    }

    private static Raiting loadRaiting() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RAITING_FILE))) {
            return (Raiting) ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            System.out.println("Ошибка загрузки рейтинга: " + e.getMessage());
            return null;
        }
    }

    private static void showRaiting() {
        Raiting raiting = loadRaiting();
        if (raiting == null || raiting.games.isEmpty()) {
            System.out.println("Рейтинг пуст.");
            return;
        }
        List<Game> sortedGames = raiting.games.stream()
                .sorted((g1, g2) -> g2.raiting.compareTo(g1.raiting))
                .toList();
        System.out.println("\n=== Рейтинг игроков ===");
        System.out.println("Игрок\tРейтинг\tДата");
        for (Game game : sortedGames) {
            System.out.printf("%s\t%d\t%tF %<tT%n",
                    game.gamer, game.raiting, game.gameDate);
        }
    }
}
