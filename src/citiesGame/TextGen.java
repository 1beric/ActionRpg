package citiesGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import engine.components.updateable.UpdateableComponent;
import engine.util.MathTools;

public class TextGen extends UpdateableComponent {

    private static final String TEXT_FILE = "res/mobydick.txt";
    private static final String OUT_FILE = "res/mobydickOut.txt";

    private Map<String, Map<Character, Integer>> m_Probabilities;


    @Override
    public void init() {
        m_Probabilities = new HashMap<>();

        System.out.println("@@@");

        try (Scanner scanner = new Scanner(new File(TEXT_FILE))) {
            scanner.useDelimiter("");
            String prefix = "||";
            while (scanner.hasNext()) {
                char current = scanner.next().charAt(0);
                if (!m_Probabilities.containsKey(prefix)) {
                    m_Probabilities.put(prefix, new HashMap<>());
                }

                Map<Character, Integer> prefixMap = m_Probabilities.get(prefix);

                if (!prefixMap.containsKey(current)) {
                    prefixMap.put(current, 1);
                } else {
                    prefixMap.put(current, prefixMap.get(current) + 1);
                }

                prefix = prefix.substring(1) + current;
                // System.out.println(prefix);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

        try (FileOutputStream fos = new FileOutputStream(new File(OUT_FILE))) {
            for (String prefix : m_Probabilities.keySet()) {
                for (char ch : m_Probabilities.get(prefix).keySet()) {
                    String line = prefix + " + " + ch + ": " + m_Probabilities.get(prefix).get(ch);
                    fos.write(line.getBytes());
                    fos.write('\n');
                }
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String text = "";
        String prefix = "||";
        for (int i = 0; i < 1000; i++) {
            char current = randomChoice(prefix);
            text += current;
            prefix = prefix.substring(1) + current;
        }

        System.out.println(text);

    }

    private char randomChoice(String prefix) {
        float totalChoices = 0;
        for (char current : m_Probabilities.get(prefix).keySet()) {
            totalChoices += m_Probabilities.get(prefix).get(current);
        }

        float randomFloat = MathTools.rFloat(0, totalChoices);

        float choiceCount = 0;
        for (char current : m_Probabilities.get(prefix).keySet()) {
            choiceCount += m_Probabilities.get(prefix).get(current);
            if (randomFloat <= choiceCount)
                return current;
        }

        return ' ';
    }


}
