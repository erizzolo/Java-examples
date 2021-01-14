public class EstateParallela {
    public static void main(String[] args) throws Exception {
        double[] numbers = new double[100_000_000];
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = i;
        {
            long start = System.nanoTime();
            double sum = sum(numbers); // somma sequenziale
            long execTime = System.nanoTime() - start;
            System.out.println("Sum is " + sum);
            System.out.println("Execution time " + execTime / 1_000_000.0 + " [ms]");
        }
        SummerThread thread = new SummerThread(numbers, 0, numbers.length);
        thread.start();
        System.out.println("Started " + thread);
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Total is " + thread.getTotal());
        Runtime runtime = Runtime.getRuntime();
        int processors = runtime.availableProcessors();
        System.out.println("Available processors = " + processors);
        long freeMemory = runtime.freeMemory();
        System.out.println("Free memory = " + freeMemory);
        long maxMemory = runtime.maxMemory();
        System.out.println("Max memory = " + maxMemory);
        {
            long start = System.nanoTime();
            double sum = sumParallel(numbers, processors); // somma parallela
            long execTime = System.nanoTime() - start;
            System.out.println("Sum is " + sum);
            System.out.println("Execution time " + execTime / 1_000_000.0 + " [ms]");
        }
    }

    private static double sum(double[] numbers) {
        double total = 0.0;
        for (int i = 0; i < numbers.length; i++)
            total += numbers[i];
        return total;
    }

    private static double sumParallel(double[] numbers, int numThreads) {
        double total = 0.0;
        SummerThread[] threads = new SummerThread[numThreads];
        int perThread = numbers.length / numThreads + 1;
        for (int i = 0; i < numThreads; i++) { // create Threads
            int begin = i * perThread;
            int end = (i + 1) * perThread;
            if (end > numbers.length) {
                end = numbers.length;
            }
            threads[i] = new SummerThread(numbers, begin, end);
        }
        for (int i = 0; i < threads.length; i++) { // start Threads
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) { // join Threads
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        for (int i = 0; i < threads.length; i++) { // get results
            total += threads[i].getTotal();
        }
        return total;
    }

}