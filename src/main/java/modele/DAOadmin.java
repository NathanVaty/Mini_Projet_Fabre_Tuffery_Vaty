/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import entity.ProductEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public HashMap<String,Double> CAofCategorie(String dateDeb, String dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
        String rqtPC = "SELECT PC.PROD_CODE, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA " +
                        "FROM PRODUCT_CODE PC " +
                        "JOIN PRODUCT P ON P.PRODUCT_CODE = PC.PROD_CODE " +
                        "JOIN PURCHASE_ORDER PO ON PO.PRODUCT_ID = P.PRODUCT_ID " +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE " +
                        "WHERE PO.SALES_DATE BETWEEN ? AND ? " + 
                        "GROUP BY PC.PROD_CODE";
        try (   Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(rqtPC)) {
                stmt.setString(1, dateDeb);
                stmt.setString(2,dateFin);
                ResultSet rs = stmt.executeQuery(); 
                while(rs.next()) {
                    String pc = rs.getString("PROD_CODE");
                    double dc = rs.getDouble("CA");
                    chiffreAff.put(pc, dc);
                }
                System.out.println("apres while");            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
         
        return chiffreAff;
    }

    /**
     * Renvoie le chiffre d'affaire par zone géographique
     * entre 2 dates données
     * @param dateDeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAofZoneGeo(String dateDeb, String dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
        String rqtPC = "SELECT MK.ZIP_CODE, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA\n" +
                        "FROM MICRO_MARKET MK\n" +
                        "JOIN CUSTOMER C ON C.ZIP = MK.ZIP_CODE\n" +
                        "JOIN PURCHASE_ORDER PO ON PO.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID\n" +
                        "JOIN PRODUCT_CODE PC ON PC.PROD_CODE = P.PRODUCT_CODE\n" +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE\n" +
                        "WHERE PO.SALES_DATE BETWEEN ? AND ? \n" +
                        "GROUP BY MK.ZIP_CODE\n";
       try (   Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(rqtPC)) {
                stmt.setString(1, dateDeb);
                stmt.setString(2,dateFin);
                ResultSet rs = stmt.executeQuery();
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
     * Renvoie le chiffre d'affaire par client
     * @param dateDeb
     * @param dateFin
     * @return 
     */
    public HashMap<String,Double> CAfromClient(String dateDeb,String dateFin) {
       HashMap<String,Double> chiffreAff = new HashMap<>();
        String rqtPC = "SELECT C.CUSTOMER_ID, SUM((PO.QUANTITY*P.PURCHASE_COST)*(1-(D.RATE*0.01))) AS CA\n" +
                        "FROM CUSTOMER C\n" +
                        "JOIN PURCHASE_ORDER PO ON PO.CUSTOMER_ID = C.CUSTOMER_ID\n" +
                        "JOIN PRODUCT P ON P.PRODUCT_ID = PO.PRODUCT_ID\n" +
                        "JOIN PRODUCT_CODE PC ON PC.PROD_CODE = P.PRODUCT_CODE\n" +
                        "JOIN DISCOUNT_CODE D ON D.DISCOUNT_CODE = PC.DISCOUNT_CODE\n" +
                        "WHERE PO.SALES_DATE BETWEEN ? AND ? \n" +
                        "GROUP BY C.CUSTOMER_ID";
        try (   Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(rqtPC)) {
                stmt.setString(1, dateDeb);
                stmt.setString(2,dateFin);
                ResultSet rs = stmt.executeQuery();
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
     * Ajouter un produit
     * @param manufactID
     * @param prodCode
     * @param prodCost
     * @param quantite
     * @param markup
     * @param dispo
     * @param desc
     */
    public void insertProduct(int manufactID,String prodCode, double prodCost,
                    int quantite, double markup, boolean dispo, String desc) {
        String sql = "INSERT INTO PRODUCT "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
         try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)  ){
             // On définit la valeur de paramètre
            
             //Génération automatique du prochain ordeNum
             int orderNum = 0; //Numero du prochain orderNum
             ResultSet clefs = discountStatement.getGeneratedKeys();
             while(clefs.next()){
                orderNum = clefs.getInt(1);
             }
             
             discountStatement.setInt(1, orderNum);
             discountStatement.setInt(2, manufactID);
             discountStatement.setString(3, prodCode);
             discountStatement.setDouble(4, prodCost);
             discountStatement.setInt(5, quantite);
             discountStatement.setDouble(6, markup);
             discountStatement.setBoolean(7, dispo);
             discountStatement.setString(8, desc);
             
             
             // On ajoute le code discount avec une requete
             discountStatement.execute();
         } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
    }
    
    /**
     * Modifier un produit
     * @param manufactID
     * @param prodCode
     * @param prodCost
     * @param quantite
     * @param markup
     * @param dispo
     * @param desc
     * @return 
     */
    public void updateProduct(int productID, int manufactID,String prodCode, double prodCost,
                    int quantite, double markup, boolean dispo, String desc) {
        String sql = "UPDATE PRODUCT SET MANUFACTURER_ID = ? , PRODUCT_CODE = ? , PURCHASE_COST = ?, QUANTITY_ON_HAND = ?, "
                + " MARKUP = ?, AVAILABLE = ?, DESCRIPTION = ? "
                + " WHERE PRODUCT_ID = ?"
                  ;
         try (Connection connection = myDataSource.getConnection();
                 PreparedStatement discountStatement = connection.prepareStatement(sql)){

             discountStatement.setInt(1, manufactID);
             discountStatement.setString(2, prodCode);
             discountStatement.setDouble(3, prodCost);
             discountStatement.setInt(4, quantite);
             discountStatement.setDouble(5, markup);
             discountStatement.setBoolean(6, dispo);
             discountStatement.setString(7, desc);
             discountStatement.setInt(8, productID);
             
             
             // On ajoute le code discount avec une requete
             discountStatement.execute();
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Supprimer un produit
     * @param productID 
     */
    public void deleteProduct(int productID) {
        // Une requête SQL paramétrée
		String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
		try (   Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)
                ) {
                        // Définir la valeur du paramètre
			stmt.setInt(1, productID);
			
                        // On execute la requete
			stmt.executeUpdate();
                        connection.commit();
		}  catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
		}
    }
    
    /** 
     * retourne vrai si le manufacturer existe
     * @param manuID
     * @return
     * @throws SQLException 
     */
    public boolean manufacturerExist(int manuID) throws SQLException {
        boolean result = false;
        String sql = "SELECT * FROM MANUFACTURER WHERE MANUFACTURER_ID = ?";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, manuID);

			ResultSet rs = stmt.executeQuery();
                        // Si on trouve au moins une ligne correspondant au customer on renvoie vrai
			if (rs.next()) {
				result = true;
			}
                }
        return result;
    }
    
    /**
     * renvoie vrai si le code produit existe
     * @param prodCode
     * @return
     * @throws SQLException 
     */
    public boolean productCodeExist(int prodCode) throws SQLException {
        boolean result = false;
        String sql = "SELECT * FROM PRODUCT_CODE WHERE PROD_CODE = ?";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, prodCode);

			ResultSet rs = stmt.executeQuery();
                        // Si on trouve au moins une ligne correspondant au customer on renvoie vrai
			if (rs.next()) {
				result = true;
			}
                }
        return result;
    }
    
        /**
     * Méthode qui permet de récuperer une liste de tous les produits
     * @return une liste de tous les produits
     */
    public List<ProductEntity> listAllProduct() throws SQLException{
        List<ProductEntity> result = new LinkedList<>();
        String sql = "SELECT * FROM PRODUCT";
        try (Connection connection = myDataSource.getConnection();
		     PreparedStatement stmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					 int productId = rs.getInt("PRODUCT_ID");
                                         int manuId = rs.getInt("MANUFACTURER_ID");
                                         String productCode = rs.getString("PRODUCT_CODE");
                                         Double costProduct = rs.getDouble("PURCHASE_COST");
                                         int quantity = rs.getInt("QUANTITY_ON_HAND");
                                         Double markup = rs.getDouble("MARKUP");
                                         boolean available = rs.getBoolean("AVAILABLE");
                                         String desc = rs.getString("DESCRIPTION");
					ProductEntity c = new ProductEntity(productId, manuId, productCode, costProduct, quantity, markup, available, desc);
					result.add(c);
				}
			}
		}
		return result ;              
    }
}



