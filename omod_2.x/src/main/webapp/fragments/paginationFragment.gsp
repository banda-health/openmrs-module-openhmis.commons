<%
	def paginationId = config.paginationId ? config.paginationId : "";
	def hide = config.hide ? config.hide.each {"${it}"} : "fetchedEntities.length == 0";
	def pagingFrom = config.pagingFrom ? config.pagingFrom : "pagingFrom(currentPage, limit)";
	def onPageChange = config.onPageChange ? config.onPageChange.each {"${it}"} : "paginate(currentPage)";
	def pagingTo = config.pagingTo ? config.pagingTo : "pagingTo(currentPage, limit, totalNumOfResults)";
	def totalNumberOfResults = config.totalNumberOfResults ? config.totalNumberOfResults : "totalNumOfResults";
	def model = config.model ? config.model : "limit";
	def onChange = config.onChange ? config.onChange : "updateContent()";
%>
<div id="below-entities-table" ng-hide="${hide}">
	<span style="float:right;">
		<div class="entity-pagination">
			<dir-pagination-controls pagination-id="${paginationId}"
			                         on-page-change="${onPageChange}"></dir-pagination-controls>
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
			<select id="pageSize" ng-model="${model}" ng-change="${onChange}">
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
			                                                      ng-change="${onChange}"></span>
			<span>${ui.message('openhmis.commons.general.includeRetired')}</span>
			<% } %>
		</div>
	</div>
</div>
