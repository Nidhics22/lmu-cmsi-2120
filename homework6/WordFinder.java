package homework6;

import java.util.List;
import java.util.ArrayList;

public class WordFinder {

    private static class Node{
        char character;
        boolean last;
        Node left;
        Node right;
        Node middle;

        Node(char character) {
            this.character = character;
        }

        void add(String word) {
            var first = word.charAt(0);
            var rest = word.substring(1);
            if(first < character) {
                if(left == null)
                    left = new Node(first);
                left.add(word);
            } else if(first > character){
                if(right == null)
                    right = new Node(first);
                right.add(word);
            } else if(rest.isEmpty()) {
                last = true;
            } else {
                if(middle == null)
                    middle = new Node(rest.charAt(0));
                middle.add(rest);
            }
        }

        Node nodeFor(String word){
            if(word.isEmpty())
                return this;
            var first = word.charAt(0);
            var rest = word.substring(1);
            if (first < character)
                return left == null ? null : left.nodeFor(word);
            if(rest.isEmpty())
                return this;
            return middle == null ? null : middle.nodeFor(rest);
        }

        void accumulateWords(String prefix){
            if(left != null)
                left.accumulateWords(prefix, words);
            if (last)
                words.add(prefix + character);
            if(middle != null)
                middle.accumulateWords(prefix + character, words);
            if(right != null)
                right.accumulateWords(prefix, words);
        }

    }

    //Initializing root: arbitrary value to save lots of null checking 
    private Node root = new Node('i');

    public void add(String word){
        word = word.trim();
        if(word.isEmpty()){
            throw new IllegalArgumentException("Empty word is not allowed");
        }
        root.add(word);
    }

    public boolean contains(String word) {
        word = word.trim();
        var node = root.nodeFor(word);
        return node != null && node.last;
    }

    public List<String> allWords() {
        var words = new ArrayList<String>();
        root.accumulateWords("", words);
        return words;
    }

    public List<String> suggestions(String prefix) {
        prefix = prefix.trim();
        if (prefix.isEmpty())
            throw new IllegalArgumentException("One character at minimum");
        var words = new ArrayList<String>();
        var node = root.nodeFor(prefix);
        if(node != null) {
            if(node.last)
                words.add(prefix);
            if(node.middle != null)
                node.middle.accumulateWords(prefix, words);
        }
        return words;
    }
    
    
}
