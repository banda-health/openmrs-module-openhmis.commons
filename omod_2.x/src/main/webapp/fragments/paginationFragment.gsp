<%
	def paginationId = ""
	if (config.paginationId) {
		paginationId = config.paginationId
	}

	def hide = ""
	if (config.hide) {
		hide = config.hide.each {
			"${it}"
		}
	} else {
		hide = "fetchedEntities.length == 0"
	}

	def pagingFrom = ""
	if (config.pagingFrom) {
		pagingFrom = config.pagingFrom
	} else {
		pagingFrom = "pagingFrom(currentPage, limit)"
	}

	def onPageChange = ""
	if (config.onPageChange) {
		onPageChange = config.onPageChange.each {
		 "${it}"
		}
	} else {
		onPageChange = "paginate(currentPage)"
	}

	def pagingTo =""
	if (config.pagingTo) {
		pagingTo= config.pagingTo
	} else {
		pagingTo = "pagingTo(currentPage, limit, totalNumOfResults)"
	}

	def totalNumberOfResults = ""
	if (config.totalNumberOfResults) {
		totalNumberOfResults = config.totalNumberOfResults
	} else {
		totalNumberOfResults = "totalNumOfResults"
	}

	def model = ""
	if (config.model) {
		model = config.model
	} else {
		model = "limit"
	}

	def onChange = ""
	if (config.onChange) {
		onChange = config.onChange
	} else {
		onChange = "updateContent()"
	}
%>
<div id="below-entities-table" ng-hide="${hide}">
	<span style="float:right;">
		<div class="entity-pagination">
			<dir-pagination-controls  pagination-id="${paginationId}" on-page-change="${onPageChange}" ></dir-pagination-controls>
		</div>
	</span>
	<br/>

	<div class="pagination-options" style="float:left;">
		<div id="showing-entities">
			<span>
				<b>
					${ui.message('openhmis.commons.general.showing')} {{${pagingFrom}}}
					${ui.message('openhmis.commons.general.to')} {{${pagingTo}}}
				</b>
			</span>
			<span>
				<b>
					${ui.message('openhmis.commons.general.of')} {{${totalNumberOfResults}}}
					${ui.message('openhmis.commons.general.entries')}
				</b>
			</span>
		</div>

		<div id="includeVoided-entities">
			${ui.message('openhmis.commons.general.show')}
			<select id="pageSize" ng-model="${model}" ng-change="${onChange}" >
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			${ui.message('openhmis.commons.general.entries')}
			<% if (config.showRetiredSection) { %>
			<% } else { %>
			<span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <input type="checkbox" ng-checked="includeRetired"
			                                                      ng-model="includeRetired"
			                                                      ng-change="updateContent()"></span>
			<span>${ui.message('openhmis.commons.general.includeRetired')}</span>
			<%} %>
		</div>
	</div>
</div>
