/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
    @Test
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
        resultAttendu.put("SW", 237855.098800);
        
        // Vérification du contenu de la table crée
        for(String key : result.keySet()){
           // On vérifie si la clé existe
           if (resultAttendu.containsKey(key)){
               // ON vérifie le contenu de la clé
               if(!(result.get(key).equals(resultAttendu.get(key)))){
                   System.out.println("Erreur sur la clé" + key 
                                    + " " + resultAttendu.get(key)
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
    @Test 
    public void testCAZoneGeo() {
        // variable locale
         String dateDeb = "2011-05-24";
         String dateFin = "2011-05-24";
         boolean valide = true;
         HashMap<String,Double> result = myDaoAdmin.CAofZoneGeo(dateDeb, dateFin);
         
         // HashMap de retour
         HashMap<String,Double> resultAttendu = new HashMap<>();
         
         // valleur attendu dans la hashmap
         resultAttendu.put("10095",48727.5);
         resultAttendu.put("10096",53039.55);
         resultAttendu.put("12347",557.535);
         resultAttendu.put("48124",6963.375);
         resultAttendu.put("48128",170026.05);
         resultAttendu.put("94401",66810.6);
         resultAttendu.put("95035",130501.8388);
         resultAttendu.put("95117",57916.35);
         
        // Vérification du contenu de la table crée
        for(String key : result.keySet()){
           // On vérifie si la clé existe
           if (resultAttendu.containsKey(key)){
               if(!(result.get(key).equals(resultAttendu.get(key)))){
                   System.out.println("Erreur sur la clé" + key 
                                    + " " + resultAttendu.get(key)
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
    @Test
    public void testCAClient() {
         // variable locale
         String dateDeb = "2011-05-24";
         String dateFin = "2011-05-24";
         boolean valide = true;
         HashMap<String,Double> result = myDaoAdmin.CAfromClient(dateDeb, dateFin);
         
         // HashMap de retour
         HashMap<String,Double> resultAttendu = new HashMap<>();
         
         // valleur attendu dans la hashmap
         resultAttendu.put("1",13460.85);
         resultAttendu.put("2",125902.8388);
         resultAttendu.put("3",557.535);
         resultAttendu.put("36",60934.8);
         resultAttendu.put("106",4599.0);
         resultAttendu.put("149",44455.5);
         resultAttendu.put("409",48727.5);
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
                   System.out.println("Erreur sur la clé" + key 
                                    + " " + resultAttendu.get(key)
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
    
    @Test @Ignore
    public void testInsertProduct() {
        // Valeur a inserer dans la table produit TODO
        
        // TODO Implementer + changer val retour
        //myDaoAdmin.insertProduct(); //bouchon
        
       // requete qui permet de trouver un produit  : Si la requete select 
        // fonctionne alors le produit a été inserré sinon => fail de l'insertion
    }
    
    @Test @Ignore
    public void testUpdateProduct(){
        //TODO implementer + changer val retour
        //myDaoAdmin.updateProduct(); //bouchon
        
        // Requete qui permet de verifie si le produti a été update 
        // => on récupere la nouvelle valeur si elle est égale a la valeur passé 
        // en param alors ça marche sinon => fail de la mise a jour
    }
    
    @Test @Ignore
    public void testDeleteProduct(){
        //TODO implementer + changer val retour
        //myDaoAdmin.deleteProduct(); // bouchon
        
        // Reque qui affiche un produit : Si la requete select marche 
        // alors le produti n'a pas été supprimé => fail de la suppression
        
    }
    
    
    /* ====================================================================== */
    /* ======================== Test Client ================================= */
    @Test
    public void testEditCustomer() throws SQLException{
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
        }
        assertEquals(name, resultTest);
    }
    // Add purchaser order
    // Edit purchaser order
    // delete purchaser order

}




