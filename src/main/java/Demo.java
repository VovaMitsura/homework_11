import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class Demo {
    private static final List<String> names = List.of("Vasya", "Petya", "Oleg", "Denis", "Vadim", "Max", "Nastya");
    private static final int[] arrayToSort = new int[]{0, -9, 5, 2, -7, 1};
    private static final Stream<Integer> stream1 = Stream.of(1, 5, 3);
    private static final Stream<Integer> stream2 = Stream.of(4, 6);

    public static void main(String[] args) {
        /*task1*/
        System.out.println("------TASK1------");
        System.out.println(task1(names));

        /*task2*/
        System.out.println("------TASK2------");
        System.out.println(task2(names));

        /*task3*/
        System.out.println("------TASK3------");
        System.out.println(task3(arrayToSort));

        /*task4*/
        System.out.println("------TASK4------");
        task4(25214903917L, 11, (long) Math.pow(2, 48))
                .limit(5)
                .forEach(System.out::println);

        /*task5*/
        System.out.println("------TASK5------");
        BiFunction<Integer, Integer, String> zipFunction = (integer, integer2) -> integer + " " + integer2;

        zip(stream1, stream2, zipFunction)
                .forEach(s -> System.out.print(s + " "));

    }

    private static String task1(List<String> names) {
        return IntStream.range(1, names.size() + 1)
                .mapToObj(o -> (o) + "." + names.get(o - 1))
                .filter(s -> Integer.parseInt(s.substring(0, 1)) % 2 != 0)
                .collect(Collectors.joining(", "));
    }

    private static List<String> task2(List<String> names) {
        return names.stream()
                .map(String::toUpperCase).sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private static String task3(int[] array) {
        return Arrays.stream(array)
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));

    }

    private static Stream<Long> task4(long a, long c, long m) {
        return LongStream.iterate(5, value -> (a * value + c) % m)
                .boxed();
    }

    public static <T, V> Stream<V> zip(Stream<T> first, Stream<T> second, BiFunction<T, T, V> zipFunction) {
        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();

        Iterator<V> zipIterator = new Iterator<>() {
            @Override
            public boolean hasNext() {
                return firstIterator.hasNext() && secondIterator.hasNext();
            }

            @Override
            public V next() {
                return zipFunction.apply(firstIterator.next(), secondIterator.next());
            }
        };

        Iterable<V> zipIterable = () -> zipIterator;
        return StreamSupport.stream(zipIterable.spliterator(), false);
    }
}
