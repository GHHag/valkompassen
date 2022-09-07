import java.util.Map;

public class User {

    private String name;
    private Result result;

    public User(String name) {
        this.name = name;
        this.result = new Result();
    }

    public String getName() {
        return this.name;
    }

    public void putResult(Map<String, Integer> partyScores) {
        this.result.updatePartyScores(partyScores);
    }

    public void printResult() {
        this.result.calculatePercentageScores();
        System.out.println(this.result.toString());
    }

}
