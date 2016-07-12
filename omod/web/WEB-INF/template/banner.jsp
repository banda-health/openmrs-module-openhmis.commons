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
<div id="banner">
	<a href="<spring:theme code=" url.organization" />">
	<div id="logosmall"><img src="<%= request.getContextPath() %><spring:theme code=" image.logo.text.small" />" alt="OpenMRS
		Logo" border="0"/>
	</div>
	</a>
	<table id="bannerbar">
		<tr>
			<td id="logocell"><img src="<%= request.getContextPath() %><spring:theme code=" image.logo.small" />" alt=""
				class="logo-reduced61" />
			</td>
			<td id="barcell">
				<div class="barsmall">
					<img align="left" src="<%= request.getContextPath() %><spring:theme code=" image.logo.bar" />" alt=""
					class="bar-round-reduced50"/>
					<openmrs:hasPrivilege privilege="View Navigation Menu">
						<%@ include file="/WEB-INF/template/gutter.jsp" %>
					</openmrs:hasPrivilege>
				</div>
			</td>
		</tr>
	</table>
</div>
