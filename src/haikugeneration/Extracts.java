/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haikugeneration;

import java.util.HashMap;

/**
 *
 * @author OluwasegunAjayi
 */
public class Extracts {
    
    //private fields
    private HashMap<String, Haiku> haikus = new HashMap<>();
    private HashMap<String, LastWords> lastWords = new HashMap<>();

    public HashMap<String, Haiku> getHaikus() {
        return haikus;
    }

    public void setHaikus(HashMap<String, Haiku> haikus) {
        this.haikus = haikus;
    }

    public HashMap<String, LastWords> getLastWords() {
        return lastWords;
    }

    public void setLastWords(HashMap<String, LastWords> lastWords) {
        this.lastWords = lastWords;
    }
    
}
