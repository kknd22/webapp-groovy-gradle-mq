
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>

	<h1>Enter time interval</h1>
    <form:form method="POST" commandName="confData" action="conf.html">
    <table>
        <tbody>
        <tr>
            <td><form:label path="interval">interval:</form:label></td>
            <td><form:input path="interval"></form:input></td>
        </tr>
        <tr>
            <td><form:label path="concurrenceSize">concurrence Size:</form:label></td>
            <td><form:input path="concurrenceSize"></form:input></td>
        </tr>

         <tr>
            <td colspan="2">
                <input type="submit" value="Submit">
            </td>
            <td></td>
            <td></td>
        </tr>

        <c:if test="${not empty confData.interval}">
        <tr>
           <td colspan="2">
               current interval is ${confData.interval}, concurrenceSize is ${confData.concurrenceSize}
           </td>
           <td></td>
           <td></td>
       </tr>
        </c:if>
    </tbody></table>
    </form:form>


</body>
</html>