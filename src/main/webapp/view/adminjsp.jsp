<%-- 
    Document   : adminjsp
    Created on : 11 déc. 2018, 11:36:14
    Author     : morga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Le bon cote</title>
    </head>
    <body>
        <h1>Bievenue dans la section administrateur</h1>
        <form method='GET'>
                    Id du fabricant : <input name="manuId" pattern="[0-9]{1}+" title="Un identifiant de fabricant"><br/>
                    Un code pour le produit : <input name="productCode" size="1" maxlength="2" pattern="[A-Z]{2}" title="Un code de produit"><br/>
                    Prix d'achat : <input name="purchaseCost" pattern="[0-9]{1}+" title="Un prix d'achat"><br/>
                    Stock : <input name="stock" pattern="[0-9]{1}+" title="Le stock de produit"><br/>
                    marge du produit : <input name="markup" pattern="[0-9]{1}+" title="Une marge sur le produit"><br/>
                    Disponibilité : <select name="dispo">
                        <option value="true">Disponible</option>
                        <option value="false">Pas Disponible</option>
                        </select><br />
                    Description du produit : <textarea name="descProd" rows="3" cols="10" placeholder="Ecrivez votre description" title="une description de produit"></textarea><br/>
                    <input type="hidden" name="action" value="ADD">
                    <input type="submit" value="Ajouter">
        </form>
        <%--  On montre un éventuel message d'erreur --%>
	<div><h4>${message}</h4></div>
        <%--  On montre La liste des produits --%>
        <div>
            <table border="1">
                <tr><th>Id du produit</th><th>Id du fabricant</th><th>Code produit</th><th>Prix achat</th><th>Stock</th><th>Marge</th><th>Disponibilite</th><th>Description</th></tr>
                <c:forEach var="list" items="${codes}">
                    <tr>
                        <td>${list.productId}</td>
                        <td>${list.manuId}</td>
                        <td>${list.productCode}</td>
                        <td>${list.costProduct}</td>
                        <td>${list.quantity}</td>
                        <td>${list.markup}</td>
                        <td>${list.available}</td>
                        <td>${list.desc}</td>
                        <td><a href="?action=DELETE&code=${list.productId}">delete</a></td>
                    </tr>
                </c:forEach> 
            </table>
            
        </div>
    </body>
</html>
