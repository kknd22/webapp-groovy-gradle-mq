
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
<%--    <link href="/webapp-groovy-gradle-mq/basic.css" rel="stylesheet"></head>--%>
</head>

<body>

	<h1>Enter MQ run configuration data</h1>
    <form:form method="POST" commandName="confData" action="conf.html">
    <table>
        <tbody>
        <tr>
            <td><form:label path="concurrenceSize">Number of concurrent mq clients:</form:label></td>
            <td><form:input path="concurrenceSize"></form:input></td>
        </tr>
        <tr>
            <td><form:label path="interval">Simulated delay time between firing of each mq client:</form:label></td>
            <td><form:input path="interval"></form:input></td>
            <td>(in milli seconds)</td>
        </tr>
        <tr>
            <td><form:label path="resopnseWaitTime">Similated response wait time:</form:label></td>
            <td><form:input path="resopnseWaitTime"></form:input></td>
            <td>(in milli seconds)</td>
        </tr>

         <tr>
            <td>
                check log on server for progress
            </td>
            <td colspan="2">
                <strong><%= System.getProperty("LOG_DIR")%>jboss-spring-mq.log </strong>
            </td>
        </tr>
        <tr>
           <td colspan="3">
               <input type="submit" value="Run">
           </td>
<%--
        <c:if test="${not empty confData.interval}">
        <tr>
           <td colspan="2">
               current interval is ${confData.interval}, concurrenceSize is ${confData.concurrenceSize}
           </td>
           <td></td>
           <td></td>
       </tr>
        </c:if>
--%>
        </tbody></table>
    </form:form>


</body>
</html>