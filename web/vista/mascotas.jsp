<%@page import="java.util.List"%>
<%@page import="modelo.Mascota"%>
<%@page import="dao.MascotaDAO"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Mascotas - SENA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body {
                background-color: #e9f0ea; /* Verde suave SENA */
            }
            .navbar {
                background-color: #00743F; /* Verde oscuro SENA */
            }
            .navbar-brand {
                font-weight: 700;
                font-size: 1.3rem;
            }
            .card-header {
                background-color: #00743F;
                color: white;
                font-weight: 600;
            }
            .table thead {
                background-color: #00a651; /* Verde SENA claro */
                color: white;
            }
            .table-hover tbody tr:hover {
                background-color: #d4f0d0;
            }
            .btn-sena {
                background-color: #00743F;
                color: #fff;
                border: none;
            }
            .btn-sena:hover {
                background-color: #00582b;
            }
            .btn-outline-sena {
                border-color: #00743F;
                color: #00743F;
            }
            .btn-outline-sena:hover {
                background-color: #00743F;
                color: #fff;
            }
            .pagination {
                justify-content: center;
            }
        </style>
    </head>
    <body>

        <%

            // Obtener lista de mascotas desde el request
            List< Mascota> lista = (List<Mascota>) request.getAttribute("lista");

            // Si no existe, obtener todas desde la base de datos
            if (lista == null) {
                MascotaDAO dao = new MascotaDAO(); // Crear DAO
                lista = dao.listar(); // Listar todas las mascotas
            }

            // Configuración de paginación
            int limite = 10; // Registros por página
            int pagina = 1;  // Página actual

            // Obtener página desde parámetro URL
            String paramPagina = request.getParameter("pagina");
            if (paramPagina != null) {
                try {
                    pagina = Integer.parseInt(paramPagina); // Convertir a entero
                } catch (NumberFormatException e) {
                    pagina = 1; // Default si error
                }
            }

            // Calcular total de registros y páginas
            int totalRegistros = lista.size();
            int totalPaginas = (int) Math.ceil((double) totalRegistros / limite);

            // Determinar sublista para la página actual
            int desde = (pagina - 1) * limite;
            int hasta = Math.min(desde + limite, totalRegistros);
            List<Mascota> listaPagina = lista.subList(desde, hasta); // Lista visible


        %>

        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-dark shadow-sm mb-4">
            <div class="container-fluid">
                <a class="navbar-brand d-flex align-items-center" href="#">
                    <i class="bi bi-paw me-2"></i> Gestión de Mascotas 
                </a>
            </div>
        </nav>

        <div class="container">

            <!-- Lista de Mascotas -->
            <div class="card shadow mb-4">
                <div class="card-header">
                    <h5 class="mb-0"><i class="bi bi-list-ul me-1"></i> Mascotas Registradas</h5>
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered align-middle text-center mb-0">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Especie</th>
                                    <th>Edad</th>
                                    <th colspan="3">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%    // Verificar si hay mascotas en la sublista de la página actual
                                    if (listaPagina != null && !listaPagina.isEmpty()) {
                                        // Recorrer cada mascota para mostrarla en la tabla
                                        for (Mascota m : listaPagina) {
                                %>

                                <tr>
                            <form action="<%=request.getContextPath()%>/mascotas" method="post">
                                <input type="hidden" name="accion" value="actualizar">
                                <td><input type="hidden" name="id" value="<%=m.getId()%>"><%=m.getId()%></td>
                                <td><input type="text" name="nombre" value="<%=m.getNombre()%>" class="form-control" required></td>
                                <td><input type="text" name="especie" value="<%=m.getEspecie()%>" class="form-control" required></td>
                                <td><input type="number" name="edad" value="<%=m.getEdad()%>" class="form-control" min="0" required></td>
                                <td>
                                    <button type="submit" class="btn btn-sm btn-sena">
                                        <i class="bi bi-pencil-square me-1"></i> Actualizar
                                    </button>
                                </td>
                            </form>
                            <td>
                                <form action="<%=request.getContextPath()%>/mascotas" method="post">
                                    <input type="hidden" name="accion" value="eliminar">
                                    <input type="hidden" name="id" value="<%=m.getId()%>">
                                    <button type="submit" class="btn btn-sm btn-danger"
                                            onclick="return confirm('¿Seguro que deseas eliminar esta mascota?')">
                                        <i class="bi bi-trash me-1"></i> Eliminar
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form action="<%=request.getContextPath()%>/mascotas" method="post">
                                    <input type="hidden" name="accion" value="certificado">
                                    <input type="hidden" name="id" value="<%=m.getId()%>">
                                    <button type="submit" class="btn btn-sm btn-outline-sena">
                                        <i class="bi bi-file-earmark-text me-1"></i> Certificado
                                    </button>
                                </form>
                            </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td colspan="7" class="text-muted">No hay mascotas registradas</td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Paginación -->
            <nav>
                <ul class="pagination">
                    <%
                        for (int i = 1; i <= totalPaginas; i++) {
                            String active = i == pagina ? "active" : "";
                    %>
                    <li class="page-item <%=active%>">
                        <a class="page-link" href="?pagina=<%=i%>"><%=i%></a>
                    </li>
                    <% }%>
                </ul>
            </nav>

            <!-- Formulario Nueva Mascota -->
            <div class="card shadow mb-4">
                <div class="card-header bg-success text-white">
                    <h5 class="mb-0"><i class="bi bi-plus-circle me-1"></i> Registrar Nueva Mascota</h5>
                </div>
                <div class="card-body">
                    <form action="<%= request.getContextPath()%>/mascotas" method="post">
                        <input type="hidden" name="accion" value="insertar">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <label class="form-label">Nombre</label>
                                <input type="text" name="nombre" class="form-control" required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Especie</label>
                                <input type="text" name="especie" class="form-control" required>
                            </div>
                            <div class="col-md-4">
                                <label class="form-label">Edad</label>
                                <input type="number" name="edad" class="form-control" min="0" required>
                            </div>
                        </div>
                        <div class="mt-3">
                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-save me-1"></i> Guardar Mascota
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Exportar y Enviar -->
            <div class="d-flex flex-wrap gap-2 justify-content-between mb-4">
                <form action="<%= request.getContextPath()%>/reporteMascotas" method="get" target="_blank">
                    <button type="submit" class="btn btn-outline-sena">
                        <i class="bi bi-file-earmark-pdf me-1"></i> Exportar PDF
                    </button>
                </form>
                <form action="<%= request.getContextPath()%>/enviarEmail" method="post" class="d-flex gap-2">
                    <input type="email" name="correo" class="form-control" placeholder="Correo destino" required>
                    <button type="submit" class="btn btn-sena">
                        <i class="bi bi-envelope me-1"></i> Enviar Reporte
                    </button>
                </form>
            </div>

            <!-- Botón Volver -->
            <div class="mb-5">
                <a href="<%= request.getContextPath()%>/index.jsp" class="btn btn-secondary">
                    <i class="bi bi-arrow-left-circle me-1"></i> Volver al inicio
                </a>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
