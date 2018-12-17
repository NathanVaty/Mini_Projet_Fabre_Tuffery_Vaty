<%-- 
    Document   : adminjsp
    Created on : 11 déc. 2018, 11:36:14
    Author     : morga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>
<%
    Gson gsonObj = new Gson();
    List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Le bon cote</title>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script type="text/javascript">
        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {
            'packages' : [ 'corechart' ]
        });

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

        // Create the data table.
        var data = google.visualization.arrayToDataTable([
               ['Pays', 'CA'],
                <c:forEach items="${listCA}" var="entry">
                       [ '${entry.key}', ${entry.value} ],
                </c:forEach>
        ]);

        // Set chart options
        var options = {
            'title' : 'Chiffre d\'affaire ', //title which will be shown right above the Google Pie Chart
            is3D : true, //render Google Pie Chart as 3D
            pieSliceText: 'label', //on mouse hover show label or name of the Country
            tooltip :  {showColorCode: true}, // whether to display color code for a Country on mouse hover
            'width' : 600, //width of the Google Pie Chart
            'height' : 300 //height of the Google Pie Chart
        };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
</script>
    </head>
    <body>
        <h1>Bievenue dans la section administrateur</h1>
        <h2>${produit}</h2>
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
                    <input type="submit" name="action" value="ADDP">
                    
        </form>
        <%--  On montre un éventuel message d'erreur --%>
	<div><h4>${codeF}</h4></div>
        <div><h4>${codeP}</h4></div>
        <div><h4>${prixA}</h4></div>
        <div><h4>${stock}</h4></div>
        <div><h4>${marge}</h4></div>
        <div><h4>${dispo}</h4></div>
        <div><h4>${desc}</h4></div>
        <div><h4>${message}</h4></div>
        <%--  On montre La liste des produits --%>
        <div>
            <table border="1">
                <tr><th>Id du produit</th><th>Id du fabricant</th><th>Code produit</th><th>Prix achat</th><th>Stock</th><th>Marge</th><th>Disponibilite</th><th>Description</th><th>Supprimer</th></tr>
                <c:forEach var="list" items="${listProduct}">
                    <tr>
                        <td>${list.productId}</td>
                        <td>${list.manuId}</td>
                        <td>${list.productCode}</td>
                        <td>${list.costProduct}</td>
                        <td>${list.quantity}</td>
                        <td>${list.markup}</td>
                        <td>${list.available}</td>
                        <td>${list.desc}</td>
                        <td><a href="?action=DELETEP&idProduit=${list.productId}" >delete</a></td>
                    </tr>
                </c:forEach> 
            </table> 
        </div>
        <div>
            <h4>Graphique de la consomation</h4>
            <form>
                <label for="dateDeb">Date de début :</label>
                <input type="date" id="dateDeb" name="dateDebut" value="2011-05-24">             
                <label for ="dateFin">Date de fin :</label>
                <input type="date" id="dateFin" name="dateFin" value="2011-05-24">
                
                <select name="typeCA">
                    <option value="caClient">Chiffre d'affaire Client</option>
                    <option value="caZoneGeo">Chiffre d'affaire Zone géo</option>
                    <option value="caCat">Chiffre d'affaire Catégorie</option>
                </select>
                
                <input type="hidden" name="actionCA" value="afficher">
                <input type="submit" value="Afficher">

            </form>
        </div>
        <div style="width: 600px">
            <%-- Affichage du google chart --%>
            <div id="chart_div"></div>
            <table border="1">
                <tr><th>Clé(pays,client,code produit)</th><th>Chiffre d'affaires</th></tr>
                <c:forEach items="${listCA}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
