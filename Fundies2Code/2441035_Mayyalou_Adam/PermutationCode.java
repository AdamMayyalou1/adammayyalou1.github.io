import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
  // The original list of characters to be encoded
  ArrayList<Character> alphabet = 
      new ArrayList<Character>(Arrays.asList(
          'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
          'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
          't', 'u', 'v', 'w', 'x', 'y', 'z'));

  ArrayList<Character> code = new ArrayList<Character>(26);

  // A random number generator
  Random rand = new Random();

  // Create a new instance of the encoder/decoder with a new permutation code 
  PermutationCode() {
    this.code = this.initEncoder();
  }

  // Create a new instance of the encoder/decoder with the given code 
  PermutationCode(ArrayList<Character> code) {
    this.code = code;
  }

  /* Class Template
   * 
   * Fields:
   * - none -
   * 
   * Methods:
   * - this.initEncoder()                               - ArrayList<Character>
   * - this.encode(String source)                       - String
   * - this.decode(String code)                         - String
   * 
   * Methods on Fields:
   * - none -
   */

  // Initialize the encoding permutation of the characters
  ArrayList<Character> initEncoder() {
    ArrayList<Character> result = new ArrayList<Character>(26);
    ArrayList<Character> alphabetTemp = new ArrayList<Character>(this.alphabet);

    while (!alphabetTemp.isEmpty()) {
      int randomIndex = this.rand.nextInt(alphabetTemp.size());
      // Adds the character at that index to the result
      result.add(alphabetTemp.get(randomIndex));
      // Removes the character from the alphabet temp arraylist
      alphabetTemp.remove(randomIndex);
    }

    return this.alphabet;
  }

  // produce an encoded String from the given String
  String encode(String source) {
    String encodedString = "";

    for (int i = 0; i < source.length(); i++) {
      char currentChar = source.charAt(i);
      int alphabetIndex = this.alphabet.indexOf(currentChar);

      if (alphabetIndex != -1) {
        encodedString = encodedString + this.code.get(alphabetIndex);
      } else {
        // If character not in alphabet keep it as is
        encodedString = encodedString + currentChar;
      }
    }

    return encodedString;
  }

  // produce a decoded String from the given String
  String decode(String code) {
    String decodedString = "";
    
    for (int i = 0; i < code.length(); i++) {
      char currentChar = code.charAt(i);
      int codeIndex = this.code.indexOf(currentChar);
      
      if (codeIndex != -1) {
        decodedString = decodedString + this.alphabet.get(codeIndex);
      } else {
        // If character not in code keep it as is
        decodedString = decodedString + currentChar;
      }
    }
    
    return decodedString;
  }
}

class ExampleTests {
  ArrayList<Character> testCode = new ArrayList<Character>(Arrays.asList(
      'b', 'e', 'a', 'c', 'd', 'f', 'g', 'h', 'i', 'j', 
      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
      't', 'u', 'v', 'w', 'x', 'y', 'z'));

  PermutationCode knownCode = new PermutationCode(testCode);

  PermutationCode randomCode = new PermutationCode();

  boolean testInitEncoder(Tester t) {
    ArrayList<Character> code = this.randomCode.code;

    // Check that the code has all 26 letters
    boolean hasCorrectSize = code.size() == 26;

    // Check that the code contains all letters from the alphabet
    boolean containsAllLetters = true;
    for (char c : this.randomCode.alphabet) {
      if (!code.contains(c)) {
        containsAllLetters = false;
      }
    }

    return t.checkExpect(hasCorrectSize, true) && 
        t.checkExpect(containsAllLetters, true);
  }


  //Test encode method
  boolean testEncode(Tester t) {
    return t.checkExpect(this.knownCode.encode("abcde"), "beacd") &&
        t.checkExpect(this.knownCode.encode("hello"), "hdllo") &&
        t.checkExpect(this.knownCode.encode("thats my cookie"), "thbts my aookid") &&
        t.checkExpect(this.knownCode.encode("xyz"), "xyz");
  }

  // Test decode method
  boolean testDecode(Tester t) {
    return t.checkExpect(this.knownCode.decode("beacd"), "abcde") &&
        t.checkExpect(this.knownCode.decode("hdllo"), "hello") &&
        t.checkExpect(this.knownCode.decode("thbts my aookid"), "thats my cookie") &&
        t.checkExpect(this.knownCode.decode("xyz"), "xyz");
  }
}