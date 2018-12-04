/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.HashMap;
import javax.sql.DataSource;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        // valeur
        Date dateDeb = new Date("2011/05/24");
        Date dateFin = new Date("2011/05/24");
        boolean valide = true;
        HashMap<String,Double> result = new HashMap<>();
        result = myDaoAdmin.CAofCategorie(dateDeb,dateFin);
        
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
               if(! result.get(key).equals(resultAttendu.get(key))){
                   valide = false;
               }
           } else {
               valide = false;
           }
        }    
        assertEquals(true, valide);
    }
    
    /**
     * Méthode de test pour obtenir le CA selon une zone géographique
     */
    @Test @Ignore
    public void testCAZoneGeo() {
        // TODO implementer test + changer val retour
         Date dateDeb = new Date("2018/11/30");
         Date dateFin = new Date("2018/11/30");
         //HashMap<String,Double> result = myDaoAdmin.CAofZoneGeo(dateDeb, dateFin); //bouchon
    }
    
    /**
     * Méthode de test pour obtenir le CA selon un client
     */
    @Test @Ignore
    public void testCAClient() {
        //TODO Implementer + changer val retour
        Date dateDeb = new Date("2018/11/30");
        Date dateFin = new Date("2018/11/30");
        HashMap<String,Double> result = myDaoAdmin.CAfromClient(dateDeb, dateFin); //bouchon

    }
    
    @Test @Ignore
    public void testInsertProduct() {
        // Valeur a inserer dans la table produit TODO
        
        // TODO Implementer + changer val retour
        myDaoAdmin.insertProduct(); //bouchon
        
       // requete qui permet de trouver un produit  : Si la requete select 
        // fonctionne alors le produit a été inserré sinon => fail de l'insertion
    }
    
    @Test @Ignore
    public void testUpdateProduct(){
        //TODO implementer + changer val retour
        myDaoAdmin.updateProduct(); //bouchon
        
        // Requete qui permet de verifie si le produti a été update 
        // => on récupere la nouvelle valeur si elle est égale a la valeur passé 
        // en param alors ça marche sinon => fail de la mise a jour
    }
    
    @Test @Ignore
    public void testDeleteProduct(){
        //TODO implementer + changer val retour
        myDaoAdmin.deleteProduct(); // bouchon
        
        // Reque qui affiche un produit : Si la requete select marche 
        // alors le produti n'a pas été supprimé => fail de la suppression
        
    }
    
    
    /* ====================================================================== */
    /* ======================== Test Client ================================= */
}


