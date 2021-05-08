/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haikugeneration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author OluwasegunAjayi
 */
public class GenerationAlgorithm {
    
    //Method to fetch the haiku set
    public Haiku generateHaiku(HashMap<String, Haiku> haikus, HashMap<String, LastWords> lastWords, HashMap<String, HashMap<String, String>> synonyms, String firstPhrase, String lastWord){
        String predPhrase = "";
        
        //Looking through the list of lastwords to match lastword
        if(lastWords.containsKey(lastWord)){
            String retValue = this.search(lastWords, firstPhrase, lastWord);
            if(retValue == null) predPhrase = "This haiku could not be generated from our dataset";
            else predPhrase = retValue;
        }else{//if the last word is not found in the list 
            //We fetch the list of synonyms of the last word
            HashMap<String, String> syns = this.getSynonyms(synonyms, lastWord);
            if(syns != null){
                for (Map.Entry<String, String> syn : syns.entrySet()) {
                    String newFirstPhrase = firstPhrase.replace(lastWord, syn.getKey());
                    String retValue = this.search(lastWords, newFirstPhrase, syn.getKey());
                    if (retValue != null) {
                        predPhrase = retValue;
                        break;
                    }
                }
            }
            
            if(predPhrase.isEmpty()) predPhrase = "This haiku could not be generated from our dataset"; 
        }
        
        if(predPhrase.equalsIgnoreCase("This haiku could not be generated from our dataset")) return new Haiku();
        else{
            Haiku retHaiku = haikus.get(predPhrase);
            retHaiku.getHaikuSet()[0] = firstPhrase;
            return retHaiku;
        }
    }
    
    //This method fetches the synonyms
    public HashMap<String, String> getSynonyms(HashMap<String, HashMap<String, String>> synonyms, String word){
        if(synonyms.containsKey(word)){
            return synonyms.get(word);
        }else{
            for(Map.Entry<String, HashMap<String, String>> synonym : synonyms.entrySet()){
                if(synonym.getValue().containsKey(word)){
                    return synonym.getValue();
                }
            }
        }
        
        return null;
    }
    
    //This method finds the first part of the haiku
    public String search(HashMap<String, LastWords> lastWords, String firstPhrase, String lastWord){
        //Looking through the list of lastwords to match lastword
        if(lastWords.containsKey(lastWord)){
            List<String> possiblePhrases = lastWords.get(lastWord).getPhrases();
            String foundPhrase = "";
        
            int min = Integer.MAX_VALUE;
            String hypotheticPhrase = "";//this is to keep track of a phrase that is closest to the searched one if not found
            
            //Compare for the one with the exact phrase as the search phrase
            for(String phrase : possiblePhrases){
                int value = firstPhrase.compareToIgnoreCase(phrase);
                if(value == 0){
                    foundPhrase = phrase;//found the exact first 5-syllable haiku
                    break;
                }else{//finding something close
                    if(min > Math.abs(value)){
                        min = value;
                        hypotheticPhrase = phrase;
                    }
                }
            }
            
            if(!foundPhrase.isEmpty()) return foundPhrase;
            else return hypotheticPhrase;
        }else{//if the last word is not found in the list 
            return null;//We cannot find match in our haiku set            
        }
    }
    
}
