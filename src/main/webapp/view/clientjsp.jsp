<%-- 
    Document   : clientjsp
    Created on : 11 déc. 2018, 11:34:36
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
                            <a href="?action=UPDATECU&code=${customerId}">Editer mes données personnelles</a>
               
                            <a href="?action=ADD&code=${customerId}">Ajouter une commande</a>
                            
			    <table border=2>
                                <%-- On affiche un tableau avec les codes --%>
                                <tr> <th>Code Produit</th> <th>Quantité</th><th>Prix</th><th>Date</th><th>Modifier</th><th>Supprimer</th></tr>           
                                <c:forEach var="purchaseOrder" items="${listePO}">
                                    <tr><td>${purchaseOrder.productId}</td>
                                        <td>${purchaseOrder.quantite}</td>
                                        <td>${purchaseOrder.finalCost}</td>
                                        <td>${purchaseOrder.salesDate}</td>
                                        <td><a href="?action=UPDATEPO&code=${purchaseOrder.orderNum}">Modifier</a></td>
                                        <td><a href="?action=DELETE&code=${purchaseOrder.orderNum}">Supprimer</a></td></tr>
                                </c:forEach>      
                            </table>
		<br>
    </body>
</html>
