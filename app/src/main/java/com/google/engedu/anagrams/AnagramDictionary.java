/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private List<String> wordList = new ArrayList<String>(); //original copy of dictionary
    private HashSet wordSet = new HashSet(); //copy of the dictionary to look up whether a word is valid
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>(); // groupings of anagrams

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word); //add word to ArrayList
            wordSet.add(word); //add word to HashSet
            String sw = sortLetters(word);

            if (lettersToWord.containsKey(sw)) {
                lettersToWord.get(sw).add(word);
            }
            else {
                ArrayList<String> a = new ArrayList<>();
                a.add(word);
                lettersToWord.put(sw, a);
            }
        }


    }

    public boolean isGoodWord(String word, String base) {
        //checking that the word does not contain the base word as a substring
        if (wordSet.contains(word) && !word.toLowerCase().contains(base.toLowerCase())) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String s: wordList) {
            if (sortLetters(s).equals(sortLetters(targetWord)) && s.length() == targetWord.length()) {
                result.add(s);
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        //find out what anagrams can be formed with one more letter
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 97; i <= 122; i++) { //loop through the alphabet
            String s = word + (char) i; //create the appended word
            sortLetters(s);
            if (lettersToWord.containsKey(s)) {
                result.add(lettersToWord.get(s));
            }

        }
        //check the given word

        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }

    public String sortLetters (String word) {
        //returns another string with the correct alphabetical order
        char chars []  = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);

    }

}

