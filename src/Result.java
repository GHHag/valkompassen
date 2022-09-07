import java.util.Map;
import java.util.HashMap;

public class Result {

    private Map<String, Integer> partyScores;

    public Result() {
        this.partyScores = new HashMap<String, Integer>();
    }

    public String toString() {
        String partyResults = "\nDitt resultat:\n";
        for (Map.Entry<String, Integer> partyScore : this.partyScores.entrySet()) {
            partyResults += partyScore.getKey() + ": " + partyScore.getValue() + "%\n";
        }
        return partyResults;
    }

    public void updatePartyScores(Map<String, Integer> partyScores) {
        for (Map.Entry<String, Integer> partyScore : partyScores.entrySet()) {
            // System.out.println();
            // System.out.println("key att uppdt: " + partyScore.getKey());
            if (this.partyScores.get(partyScore.getKey()) == null) {
                this.partyScores.put(
                        partyScore.getKey(),
                        partyScore.getValue());
                // (29 * 4) / 2); // landgen av questionsArray * max score / 2 för att få 50%
                // score
            } else {
                // System.out.println("val före uppdt: " +
                // this.partyScores.get(partyScore.getKey()));
                this.partyScores.put(
                        partyScore.getKey(),
                        this.partyScores.get(partyScore.getKey()) + partyScore.getValue());
            }
            // System.out.println("val efter uppdt: " +
            // this.partyScores.get(partyScore.getKey()));
        }
    }

    public void calculatePercentageScores() {
        int x = 29 * 4;
        float ps = x / this.partyScores.size();
        for (Map.Entry<String, Integer> partyScore : this.partyScores.entrySet()) {
            System.out.println(50 * (partyScore.getValue() / ps));
            this.partyScores.put(
                    partyScore.getKey(), (int) (50 * (partyScore.getValue() / ps)));
        }
    }

}
