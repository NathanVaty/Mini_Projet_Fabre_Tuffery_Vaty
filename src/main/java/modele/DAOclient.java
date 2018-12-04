/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author Morgane Tuffery
 */
public class DAOclient {

    
    protected final DataSource myDataSource;
    
    public DAOclient(DataSource dataSource){
        this.myDataSource = dataSource;
    }
    
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
    public void addPurchaseOrder(int customerId ,int productId,int quantite, float shippingCost, String salesDate,
                                 String shippingDate, String freightCompany){
        
        String sql = "INSERT INTO DISCOUNT_CODE(ORDER_NUM,CUSTOMER_ID, PRODUCT_ID, QUANTITY, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, FREIGHT_COMPANY) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
         try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)  ){
             // On définit la valeur de paramètre
             //A voir pour ajouter automatiquement l'orderNum
             discountStatement.setString(1, discountCode);
             discountStatement.setInt(2, customerId);
             discountStatement.setInt(3, productId);
             discountStatement.setInt(4, quantite);
             discountStatement.setFloat(5, shippingCost);
             discountStatement.setString(6, salesDate);
             discountStatement.setString(7, shippingDate);
             discountStatement.setString(8, freightCompany);
             
             
             // On ajoute le code discount avec une requete
             discountStatement.execute();
         } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
        
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
       
        // Une requête SQL paramétrée
		String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        // Définir la valeur du paramètre
			stmt.setInt(1, orderId);
			
                        // On execute la requete
			stmt.executeUpdate();

		}  catch (SQLException ex) {
			Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		}
    }
    
            
}




