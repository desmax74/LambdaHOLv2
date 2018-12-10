package exercises.eclipse;

import org.junit.Test;
import org.junit.Ignore;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class H_Challenges {

    /**
     * Denormalize this map. The input is a map whose keys are the number of legs of an animal
     * and whose values are lists of names of animals. Run through the map and generate a
     * "denormalized" list of strings describing the animal, with the animal's name separated
     * by a colon from the number of legs it has. The ordering in the output list is not
     * considered significant.
     * <p>
     * Input is Map<Integer, List<String>>:
     *   { 4=["ibex", "hedgehog", "wombat"],
     *     6=["ant", "beetle", "cricket"],
     *     ...
     *   }
     * <p>
     * Output should be a List<String>:
     *   [ "ibex:4",
     *     "hedgehog:4",
     *     "wombat:4",
     *     "ant:6",
     *     "beetle:6",
     *     "cricket:6",
     *     ...
     *   ]
     */
    @Test @Ignore
    public void h1_denormalizeMap() {
        Map<Integer, List<String>> input = new HashMap<>();
        input.put(4, Arrays.asList("ibex", "hedgehog", "wombat"));
        input.put(6, Arrays.asList("ant", "beetle", "cricket"));
        input.put(8, Arrays.asList("octopus", "spider", "squid"));
        input.put(10, Arrays.asList("crab", "lobster", "scorpion"));
        input.put(750, Arrays.asList("millipede"));

        List<String> result = null; // TODO

        assertThat(result.size()).isEqualTo(13);
        assertThat(result)
                .contains("ibex:4", "hedgehog:4", "wombat:4", "ant:6", "beetle:6", "octopus:8", "spider:8",
                        "squid:8", "crab:10", "lobster:10", "scorpion:10", "millipede:750");
    }
    /* Hint 1:
     * There are several ways to approach this. You could use a stream of map keys,
     * a stream of map entries, or nested forEach() methods.
     */
    /* Hint 2:
     * If you use streams, consider using Stream.flatMap().
     */


    /**
     * Invert a "multi-map". (From an idea by Paul Sandoz)
     * <p>
     * Given a Map<X, Set<Y>>, convert it to Map<Y, Set<X>>.
     * Each set member of the input map's values becomes a key in
     * the result map. Each key in the input map becomes a set member
     * of the values of the result map. In the input map, an item
     * may appear in the value set of multiple keys. In the result
     * map, that item will be a key, and its value set will be
     * its corresopnding keys from the input map.
     * <p>
     * In this case the input is Map<String, Set<Integer>>
     * and the result is Map<Integer, Set<String>>.
     * <p>
     * For example, if the input map is
     *     {p=[10, 20], q=[20, 30]}
     * then the result map should be
     *     {10=[p], 20=[p, q], 30=[q]}
     * irrespective of ordering. Note that the Integer 20 appears
     * in the value sets for both p and q in the input map. Therefore,
     * in the result map, there should be a mapping with 20 as the key
     * and p and q as its value set.
     * <p>
     * It is possible to accomplish this task using a single stream
     * pipeline (not counting nested streams), that is, in a single pass
     * over the input, without storing anything in a temporary collection.
     */
    @Test @Ignore
    public void h2_invertMultiMap() {
        Map<String, Set<Integer>> input = new HashMap<>();
        input.put("a", new HashSet<>(Arrays.asList(1, 2)));
        input.put("b", new HashSet<>(Arrays.asList(2, 3)));
        input.put("c", new HashSet<>(Arrays.asList(1, 3)));
        input.put("d", new HashSet<>(Arrays.asList(1, 4)));
        input.put("e", new HashSet<>(Arrays.asList(2, 4)));
        input.put("f", new HashSet<>(Arrays.asList(3, 4)));

        Map<Integer, Set<String>> result = null; // TODO

        assertEquals(new HashSet<>(Arrays.asList("a", "c", "d")), result.get(1));
        assertEquals(new HashSet<>(Arrays.asList("a", "b", "e")), result.get(2));
        assertEquals(new HashSet<>(Arrays.asList("b", "c", "f")), result.get(3));
        assertEquals(new HashSet<>(Arrays.asList("d", "e", "f")), result.get(4));
        assertEquals(4, result.size());
    }
    /* Hint 1:
     * A general approach is to flatten the input structure in one stage
     * of the pipeline and then to create the result structure using a collector.
     */
    /* Hint 2:
     * A useful intermediate data structure after the flattening step
     * is a pair of items. You can write your own pair class, or you can
     * use a pre-existing class like AbstractMap.SimpleEntry.
     */


    /**
     * Select the longest words from an input stream. That is, select the words
     * whose lengths are equal to the maximum word length. For this exercise,
     * you must compute the result in a single pass over the input stream.
     * The type of the input is a Stream, so you cannot access elements at random.
     * The stream is run in parallel, so the combiner function must be correct.
     */
    @Test @Ignore
    public void h3_selectLongestWordsOnePass() {
        Stream<String> input = Stream.of(
            "alfa", "bravo", "charlie", "delta",
            "echo", "foxtrot", "golf", "hotel").parallel();

        List<String> result = input.collect(
            Collector.of(null, null, null, null));
        // TODO implement a collector by replacing the nulls above

        assertThat(result).containsExactly("charlie", "foxtrot");
    }
    /* Hint:
     * There are several ways to solve this exercise, but one approach is to
     * create a helper class with four functions, and then pass method
     * references to these functions to the Collector.of() method.
     */



    /**
     * Given a string, split it into a list of strings consisting of
     * consecutive characters from the original string. Note: this is
     * similar to Python's itertools.groupby function, but it differs
     * from Java's Collectors.groupingBy() collector.
     */
    @Test @Ignore
    public void h4_splitCharacterRuns() {
        String input = "aaaaabbccccdeeeeeeaaafff";

        List<String> result = null; // TODO

        assertThat(result).containsExactly("aaaaa", "bb", "cccc", "d", "eeeeee", "aaa", "fff");
    }
    /* Hint:
     * One possibility is a two-pass approach: one pass to gather data about
     * the boundaries between the runs, and the second to create the substrings
     * based on output from the first.
     */

    /**
     * Given a parallel stream of strings, collect them into a collection in reverse order.
     * Since the stream is parallel, you MUST write a proper combiner function in order to get
     * the correct result.
     */
    @Test @Ignore
    public void h5_reversingCollector() {
        Stream<String> input =
            IntStream.range(0, 100).mapToObj(String::valueOf).parallel();

        Collection<String> result =
            input.collect(Collector.of(null, null, null));
            // TODO fill in collector functions above

        String[] expectedResult = IntStream.range(0, 100)
                     .map(i -> 99 - i)
                     .mapToObj(String::valueOf)
                .toArray(String[]::new);
        assertThat(result).containsExactly(expectedResult);
    }
    /* Hint 1:
     * ArrayDeque supports fast insertion at the front.
     */
    /* Hint 2:
     * Be careful with ordering of the arguments and results in the combiner.
     */

    /**
     * Given an array of int, find the int value that occurs a majority
     * of times in the array (that is, strictly more than half of the
     * elements are that value), and return that int value in an OptionalInt.
     * Note, return the majority int value, not the number of times it occurs.
     * If there is no majority value, return an empty OptionalInt.
     * <p>
     * For example, given an input array [11, 12, 12] the result should be
     * an OptionalInt containing 12. Given an input array [11, 12, 13]
     * the result should be an empty OptionalInt.
     */

    OptionalInt majority(int[] array) {
        return null; // TODO
    }
    /* Hint:
     * A two-pass approach may be called for here: a counting pass
     * and a majority-finding pass.
     */

    @Test @Ignore
    public void h6_majority() {
        int[] array1 = { 13, 13, 24, 35, 24, 24, 35, 24, 24 };
        int[] array2 = { 13, 13, 24, 35, 24, 24, 35, 24 };

        OptionalInt result1 = majority(array1);
        OptionalInt result2 = majority(array2);

        assertThat(result1).isEqualTo(OptionalInt.of(24));
        assertThat(result2).isEmpty();
    }

    /**
     * Write a method that takes an IntFunction and returns a Supplier.
     * An IntFunction takes an int as an argument and returns some object.
     * A Supplier takes no arguments and returns some object. The object
     * type in this case is a Shoe that has a single attribute, its size.
     * The goal is to write a lambda expression that uses the IntFunction
     * and size values provided as arguments, and that returns a Supplier
     * that embodies both of them. This is an example of a functional
     * programming concept called "partial application."
     */
    Supplier<Shoe> makeShoeSupplier(IntFunction<Shoe> ifunc, int size) {
        return null; // TODO
    }
    /* Hint:
     * You don't want to return the result of calling the IntFunction.
     * Instead, you want to return a lambda that calls the IntFunction.
     */

    static class Shoe {
        final int size;
        public Shoe(int size) { this.size = size; }
        public int hashCode() { return size ^ 0xcafebabe; }
        public boolean equals(Object other) {
            return (other instanceof Shoe) && this.size == ((Shoe)other).size;
        }
    }

    @Test @Ignore
    public void h7_shoemaker() {
        Supplier<Shoe> sup1 = makeShoeSupplier(Shoe::new, 9);
        Supplier<Shoe> sup2 = makeShoeSupplier(Shoe::new, 13);

        Shoe shoe1 = sup1.get();
        Shoe shoe2 = sup1.get();
        Shoe shoe3 = sup2.get();
        Shoe shoe4 = sup2.get();

        assertThat(shoe1).isNotSameAs(shoe2);
        assertThat(shoe3).isNotSameAs(shoe4);

        assertThat(shoe1).isEqualTo(new Shoe(9));
        assertThat(shoe1).isEqualTo(shoe2);
        assertThat(shoe3).isEqualTo(new Shoe(13));
        assertThat(shoe3).isEqualTo(shoe4);
    }
}
