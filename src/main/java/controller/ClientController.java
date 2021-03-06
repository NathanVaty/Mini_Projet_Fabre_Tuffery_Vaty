/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ProductEntity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import modele.PurchaseOrder;

/**
 *
 * @author Nathan Vaty
 */

@WebServlet(name = "ClientController", urlPatterns = {"/ClientController"})
public class ClientController extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        //String action = request.getParameter("action");
	//action = (action == null) ? "" : action;
        // Create an instance of SimpleDateFormat used for formatting 
// the string representation of date (month/day/year)
DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

// Get the date today using Calendar object.
Date today = Calendar.getInstance().getTime();        
// Using DateFormat format method we can create a string 
// representation of a date with the defined format.
String reportDate = df.format(today);

        //Parametre permettant de savoir ou veut aller l'utilisateur
        String action = request.getParameter("action");
        
        //Parametre permettant de savoir quel utilisateur est connecté
        int customerId = (request.getParameter("CustomerID") == null) ? -1 : Integer.parseInt(request.getParameter("CustomerID"));
        //Lien vers le daoclient
        DAOclient daoclient = new DAOclient(DataSourceFactory.getDataSource());
        DAOadmin daoAdmin; //DAO de l'admin
        daoAdmin = new DAOadmin(DataSourceFactory.getDataSource());
        
        //Recupere l'id du produit renvoyé par la page jsp
        String produit = request.getParameter("produit");
        //Recupere la quantite voulue
        String quantite = request.getParameter("quantite");
        //Recupere le code Purchase Order renvoyé par la page jsp
        String codePO = request.getParameter("code");
        //Liste des factures d'un client 
        //Constante mise en parametre car gérée plus tard danas une autre classe
        List<PurchaseOrder> listePO = daoclient.listeOrder(2);
        ProductEntity leProduit;//Le produit qu'on recupera grace a la fonction getproduct
        PurchaseOrder laFacture;//La facture qu'on recupera grace a la fonction
        
        
        
        try { 
            if(action != null) {
                if (action.equals("DELETE")) {
                    //Supprime une facture de la bd
                    daoclient.deletePurchaseOrder(Integer.parseInt(codePO));
//                    listePO = daoclient.listeOrder(2);
                } else if (action.equals("ADD")) {
                    //Renvoie le client vers addPo.jsp
                    request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    request.getRequestDispatcher("view/addPo.jsp").forward(request, response);
                } else if(action.equals("ADDPO")){
                    //Ajoute une facture a a bd
                    leProduit = daoAdmin.getProduct(Integer.parseInt(produit));
                    daoclient.addPurchaseOrder(customerId ,Integer.parseInt(produit),Integer.parseInt(quantite), leProduit.getCostProduct() , reportDate,
                                 reportDate, null);
                    
                    
                } else if (action.equals("UPDATEPO")) {
                    //Permet de renvoyer le client vers la page updatePo.jsp
                    //Mis en commentaire car sinon on ne peut pas aceder a la page
                    //leProduit = daoAdmin.getProduct(Integer.parseInt(produit));
                    //laFacture = daoclient.getPurchaseOrder(Integer.parseInt(codePO));
                    //request.setAttribute("prod", leProduit);
                    //request.setAttribute("po", laFacture);
                    request.getRequestDispatcher("view/updatePo.jsp").forward(request, response);
                    
                }else if (action.equals("UPDATECU")) {
                    //Permet de renvoyer le client vers la page updatePo.jsp
                    request.getRequestDispatcher("view/updateCu.jsp").forward(request, response);
                }
            }
            
            request.setAttribute("listePO", listePO);
	    request.getRequestDispatcher("view/clientjsp.jsp").forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger("discountEditor").log(Level.SEVERE, "Action en erreur", ex);
            request.setAttribute("message", ex.getMessage());
	} finally {

	}
        System.out.println("Envoie des valeurs vers le jsp");
        request.getRequestDispatcher("view/clientjsp.jsp").forward(request, response);
        
        
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
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






