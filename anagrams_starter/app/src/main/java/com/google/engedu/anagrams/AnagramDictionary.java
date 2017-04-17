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

import android.util.Log;

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

    private ArrayList<String> wordList = new ArrayList();
    private HashSet<String> wordSet = new HashSet();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            // Add the words in the dictionary to the HashMap
            String sorted = sortLetters(word);
            if (lettersToWord.containsKey(sorted)) {
                lettersToWord.get(sorted).add(word);
            }
            else {
                ArrayList<String> anagrams = new ArrayList<>();
                anagrams.add(word);
                lettersToWord.put(sorted, anagrams);
            }
        }


    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !word.contains(base))
            return true;
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        int targetWordSize = 0;
        for (int i = 0; i < targetWord.length(); i++) {
            targetWordSize += targetWord.codePointAt(i);
        }

        for (int i = 0; i < wordList.size(); i++) {
            if (wordValue(wordList.get(i)) == targetWordSize) {
                result.add(wordList.get(i));
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp;
        String key;
        for (char c = 'a'; c < 'z'; c++) {
            key = sortLetters(word + c);
            if (lettersToWord.containsKey(key)) {
                temp = lettersToWord.get(key);
                // Remove target word with letter added to the beginning
                if (temp.contains(c + word))
                    temp.remove(c + word);
                // Remove target word with letter added to the end
                if (temp.contains(word + c))
                    temp.remove(word + c);
                result.addAll(temp);
            }
        }
        Log.v("TAG", Integer.toString(result.size()));
        Log.v("TAG", result.toString());

        return result;
    }

    public List<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> temp;
        String key;
        for (char c = 'a'; c < 'z'; c++) {
            for (char d = 'a'; d < 'z'; d++) {
                key = sortLetters(word + c + d);
                if (lettersToWord.containsKey(key)) {
                    temp = lettersToWord.get(key);
                    // TODO: maybe need to remove the beginning and end letter words
                    result.addAll(temp);
                }
            }
        }
        Log.v("TAG", Integer.toString(result.size()));
        Log.v("TAG", result.toString());
        return result;
    }

    public String pickGoodStarterWord() {
        int index = random.nextInt(wordList.size());
        String word;
        ArrayList<String> anagrams;

        do {
            word = wordList.get(index);
            anagrams = lettersToWord.get(sortLetters(word));
            index = random.nextInt(wordList.size());
        } while(anagrams.size() < MIN_NUM_ANAGRAMS);

        return word;
    }

    public String sortLetters(String word) {
        char[] sortedWord = word.toCharArray();
        Arrays.sort(sortedWord);
        return new String (sortedWord);
    }

    public int wordValue(String word) {
        int val= 0;
        for (int i = 0; i < word.length(); i++) {
            val += word.codePointAt(i);
        }
        return val;
    }
}
