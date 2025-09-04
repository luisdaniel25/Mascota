<%@page import="java.util.List"%>
<%@page import="modelo.Trazabilidad"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Historial de Trazabilidad</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #f4f6f9;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            header {
                background: linear-gradient(90deg, #004aad, #0066cc);
                color: white;
                padding: 20px;
                text-align: center;
                box-shadow: 0 4px 10px rgba(0,0,0,0.2);
            }
            header h1 {
                margin: 0;
                font-size: 26px;
                font-weight: 600;
            }
            .card {
                border-radius: 12px;
                box-shadow: 0 6px 16px rgba(0,0,0,0.1);
            }
            table th {
                background-color: #004aad !important;
                color: white;
            }
            table tbody tr:hover {
                background-color: #f0f6ff;
            }
            footer {
                text-align: center;
                font-size: 13px;
                color: #666;
                padding: 15px 0;
                margin-top: 40px;
                border-top: 1px solid #ddd;
            }
            .btn-sena {
                background-color: #004aad;
                color: white;
                border-radius: 6px;
                transition: 0.3s;
            }
            .btn-sena:hover {
                background-color: #0066cc;
                color: white;
            }
        </style>
    </head>
    <body>
        <!-- Header -->
        <header>
            <h1>📋 Historial de Trazabilidad</h1>
            <p class="mb-0">Registro de acciones realizadas sobre las mascotas</p>
        </header>

        <!-- Contenedor -->
        <div class="container mt-4">
            <div class="card p-4">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle text-center">
                        <thead>
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
                                List<Trazabilidad> trazas = (List<Trazabilidad>) request.getAttribute("trazas"); // obtiene y castea 'trazas' del request
                                if (trazas != null && !trazas.isEmpty()) { // valida que la lista exista y tenga datos
                                    for (Trazabilidad t : trazas) { // itera cada objeto Trazabilidad de la lista
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
                </div>
                <div class="d-flex justify-content-start">
                    <a href="<%=request.getContextPath()%>/mascotas" class="btn btn-sena mt-3">⬅ Volver a Mascotas</a>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <footer>
            &copy; 2025 Sistema de Gestión de Mascotas - SENA
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
