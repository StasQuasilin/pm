<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 15.07.20
  Time: 10:23
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/timePicker.css?v=${now}">
<script src="${context}/vue/templates/datetime/datePicker.vue?v=${now}"></script>
<script src="${context}/vue/templates/datetime/timePicker.vue?v=${now}"></script>
<script src="${context}/vue/templates/inputWithSearch.vue?v=${now}"></script>
<script src="${context}/vue/calendarEdit.vue?v=${now}"></script>
<script>
    calendarEdit.api.save = '${save}';
    calendarEdit.props.findCategory = '${findCategory}';
    <c:forEach items="${repeats}" var="r">
    calendarEdit.repeats.push('${r}');
    calendarEdit.repeatNames['${r}'] = '<fmt:message key="repeat.${r}"/>';
    </c:forEach>
    <c:forEach begin="0" end="6" var="d">
    calendarEdit.dayNames[${d}] = '<fmt:message key="day.${d}.short"/>';
    </c:forEach>
    <c:if test="${not empty item}">
    calendarEdit.calendarItem = ${item.toJson()};
    calendarEdit.calendarItem.header = {
        id:calendarEdit.calendarItem.header,
        title:calendarEdit.calendarItem.title
    };
    </c:if>
    calendarEdit.calendarItem.weekDays = [];
    <c:forEach items="${weekDays.days}" var="d">
    calendarEdit.calendarItem.weekDays.push({
        v:${d}
    });
    </c:forEach>

    now = new Date();
    if (calendarEdit.calendarItem.date){
        calendarEdit.useDate = true;
        calendarEdit.useTime = !!calendarEdit.calendarItem.time;
    } else {
        calendarEdit.useDate = false;
        calendarEdit.useTime = false;
        calendarEdit.calendarItem.date = new Date().toISOString().substring(0, 10);
    }
    if(!calendarEdit.useTime){
        calendarEdit.calendarItem.time = new Date().toLocaleTimeString();
    }
</script>
<table id="calendarEdit">
    <tr>
        <td colspan="2">
            <fmt:message key="calendar.title"/>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input-search :object="calendarItem.header" :props="props"></input-search>
        </td>
    </tr>
    <tr v-if="!useDate">
        <td colspan="2" style="text-align: right">
            <span class="text-button" v-on:click="useDate = true">
                <fmt:message key="task.date.add"/>
            </span>
        </td>
    </tr>
    <template v-else>
        <tr>
            <td>
                <fmt:message key="task.date"/>
            </td>
            <td>
                <date-picker :date="calendarItem.date" :props="dateProps"></date-picker>
                <span class="text-button" v-on:click="useDate = false">
                    &times;
                </span>
            </td>
        </tr>
        <tr v-if="!useTime">
            <td colspan="2" style="text-align: right">
                <span class="text-button" v-on:click="useTime = true">
                    <fmt:message key="task.time.add"/>
                </span>
            </td>
        </tr>
        <tr v-else>
            <td>
                <fmt:message key="task.time"/>
            </td>
            <td>
                <time-picker :time="calendarItem.time" :props="timeProps"></time-picker>
                <span class="text-button" v-on:click="useTime = false">
                    &times;
                </span>
            </td>
        </tr>
    </template>
    <tr>
        <td>
            <fmt:message key="task.length"/>
        </td>
        <td>
            {{calendarItem.length}}
        </td>
    </tr>
    <tr>
        <td>
            <label for="repeat">
                <fmt:message key="task.repeat"/>
            </label>
        </td>
        <td>
            <select id="repeat" v-model="calendarItem.repeat">
                <option v-for="r in repeats" :value="r">
                    {{repeatNames[r]}}
                </option>
            </select>
        </td>
    </tr>
    <tr v-if="calendarItem.repeat === 'day'">
        <td colspan="2" style="font-size: 10pt">
            <template v-for="(wd, wIdx) in calendarItem.weekDays">
                <input :id="'w' + wIdx" type="checkbox" v-model="wd.v">
                {{dayNames[wIdx]}}
            </template>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>
