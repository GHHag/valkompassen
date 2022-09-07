import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Storage {

    public static ArrayList<Opinion> parseOpinionsTextfile() throws FileNotFoundException, IOException {
        ArrayList<Opinion> opinionList = new ArrayList<Opinion>();

        try (BufferedReader reader = new BufferedReader(new FileReader("files/opinions.txt"))) {
            String opinionStr = reader.readLine();
            String partyScores = reader.readLine();
            String blank = reader.readLine();

            while (opinionStr != null && partyScores != null && blank != null) {
                opinionStr = reader.readLine();

                partyScores = reader.readLine();
                List<String> partyScoresSplit = Arrays.asList(partyScores.split(","));
                HashMap<String, Integer> partyScoresMap = new HashMap<String, Integer>();
                for (String partyScore : partyScoresSplit) {
                    if (partyScore.charAt(0) == Character.valueOf(' ')) {
                        partyScore = partyScore.replaceFirst(" ", "");
                    }
                    partyScore = partyScore.replace("+", "");
                    String partyScoreArray[] = partyScore.split(" ");
                    partyScoresMap.put(partyScoreArray[0], Integer.valueOf(partyScoreArray[1]));
                }

                Opinion opinion = new Opinion(opinionStr, partyScoresMap);
                opinionList.add(opinion);

                blank = reader.readLine();
                if (blank != null && blank.length() > 0) {
                    throw new Error("Parser failed");
                }
            }
        }

        return opinionList;
    }

}
