/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author morga
 */
public class PurchaseOrder {
    
    public int orderNum;
    public int productId;
    public int quantite;
    public float finalCost;
    public String salesDate;

    
    
    
    public PurchaseOrder(int order_num,int product_id,int quantite,float final_cost,String sales_date){

        this.orderNum = order_num;
        this.productId = product_id;
        this.quantite = quantite;
        this.finalCost = final_cost;
        this.salesDate = sales_date;

    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getFinalCost() {
        return finalCost;
    }

    public String getSalesDate() {
        return salesDate;
    }
    
    
}

