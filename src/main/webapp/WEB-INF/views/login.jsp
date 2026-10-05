<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Iniciar sesión</h1>
    <c:if test="${not empty errorLogin}">
        <p class="error">${errorLogin}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/app">
        <input type="hidden" name="comando" value="login">
        <label>Usuario:
            <input type="text" name="username" required>
        </label>
        <label>Contraseña:
            <input type="password" name="clave" required>
        </label>
        <button type="submit">Ingresar</button>
    </form>
</body>
</html>
