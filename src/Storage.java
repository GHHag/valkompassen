import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Storage {

    public static ArrayList<Opinion> parseOpinionsTextfile() throws FileNotFoundException, IOException {
        ArrayList<Opinion> opinionList = new ArrayList<Opinion>();

        try (InputStream is = Files.newInputStream(Paths.get("files/opinions.txt"), StandardOpenOption.READ)) {
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(reader);
            String opinionStr;
            String partyScores;
            String blank;

            while ((opinionStr = lineReader.readLine()) != null) {
                partyScores = lineReader.readLine();
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

                blank = lineReader.readLine();
                if (blank != null && blank.length() > 0) {
                    throw new Error("Parser failed");
                }
            }
        }

        return opinionList;
    }

    public static boolean serializeResults(ArrayList<Result> results) {
        try {
            Path path = Paths.get("files/serialized_results.ser");
            ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(results);
            out.close();

            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();

            return false;
        }
    }

    public static ArrayList<Result> deserializeResults() {
        try {
            FileInputStream fileIn = new FileInputStream("files/serialized_results.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            ArrayList<Result> results = (ArrayList<Result>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            return results;
        } catch (FileNotFoundException fne) {
            return null;
        } catch (IOException ioe) {
            ioe.printStackTrace();

            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Result class not found");
            cnfe.printStackTrace();

            return null;
        }
    }

}
