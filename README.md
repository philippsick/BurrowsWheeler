My solution to the Burrows-Wheeler assignment from Princeton's Algorithms, Part II course. The assignment spec can be found here: https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php. A brief summary is below.

The Burrows–Wheeler data compression algorithm outcompresses gzip and PKZIP, is relatively easy to implement, and is not protected by any patents. It forms the basis of the Unix compression utility bzip2.
The Burrows–Wheeler data compression algorithm consists of three algorithmic components, which are applied in succession:

1. Burrows–Wheeler transform. Given a typical English text file, transform it into a text file in which sequences of the same character occur near each other many times.
2. Move-to-front encoding. Given a text file in which sequences of the same character occur near each other many times, convert it into a text file in which certain characters appear much more frequently than others.
3. Huffman compression. Given a text file in which certain characters appear much more frequently than others, compress it by encoding frequently occurring characters with short codewords and infrequently occurring characters with long codewords.

For the assignment, I implement the Burrows–Wheeler and move-to-front components.
