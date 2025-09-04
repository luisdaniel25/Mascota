<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Mascotas - SENA</title>
        <style>
            /* ===== Estilos Globales ===== */
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background: #f2f4f8;
                margin: 0;
                padding: 0;
            }

            /* ===== Header ===== */
            header {
                background: linear-gradient(90deg, #004aad, #0066cc);
                color: white;
                padding: 25px 20px;
                text-align: center;
                box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            }
            header h1 {
                margin: 0;
                font-size: 30px;
                font-weight: 600;
            }
            header p {
                margin: 5px 0 0;
                font-size: 14px;
                font-style: italic;
                opacity: 0.9;
            }

            /* ===== Main ===== */
            main {
                display: flex;
                justify-content: center;
                margin: 60px 0;
            }

            /* ===== Tarjeta del menú ===== */
            .menu {
                background: white;
                border-radius: 12px;
                box-shadow: 0 8px 20px rgba(0,0,0,0.12);
                padding: 50px 30px;
                width: 420px;
                text-align: center;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .menu:hover {
                transform: translateY(-5px);
                box-shadow: 0 12px 25px rgba(0,0,0,0.2);
            }

            .menu h2 {
                margin-bottom: 35px;
                color: #004aad;
                font-size: 24px;
            }


            .menu a {
                display: block;
                margin: 15px 0;
                padding: 14px 0;
                background: #004aad;
                color: white;
                text-decoration: none;
                border-radius: 8px;
                font-weight: 500;
                font-size: 16px;
                transition: all 0.3s ease;
            }
            .menu a:hover {
                background: #0066cc;
                transform: translateY(-2px);
                box-shadow: 0 6px 12px rgba(0,0,0,0.15);
            }


            footer {
                text-align: center;
                font-size: 12px;
                color: #666;
                padding: 15px 0;
                border-top: 1px solid #ddd;
                margin-top: 40px;
            }

        </style>
    </head>
    <body>
        <!-- Encabezado corporativo estilo SENA -->
        <header>
            <h1>Sistema de Gestión de Mascotas</h1>
            <p>Solución empresarial para control y seguimiento</p>
        </header>

        <!-- Menú principal -->
        <main>
            <div class="menu">
                <h2>Menú Principal</h2>

                <!-- CRUD de Mascotas -->
                <a href="<%= request.getContextPath()%>/mascotas">CRUD Mascotas</a>

                <!-- Historial de Trazabilidad -->
                <a href="<%= request.getContextPath()%>/trazabilidad">Historial de Acciones</a>

                <!-- Futuro envío de reportes por correo -->
                <!-- <a href="<%= request.getContextPath()%>/email.jsp">📧 Enviar Reporte por Correo</a> -->
            </div>
        </main>

        <footer>
            &copy; 2025 Sistema de Gestión de Mascotas - SENA
        </footer>
    </body>
</html>
