<%--
~ The contents of this file are subject to the OpenMRS Public License
~ Version 2.0 (the "License"); you may not use this file except in
~ compliance with the License. You may obtain a copy of the License at
~ http://license.openmrs.org
~
~ Software distributed under the License is distributed on an "AS IS"
~ basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
~ License for the specific language governing rights and limitations
~ under the License.
~
~ Copyright (C) OpenMRS, LLC.  All Rights Reserved.
--%>
<br/>
</div>
</div>

<div id="footer">
	<div id="footerInner">

		<openmrs:extensionPoint pointId="org.openmrs.footerFullBeforeStatusBar" type="html"/>
		
			<span id="localeOptions">
				<% //removes last instance of lang= from querystring and encodes the url to avoid xml problems
					String qs = org.apache.commons.lang.StringEscapeUtils.escapeXml(request.getQueryString());
					if (qs == null) {
						qs = "";
					}
					int i = qs.lastIndexOf("&amp;lang=");
					if (i == -1) {
						i = qs.length();
					}
					pageContext.setAttribute("qs", qs.substring(0, i));
					pageContext.setAttribute("locales",
							org.openmrs.api.context.Context.getAdministrationService().getPresentationLocales());
					pageContext.setAttribute("openmrsVersion", org.openmrs.util.OpenmrsConstants.OPENMRS_VERSION);
					pageContext.setAttribute("locale", org.openmrs.api.context.Context.getLocale());
				%>
		
				<c:forEach items="${locales}" var="loc" varStatus="status">
					<%
						java.util.Locale locTmp = (java.util.Locale)pageContext.getAttribute("loc");
						pageContext.setAttribute("locDisplayName", locTmp.getDisplayName(locTmp));
					%>
					<c:if test="${status.index != 0}">| </c:if>
					<c:if test="${fn:toLowerCase(locale) == fn:toLowerCase(loc)}">${locDisplayName}</c:if>
					<c:if test="${fn:toLowerCase(locale) != fn:toLowerCase(loc)}"><a
							href="?${qs}&amp;lang=${loc}">${locDisplayName}</a></c:if>
				</c:forEach>
			</span>

		<span id="buildDate"><openmrs:message code="footer.lastBuild"/>: <%= org.openmrs.web.WebConstants.BUILD_TIMESTAMP %></span>

		<span id="codeVersion"><openmrs:message code="footer.version"/>: ${openmrsVersion}</span>

		<span id="poweredBy"><a href="http://openmrs.org"><openmrs:message code="footer.poweredBy"/> <img border="0"
		                                                                                                  align="top"
		                                                                                                  src="<%= request.getContextPath() %>/images/openmrs_logo_tiny.png"/></a></span>
	</div>
</div>

</body>
</html>
