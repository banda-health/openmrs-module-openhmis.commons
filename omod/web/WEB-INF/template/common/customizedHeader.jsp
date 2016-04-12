
<%-- The header section is slightly different from the origin 1.x header file.
     It has been designed to be consistent with other 2.x header pages.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.openmrs.web.WebConstants" %>
<%
	pageContext.setAttribute("msg", session.getAttribute(WebConstants.OPENMRS_MSG_ATTR));
	pageContext.setAttribute("msgArgs", session.getAttribute(WebConstants.OPENMRS_MSG_ARGS));
	pageContext.setAttribute("err", session.getAttribute(WebConstants.OPENMRS_ERROR_ATTR));
	pageContext.setAttribute("errArgs", session.getAttribute(WebConstants.OPENMRS_ERROR_ARGS));
	session.removeAttribute(WebConstants.OPENMRS_MSG_ATTR);
	session.removeAttribute(WebConstants.OPENMRS_MSG_ARGS);
	session.removeAttribute(WebConstants.OPENMRS_ERROR_ATTR);
	session.removeAttribute(WebConstants.OPENMRS_ERROR_ARGS);
%>

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:spring="http://www.springframework.org/tags"
      xmlns:openmrs="urn:jsptld:/WEB-INF/taglibs/openmrs.tld">
<head>
	<openmrs:htmlInclude file="/openmrs.js" />
	<openmrs:htmlInclude file="/scripts/openmrsmessages.js" appendLocale="true" />
	<openmrs:htmlInclude file="/openmrs.css" />
	<link href="<openmrs:contextPath/><spring:theme code='stylesheet' />" type="text/css" rel="stylesheet" />
	<openmrs:htmlInclude file="/style.css" />
	<openmrs:htmlInclude file="/dwr/engine.js" />
	<openmrs:htmlInclude file="/scripts/html-sanitizer-min.js" />
	<openmrs:htmlInclude file="/dwr/interface/DWRAlertService.js" />
	<c:if test="${empty DO_NOT_INCLUDE_JQUERY}">
		<openmrs:htmlInclude file="/scripts/jquery/jquery.min.js" />
		<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
		<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-timepicker-addon.js" />
		<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js" />
		<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-timepicker-i18n.js" />
		<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
	</c:if>
	<link rel="shortcut icon" type="image/ico" href="<openmrs:contextPath/><spring:theme code='favicon' />">
	<link rel="icon" type="image/png" href="<openmrs:contextPath/><spring:theme code='favicon.png' />">
	<script type="text/javascript" src="${pageContext.request.contextPath}/ms/uiframework/resource/uicommons/scripts/knockout-2.1.0.js"></script>

	<c:choose>
		<c:when test="${!empty pageTitle}">
			<title>${pageTitle}</title>
		</c:when>
		<c:otherwise>
			<title><openmrs:message code="openmrs.title"/></title>
		</c:otherwise>
	</c:choose>


	<script type="text/javascript">
		<c:if test="${empty DO_NOT_INCLUDE_JQUERY}">
		var $j = jQuery.noConflict();
		</c:if>
		/* variable used in js to know the context path */
		var openmrsContextPath = '${pageContext.request.contextPath}';
		var dwrLoadingMessage = '<openmrs:message code="general.loading" />';
		var jsDateFormat = '<openmrs:datePattern localize="false"/>';
		var jsTimeFormat = '<openmrs:timePattern format="jquery" localize="false"/>';
		var jsLocale = '<%= org.openmrs.api.context.Context.getLocale() %>';
		/* prevents users getting false dwr errors msgs when leaving pages */
		var pageIsExiting = false;
		if (typeof(jQuery) != "undefined")
			jQuery(window).bind('beforeunload', function () { pageIsExiting = true; } );
		var handler = function(msg, ex) {
			if (!pageIsExiting) {
				var div = document.getElementById("openmrs_dwr_error");
				div.style.display = ""; // show the error div
				var msgDiv = document.getElementById("openmrs_dwr_error_msg");
				msgDiv.innerHTML = '<openmrs:message code="error.dwr"/>' + " <b>" + msg + "</b>";
			}
		};
		dwr.engine.setErrorHandler(handler);
		dwr.engine.setWarningHandler(handler);
	</script>

	<openmrs:extensionPoint pointId="org.openmrs.headerFullIncludeExt" type="html" requiredClass="org.openmrs.module.web.extension.HeaderIncludeExt">
		<c:forEach var="file" items="${extension.headerFiles}">
			<openmrs:htmlInclude file="${file}" />
		</c:forEach>
	</openmrs:extensionPoint>

	<script type="text/javascript">
		var sessionLocationModel = {
			id: ko.observable(),
			text: ko.observable()
		};
		jQuery(function () {
			ko.applyBindings(sessionLocationModel, jQuery('.change-location').get(0));
			sessionLocationModel.id(${ sessionLocationId });
			sessionLocationModel.text("${ sessionLocationName }");

			jQuery(".change-location a").click(function () {
				jQuery('#session-location').show();
				jQuery(this).addClass('focus');
				jQuery(".change-location a i:nth-child(3)").removeClass("glyphicon glyphicon-menu-down");
				jQuery(".change-location a i:nth-child(3)").addClass("glyphicon glyphicon-menu-up");
			});
			jQuery('#session-location').mouseleave(function () {
				jQuery('#session-location').hide();
				jQuery(".change-location a").removeClass('focus');
				jQuery(".change-location a i:nth-child(3)").removeClass("glyphicon glyphicon-menu-down");
				jQuery(".change-location a i:nth-child(3)").addClass("glyphicon glyphicon-menu-up");
			});
			jQuery("#session-location ul.select li").click(function (event) {
				var element = jQuery(event.target);
				var locationId = element.attr("locationId");
				var locationName = element.attr("locationName");

				jQuery.post("${pageContext.request.contextPath}/appui/session/setLocation.action?locationId="+locationId, function (data) {
					sessionLocationModel.id(locationId);
					sessionLocationModel.text(locationName);
					jQuery('#session-location li').removeClass('selected');
					element.addClass('selected');
					jQuery(document).trigger("sessionLocationChanged");
				});

				jQuery('#session-location').hide();
				jQuery(".change-location a").removeClass('focus');
				jQuery(".change-location a i:nth-child(3)").removeClass("glyphicon glyphicon-menu-down");
				jQuery(".change-location a i:nth-child(3)").addClass("glyphicon glyphicon-menu-up");
			});
		});
	</script>

</head>
<body id="body">
<div>
	<header>
		<div class="logo">
			<a href="${pageContext.request.contextPath}/referenceapplication/home.page">
				<img src="${pageContext.request.contextPath}/ms/uiframework/resource/uicommons/images/logo/openmrs-with-title-small.png">
			</a>
		</div>
		<openmrs:authentication>
			<c:if test="${authenticatedUser != null}">
				<ul class="user-options">
					<i class="glyphicon glyphicon-user small"></i>
					<li><span id="userLoggedInAs" class="firstChild">
                        <c:choose>
							<c:when test="${authenticatedUser.username} == null">
								<c:out value="${authenticatedUser.systemId}" />
							</c:when>
							<c:otherwise>
								<c:out value="${authenticatedUser.username}" />
							</c:otherwise>
						</c:choose>

					</span></li>
					<li class="change-location">
						<a href="javascript:void(0);">
							<i class="glyphicon glyphicon-map-marker small"></i>
							<span data-bind="text: text"></span>
							<c:if test="${multipleLoginLocations}">
								<i class="glyphicon glyphicon-menu-down link"></i>
							</c:if>
						</a>
					</li>

					<li><span id="userLogout">
						<a href='${pageContext.request.contextPath}/logout'><openmrs:message code="header.logout" /></a>
					</span></li>
					<i class="glyphicon glyphicon-log-out small"></i>
				</ul>
			</c:if>
			<c:if test="${authenticatedUser == null}">
				<ul class="user-options">
					<li><span id="userLoggedOut" class="firstChild">
						<openmrs:message code="header.logged.out"/>
					</span></li>
					<li><span id="userLogIn">
						<a href='${pageContext.request.contextPath}/login.htm'><openmrs:message code="header.login"/></a>
					</span></li>
				</ul>
			</c:if>
		</openmrs:authentication>
		<div id="session-location">
			<ul class="select">
				<c:forEach var="location" items="${loginLocations}">
					<%!
						String selected = "";
					%>
					<c:if test="${location.id == sessionLocationId}">
						<%
							selected = "selected";
						%>
					</c:if>
					<li class="${selected}" locationId="${location.id}" locationName="${location.name}">
						${location.name}
					</li>
				</c:forEach>
			</ul>
		</div>
	</header>

	<%-- This is where the My Patients popup used to be. I'm leaving this placeholder here
		 as a reminder of where to put back an extension point when I've figured out what it should
		 look like. -DJ
	 <div id="popupTray">
	 </div>
	 --%>

	<div id="cont">
		<openmrs:forEachAlert>
		<c:if test="${varStatus.first}"><div id="alertOuterBox"></c:if>
		<c:if test="${varStatus.last}">
			<div id="alertBar">
				<img src="${pageContext.request.contextPath}/images/alert.gif" align="center" alt='<openmrs:message htmlEscape="false" code="Alert.unreadAlert"/>' title='<openmrs:message htmlEscape="false" code="Alert.unreadAlert"/>'/>
				<c:if test="${varStatus.count == 1}"><openmrs:message htmlEscape="false" code="Alert.unreadAlert"/></c:if>
				<c:if test="${varStatus.count != 1}"><openmrs:message htmlEscape="false" code="Alert.unreadAlerts" arguments="${varStatus.count}" /></c:if>
				<c:if test="${alert.satisfiedByAny}"><i class="smallMessage">(<openmrs:message code="Alert.mark.satisfiedByAny"/>)</i></c:if>
				<a href="#markAllAsRead" onclick="return markAllAlertsRead(this)" HIDEFOCUS class="markAllAsRead" >
					<img src="${pageContext.request.contextPath}/images/markRead.gif" alt='<openmrs:message code="Alert.markAllAsRead"/>' title='<openmrs:message code="Alert.markAllAlertsAsRead"/>' /> <span class="markAllAsRead"><openmrs:message code="Alert.markAllAsRead"/></span>
				</a>
			</div>
		</c:if>
		</openmrs:forEachAlert>
		<openmrs:forEachAlert>
		<c:if test="${varStatus.first}"><div id="alertInnerBox"></c:if>
			<div class="alert">
				<a href="#markRead" onClick="return markAlertRead(this, '${alert.alertId}')" HIDEFOCUS class="markAlertRead">
					<img src="${pageContext.request.contextPath}/images/markRead.gif" alt='<openmrs:message code="Alert.mark"/>' title='<openmrs:message code="Alert.mark"/>'/> <span class="markAlertText"><openmrs:message code="Alert.markAsRead"/></span>
				</a>
					${alert.text} ${alert.dateToExpire}
			</div>
			<c:if test="${varStatus.last}">
		</div>
	</div>
		</c:if>
		</openmrs:forEachAlert>

		<c:if test="${msg != null}">
		<div id="openmrs_msg"><openmrs:message code="${msg}" text="${msg}" arguments="${msgArgs}"  htmlEscape="false" /></div>
		</c:if>
		<c:if test="${err != null}">
		<div id="openmrs_error"><openmrs:message code="${err}" text="${err}" arguments="${errArgs}" htmlEscape="false"/></div>
		</c:if>
		<div id="openmrs_dwr_error" style="display:none" class="error">
			<div id="openmrs_dwr_error_msg"></div>
			<div id="openmrs_dwr_error_close" class="smallMessage">
				<i><openmrs:message code="error.dwr.stacktrace"/></i>
				<a href="#" onclick="this.parentNode.parentNode.style.display='none'"><openmrs:message code="error.dwr.hide"/></a>
			</div>
		</div>
