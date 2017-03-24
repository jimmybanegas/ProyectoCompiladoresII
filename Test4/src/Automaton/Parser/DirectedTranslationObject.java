package Automaton.Parser;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.HashMap;

/**
 * Created by Jimmy Ramos on 16-Mar-17.
 */
public class DirectedTranslationObject {
    private String terminal;
    private int numberOfProduction;
    private int positionJavaCode;
    private String javaCode;
    private String originalProduction;
    private HashMap<String,String> labels;
    private ListMultimap<String, String> multimap ;

    public DirectedTranslationObject(int numberOfProduction, int positionJavaCode, String javaCode,String terminal, String originalProduction) {
        this.numberOfProduction = numberOfProduction;
        this.positionJavaCode = positionJavaCode;
        this.javaCode = javaCode;
        this.terminal = terminal;
        this.originalProduction = originalProduction;
        labels = new HashMap<>();
        multimap =  ArrayListMultimap.create();
    }

    public int getNumberOfProduction() {
        return numberOfProduction;
    }

    public void setNumberOfProduction(int numberOfProduction) {
        this.numberOfProduction = numberOfProduction;
    }

    public int getPositionJavaCode() {
        return positionJavaCode;
    }

    public void setPositionJavaCode(int positionJavaCode) {
        this.positionJavaCode = positionJavaCode;
    }

    public String getJavaCode() {
        return javaCode;
    }

    public void setJavaCode(String javaCode) {
        this.javaCode = javaCode;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getOriginalProduction() {
        return originalProduction;
    }

    public void setOriginalProduction(String originalProduction) {
        this.originalProduction = originalProduction;
    }

    public HashMap<String, String> getLabels() {
        return labels;
    }

    public void setLabels(HashMap<String, String> labels) {
        this.labels = labels;
    }

    public void setMultimap(ListMultimap<String, String> multimap) {
        this.multimap = multimap;
    }

    public ListMultimap<String, String> getMultimap() {
        return multimap;
    }
}
