import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class OpinionCompass {

    private ArrayList<Opinion> opinionList;
    private Map<Integer, Integer> scoreMappings;
    private Scanner scanner;
    private User user;

    public OpinionCompass() {
        try {
            this.opinionList = Storage.parseOpinionsTextfile();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println(ioe.getMessage());
        }
        this.scoreMappings = new HashMap<Integer, Integer>();
        this.scoreMappings.put(1, 1);
        this.scoreMappings.put(2, 1);
        this.scoreMappings.put(3, -1);
        this.scoreMappings.put(4, -2);
        this.scanner = new Scanner(System.in);
        this.createUser();
        this.run();
    }

    private void run() {
        for (Opinion opinion : this.opinionList) {
            boolean answerAccepted = false;

            while (!answerAccepted) {
                opinion.prompt();
                System.out.print("Ange svar: ");
                String answerStr = this.scanner.next();

                try {
                    int answer = Integer.parseInt(answerStr);
                    if (answer != 1 && answer != 2 && answer != 3 && answer != 4) {
                        System.out.println("Felaktig inmatning. Försök igen.");
                    } else {
                        this.user.putResult(
                                opinion.getPartyScoreResult(
                                        this.scoreMappings.get(answer)));
                        answerAccepted = true;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Felaktig inmatning. Försök igen.");
                }
            }
        }
        this.user.printResult();
    }

    private void createUser() {
        System.out.println("Ange namn: ");
        String userName = this.scanner.nextLine();
        this.user = new User(userName);
    }

    private void getResultsFromStorage() {

    }

    private void saveResultsToStorage() {

    }

}
