<%@page import="java.util.List"%>
<%@page import="modelo.Mascota"%>
<%@page import="dao.MascotaDAO"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Mascotas</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <%
            // Crear DAO y obtener lista de mascotas si no viene en el request
            List<Mascota> lista = (List<Mascota>) request.getAttribute("lista");
            if (lista == null) {
                MascotaDAO dao = new MascotaDAO();
                lista = dao.listar();
            }
        %>

        <!-- BARRA SUPERIOR -->
        <nav class="navbar navbar-dark bg-primary mb-4 shadow">
            <div class="container-fluid">
                <span class="navbar-brand mb-0 h1">
                    <i class="bi bi-building"></i> Gestión Empresarial de Mascotas
                </span>
            </div>
        </nav>

        <div class="container">

            <!-- LISTA DE MASCOTAS -->
            <div class="card shadow mb-4">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0"><i class="bi bi-list-ul"></i> Lista de Mascotas Registradas</h5>
                </div>
                <div class="card-body">
                    <table class="table table-hover table-bordered align-middle text-center">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Especie</th>
                                <th>Edad</th>
                                <th colspan="3">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                if (lista != null && !lista.isEmpty()) {
                                    for (Mascota m : lista) {
                            %>
                            <tr>
                        <form action="<%=request.getContextPath()%>/mascotas" method="post">
                            <input type="hidden" name="accion" value="actualizar">
                            <td><input type="hidden" name="id" value="<%=m.getId()%>"><%=m.getId()%></td>
                            <td><input type="text" name="nombre" value="<%=m.getNombre()%>" class="form-control" required></td>
                            <td><input type="text" name="especie" value="<%=m.getEspecie()%>" class="form-control" required></td>
                            <td><input type="number" name="edad" value="<%=m.getEdad()%>" class="form-control" min="0" required></td>
                            <td>
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <i class="bi bi-pencil-square"></i> Actualizar
                                </button>
                            </td>
                        </form>
                        <td>
                            <form action="<%=request.getContextPath()%>/mascotas" method="post" class="d-inline">
                                <input type="hidden" name="accion" value="eliminar">
                                <input type="hidden" name="id" value="<%=m.getId()%>">
                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirm('¿Seguro que deseas eliminar esta mascota?')">
                                    <i class="bi bi-trash"></i> Eliminar
                                </button>
                            </form>
                        </td>
                        <td>
                            <form action="<%=request.getContextPath()%>/mascotas" method="post" class="d-inline">
                                <input type="hidden" name="accion" value="certificado">
                                <input type="hidden" name="id" value="<%=m.getId()%>">
                                <button type="submit" class="btn btn-sm btn-info">
                                    <i class="bi bi-file-earmark-text"></i> Certificado
                                </button>
                            </form>
                        </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="7" class="text-muted">No hay mascotas registradas</td>
                        </tr>
                        <% }%>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- FORMULARIO NUEVA MASCOTA -->
            <div class="card shadow mb-4">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0"><i class="bi bi-plus-circle"></i> Registrar Nueva Mascota</h5>
                </div>
                <div class="card-body">
                    <form action="<%= request.getContextPath()%>/mascotas" method="post">
                        <input type="hidden" name="accion" value="insertar">
                        <div class="mb-3">
                            <label class="form-label">Nombre</label>
                            <input type="text" name="nombre" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Especie</label>
                            <input type="text" name="especie" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Edad</label>
                            <input type="number" name="edad" class="form-control" min="0" required>
                        </div>
                        <button type="submit" class="btn btn-success">
                            <i class="bi bi-save"></i> Guardar Mascota
                        </button>
                    </form>
                </div>
            </div>

            <!-- EXPORTAR Y ENVIAR -->
            <div class="d-flex justify-content-between mb-4">
                <form action="<%= request.getContextPath()%>/reporteMascotas" method="get" target="_blank">
                    <button type="submit" class="btn btn-outline-primary">
                        <i class="bi bi-file-earmark-pdf"></i> Exportar PDF
                    </button>
                </form>

                <form action="<%= request.getContextPath()%>/enviarEmail" method="post" class="d-flex">
                    <input type="email" name="correo" class="form-control me-2" placeholder="Correo destino" required>
                    <button type="submit" class="btn btn-outline-dark">
                        <i class="bi bi-envelope"></i> Enviar Reporte
                    </button>
                </form>
            </div>

            <!-- Botón Volver general al final -->
            <div class="mb-5">
                <a href="<%= request.getContextPath()%>/index.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Volver al inicio
                </a>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
