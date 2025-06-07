import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GeneratorQuestions {
    public static void main(String[] args) {
        Questions questions = new Questions();

        questions.question1 = "Какой рукой бросает Леброн Джеймс?";
        questions.response1 = new String[]{"Правой", "Левой", "Он амбидекстер"};
        questions.goodResponseIndex1 = 0;

        questions.question2 = "Сколько титулов у Леброна?";
        questions.response2 = new String[]{"1", "4", "6"};
        questions.goodResponseIndex2 = 1;

        questions.question3 = "Сколько MVP у Леброна?";
        questions.response3 = new String[]{"2", "5", "4"};
        questions.goodResponseIndex3 = 2;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("questions.dat"))) {
            oos.writeObject(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}