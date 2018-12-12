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
public class ProductEntity {
    private int productId;
    private int manuId;
    private String productCode;
    private Double costProduct;
    private int quantity;
    private Double markup;
    private boolean available;
    private String desc;
    
    public ProductEntity(int productId, int manuId, String productCode, Double costProduct, int quantity, Double markup, boolean available, String desc){
        this.productId = productId;
        this.manuId = manuId;
        this.productCode = productCode;
        this.costProduct = costProduct;
        this.quantity = quantity;
        this.markup = markup;
        this.available = available;
        this.desc = desc;
    }

    public int getProductId() {
        return productId;
    }

    public int getManuId() {
        return manuId;
    }

    public String getProductCode() {
        return productCode;
    }

    public Double getCostProduct() {
        return costProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getMarkup() {
        return markup;
    }

    public boolean isAvailable() {
        return available;
    }
   
   public String getDesc() {
       return desc;
   }
}
