/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author pierr
 */
public class ListCA {
    private String key;
    private Double value;
    
    public ListCA(String key, Double value) {
        this.key = key;
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public Double getValue() {
        return value;
    }
}
