/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import modele.DAOadmin;
import modele.DataSourceFactory;

/**
 *
 * @author Nathan Vaty
 */
@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
	action = (action == null) ? "" : action; // Pour le switch qui n'aime pas les null
	String codeFabricant = request.getParameter("manuId");
        String codeProduit = request.getParameter("productCode");
        String prixAchat = request.getParameter("purchaseCost");
        String stock = request.getParameter("stock");
        String marge = request.getParameter("markup");
        String dispo = request.getParameter("dispo");
        String descproduit = request.getParameter("descProd");
         
        try {
            DAOadmin daoAdmin = new DAOadmin(DataSourceFactory.getDataSource());
	    request.setAttribute("productId", daoAdmin.listAllProduct());
	    switch (action) {
	    case "ADD": // Requête d'ajout (vient du formulaire de saisie)
		daoAdmin.insertProduct(Integer.valueOf(codeFabricant), codeProduit, Float.valueOf(prixAchat), Integer.valueOf(stock), Float.valueOf(marge), Boolean.valueOf(dispo), descproduit);
                request.setAttribute("message", "Produit  Ajouté");
		request.setAttribute("codes", daoAdmin.listAllProduct());	
                break;
            case "DELETE": // Requête de suppression (vient du lien hypertexte)
		//try {
		// TODO DELETE									
		//} catch (SQLIntegrityConstraintViolationException e) {
                    //request.setAttribute("message", "Impossible de supprimer " + code + ", ce code est utilisé.");
		//}
            break;
            }
	} catch (Exception ex) {
            Logger.getLogger("discountEditor").log(Level.SEVERE, "Action en erreur", ex);
            request.setAttribute("message", ex.getMessage());
	} finally {

	}
        // On continue vers la page JSP sélectionnée
	request.getRequestDispatcher("view/adminjsp.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

