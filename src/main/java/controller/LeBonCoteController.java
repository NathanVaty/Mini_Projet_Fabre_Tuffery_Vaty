/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.BeforeCompletion;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import modele.DAOConnexion;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import org.json.JSONObject;

/**
 *
 * @author Nathan Vaty
 */
@WebServlet(name = "LeBonCoteController", urlPatterns = {"/LeBonCoteController"})
public class LeBonCoteController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        if (actionIs(request,"DELETEP")) {
            adminJSP(request,response);
        } else if (actionIs(request, "ADDP")) {
            adminJSP(request,response);
        }
        if (actionIs(request,"Connexion")) {
            DAOConnexion connexion = new DAOConnexion(DataSourceFactory.getDataSource());
            String login = request.getParameter("login");
            String mdp = request.getParameter("mdp");
            request.setAttribute("login", login);
            String modeCo;
            if(login != null && mdp != null) {
                modeCo = connexion.connexion(login, mdp);
                switch(modeCo){
                    case "admin":
                        adminJSP(request,response);
                        break;
                    case "client":
                        clientJSP(request,response,mdp);
                        break;
                    default: 
                        break;
                }
                
            }
        } else {
            loginJSP(request,response);
        }
        
    }
    
    private boolean actionIs(HttpServletRequest request, String action) {
		return action.equals(request.getParameter("action"));
	}
    
    private void loginJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        showView("loginjsp.jsp",request, response);
    }
    
    private void clientJSP(HttpServletRequest request, HttpServletResponse response,String customerId) throws ServletException, IOException, SQLException {
                               String action = request.getParameter("action");
	action = (action == null) ? "" : action;
        
        int idcustomer = Integer.parseInt(customerId);
      //  int customerId = (request.getParameter("CustomerID") == null) ? -1 : Integer.parseInt(request.getParameter("CustomerID"));
        DAOclient daoclient = new DAOclient(DataSourceFactory.getDataSource());
        String nomUser = daoclient.findCustomer(idcustomer);
        
        try { 
            request.setAttribute("nomClient",nomUser);
            request.setAttribute("listePO", daoclient.listeOrder(idcustomer));
            switch (action) {
	    case "ADD": // Requête d'ajout (vient du formulaire de saisie)
   
                break;
            case "DELETE": // Requête de suppression (vient du lien hypertexte)
                String orderNum = request.getParameter("code");
		daoclient.deletePurchaseOrder(Integer.parseInt(orderNum));
                clientJSP(request,response,customerId);
            break;
            }
            
	    request.getRequestDispatcher("view/clientjsp.jsp").forward(request, response);
            
        } catch (Exception ex) {
            Logger.getLogger("discountEditor").log(Level.SEVERE, "Action en erreur", ex);
            request.setAttribute("message", ex.getMessage());
	} finally {

	}

		
    }
    private void adminJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "" : action; // Pour le switch qui n'aime pas les null
        String actionCA = request.getParameter("actionCA");
        actionCA = (action == null) ? "" : actionCA;
        String dateDeb = request.getParameter("dateDeb");
        String dateFin = request.getParameter("dateFin");
        String codeFabricant = request.getParameter("manuId");
        String codeProduit = request.getParameter("productCode");
        String prixAchat = request.getParameter("purchaseCost");
        String stock = request.getParameter("stock");
        String marge = request.getParameter("markup");
        String dispo = request.getParameter("dispo");
        String descproduit = request.getParameter("descProd");
        HashMap<String,Double> resultCa = new HashMap<>();
        JSONObject json;
        DataSource myDataSource = DataSourceFactory.getDataSource();
        DAOadmin daoAdmin; //DAO de l'admin
        daoAdmin = new DAOadmin(myDataSource);
        
        try {
            request.setAttribute("listProduct", daoAdmin.listAllProduct());
            switch (action) {
                case "ADDP": // Requête d'ajout (vient du formulaire de saisie)
                    //request.setAttribute("codeF",Integer.parseInt(codeFabricant));
                    //request.setAttribute("codeP",codeProduit);
                    //request.setAttribute("prixA", Float.parseFloat(prixAchat));
                    //request.setAttribute("stock",Integer.parseInt(stock));
                    //request.setAttribute("marge",Float.parseFloat(marge));
                    //request.setAttribute("dispo", dispo.toUpperCase());
                    //request.setAttribute("desc",descproduit);
                    //daoAdmin.insertProduct(19985678,"SW",234.0,10,10.5,"TRUE","A Supp");
                   daoAdmin.insertProduct(Integer.parseInt(codeFabricant), codeProduit, 
                           Double.parseDouble(prixAchat), Integer.parseInt(stock), 
                           Double.parseDouble(marge), dispo.toUpperCase(), descproduit);
                    break;
                case "DELETE": // Requête de suppression (vient du lien hypertexte)
                    try {
                        //daoAdmin.deleteProduct(Integer.parseInt(idProduit));
                        request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    } catch (SQLIntegrityConstraintViolationException e) {
                        //request.setAttribute("message", "Impossible de supprimer " + idProduit + ", ce code est utilisé.");
                    }
                    break;
            }
            switch(actionCA){
                case "caClient": // Cas du chiffre d'affaire par client
                    resultCa = daoAdmin.CAfromClient(dateDeb, dateFin);
                    json = new JSONObject(resultCa);
                    break;
                case "caZoneGeo": // Cas du chiffre d'affaire par la zone geo
                    resultCa = daoAdmin.CAofZoneGeo(dateDeb, dateFin);
                    json = new JSONObject(resultCa);
                    break;
                case "caCat": // Cas du chiffre d'affaire par categorie
                    resultCa = daoAdmin.CAofCategorie(dateDeb, dateFin);
                    json = new JSONObject(resultCa);
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
        } finally {
        }
        showView("adminjsp.jsp",request, response);
    }
    
    private void showView(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext().getRequestDispatcher("/view"+ "/" + jsp).forward(request, response);
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
            Logger.getLogger(LeBonCoteController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LeBonCoteController.class.getName()).log(Level.SEVERE, null, ex);
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



