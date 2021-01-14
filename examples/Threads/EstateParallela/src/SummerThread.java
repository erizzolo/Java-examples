public class SummerThread extends Thread {

    private double[] numbers;
    private int begin, end; // [begin..end[
    private double total = 0.0;

    public SummerThread(double[] numbers, int begin, int end) {
        this.numbers = numbers;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = begin; i < end; i++) {
            total += numbers[i];
        }
        System.out.println("Terminato " + currentThread());
    }

    public double getTotal() {
        return total;
    }
}
