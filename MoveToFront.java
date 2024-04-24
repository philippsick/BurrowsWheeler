/* *****************************************************************************
 *  Name: Philipp Sick
 *  Date: March 12, 2024
 *  Description: Move to Front Encoding maintains an ordered sequence of the
 *  characters in the alphabet by repeatedly reading a character from the input
 *  message; printing the position in the sequence in which that character
 *  appears; and moving that character to the front of the sequence.
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // Apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] index = new int[256];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(index[c], 8);

            int changes = index[c];
            index[c] = 0;

            int i = 0;
            int j = 0;
            while (j < changes) {
                if (i != c) {
                    if (index[i] < changes) {
                        index[i]++;
                        j++;
                    }
                }
                i++;
            }
        }
        BinaryStdOut.flush();
    }

    // Apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] index = new int[256];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char pos = BinaryStdIn.readChar();
            BinaryStdOut.write(index[pos], 8);

            int changes = index[pos];

            int current = index[0];
            for (int i = 0; i < pos; i++) {
                int swap = index[i + 1];
                index[i + 1] = current;
                current = swap;
            }

            index[0] = changes;
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) encode();
        if (args[0].equals("+")) decode();
    }
}
