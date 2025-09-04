<%@page import="java.util.List"%>
<%@page import="modelo.Trazabilidad"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Historial de Trazabilidad</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container mt-4">
            <h3 class="mb-3">📋 Historial de Trazabilidad</h3>
            <table class="table table-bordered table-hover text-center">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Acción</th>
                        <th>ID Mascota</th>
                        <th>Nombre</th>
                        <th>Especie</th>
                        <th>Edad</th>
                        <th>Fecha/Hora</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Trazabilidad> trazas = (List<Trazabilidad>) request.getAttribute("trazas");
                        if (trazas != null && !trazas.isEmpty()) {
                            for (Trazabilidad t : trazas) {
                    %>
                    <tr>
                        <td><%= t.getId()%></td>
                        <td><%= t.getAccion()%></td>
                        <td><%= t.getIdMascota()%></td>
                        <td><%= t.getNombre()%></td>
                        <td><%= t.getEspecie()%></td>
                        <td><%= t.getEdad()%></td>
                        <td><%= t.getFechaHora()%></td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-muted">No hay registros de trazabilidad.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
            <a href="<%=request.getContextPath()%>/mascotas" class="btn btn-secondary mt-3">⬅ Volver a Mascotas</a>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
