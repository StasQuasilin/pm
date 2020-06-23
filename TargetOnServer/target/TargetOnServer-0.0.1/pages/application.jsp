<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 13.06.2020
  Time: 15:56
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Targeton</title>
    <link rel="icon" href="${context}/images/icon.svg">
    <link rel="stylesheet" href="${context}/css/main.css">
    <link rel="stylesheet" href="${context}/css/application.css">
    <script src="${context}/external/jquery.min.js"></script>
    <script src="${context}/external/vue.js"></script>
    <script src="${context}/js/constants.js"></script>
    <script src="${context}/js/connection.js"></script>
    <script src="${context}/js/subscriber.js"></script>
    <script src="${context}/js/application.js"></script>
    <script src="${context}/vue/list.vue"></script>

    <script>
        SUBSCRIBE_API = '${subscribe}'
        user = 1;
        if (typeof context === 'undefined'){
            context = '${context}'
        }
        subscriber.connect();
    </script>

</head>
<body>
    <div id="modalLayer" class="modal-layer"></div>
    <div class="body-wrapper">
        <table border="1" style="height: 100%;">
            <tr>
                <td rowspan="2" style="vertical-align: top">
                    <jsp:include page="navigation/navigationMenu.jsp"/>
                </td>
                <td id="titleHolder">
                    Title
                </td>
                <td style="width: 20%">
                    User data
                </td>
            </tr>
            <tr>
                <td id="contentHolder" colspan="2" style="width: 100%; height: 100%" >
                    Content
                </td>
            </tr>
            <tr>
                <td colspan="3" style="text-align: center">
                    2019
                    <c:if test="${year ne 2019}">
                        - ${year}
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
