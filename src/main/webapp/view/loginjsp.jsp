<%-- 
    Document   : loginjsp
    Created on : 11 déc. 2018, 11:36:25
    Author     : morga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connexion</title>
    </head>
    <body>
        <h1>Bienvenue sur Le bon coté !</h1>
        <hr/>
        <form method='POST'>
             <p>
            Identifiez vous:<br/>
            ID: <input type="text" name="login"/><br/>
            mdp: <input type="password" name="mdp"><br/>
            <input type="submit" name="action" value="Connexion">
             </p>
        </form>
    </body>
</html>
