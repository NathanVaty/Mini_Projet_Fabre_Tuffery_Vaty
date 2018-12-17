/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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

        
        String action = request.getParameter("action");
        int customerId = (request.getParameter("CustomerID") == null) ? -1 : Integer.parseInt(request.getParameter("CustomerID"));
        DAOclient daoclient = new DAOclient(DataSourceFactory.getDataSource());
        DAOadmin daoAdmin; //DAO de l'admin
        daoAdmin = new DAOadmin(DataSourceFactory.getDataSource());
        String produit = request.getParameter("produit");
        String quantite = request.getParameter("quantite");
        String code = request.getParameter("code");
        List<PurchaseOrder> listePO = daoclient.listeOrder(2);
        
        try { 
            if(action != null) {
                if (action.equals("DELETE")) {
                    request.setAttribute("code",code);
                    daoclient.deletePurchaseOrder(Integer.parseInt(code));
                    listePO = daoclient.listeOrder(2);
                } else if (action.equals("ADD")) {
                    request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    request.getRequestDispatcher("view/addPo.jsp").forward(request, response);
                } else if(action.equals("ADDPO")){
                    
                    
                    request.setAttribute("idprod",produit);
                    
                    
                } else if (action.equals("UPDATEPO")) {	
                    request.getRequestDispatcher("view/updatePo.jsp").forward(request, response);
                    
                }else if (action.equals("UPDATECU")) {
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




