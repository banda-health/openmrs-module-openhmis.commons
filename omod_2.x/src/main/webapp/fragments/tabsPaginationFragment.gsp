	<span style="float:right;">
		<div class="entity-pagination">
			<dir-pagination-controls
				<% if (config.paginationId) { %>
					pagination-id=${config.paginationId}
				<% } %>
				<% if (config.onPageChange) { %>
					on-page-change=
						<% config.onPageChange.each { %>
								"${ it }"
						<% } %>
				<% } %>
			>
			</dir-pagination-controls>
		</div>
	</span>
	<br/>

	<div class="pagination-options" style="float:left;">
		<div id="showing-entities">
			<span>
				<b>
					${ui.message('openhmis.inventory.general.showing')}
					{{${config.pagingFrom}}}
					${ui.message('openhmis.inventory.general.to')}
					{{${config.pagingTo}}}
				</b>
			</span>
			<span>
				<b>
					${ui.message('openhmis.inventory.general.of')}
					{{${config.totalNumberOfResults}}}
					${ui.message('openhmis.inventory.general.entries')}
				</b>
			</span>
		</div>

		<div id="includeVoided-entities">
			${ui.message('openhmis.inventory.general.show')}
			<select id="pageSize"
				<% if (config.model) { %>
		            ng-model=${config.model}
				<% } %>
		        <% if (config.onChange) { %>
		            ng-change=${config.onChange}>
				<% } %>
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			${ui.message('openhmis.inventory.general.entries')}
		</div>
	</div>
