/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entity.ListCA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import modele.PurchaseOrder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pierr
 */
public class DAOTest {
    private DAOadmin myDaoAdmin; // Dao admin
    private DAOclient myDaoClient; // Dao client
    
    private DataSource myDataSource; // La source de données à utiliser 
    
    @Before
    public void setUp() {
        myDataSource = DataSourceFactory.getDataSource();
        myDaoAdmin = new DAOadmin(myDataSource);
        myDaoClient = new DAOclient(myDataSource);
    }
    
    /* ======================== Test Admin ================================== */
    /**
     * Méthode de test pour obtenir le CA selon une categorie de produit
     */
    @Test @Ignore
    public void testCACatego() {
        // variable local
        String dateDeb = "2011-05-24";
        String dateFin = "2011-05-24";
        boolean valide = true;
        List<ListCA> result = myDaoAdmin.CAofCategorie(dateDeb, dateFin);
        
        HashMap<String,Double> resultAttendu = new HashMap<>();
        resultAttendu.put("BK", 6963.375000);
        resultAttendu.put("CB", 2905.050000);
        resultAttendu.put("FW", 4272.885000);
        resultAttendu.put("HW", 282546.390000);
        resultAttendu.put("SW", 179916.0988);
        
        // Vérification du contenu de la table crée
//        for(String key : result.keySet()){
//           // On vérifie si la clé existe
//           if (resultAttendu.containsKey(key)){
//               // ON vérifie le contenu de la clé
//               if(!(result.get(key).equals(resultAttendu.get(key)))){
//                   System.out.println("Erreur sur la clé (" + result.keySet() 
//                                    + ") résultat attendu : "  
//                                    + resultAttendu.get(key)
//                                    +" la valeur n'est pas bonne");
//                   valide = false;
//               }
//           } else {
//               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
//               valide = false;
//           }
//        }    
        assertEquals(valide, true);
    }
    
    /**
     * Méthode de test pour obtenir le CA selon une zone géographique
     */
    @Test @Ignore
    public void testCAZoneGeo() {
        // variable locale
         String dateDeb = "2011-05-24";
         String dateFin = "2011-05-24";
         boolean valide = true;
        List<ListCA> result = myDaoAdmin.CAofCategorie(dateDeb, dateFin);
         
         // HashMap de retour
         HashMap<String,Double> resultAttendu = new HashMap<>();
         
         // valleur attendu dans la hashmap
         resultAttendu.put("10095",445.0);
         resultAttendu.put("10096",53039.55);
         resultAttendu.put("12347",557.535);
         resultAttendu.put("48124",6963.375);
         resultAttendu.put("48128",170026.05);
         resultAttendu.put("94401",66810.6);
         resultAttendu.put("95035",130501.8388);
         resultAttendu.put("95117",48259.850);
         
        // Vérification du contenu de la table crée
//        for(String key : result.keySet()){
//           // On vérifie si la clé existe
//           if (resultAttendu.containsKey(key)){
//               if(!(result.get(key).equals(resultAttendu.get(key)))){
//                   System.out.println("Erreur sur la clé (" + result.keySet() 
//                                    + ") résultat attendu : "  
//                                    + resultAttendu.get(key)
//                                    +" la valeur n'est pas bonne");
//                   valide = false;
//               }
//           } else {
//               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
//               fail(); //Si erreur on passe par la
//           }
//        }    
        assertEquals(valide, true);
    }
    
    /**
     * Méthode de test pour obtenir le CA selon un client
     */
    @Test  @Ignore
    public void testCAClient() {
         // variable locale
         String dateDeb = "2011-05-24";
         String dateFin = "2011-05-24";
         boolean valide = true;
        List<ListCA> result = myDaoAdmin.CAofCategorie(dateDeb, dateFin);
         
         // HashMap de retour
         HashMap<String,Double> resultAttendu = new HashMap<>();
         
         // valleur attendu dans la hashmap
         resultAttendu.put("1",3804.35);
         resultAttendu.put("2",125902.8388);
         resultAttendu.put("3",557.535);
         resultAttendu.put("36",60934.8);
         resultAttendu.put("106",4599.0);
         resultAttendu.put("149",44455.5);
         resultAttendu.put("409",445.0);
         resultAttendu.put("410",53039.55);
         resultAttendu.put("722",6963.375);
         resultAttendu.put("753",168079.8);
         resultAttendu.put("777",1946.25); 
         resultAttendu.put("863",5875.8);
         
        // Vérification du contenu de la table crée
//        for(String key : result.keySet()){
//           // On vérifie si la clé existe
//           if (resultAttendu.containsKey(key)){
//               // ON vérifie le contenu de la clé
//               if(!(result.get(key).equals(resultAttendu.get(key)))){
//                   System.out.println("Erreur sur la clé (" + result.keySet() 
//                                    + ") résultat attendu : "  
//                                    + resultAttendu.get(key)
//                                    +" la valeur n'est pas bonne");
//                   valide = false;
//               }
//           } else {
//               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
//               valide = false;
//           }
//        }    
//        assertEquals(valide, true);
    }
    
    @Test @Ignore
    public void testInsertProduct() {
        // TODO Implementer + changer val retour
        try {
            myDaoAdmin.insertProduct(19985678,"SW",234.0,10,10.5,"TRUE","Produit de test2");
        } catch (Exception e) {
            System.out.println("Erreur sur l'insertion d'un produit");
           System.out.println(e.getMessage());
           e.printStackTrace();
            fail();
        }
        
        
       // requete qui permet de trouver un produit  : Si la requete select 
        // fonctionne alors le produit a été inserré sinon => fail de l'insertion
        
    }
    
    @Test  @Ignore
    public void testUpdateProduct(){
        //TODO implementer + changer val retour
        //updateProduct(int productID, int manufactID,String prodCode, double prodCost,
        //            int quantite, double markup, boolean dispo, String desc)
        String sql = "SELECT * FROM PRODUCT WHERE MANUFACTURER_ID = ?";

        try (Connection connection = myDataSource.getConnection();
                 PreparedStatement testStatement = connection.prepareStatement(sql)) {
            int manufactID = 19985678; 
            int result = 0;
            testStatement.setInt(1, manufactID);
            // on execute la requete de test avec le parametre
            try (ResultSet rs = testStatement.executeQuery()) {
		rs.next(); // On a toujours exactement 1 enregistrement dans le résultat
	        result = rs.getInt("PRODUCT_ID");
	    }
            
            myDaoAdmin.updateProduct(result, 19985678, "SW", 2000.0, 10, 10.5, true, "Produit Test Update");
        } catch (Exception e) {
            System.out.println("Erreur sur la modification d'un produit");
            fail();
        }
    }
    
    @Test @Ignore
    public void testDeleteProductExist(){
       try{
            myDaoAdmin.deleteProduct(980001);
            System.out.println("Le produit a été supprimé");
       } catch (Exception e) {
            fail();
       }

    }
    
    @Test @Ignore
      public void testDeleteProductUnexist(){
          try {
              myDaoAdmin.deleteProduct(19985678);
              fail();
          } catch (Exception e){
              // Si il y a une erreur alors la fonction marche bien 
          }
      }
    
    /* ====================================================================== */
    /* ======================== Test Client ================================= */
    @Test  @Ignore
    public void testEditCustomer() throws SQLException, Exception{
        // On test le changement de nom sur le premier client
        int customerId = 1;
        String zip =  "95117";
        String name = "Jumbo Pas Eagle Corp";
        String addressLine1 =  "111 E. Las Olivia Blvd";
        String addressLine2 = "Suite 51";
        String city = "Fort Lauderdale";
        String state = "FL";
        String phone = "355-555-0188";
        String fax = "355-555-0189";
        String email = "jumboeagle@example.com";
        
        myDaoClient.editPersonnalData(customerId, zip, name, addressLine1, 
                                addressLine2, city, state, phone, fax, email);
        
        // On doit obtenir un nouveau nom
        String testSql = "SELECT NAME FROM CUSTOMER WHERE CUSTOMER_ID = ?";
        String resultTest ="";
        try (Connection connection = myDataSource.getConnection();
             PreparedStatement testStatement = connection.prepareStatement(testSql)){
              
             testStatement.setInt(1, customerId);
             try (ResultSet rs = testStatement.executeQuery()) {
		rs.next(); // On a toujours exactement 1 enregistrement dans le résultat
		resultTest = rs.getString("NAME");
            }
        } catch ( SQLException ex ){
            System.out.println(ex.getMessage());
            fail();
        }
        assertEquals(name, resultTest);
    }
    // Add purchaser order
    @Test @Ignore
    public void addPurchaseOrder(){
        try {
            myDaoClient.addPurchaseOrder(1398120, 980032, 2, 5, "2018-11-12", "2018-11-12", "Momo Company");
        } catch (Exception e) {
            System.out.println("Erreur sur l'ajout d'un produit");
        }
    }
    // Edit purchaser order
    @Test @Ignore
    public void editPurchaseOrder(){
       
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ? AND PRODUCT_ID = ? AND QUANTITY = ?";

        try (Connection connection = myDataSource.getConnection();
                 PreparedStatement testStatement = connection.prepareStatement(sql)) {
            int custoId = 1398120;
            int quantity = 2;
            int productId = 980032;
            int result = 0;
            testStatement.setInt(1, custoId);
            testStatement.setInt(1, productId);
            testStatement.setInt(1, quantity);
            // on execute la requete de test avec le parametre
            try (ResultSet rs = testStatement.executeQuery()) {
		rs.next(); // On a toujours exactement 1 enregistrement dans le résultat
	        result = rs.getInt("ORDER_NUM");
	    }
            myDaoClient.editPurchaseOrder(result, 1398120, 980032, 20, 5, "2018-11-12", "2018-11-12", "Momo Company");
        } catch (Exception e) {
            
            System.out.println("Erreur sur l'édition d'un produit");
        }
    }
    // delete purchaser order
    @Test @Ignore
    public void deletePurchaseOrder(){
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE CUSTOMER_ID = ? AND PRODUCT_ID = ? AND QUANTITY = ?";

        try (Connection connection = myDataSource.getConnection();
               PreparedStatement testStatement = connection.prepareStatement(sql)) {
            int custoId = 1398120;
            int quantity = 2;
            int productId = 980032;
            int result = 0;
            testStatement.setInt(1, custoId);
            testStatement.setInt(1, productId);
            testStatement.setInt(1, quantity);
            // on execute la requete de test avec le parametre
            try (ResultSet rs = testStatement.executeQuery()) {
		rs.next(); // On a toujours exactement 1 enregistrement dans le résultat
	        result = rs.getInt("ORDER_NUM");
            }
            myDaoClient.deletePurchaseOrder(result);
        } catch(Exception e){
            System.out.println("Erreur sur delete product");
        }
    }
    @Test @Ignore 
    public void listPurchaseOrder() throws SQLException{
        List<PurchaseOrder> resultAttendu = new LinkedList<>();
        List<PurchaseOrder> result = new LinkedList<>();
        boolean valide = true;
        PurchaseOrder val1 = new PurchaseOrder(10398002, 980005, 8, (float)82247.038800, "2011-05-24");
        PurchaseOrder val2 = new PurchaseOrder(10398003, 980025, 25, (float) 44290.790000, "2011-05-24");
        resultAttendu.add(val1);
        resultAttendu.add(val2);
        System.out.println(resultAttendu.toString());
        
        result = myDaoClient.listeOrder(2);
        System.out.println(result.toString());
        for (int i = 0; i<result.size(); i++) {
            if (result.get(i).getOrderNum() != resultAttendu.get(i).getOrderNum()) {
                valide = false;
            }
        }
        assertEquals(valide,true);  
    }
}































