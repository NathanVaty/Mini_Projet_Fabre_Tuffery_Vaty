/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author Morgane Tuffery
 */
public class DAOclient {

    
    protected final DataSource myDataSource;
    private final String mess1 = "Customer inconnu";
    private final String mess2 = "Purchase Order inconnu";
    
    
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
     * @throws java.lang.Exception 
     */
    public void editPersonnalData(int customerId , String zip, String name, String addressLine1 ,
                                  String addressLine2,String city, String state,
                                  String phone, String fax, String email)throws Exception{
        
         String sql = "UPDATE CUSTOMER SET ZIP = ?, NAME = ?, ADDRESSLINE1 = ?, ADDRESSLINE2 = ?, CITY = ? , STATE = ?, PHONE = ?, FAX = ?, EMAIL = ? "
                   + " WHERE CUSTOMER_ID = ? ";
         
          try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)  ){

              
              
             discountStatement.setString(1, zip);
             discountStatement.setString(2, name);
             discountStatement.setString(3, addressLine1);
             discountStatement.setString(4, addressLine2);
             discountStatement.setString(5, city);
             discountStatement.setString(6, state);
             discountStatement.setString(7, phone);
             discountStatement.setString(8, fax);
             discountStatement.setString(9, email);
             discountStatement.setInt(10, customerId);
              
            
             // On ajoute le code discount avec une requete
             discountStatement.execute();
              
          } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
        
    }
    
    public int getPOKey(){
       int key = 0;
        String sql = "SELECT MAX(ORDER_NUM) AS Cle FROM PURCHASE_ORDER";
        try (Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();   
                ResultSet rs = stmt.executeQuery(sql) ) {
                    if(rs.next()) {
                        key = rs.getInt("Cle");
                    }
        } catch (SQLException e) {
            
        }
        return key;
    }
    /**
     * Fonction permettant au client d'ajouter un bon de commande
     * @param customerId
     * @param productId
     * @param quantite
     * @param shippingCost
     * @param salesDate
     * @param shippingDate
     * @param freightCompany 
     * @throws java.lang.Exception 
     */
    public void addPurchaseOrder(int customerId ,int productId,int quantite, Double shippingCost, String salesDate,
                                 String shippingDate, String freightCompany)throws Exception {
        
        String sql = "INSERT INTO PURCHASE_ORDER(ORDER_NUM,CUSTOMER_ID, PRODUCT_ID, QUANTITY, SHIPPING_COST, SALES_DATE, SHIPPING_DATE, FREIGHT_COMPANY) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
         try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)  ){
             // On définit la valeur de paramètre
            
             //Génération automatique du prochain ordeNum
             int orderNum = getPOKey(); //Numero du prochain orderNum
             
             discountStatement.setInt(1, orderNum);
             discountStatement.setInt(2, customerId);
             discountStatement.setInt(3, productId);
             discountStatement.setInt(4, quantite);
             discountStatement.setDouble(5, shippingCost);
             discountStatement.setString(6, salesDate);
             discountStatement.setString(7, shippingDate);
             discountStatement.setString(8, freightCompany);
             
             
             // On ajoute le code discount avec une requete
             discountStatement.executeUpdate();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
        
    }
    
    /**
     * Fonction permettant aux clients de changer leurs bons de commande
     * @param orderNum
     * @param customerId
     * @param productId
     * @param quantite
     * @param shippingCost
     * @param salesDate
     * @param shippingDate
     * @param freightCompany 
     */
    public void editPurchaseOrder(int orderNum, int customerId ,int productId,int quantite, float shippingCost, String salesDate,
                                 String shippingDate, String freightCompany)throws Exception {
        
        String sql = "UPDATE PURCHASE_ORDER SET CUSTOMER_ID = ? , PRODUCT_ID = ? , QUANTITY = ?, SHIPPING_COST = ?, SALES_DATE = ?, SHIPPING_DATE = ?, FREIGHT_COMPANY = ? "
                   + " WHERE ORDER_NUM = ?"
                  ;
         try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)){
             
            if (!findPurchaseOrder(orderNum)){
                throw new Exception(mess2);
            }  

             discountStatement.setInt(1, customerId);
             discountStatement.setInt(2, productId);
             discountStatement.setInt(3, quantite);
             discountStatement.setFloat(4, shippingCost);
             discountStatement.setString(5, salesDate);
             discountStatement.setString(6, shippingDate);
             discountStatement.setString(7, freightCompany);
             discountStatement.setInt(8, orderNum);
             
             
             // On ajoute le code discount avec une requete
             discountStatement.execute();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
        
    }
    
    /**
     * Fonction permettant aux clients de supprimer leurs bons de commande
     * @param orderNum
     * @throws java.lang.Exception
     */
    public void deletePurchaseOrder(int orderNum)throws Exception {
       
        // Une requête SQL paramétrée
		String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                    
                    if (!findPurchaseOrder(orderNum)){
                        throw new Exception(mess2);
                    } 
                        // Définir la valeur du paramètre
			stmt.setInt(1, orderNum);
			
                        // On execute la requete
			stmt.executeUpdate();

		}  catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		}
    }
    
    
    
    /**
     * 
     * @param num
     * @return boolean si il estxiste ou pas dans la BD
     * @throws java.sql.SQLException
     */
    public boolean findPurchaseOrder(int num) throws SQLException{
        boolean result = false;
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, num);

			ResultSet rs = stmt.executeQuery();
                        // Si on trouve au moins une ligne correspondant au purchase order on renvoie vrai
			if (rs.next()) {
				result = true;
			}
                }
        return result;
    }
    
    /**
     * 
     * @param num
     * @return Renvoie la ligne de la purchase order
     * @throws java.sql.SQLException
     */
    public PurchaseOrder getPurchaseOrder(int num) throws SQLException{
        PurchaseOrder result = null;
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, num);

			ResultSet rs = stmt.executeQuery();
                        // Si on trouve au moins une ligne correspondant au purchase order on renvoie vrai
			if (rs.next()) {
                            int orderNum = rs.getInt("ORDER_NUM");
                            int productId = rs.getInt("PRODUCT_ID");
                            int quantite = rs.getInt("QUANTITY");
                            String salesDate = rs.getString("SALES_DATE");
                            float finalCost = rs.getFloat("TOTAL");
                            // On crée l'objet DiscountCodeEntity
                            result = new PurchaseOrder(orderNum, productId,quantite,finalCost,salesDate);
			}
                }
        return result;
    }
   
    /**
     *
     * @param customerID
     * @return
     * @throws SQLException
     */
    public List<PurchaseOrder> listeOrder(int customerID) throws SQLException{
        List<PurchaseOrder> result = new LinkedList<>();
        String rqSqlListDiscount = "SELECT PO.ORDER_NUM,PO.PRODUCT_ID,PO.QUANTITY, "
                + "PO.SALES_DATE,SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))+PO.SHIPPING_COST) AS TOTAL"
                + " FROM PURCHASE_ORDER PO "
                + "JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID "
                + "JOIN PRODUCT_CODE PC ON PC.PROD_CODE = P.PRODUCT_CODE "
                + "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE "
                + "WHERE PO.CUSTOMER_ID = ? "
                + "GROUP BY PO.ORDER_NUM,PO.PRODUCT_ID,PO.QUANTITY,PO.SALES_DATE ";
        
        try (	Connection connection = myDataSource.getConnection();
		     PreparedStatement stmt = connection.prepareStatement(rqSqlListDiscount)){
            
             stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();

                while (rs.next()){ // On récupere la liste des purchase order
                    
                    int orderNum = rs.getInt("ORDER_NUM");
                    int productId = rs.getInt("PRODUCT_ID");
                    int quantite = rs.getInt("QUANTITY");
                    String salesDate = rs.getString("SALES_DATE");
                    float finalCost = rs.getFloat("TOTAL");
                    // On crée l'objet DiscountCodeEntity
                    PurchaseOrder discCode = new PurchaseOrder(orderNum, productId,quantite,finalCost,salesDate);
                    // On l'ajoute à la liste des résultats
                    result.add(discCode);
                    
                }
            } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            }

         return result;
        
    }
    
        public String findCustomer(int num) throws SQLException{
            String result = "";
            String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
                    try (Connection connection = myDataSource.getConnection();
                            PreparedStatement stmt = connection.prepareStatement(sql)) {
                            stmt.setInt(1, num);

                            ResultSet rs = stmt.executeQuery();
                            // Si on trouve au moins une ligne correspondant au customer on renvoie vrai
                            if (rs.next()) {
                                    result = rs.getString("NAME");
                            }
                    }
            return result;
        }
   
    }           
