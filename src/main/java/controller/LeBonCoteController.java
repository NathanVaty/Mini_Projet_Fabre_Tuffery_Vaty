/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ListCA;
import entity.ProductEntity;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import modele.DAOConnexion;
import modele.DAOadmin;
import modele.DAOclient;
import modele.DataSourceFactory;
import modele.PurchaseOrder;
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
            throws ServletException, IOException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        action = action == null? "": action;
        switch (action) {
            case "DELETEP" :
            case "MODIFYP" :
            case "MODIFY" : 
            case "ADDP" : adminJSP(request,response);
                    break;
            case "ADD":
            case "DELETE":
            case "ADDPO":
            case "UPDATEPO":
            case "UPDATECU": clientJSP(request,response);
                break;
                        
            case "Connexion":
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
                        clientJSP(request,response);
                        break;
                    default: 
                        break;
                }
                
            }
            default: loginJSP(request,response);
                       break;
        }
        
    }

    private void loginJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        showView("loginjsp.jsp",request, response);
    }
    
    private void clientJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, Exception {
        String action = request.getParameter("action");
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
// Get the date today using Calendar object.
Date today = Calendar.getInstance().getTime();
// Using DateFormat format method we can create a string
// representation of a date with the defined format.
String reportDate = df.format(today);

int customerID = request.getParameter("mdp") == null ?
        0 : Integer.parseInt(request.getParameter("mdp"));
DAOclient daoclient = new DAOclient(DataSourceFactory.getDataSource());
DAOadmin daoAdmin; //DAO de l'admin
daoAdmin = new DAOadmin(DataSourceFactory.getDataSource());
String produit = request.getParameter("produit");
String quantite = request.getParameter("quantite");
String code = request.getParameter("code");
List<PurchaseOrder> listePO = daoclient.listeOrder(customerID);
request.setAttribute("nomClient", daoclient.findCustomer(customerID));
ProductEntity leProduit;//Le produit qu'on recupera grace a la fonction getproduct
request.setAttribute("listePO", listePO);


try {
    if (action.equals("DELETE")) {
        daoclient.deletePurchaseOrder(Integer.parseInt(code));
        listePO = daoclient.listeOrder(customerID);
    } else if (action.equals("ADD")) {
        request.setAttribute("listProduct", daoAdmin.listAllProduct());
        showView("addPo.jsp", request, response);
    } else if(action.equals("ADDPO")){
        leProduit = daoAdmin.getProduct(Integer.parseInt(produit));
        daoclient.addPurchaseOrder(customerID ,Integer.parseInt(produit),Integer.parseInt(quantite), leProduit.getCostProduct() , reportDate,
                reportDate, null);
        
        
    } else if (action.equals("UPDATEPO")) {
        showView("updatePo.jsp", request, response);
        
    }else if (action.equals("UPDATECU")) {
        showView("updateCu.jsp", request, response);
    }
    
    //request.setAttribute("listePO", listePO);
    showView("clientjsp.jsp", request, response);
    
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
        String idProduit = request.getParameter("idProduit");
        List<ListCA> resultCa = new LinkedList<>();
        JSONObject json;
        DataSource myDataSource = DataSourceFactory.getDataSource();
        DAOadmin daoAdmin; //DAO de l'admin
        daoAdmin = new DAOadmin(myDataSource);
        DAOadmin dao = (DAOadmin) getServletContext().getAttribute("dao");
        
        try {
            request.setAttribute("listProduct", daoAdmin.listAllProduct());
            switch (action) {
                case "ADDP": // Requête d'ajout (vient du formulaire de saisie)
               
                    int codeFabricant = Integer.parseInt(request.getParameter("manuId"));
                    String codeProduit = request.getParameter("productCode");
                    float prixAchat = Float.parseFloat(request.getParameter("purchaseCost").replaceAll(" ", ""));
                    int stock = Integer.parseInt(request.getParameter("stock"));
                    float marge = Float.parseFloat(request.getParameter("markup").replaceAll(" ", ""));
                    String dispo = request.getParameter("dispo");
                    String descproduit = request.getParameter("descProd");
                    int codeM = (int) codeFabricant;
                    float prix = prixAchat;
                    float margee = marge;
                    int stockage = (int) stock;
                    String disponible = ""+dispo+"";
                    String description = ""+descproduit+"";
                    String codeP = ""+codeProduit+"";
                    
                    daoAdmin.insertProduct(codeM, codeP,
                            prix, stockage,
                            margee, disponible.toUpperCase(), description);
                    request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    break;
                case "DELETEP": // Requête de suppression (vient du lien hypertexte)
                    try {
                        daoAdmin.deleteProduct(Integer.parseInt(idProduit));
                        request.setAttribute("listProduct", daoAdmin.listAllProduct());
                    } catch (SQLIntegrityConstraintViolationException e) {
                        // request.setAttribute("message", "Impossible de supprimer " + idProduit + ", ce code est utilisé.");
                    }
                    break;
                    
                case "MODIFYP": // Requete de modification pour afficher le formulaire
                    request.setAttribute("prodId",idProduit);
                    showView("modifyPro.jsp", request, response);
                    break;
                    
                case "MODIFY": //Requete qui met a jour le produit
                    int codeF = Integer.parseInt(request.getParameter("manuId"));
                    String pCode = request.getParameter("productCode");
                    float pAchat = Float.parseFloat(request.getParameter("purchaseCost").replaceAll(" ", ""));
                    int stockP = Integer.parseInt(request.getParameter("stock"));
                    float margeP = Float.parseFloat(request.getParameter("markup").replaceAll(" ", ""));
                    String dispoP = request.getParameter("dispo");
                    String descP = request.getParameter("descProd");
                    int codeMa = (int) codeF;
                    float prixA = (float) pAchat;
                    float margeeP = (float) margeP;
                    int stockageP = (int) stockP;
                    String disponibleP = ""+dispoP+"";
                    String descriptionP = ""+descP+"";
                    String codeProd = ""+pCode+"";
                    int idP = Integer.parseInt(request.getParameter("idProduit").replaceAll(" ", ""));
                    int idProd = (int) idP;
                    daoAdmin.updateProduct(idProd,codeMa,codeProd,prixA,stockageP,margeeP,disponibleP,descriptionP);
                   // daoAdmin.updateProduct(Integer.parseInt(idProduit), codeMa, codeProd,
                     //       prixA, stockageP,
                       //     margeeP, disponibleP.toUpperCase(),descriptionP);
                     request.setAttribute("listProduct", daoAdmin.listAllProduct());
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
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

