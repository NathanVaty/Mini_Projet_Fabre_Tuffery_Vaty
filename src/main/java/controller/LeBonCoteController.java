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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import modele.DAOConnexion;
import modele.DAOadmin;
import modele.DataSourceFactory;

/**
 *
 * @author Nathan Vaty
 */
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
        if (actionIs(request,"Connexion")){
            loginJSP(request,response);
        } else if (actionIs(request,"admin")) {
            adminJSP(request,response);
        } else if (actionIs(request,"client")) {
            clientJSP(request,response,request.getParameter("CustomerId"));
        } else {
            loginJSP(request,response);
        }
        
    }
    
    private boolean actionIs(HttpServletRequest request, String action) {
		return action.equals(request.getParameter("action"));
	}
    
    private void loginJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        showView("view/loginjsp.jsp",request, response);
        DAOConnexion connexion = new DAOConnexion(DataSourceFactory.getDataSource());
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        String modeCo;
        if(login != null && mdp != null) {
            modeCo = connexion.connexion(login, mdp);
            switch(modeCo){
                case "admin":
                    adminJSP(request,response);
                    break;
                case "client":
                    request.setAttribute("CustomerId", mdp);
                    clientJSP(request,response,mdp);
                    break;
                default:
                    break;
            }
            
        }
        
    }
    
    private void clientJSP(HttpServletRequest request, HttpServletResponse response,String customerId) throws ServletException, IOException {
                        showView("view/clientjsp.jsp",request, response);
			String playerName = request.getParameter("playerName");
			request.setAttribute("playerName", playerName);
		
    }
    private void adminJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showView("view/adminjsp.jsp",request, response);
       // HttpSession session = request.getSession();
        String action = request.getParameter("action");
        action = (action == null) ? "" : action; // Pour le switch qui n'aime pas les null
        String idProduit = request.getParameter("idProduit");
        String codeFabricant = request.getParameter("manuId");
        String codeProduit = request.getParameter("productCode");
        String prixAchat = request.getParameter("purchaseCost");
        String stock = request.getParameter("stock");
        String marge = request.getParameter("markup");
        String dispo = request.getParameter("dispo");
        String descproduit = request.getParameter("descProd");
        
        DAOadmin daoAdmin; //DAO de l'admin
        DataSource myDataSource = DataSourceFactory.getDataSource(); //Data source de l'application
        daoAdmin = new DAOadmin(myDataSource);
        
        try {
            
            request.setAttribute("listProduct", daoAdmin.listAllProduct());
            switch (action) {
                case "ajouter": // Requête d'ajout (vient du formulaire de saisie)
                    daoAdmin.insertProduct(Integer.parseInt(codeFabricant), codeProduit, Float.parseFloat(prixAchat), Integer.parseInt(stock), Float.parseFloat(marge), Boolean.parseBoolean(dispo), descproduit);
                    request.setAttribute("message","Code " + idProduit + " Ajouté");
                    request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    break;
                case "DELETE": // Requête de suppression (vient du lien hypertexte)
                    try {
                        daoAdmin.deleteProduct(Integer.parseInt(idProduit));
                        request.setAttribute("message", "Code " + idProduit + " Supprimé");
                        request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    } catch (SQLIntegrityConstraintViolationException e) {
                        request.setAttribute("message", "Impossible de supprimer " + idProduit + ", ce code est utilisé.");
                    }
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("message", ex.getMessage());
        } finally {
            
        }
        
    }
    
    private void showView(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletConfig().getServletContext().getRequestDispatcher("/views/" + jsp).forward(request, response);
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









