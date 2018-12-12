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
import javax.sql.DataSource;

/**
 *
 * @author Nathan Vaty
 */
public class DAOConnexion {
    
    private static String login;
    private static String mdp;
    protected final DataSource myDataSource;
    
    public DAOConnexion(DataSource dataSource) {
        this.myDataSource = dataSource;
        this.login = "admin";
        this.mdp = "admin";
    }
    
    public String connexion(String login, String mdp) throws SQLException {
        login = login != null? login: "";
        mdp = mdp != null ? mdp: "";
        if (login.equals("admin") && mdp.equals("admin")) {
            return "admin";
        } 
        String sql = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ? AND EMAIL = ?";
		try (Connection connection = myDataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, mdp);
                        stmt.setString(2, login);

			ResultSet rs = stmt.executeQuery();
                        // Si on trouve au moins une ligne correspondant au customer on renvoie vrai
			if (rs.next()) {
				return "client";
			}
                }
        return "erreur";
    }
}





