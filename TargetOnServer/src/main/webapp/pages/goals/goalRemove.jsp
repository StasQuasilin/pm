<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 30.07.20
  Time: 10:08
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/remover.vue?v=${now}"></script>
<script>
    remover.api.remove = '${remove}';
    remover.object = ${goal.id};
</script>
<table id="remover">
    <tr>
        <td>
            <fmt:message key="deal.remove.sure"/> "${goal.title}"?
        </td>
    </tr>
    <c:if test="${not empty statistic}">
        <tr>
            <td>
                <fmt:message key="task.done"/>: <fmt:formatNumber value="${statistic.doneChildren}"/>
            </td>
        </tr>
    </c:if>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="remove()">
                <fmt:message key="button.remove"/>
            </button>
        </td>
    </tr>
</table>

