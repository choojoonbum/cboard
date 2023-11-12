<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <script src="/resources/vendor/jquery/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $("form").submit();
        });
    </script>
</head>
<body>
<form role="form" method="post" action="/customLogout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>
