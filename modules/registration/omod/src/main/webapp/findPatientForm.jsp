<!-- Header & Include -->
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2>Hello register/find patient</h2>


        <div class="box">
            <spring:message code="Person.search.instructions"/> <br/>
            
            <form method="post" action="${model.postURL}">
                
                <table>
                    <tr>
                        <td><spring:message code="Person.name"/></td>
                        <td>
                            <input type="text" name="addName" id="personName" size="40" onKeyUp="hideError('nameError'); hideError('invalidNameError');" />
                            <span class="error" id="nameError"><spring:message code="Person.name.required"/></span>
                            <span class="error" id="invalidNameError"><spring:message code="Person.name.invalid"/></span>
                        </td>
                    </tr>
                    <tr>
                        <td><spring:message code="Person.birthdate"/><br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="general.format"/>: <openmrs:datePattern />)</i></td>
                        <td valign="top">
                            <input type="text" name="addBirthdate" id="birthdate" size="11" value="" onfocus="showCalendar(this,60)" onChange="hideError('birthdateError')" />
                            <spring:message code="Person.age.or"/>
                            <input type="text" name="addAge" id="age" size="5" value="" onKeyUp="hideError('birthdateError')" />
                            <span class="error" id="birthdateError"><spring:message code="Person.birthdate.required"/></span>
                        </td>
                    </tr>
                    <tr>
                        <td><spring:message code="Person.gender"/></td>
                        <td>
                            <openmrs:forEachRecord name="gender">
                                <input type="radio" name="addGender" id="gender-${record.key}" value="${record.key}"  onClick="hideError('genderError')" /><label for="gender-${record.key}"> <spring:message code="Person.gender.${record.value}"/> </label>
                            </openmrs:forEachRecord>
                            <span class="error" id="genderError"><spring:message code="Person.gender.required"/></span>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value='<spring:message code="Person.create"/>'/>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="personType" value="${model.personType}"/>
                <input type="hidden" name="viewType" value="${model.viewType}"/>
            </form>
        </div>