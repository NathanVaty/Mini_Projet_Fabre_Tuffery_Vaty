<%-- 
    Document   : modifyPro
    Created on : 18 déc. 2018, 00:33:17
    Author     : pierr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Bienvenue sur la modfication du produit ${prodId}</h1>
         <form method='GET'>
                    <input type="hidden" name="IdProduit" value="${prodId}">
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
                    <input type="submit" name="action" value="MODIFY">        
        </form>
        
    </body>
</html>
