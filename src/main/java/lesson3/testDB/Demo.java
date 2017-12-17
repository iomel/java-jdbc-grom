package lesson3.testDB;

public class Demo {

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Delete ALL test: " + solution.testDeletePerformance());

        System.out.println("Save test: " + solution.testSavePerformance());

        System.out.println("Select by ID test: " + solution.testSelectByIdPerformance());

        System.out.println("SELECT ALL test: " + solution.testSelectPerformance());

        System.out.println("Delete by ID test: " + solution.testDeleteByIdPerformance());

        System.out.println("Delete ALL test: " + solution.testDeletePerformance());

    }
}
