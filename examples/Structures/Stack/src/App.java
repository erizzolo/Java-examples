public class App {
    public static void main(String[] args) throws Exception {
        test(new ArrayStack<>(5));
        test(new SafeArrayStack<>(5));
    }

    public static void test(Stack<Integer> stack) {
        System.out.println(stack);
        for (int i = 0; i < 10; i++) {
            try {
                stack.push(i);
            } catch (UnsupportedOperationException e) {
                System.out.println(e);
            }
        }
        System.out.println(stack);
        for (int i = 0; i < 11; i++) {
            // if(s.size() > 0)
            try {
                System.out.println(stack.pop());
            } catch (UnsupportedOperationException e) {
                System.out.println(e);
            }
        }
        System.out.println(stack);
        threadTest(stack);
    }

    public static void threadTest(Stack<Integer> stack) {
        System.out.println("Test thread safety of " + stack);
        for (int i = 0; i < 10_000_000; i++) {
            stack.push(i);
        }
        System.out.println("Size = " + stack.size());
        StackThread[] t = new StackThread[3];
        for (int i = 0; i < t.length; i++) {
            t[i] = new StackThread(stack);
            t[i].start();
        }
        long total = 0;
        for (int i = 0; i < t.length; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ex) {
            }
            total += t[i].getExtracted();
        }
        System.out.println(total);
    }
}
