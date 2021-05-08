/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haikugeneration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author OluwasegunAjayi
 */
public class HaikuGeneration {

    //private fields
    private static HashMap<String, Haiku> haikus;
    private static HashMap<String, LastWords> lastWords;
    private static HashMap<String, HashMap<String, String>> synonyms;
    
    public HaikuGeneration(){
        haikus = new HashMap<>();
        lastWords = new HashMap<>();
        synonyms = new HashMap<>();
    }
    
    //Adding an haiku set into the list
    public Extracts addHaikuSet(String first, String second, String third){
        Haiku haiku = new Haiku();
        String[] hSet = new String[3];
        
        hSet[0] = first;
        hSet[1] = second;
        hSet[2] = third;
        haiku.setHaikuSet(hSet);
        
        //Getting the last word in the first phrase
        String[] words = first.split(" ");
        String lastWord = words[words.length - 1];
        if(!lastWords.containsKey(lastWord)){
            LastWords lWords = new LastWords();
            List<String> newLastWord = new ArrayList<>();
            newLastWord.add(first);
            lWords.setPhrases(newLastWord);
            lastWords.put(lastWord, lWords);
        }else{
            lastWords.get(lastWord).getPhrases().add(first);
        }
        haikus.put(first, haiku);
        
        Extracts extract = new Extracts();
        extract.setHaikus(haikus);
        extract.setLastWords(lastWords);
        
        return extract;
    }
    
    //Adding the a synonym list
    public HashMap<String, HashMap<String, String>> addSynonym(String word, List<String> otherWords){
        HashMap<String, String> aSynonym = new HashMap<>();
        if(synonyms.containsKey(word)) aSynonym = synonyms.get(word);
        //Otherwords should also include the starting word
        if(!aSynonym.containsKey(word)) aSynonym.put(word, word);
        for(String otherWord : otherWords){
            if(!aSynonym.containsKey(otherWord)) aSynonym.put(otherWord, otherWord);
        }
        
        synonyms.put(word, aSynonym);
        return synonyms;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("Machine Learning Final Project Three");
        System.out.println("The 5-7-5 Haiku Generation From First Phrase");
        HaikuGeneration haikuGen = new HaikuGeneration();
        GenerationAlgorithm genAlgo = new GenerationAlgorithm();
        Scanner input = new Scanner(System.in);
        
        //Extrating haiku list from csv
        String line = "";
        int i = 1;
        BufferedReader br = new BufferedReader(new FileReader("haikus.csv"));  
        while ((line = br.readLine()) != null) {
            if(i != 1){
                String[] split = line.split(",");
                int n = split.length;
                if(n > 5 && split[n-1].equalsIgnoreCase("5") && split[n-2].equalsIgnoreCase("7") && split[n-3].equalsIgnoreCase("5")){
                    Extracts extract = haikuGen.addHaikuSet(split[0], split[1], split[2]);
                    haikus = extract.getHaikus();
                    lastWords = extract.getLastWords();
                }
            }
            
            i++;
        }
        
        //Extracting synonyms from list
        String aline = "";
        int j = 1;
        BufferedReader dr = new BufferedReader(new FileReader("synonyms.txt"));  
        while ((aline = dr.readLine()) != null) {
            if(j != 1){
                String[] split = aline.split(",");
                int n = split.length;
                List<String> otherWords = new ArrayList<>();
                for(int x = 1; x < n; x++){
                    otherWords.add(split[x]);
                }
                synonyms = haikuGen.addSynonym(split[0], otherWords);
            }
            
            j++;
        }
        
        //Generating the Haikus
        String test = "";
        BufferedReader testr = new BufferedReader(new FileReader("test.txt"));  
        System.out.println("Getting the first 5-syllable of your test haiku");
        while ((test = testr.readLine()) != null){
            String first = test;
            String[] words = first.split(" ");
            Haiku haiku = genAlgo.generateHaiku(haikus, lastWords, synonyms, first, words[words.length - 1]);
            if (haiku == null) {
                System.out.println("5-7-5 Haiku could not be generated with given first phrase");
            } else {
                System.out.println("RESULTING HAIKU:");
                System.out.println(haiku.getHaikuSet()[0] + "\n" + haiku.getHaikuSet()[1] + "\n" + haiku.getHaikuSet()[2]);
            }
            
            //System.out.println("\n");
        }
        
        //System.out.println("\n");
        System.out.println("End Machine Learning Project Three - Haiku Generation");
    }
    
}
