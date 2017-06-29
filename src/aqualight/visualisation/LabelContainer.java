/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import javafx.scene.control.Label;

/**
 * @Brief contains label information
 * @author tsobieroy
 */
public class LabelContainer {

    private int Index;
    private Label NameLabel;
    private Label ValueLabel;
    private String Address;    

    public LabelContainer(int index, Label nameLabel, Label valueLabel, String Address){
        this.Index = index;
        this.NameLabel = nameLabel;
        this.ValueLabel = valueLabel;
        this.Address = Address;
    }
    
    /**
     * @return the Index
     */
    public int getIndex() {
        return Index;
    }

    /**
     * @param Index the Index to set
     */
    public void setIndex(int Index) {
        this.Index = Index;
    }

    /**
     * @return the NameLabel
     */
    public Label getNameLabel() {
        return NameLabel;
    }

    /**
     * @param NameLabel the NameLabel to set
     */
    public void setNameLabel(Label NameLabel) {
        this.NameLabel = NameLabel;
    }

    /**
     * @return the ValueLabel
     */
    public Label getValueLabel() {
        return ValueLabel;
    }

    /**
     * @param ValueLabel the ValueLabel to set
     */
    public void setValueLabel(Label ValueLabel) {
        this.ValueLabel = ValueLabel;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param Address the Address to set
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

       
    
}
