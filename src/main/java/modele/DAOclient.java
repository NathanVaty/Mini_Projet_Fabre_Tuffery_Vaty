/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import javax.sql.DataSource;

/**
 * @author Morgane Tuffery
 */
public class DAOclient {

    /**
     * Constructeur du DAOclient
     */
    public DAOclient(DataSource dataSource){
        this.myDataSource = dataSource;
    }
    
    
    protected final DataSource myDataSource;
    
    /**
     * Fonction permettant aux clients de modifier ses données personnelles.
     * @param customerId
     * @param zip 
     * @param name
     * @param addressLine1
     * @param addressLine2
     * @param city
     * @param state
     * @param phone
     * @param fax
     * @param email 
     */
    public void editPersonnalData(int customerId , String zip, String name, String addressLine1 ,
                                  String addressLine2,String city, String state,
                                  String phone, String fax, String email){
        
    }
    
    /**
     * Fonction permettant au client d'ajouter un bon de commande
     * @param customerId
     * @param quantite
     * @param shippingCost
     * @param salesDate
     * @param shippingDate
     * @param freightCompany 
     */
    public void addPurchaseOrder(int customerId ,int quantite, float shippingCost, String salesDate,
                                 String shippingDate, String freightCompany){
        
    }
    
    /**
     * Fonction permettant aux clients de changer leurs bons de commande
     * @param customerId
     * @param quantite
     * @param shippingCost
     * @param salesDate
     * @param shippingDate
     * @param freightCompany 
     */
    public void editPurchaseOrder(int customerId ,int quantite, float shippingCost, String salesDate,
                                 String shippingDate, String freightCompany){
        
    }
    
    /**
     * Fonction permettant aux clients de supprimer leurs bons de commande
     * @param orderId 
     */
    public void deletePurchaseOrder(int orderId){
        
    }
    
            
}


