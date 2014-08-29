/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AgenziaCore;
import java.util.*;

/**
 *
 * @author Andrea
 */
public class OptionValue {
    private String value = null;
    private String descrizione = null;
    private String nome = null;
    private int idOption;
    
    private List<String> possibleValue=null;

    public OptionValue() {
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OptionValue(String value, String descrizione, String nome, int idOption) {
        this.value = value;
        this.descrizione = descrizione;
        this.nome = nome;
        this.idOption = idOption;
        
        possibleValue = new ArrayList();
    }
    
    public void add(String v){
        possibleValue.add(v);
    }
    public List<String> getPossibleValue(){
        return possibleValue;
    }

    
    
    
    
    
    
    
}
