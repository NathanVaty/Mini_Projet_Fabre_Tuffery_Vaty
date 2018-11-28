/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Nathan Vaty
 */
public class DAOadmin {

    /**
     * Constructeur du DAOadmin
     */
    protected final DataSource myDataSource;
    
    public DAOadmin(DataSource dataSource) {
        this.myDataSource = dataSource;
    }
    
    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @param dateDeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAofCategorie(Date dateDeb, Date dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
        String rqtPC = "SELECT PC.PROD_CODE, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA\n" +
                        "FROM PRODUCT_CODE PC\n" +
                        "JOIN PRODUCT P ON P.PRODUCT_CODE = PC.PROD_CODE\n" +
                        "JOIN PURCHASE_ORDER PO ON PO.PRODUCT_ID = P.PRODUCT_ID\n" +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE\n" +
                        "GROUP BY PC.PROD_CODE;";
        try (   Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(rqtPC)) {
                while(rs.next()) {
                    String pc = rs.getString("PROD_CODE");
                    double dc = rs.getDouble("CA");
                    chiffreAff.put(pc, dc);
                }
            
        } catch(SQLException e) {
        }
         
        return chiffreAff;
    }

    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @param datedeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAofZoneGeo(Date datedeb, Date dateFin) {
        HashMap<String,Double> chiffreAff = new HashMap<>();
        return chiffreAff;
    }

    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @param dateDeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAfromClient(Date dateDeb,Date dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
       return chiffreAff;
    }

    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @return 
     */
    public int insertProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @return 
     */
    public int updateProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * TODO modifier type retour+param ==> fichier de test aussi
     * @return 
     */
    public int deleteProduct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}











