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
    
    protected final DataSource myDataSource;
    
    public DAOadmin(DataSource dataSource) {
        this.myDataSource = dataSource;
    }
    
    public HashMap<String,Double> caCategorieArticle(Date dateDeb,Date dateFin) throws Exception {
        HashMap<String,Double> chiffreAff = new HashMap<>();
        List<String> codes = new LinkedList<>();
        String rqtPC = "SELECT prod_code FROM product_code";
        try (   Connection connection = myDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(rqtPC)) {
                while(rs.next()) {
                    
                }
            
        } catch(SQLException e) {
            
        }
        return chiffreAff;
    }
}



