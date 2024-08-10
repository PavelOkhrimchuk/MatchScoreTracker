<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="error-container">
    <h1>Oops! Something Went Wrong</h1>
    <p>${errorMessage}</p>
    <p><a href="${pageContext.request.contextPath}/">Return to Home</a></p>
</div>
</body>
</html>
