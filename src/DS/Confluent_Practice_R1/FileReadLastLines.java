package DS.Confluent_Practice_R1;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileReadLastLines {
    public static void tail(String fileName, int n) throws IOException {
        if (n <= 0) return;

        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            long fileLength = file.length();
            long pointer = fileLength - 1;
            int linesFound = 0;

            // Step 1: Scan backwards to find start position
            while (pointer >= 0) {
                file.seek(pointer);
                if (file.readByte() == '\n') {
                    linesFound++;
                    if (linesFound > n) {
                        break;
                    }
                }
                pointer--;
            }

            // Step 2: Read forward from the identified position
            if (pointer < 0) {
                file.seek(0); // file has fewer than n lines
            }

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(
                            new FileInputStream(file.getFD()),
                            StandardCharsets.UTF_8
                    ));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String file = "test.log";

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("line 1");
            pw.println("line 2");
            pw.println("line 3");
            pw.println("line 4");
            pw.println("line 5");
        }

        System.out.println("\nLast 2 lines:");
        FileReadLastLines.tail(file, 2);

        System.out.println("\nLast 10 lines:");
        FileReadLastLines.tail(file, 10);

        System.out.println("\nLast 0 lines:");
        FileReadLastLines.tail(file, 0);

        File f = new File(file);
        f.delete();
    }
}
