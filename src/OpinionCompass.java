import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class OpinionCompass {

    private ArrayList<Opinion> opinionList;
    private ArrayList<Result> resultHistory;
    private boolean resultsLoadedSuccessful;
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
        this.scoreMappings.put(1, 2);
        this.scoreMappings.put(2, 1);
        this.scoreMappings.put(3, -1);
        this.scoreMappings.put(4, -2);
        this.scanner = new Scanner(System.in);
        this.resultsLoadedSuccessful = this.loadResults();
        this.menu();
    }

    private void menu() {
        System.out.println("1. Visa resultathistorik\n2. Starta valkompassen\n3. Avsluta");
        String inputStr = this.scanner.nextLine();
        try {
            int input = Integer.parseInt(inputStr);
            if (input == 1) {
                if (this.resultsLoadedSuccessful) {
                    this.printResultHistory();
                } else {
                    System.out.println("Resultathistorik kunde ej hittas.\n");
                }
                this.menu();
            } else if (input == 2) {
                this.createUser();
                this.run();
                this.menu();
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Felaktig inmatning, försök igen.\n");
            this.menu();
        } catch (InputMismatchException ime) {
            System.out.println("Felaktig inmatning, försök igen.\n");
            this.menu();
        }
    }

    private void run() {
        for (Opinion opinion : this.opinionList) {
            boolean answerAccepted = false;

            while (!answerAccepted) {
                System.out.println(
                        "\n(" + (this.opinionList.indexOf(opinion) + 1) + "/" + (this.opinionList.size()) + ")");
                opinion.printAlternatives();
                System.out.print("Ange svar: ");
                String answerStr = this.scanner.nextLine();

                try {
                    int answer = Integer.parseInt(answerStr);
                    if (answer != 1 && answer != 2 && answer != 3 && answer != 4) {
                        System.out.println("Felaktig inmatning. Försök igen.\n");
                    } else {
                        this.user.putResult(
                                opinion.getPartyScoreResult(
                                        this.scoreMappings.get(answer)));
                        answerAccepted = true;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Felaktig inmatning. Försök igen.\n");
                }
            }
        }
        this.user.printResult();
        System.out.println("1. Spara resultat och avsluta\n2. Avsluta utan att spara resultat");
        String saveResult = this.scanner.nextLine();
        if (Integer.parseInt(saveResult) == 1) {
            this.resultHistory.add(this.user.getResult());
            boolean saveSuccessful = Storage.serializeResults(this.resultHistory);
            if (!saveSuccessful) {
                System.out.println("Någonting gick fel vid sparandet av resultatet.");
            }
        }
    }

    private void createUser() {
        System.out.println("Ange namn: ");
        String userName = this.scanner.nextLine();
        this.user = new User(userName);
    }

    private boolean loadResults() {
        this.resultHistory = Storage.deserializeResults();
        if (this.resultHistory != null) {
            return true;
        } else {
            this.resultHistory = new ArrayList<Result>();

            return false;
        }
    }

    private void printResultHistory() {
        for (Result result : this.resultHistory) {
            result.printResult();
        }
    }

}
