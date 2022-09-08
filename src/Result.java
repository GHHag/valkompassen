import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Result implements Serializable {

    private User user;
    private Map<String, Integer> partyScores;
    private Date date;
    private int opinionRange;
    private int numOfOpinions;

    public Result(User user) {
        this.user = user;
        this.partyScores = new HashMap<String, Integer>();
        this.date = new Date();
        this.opinionRange = 4;
        this.numOfOpinions = 0;
    }

    public void printResult() {
        System.out.println("\nResultat: ");
        this.partyScores.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue() + "%"));
        System.out.println("Användare: " + this.user.getName() + "\nDatum & tid: " +
                this.date.toString() + "\n");
    }

    public void updatePartyScores(Map<String, Integer> partyScores) {
        for (Map.Entry<String, Integer> partyScore : partyScores.entrySet()) {
            if (this.partyScores.get(partyScore.getKey()) == null) {
                this.partyScores.put(partyScore.getKey(), this.opinionRange - Math.abs(partyScore.getValue()));
            } else {
                this.partyScores.put(partyScore.getKey(),
                        this.partyScores.get(partyScore.getKey()) +
                                (this.opinionRange - Math.abs(partyScore.getValue())));
            }
        }
        this.numOfOpinions++;
    }

    public void calculatePercentages() {
        for (Map.Entry<String, Integer> partyScore : this.partyScores.entrySet()) {
            double partyPercentage = (double) partyScore.getValue()
                    / ((double) this.opinionRange * (double) this.numOfOpinions) * 100 + 0.5;
            this.partyScores.put(partyScore.getKey(), (int) partyPercentage);
        }
    }

}
