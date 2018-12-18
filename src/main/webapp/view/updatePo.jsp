<%-- 
    Document   : updatePo
    Created on : 17 déc. 2018, 18:26:42
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
        <h1>Bienvenue ${nomClient}</h1>
        
        <a href="?action=&code=${customerId}">Retour</a>
        
        Modifier la facture numero:${po.getProductId()}
        
        <form method='GET'>
                    
            Produit : ${prod.getDesc()}
            Prix à l'unité : ${prod.getCostProduct()}
            
            
            Quantite : <input name="quantite" pattern="[0-9]{1}+" title="Nombre de produit" hint="${po.quantite}"><br/>
            <input type="hidden" name="action" value="UPDATE">
            <input type="submit" value="Modifier">
        </form>
    </body>
</html>
