<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:27
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<script type="application/javascript" src="${context}/vue/finances/transactions/transactionsList.vue"></script>
<script type="application/javascript" src="${context}/vue/finances/fastTransactions.vue"></script>
<script type="application/javascript" src="${context}/vue/finances/accounts.vue"></script>
<script type="application/javascript" src="${context}/vue/finances/buyList.vue"></script>
<script type="application/javascript">
  transactionsList.api.edit = '${transactionEdit}';
  fastTransactions.api.edit = '${fastTransactionEdit}';
  accounts.api.edit = '${accountEdit}';
  buyList.api.edit = '${buyListEdit}';
  <c:forEach items="${units}" var="unit">
  buyList.unitNames['${unit}'] = '<fmt:message key="unit.${unit}"/>';
  </c:forEach>
  subscriber.subscribe('${transactionSubscribe}', transactionsList.handler);
  subscriber.subscribe('${accountSubscribe}', accounts.handler);
  subscriber.subscribe('${buyListSubscribe}', buyList.handler);
</script>
<table class="full-size" border="1">
  <tr>
    <td rowspan="2" style="width: 40%">
      <jsp:include page="transactions.jsp"/>
    </td>
    <td style="width: 30%">
      <jsp:include page="fastTransactions.jsp"/>
    </td>
    <td rowspan="2" style="width: 30%;">
      <jsp:include page="buyList/buyList.jsp"/>
    </td>
  </tr>
  <tr>
    <td>
      <jsp:include page="accounts.jsp"/>
    </td>
  </tr>
</table>
