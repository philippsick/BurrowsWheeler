/* *****************************************************************************
 *  Name: Philipp Sick
 *  Date: March 12, 2024
 *  Description: Creates circular suffix array, a data structure which
 *  describes the abstraction of a sorted array of the n circular suffixes of
 *  a string of length n. Uses key-indexed counting to sort the suffixes.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {

    private static final int R = 256;   // Extended ASCII alphabet size
    private static final int CUTOFF = 5;   // Cutoff to insertion sort
    private final String str;
    private final CircularSuffix[] suffixes;

    // Generate circular suffix array from input string
    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();

        str = s;
        suffixes = new CircularSuffix[str.length()];
        for (int i = 0; i < str.length(); i++) {
            suffixes[i] = new CircularSuffix(i);
        }
        sort(suffixes, length());

    }

    private class CircularSuffix {
        private final int start;

        CircularSuffix(int start) {
            this.start = start;
        }

        int charAt(int i) {
            return i >= str.length() ? -1 : str.charAt((start + i) % str.length());
        }
    }

    private static void sort(CircularSuffix[] a, int len) {
        int n = a.length;
        CircularSuffix[] aux = new CircularSuffix[n];
        sort(a, 0, n - 1, 0, len, aux);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private static void sort(CircularSuffix[] a, int lo, int hi, int d, int len,
                             CircularSuffix[] aux) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(a, lo, hi, d, len);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            int c = a[i].charAt(d);
            count[c + 2]++;
        }

        // transform counts to indices
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];

        // distribute
        for (int i = lo; i <= hi; i++) {
            int c = a[i].charAt(d);
            aux[count[c + 1]++] = a[i];
        }

        // copy back
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // recursively sort for each character (excludes sentinel -1)
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, len, aux);
    }

    // insertion sort a[lo..hi], starting at dth character
    private static void insertion(CircularSuffix[] a, int lo, int hi, int d, int len) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d, len); j--)
                exch(a, j, j - 1);
    }

    // // exchange a[i] and a[j]
    private static void exch(CircularSuffix[] a, int i, int j) {
        CircularSuffix temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // is v less than w, starting at character d
    private static boolean less(CircularSuffix v, CircularSuffix w, int d, int len) {
        // assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < len; i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return false;
    }

    // length of s
    public int length() {
        return str.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i >= this.length() || i < 0) throw new IllegalArgumentException();
        return suffixes[i].start;
    }

    // Test
    public static void main(String[] args) {
        CircularSuffixArray test = new CircularSuffixArray("ABRACADABRA!");
        StdOut.println(test.index(3));
    }

}
