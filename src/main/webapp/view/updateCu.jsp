<%-- 
    Document   : updateCu
    Created on : 17 déc. 2018, 18:26:59
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
          Modification de vos données personnelles.
        
        <a href="?action=&code=${customerId}">Retour</a>
        
        Modifier la facture numero:${po.getProductId()}
        
        <form method='GET'>
                    
            Nom: <input name="nom" title="Nom du customer"><br/>
            Adresse ligne 1: <input name="adresse1" title="Adresse 1 du customer"><br/>
            Adresse ligne 2: <input name="adresse2" title="Adresse 2 du customer"><br/>
            Zip : <input name="zip" title="zip du customer"><br/>
            Ville :  <input name="ville" title="Ville du customer"><br/>
            Pays :  <input name="pays" title="Pays du customer"><br/>
            Telephone :  <input name="telephone" title="Telephone du customer"><br/>
            Fax :  <input name="fax" title="Fax du customer"><br/>
            Email :  <input name="email" title="Email du customer"><br/>

            <input type="hidden" name="action" value="UPDATE">
            <input type="submit" value="Modifier">
        </form>
    </body>
</html>
