<div id="below-entities-table"
	<% if (config.hide) { %> ng-hide=<% config.hide.each { %>"${it}" <% } %>
	<% } else { %> ng-hide="fetchedEntities.length == 0" <% } %>>
	<span style="float:right;">
		<div class="entity-pagination">
			<dir-pagination-controls
				<% if (config.paginationId) { %> pagination-id=${config.paginationId} <% } %>
				                                 <% if (config.onPageChange) { %> on-page-change= <% config.onPageChange.each { %>"${
				it}" <% } %>
		<% } else { %> on-page-change="paginate(currentPage)" <% } %> >
		</dir-pagination-controls>
		</div>
	</span>
	<br/>

	<div class="pagination-options" style="float:left;">
		<div id="showing-entities">
			<span>
				<b>
					${ui.message('openhmis.commons.general.showing')} <% if (config.pagingFrom) { %>
					{{${config.pagingFrom}}} <% } else { %> {{pagingFrom(currentPage, limit)}} <% } %>
					${ui.message('openhmis.commons.general.to')} <% if (config.pagingTo) { %> {{${config.pagingTo}}} <%
						} else { %> {{pagingTo(currentPage, limit, totalNumOfResults)}} <% } %>
				</b>
			</span>
			<span>
				<b>
					${ui.message('openhmis.commons.general.of')}<% if (config.totalNumberOfResults) { %> {{${config.totalNumberOfResults}}}
							 <% } else {%> {{totalNumOfResults}} <% } %>
					${ui.message('openhmis.commons.general.entries')}
				</b>
			</span>
		</div>

		<div id="includeVoided-entities">
			${ui.message('openhmis.commons.general.show')}
			<select id="pageSize"
				<% if (config.model) { %> ng-model=${config.model} <% } else { %> ng-model="limit" <% } %>
				<% if (config.onChange) { %> ng-change= ${config.onChange}><% } else { %> ng-change="updateContent()" <%
				} %>
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
