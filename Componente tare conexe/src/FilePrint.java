import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class FilePrint {
    private File file;
    public FilePrint(Vector<Vector<Integer>> adjencyMatrix) {
        try {
            file = new File("src/adjencyMatrix.txt");
            PrintWriter printWriter = new PrintWriter(file);
            for (int i = 0; i < adjencyMatrix.size(); i++) {
                for (int j = 0; j < adjencyMatrix.size(); j++) {
                    printWriter.print(adjencyMatrix.get(i).get(j) + " ");
                }
                printWriter.println();
            }
            printWriter.close();
        } catch (Exception e) {
            System.out.println("Error");
        }

    }

    public FilePrint() {
        file = new File("src/adjencyMatrix.txt");
    }

    public Vector<Vector<Integer>> FileRead() {
        Vector<Vector<Integer>> adjencyMatrix = new Vector<Vector<Integer>>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] numbers = line.split(" ");
                Vector<Integer> lineVector = new Vector<Integer>();
                for (int i = 0; i < numbers.length; i++) {
                    lineVector.add(Integer.parseInt(numbers[i]));
                }
                adjencyMatrix.add(lineVector);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return adjencyMatrix;
    }

}
