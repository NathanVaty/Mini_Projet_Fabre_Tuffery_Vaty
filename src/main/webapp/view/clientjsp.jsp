<%-- 
    Document   : clientjsp
    Created on : 11 déc. 2018, 11:34:36
    Author     : morga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Display Client</h1>
		<c:choose>
			<%-- On n'a pas trouvé le client --%>
			<c:when test="${empty customerId}">
				Client inconnu.			
			</c:when>
                                
                        <c:otherwise> <%-- On a trouvé --%>
                            
                            
                            <a href="clientEdit.jsp">Editer mes données personnelles</a>
               
                            <a href="clientAjout.jsp">Ajouter une commande</a>
                            
			    <table border=2>
                                <%-- On affiche un tableau avec les codes --%>
                                <tr> <th>Code Produit</th> <th>Quantité</th><th>Prix</th><th>Date</th><th>Modifier</th><th>Supprimer</th></tr>           
                                <c:forEach var="purchaseOrder" items="${listeOP}">
                                    <tr><td>${purchaseOrder.product_id}</td><td>${purchaseOrder.quantity}</td><td>${purchaseOrder.final_cost}</td><td>${purchaseOrder.sales_date}</td><td><a href="orderModif">Modifier</a></td><td><a href="?action=DELETE&code=${purchaseOrder.order_Num}">Supprimer</a></td></tr>
                                </c:forEach>      
                            </table>
			</c:otherwise>
                                
                </c:choose>
		<br>
		<%-- Equivalent de request.getContextPath() en java --%>
		<a href='${pageContext.request.contextPath}'>Retour au menu</a><br>
    </body>
</html>
