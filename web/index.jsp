<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Mascotas</title>
        <style>
            /* ===== Estilos Globales ===== */
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f5f7fa;
                margin: 0;
                padding: 0;
            }
            header {
                background: linear-gradient(90deg, #2c3e50, #34495e);
                color: white;
                padding: 20px;
                text-align: center;
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
            header h1 {
                margin: 0;
                font-size: 28px;
            }
            header p {
                margin: 5px 0 0;
                font-size: 14px;
                font-style: italic;
                opacity: 0.9;
            }
            main {
                display: flex;
                justify-content: center;
                margin-top: 60px;
            }
            .menu {
                background: white;
                border-radius: 12px;
                box-shadow: 0 6px 12px rgba(0,0,0,0.15);
                padding: 40px;
                width: 380px;
                text-align: center;
            }
            .menu h2 {
                margin-bottom: 30px;
                color: #2c3e50;
                font-size: 22px;
            }
            .menu a {
                display: block;
                margin: 15px 0;
                padding: 14px;
                background: #3498db;
                color: white;
                text-decoration: none;
                border-radius: 8px;
                font-weight: 500;
                transition: all 0.3s ease;
            }
            .menu a:hover {
                background: #2980b9;
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
        </style>
    </head>
    <body>
        <!-- Encabezado corporativo -->
        <header>
            <h1>Sistema de Gestión de Mascotas</h1>
            <p>Solución empresarial para el control y seguimiento</p>
        </header>

        <!-- Menú principal -->
        <main>
            <div class="menu">
                <h2>Menú Principal</h2>

                <!-- CRUD de Mascotas -->
                <a href="<%= request.getContextPath()%>/mascotas"> CRUD Mascotas</a>

                <!-- Historial de Trazabilidad -->
                <a href="<%= request.getContextPath()%>/trazabilidad"> Historial de Acciones</a>

                <!-- Enlace futuro: envío de reportes por correo -->
                <!-- <a href="<%= request.getContextPath()%>/email.jsp">📧 Enviar Reporte por Correo</a> -->
            </div>
        </main>
    </body>
</html>
