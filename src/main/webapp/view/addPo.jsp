<%-- 
    Document   : addPo
    Created on : 17 déc. 2018, 18:26:20
    Author     : morga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Bienvenue ${nomClient}</h1>
        
        <a href="?action=&code=${customerId}">Retour</a>
        
        <form method='GET'>
                    
            <select name="produit">
                
                <c:forEach var="prod" items="${listProduct}">
                    <option value="${prod.productId}">${prod.desc}, ${prod.costProduct} $</option>
                </c:forEach>
                        
            </select><br />
            
            
            Quantite : <input name="quantite" pattern="[0-9]{1}+" title="Le stock de produit"><br/>
            <input type="hidden" name="action" value="ADDPO">
            <input type="submit" value="Ajouter">
        </form>
        
        

        int customerId ,int productId,int quantite, float shippingCost, String salesDate,
                                 String shippingDate, String freightCompany
    </body>
</html>
