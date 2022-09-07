import java.util.Map;
import java.util.HashMap;

public class Opinion {

    private String opinion;
    private Map<String, Integer> partyScores;

    public Opinion(String opinion, Map<String, Integer> partyScores) {
        this.opinion = opinion;
        this.partyScores = partyScores;
    }

    public String toString() {
        return this.opinion;
    }

    public void prompt() {
        System.out.println("\n" + this.toString());
        System.out.println("1. Instämmer helt\n" +
                "2. Instämmer delvis\n" +
                "3. Delvis emot\n" +
                "4. Helt emot");
    }

    public Map<String, Integer> getPartyScoreResult(int answer) {
        Map<String, Integer> partyScoreResults = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> partyScore : this.partyScores.entrySet()) {
            partyScoreResults.put(
                    partyScore.getKey(),
                    answer * partyScore.getValue());
        }
        return partyScoreResults;
    }

}
