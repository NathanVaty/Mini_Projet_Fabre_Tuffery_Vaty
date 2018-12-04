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
     * Renvoie le chiffre d'affaire par catégorie d'article
     * entre 2 dates données
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
     * Renvoie le chiffre d'affaire par zone géographique
     * entre 2 dates données
     * @param datedeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAofZoneGeo(Date datedeb, Date dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
        String rqtPC = "SELECT MK.ZIP_CODE, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA\n" +
                        "FROM MICRO_MARKET MK\n" +
                        "JOIN CUSTOMER C ON C.ZIP = MK.ZIP_CODE\n" +
                        "JOIN PURCHASE_ORDER PO ON PO.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID\n" +
                        "JOIN PRODUCT_CODE PC ON PC.PROD_CODE = P.PRODUCT_CODE\n" +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE\n" +
                        "GROUP BY MK.ZIP_CODE;\n";
        try (   Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(rqtPC)) {
                while(rs.next()) {
                    String pc = rs.getString("ZIP_CODE");
                    double dc = rs.getDouble("CA");
                    chiffreAff.put(pc, dc);
                }
            
        } catch(SQLException e) {
        }
         
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
        String rqtPC = "SELECT C.CUSTOMER_ID, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA\n" +
                        "FROM CUSTOMER C\n" +
                        "JOIN PURCHASE_ORDER PO ON PO.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID\n" +
                        "JOIN PRODUCT_CODE PC ON PC.PROD_CODE = P.PRODUCT_CODE\n" +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE\n" +
                        "GROUP BY C.CUSTOMER_ID;";
        try (   Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(rqtPC)) {
                while(rs.next()) {
                    String pc = rs.getString("CUSTOMER_ID");
                    double dc = rs.getDouble("CA");
                    chiffreAff.put(pc, dc);
                }
            
        } catch(SQLException e) {
        }
         
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





















