import java.io.Serializable;
import java.util.Map;

public class User implements Serializable {

    private String name;
    private Result result;

    public User(String name) {
        this.name = name;
        this.result = new Result(this);
    }

    public String getName() {
        return this.name;
    }

    public Result getResult() {
        return this.result;
    }

    public void putResult(Map<String, Integer> partyScores) {
        this.result.updatePartyScores(partyScores);
    }

    public void printResult() {
        this.result.calculatePercentages();
        this.result.printResult();
    }

}
