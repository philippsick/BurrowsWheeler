/* *****************************************************************************
 *  Name: Philipp Sick
 *  Date: March 12, 2024
 *  Description: Apply the Burrows-Wheeler transform and inverse transform,
 *  reading from standard input and writing to standard output.
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    /*
    Apply Burrows-Wheeler transform, reading from standard input and writing to
    standard output last column in the circular suffix array preceded by the row
    number first in which the original string ends up
     */
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray sa = new CircularSuffixArray(s);

        int first = 0;
        char[] t = new char[s.length()];
        for (int i = 0; i < sa.length(); i++) {
            int index = sa.index(i);
            if (index == 0) { // Row containing original string
                t[i] = s.charAt(s.length() - 1);
                first = i;
            }
            else {
                t[i] = s.charAt(index - 1); // Get character in suffix array
            }
        }

        BinaryStdOut.write(first);
        for (int i : t) {
            BinaryStdOut.write(i, 8);
        }
        BinaryStdOut.flush();
    }

    /*
    Apply Burrows-Wheeler inverse transform,
    reading from standard input and writing to standard output
    */
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] t = s.toCharArray();

        // Use key-index counting
        int R = 256; // Radix for extended ASCII
        int N = t.length;
        int[] count = new int[R + 1];
        char[] aux = new char[N];
        int[] next = new int[N];
        char[] input = new char[N];

        for (int i = 0; i < N; i++) {
            count[t[i] + 1]++;
        }

        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }

        for (int i = 0; i < N; i++) {
            aux[count[t[i]]++] = t[i];
        }

        // Generate next[] array
        int zero = 0;
        for (int i = 0; i < N; i++) {
            if (t[i] == 0) { // Account for when char is 0
                next[zero] = i;
                zero++;
            }
            else {
                next[count[t[i] - 1]] = i;
                count[t[i] - 1]++;
            }
        }

        int key = first;
        for (int i = 0; i < N; i++) {
            input[i] = aux[key];
            key = next[key];
        }

        for (char c : input) {
            BinaryStdOut.write(c, 8);
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        if (args[0].equals("+")) inverseTransform();
    }

}
