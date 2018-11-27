package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


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
        // TODO implementer test + changer val retour
        String productCode = "TT";
        float result = myDaoAdmin.CAofCategorie(productCode); //bouchon
    }
    
    /**
     * Méthode de test pour obtenir le CA selon une zone géographique
     */
    @Test
    public void testCAZoneGeo() {
        // TODO implementer test + changer val retour
        String zipCode = "12345";
        float result = myDaoAdmin.CAofZoneGeo(zipCode); //bouchon
    }
    
    /**
     * Méthode de test pour obtenir le CA selon un client
     */
    @Test
    public void testCAClient() {
        //TODO Implementer + changer val retour
        int codeClient = 5;
        float result = myDaoAdmin.CAfromClient(codeClient); //bouchon
    }
    
    @Test
    public void testInsertProduct() {
        // TODO Implementer + changer val retour
        int result = myDaoAdmin.insertProduct(); //bouchon
    }
    
    public void testUpdateProduct(){
        //TODO implementer + changer val retour
        int result = myDaoAdmin.updateProduct(); //bouchon
    }
    
    public void testDeleteProduct(){
        //TODO implementer + changer val retour
        int result = myDaoAdmin.deleteProduct(); // bouchon
    }
    
    
    /* ====================================================================== */
    /* ======================== Test Client ================================= */
    
    
    
}
