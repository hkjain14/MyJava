package Interviews.Confluent_Dec25.DSA_Practice;

import java.io.*;

public class FileReadLastLines {
    static void tail(String fileName, int lines) throws Exception{
        if (lines <=0)
            return;
        try(RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            long totalFileLength = file.length();
            long pointer = totalFileLength - 1;
            int linesFound = 0;

            if (pointer < 0) {
                System.out.println("File is empty.");
                return;
            }

            file.seek(pointer);
            if(file.readByte() == '\n') {
                pointer--;
            }

            while(pointer >= 0) {
                file.seek(pointer);
                if(file.readByte() == '\n') {
                    linesFound++;
                    if (linesFound > lines)
                        break;
                }
                pointer --;
            }
            if (pointer < 0)
                file.seek(0); // When lines is more than total lines in file, then pointer would have value -1.

            InputStreamReader in = new InputStreamReader(new FileInputStream(file.getFD()));
            BufferedReader reader = new BufferedReader(in);

            String line;
            while((line = reader.readLine()) !=null ) {
                System.out.println(line);
            }

        } catch(Exception e) {
            // Example: when file can't be opened, coz it doesnt exist.
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception{
        String fileName = "testingFile.log";

        try(PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("Line 1");
            pw.println("Line 2");
            pw.println("Line 3");
            pw.println("Line 4");
            pw.println("Line 5");
            pw.println("Line 6\n");
        }
        System.out.println("Printing last 7 lines:");
        tail(fileName, 7);

        System.out.println("Printing last 2 lines:");
        tail(fileName, 2);

        File f = new File(fileName);
        f.delete();
    }
}
