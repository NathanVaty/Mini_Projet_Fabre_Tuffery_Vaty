/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.sql.DataSource;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
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
    @Test  @Ignore
    public void testCACatego() {
        // variable local
        String dateDeb = "2011-05-24";
        String dateFin = "2011-05-24";
        boolean valide = true;
        HashMap<String,Double> result = myDaoAdmin.CAofCategorie(dateDeb, dateFin);
        
        HashMap<String,Double> resultAttendu = new HashMap<>();
        resultAttendu.put("BK", 6963.375000);
        resultAttendu.put("CB", 2905.050000);
        resultAttendu.put("FW", 4272.885000);
        resultAttendu.put("HW", 282546.390000);
        resultAttendu.put("SW", 179916.0988);
        
        // Vérification du contenu de la table crée
        for(String key : result.keySet()){
           // On vérifie si la clé existe
           if (resultAttendu.containsKey(key)){
               // ON vérifie le contenu de la clé
               if(!(result.get(key).equals(resultAttendu.get(key)))){
                   System.out.println("Erreur sur la clé (" + result.keySet() 
                                    + ") résultat attendu : "  
                                    + resultAttendu.get(key)
                                    +" la valeur n'est pas bonne");
                   valide = false;
               }
           } else {
               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
               valide = false;
           }
        }    
        assertEquals(valide, true);
    }
    
    /**
     * Méthode de test pour obtenir le CA selon une zone géographique
     */
    @Test  @Ignore
    public void testCAZoneGeo() {
        // variable locale
         String dateDeb = "2011-05-24";
         String dateFin = "2011-05-24";
         boolean valide = true;
         HashMap<String,Double> result = myDaoAdmin.CAofZoneGeo(dateDeb, dateFin);
         
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
        for(String key : result.keySet()){
           // On vérifie si la clé existe
           if (resultAttendu.containsKey(key)){
               if(!(result.get(key).equals(resultAttendu.get(key)))){
                   System.out.println("Erreur sur la clé (" + result.keySet() 
                                    + ") résultat attendu : "  
                                    + resultAttendu.get(key)
                                    +" la valeur n'est pas bonne");
                   valide = false;
               }
           } else {
               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
               fail(); //Si erreur on passe par la
           }
        }    
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
         HashMap<String,Double> result = myDaoAdmin.CAfromClient(dateDeb, dateFin);
         
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
        for(String key : result.keySet()){
           // On vérifie si la clé existe
           if (resultAttendu.containsKey(key)){
               // ON vérifie le contenu de la clé
               if(!(result.get(key).equals(resultAttendu.get(key)))){
                   System.out.println("Erreur sur la clé (" + result.keySet() 
                                    + ") résultat attendu : "  
                                    + resultAttendu.get(key)
                                    +" la valeur n'est pas bonne");
                   valide = false;
               }
           } else {
               System.out.println("Erreur la clé(" + key + ") n'est pas connu");
               valide = false;
           }
        }    
        assertEquals(valide, true);
    }
    
    @Test  @Ignore
    public void testInsertProduct() {
        // TODO Implementer + changer val retour
        try {
            myDaoAdmin.insertProduct(19985678,"SW",2000.0,10,10.5,true,"Produit de test");
        } catch (Exception e) {
            System.out.println("Erreur sur l'insertion d'un produit");
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
        // Requete qui permet de verifie si le produti a été update 
        // => on récupere la nouvelle valeur si elle est égale a la valeur passé 
        // en param alors ça marche sinon => fail de la mise a jour
    }
    
    @Test
    public void testDeleteProductExist(){
        //TODO implementer + changer val retour
       // myDaoAdmin.deleteProduct(); // bouchon
       try{
            myDaoAdmin.deleteProduct(980001);
            System.out.println("Le produit a été supprimé");
       } catch (Exception e) {
            fail();
       }
        // Reque qui affiche un produit : Si la requete select marche 
        // alors le produti n'a pas été supprimé => fail de la suppression
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
    public void addPurchaseOrder(){
        try {
            myDaoClient.addPurchaseOrder(1398120, 2, 980032, 5, "2018-11-12", "2018-11-12", "Momo Company");
        } catch (Exception e) {
            System.out.println("Erreur sur l'ajout d'un produit");
        }
    }
    // Edit purchaser order
    // delete purchaser order

}










