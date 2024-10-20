package co.edu.unbosque.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceAlgorithmicComplexity {

    private String content;

    public String readFile(MultipartFile file) throws IOException {
        content = new String(file.getBytes());
        return content;
    }

    public Map<String, Object> kmpSearch(String pattern) {
        if (content == null || content.isEmpty()) {
            throw new IllegalStateException("El contenido del archivo no ha sido leído.");
        }
        return kmpSearchInText(content, pattern);
    }

    private Map<String, Object> kmpSearchInText(String text, String pattern) {
        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int[] lps = computeLPSArray(pattern);
        int i = 0;
        int j = 0;
        int count = 0;
        List<Integer> positions = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                positions.add(i - j);
                count++;
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("positions", positions);
        result.put("duration", duration);

        return result;
    }

    private int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        lps[0] = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    public Map<String, Object> bmSearch(String pattern) {
        if (content == null || content.isEmpty()) {
            throw new IllegalStateException("El contenido del archivo no ha sido leído.");
        }
        return bmSearchInText(content, pattern);
    }

    private Map<String, Object> bmSearchInText(String text, String pattern) {
        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int[] badChar = computeBadCharTable(pattern);
        List<Integer> positions = new ArrayList<>();
        int count = 0;

        long startTime = System.currentTimeMillis();

        int m = pattern.length();
        int n = text.length();
        int s = 0;

        while (s <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                positions.add(s);
                count++;
                s += (s + m < n) ? m - badChar[text.charAt(s + m)] : 1;
            } else {
                s += Math.max(1, j - badChar[text.charAt(s + j)]);
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("positions", positions);
        result.put("duration", duration);

        return result;
    }

    private int[] computeBadCharTable(String pattern) {
        int[] badChar = new int[256];
        for (int i = 0; i < badChar.length; i++) {
            badChar[i] = -1;
        }

        for (int i = 0; i < pattern.length(); i++) {
            badChar[(int) pattern.charAt(i)] = i;
        }

        return badChar;
    }
}
