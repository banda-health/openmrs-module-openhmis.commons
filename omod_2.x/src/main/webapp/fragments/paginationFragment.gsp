<div id="below-entities-table"
	<% if (config.hide) { %>
	 ng-hide=<% config.hide.each { %>
     "${it}"
	<% } %>
	<% } %>>
	<span style="float:left;">
		<div id="showing-entities">
			<span><b>
				${ui.message('openhmis.inventory.general.showing')} {{pagingFrom(currentPage, limit)}}
				${ui.message('openhmis.inventory.general.to')} {{pagingTo(currentPage, limit, totalNumOfResults)}}</b>
			</span>
			<span><b>${ui.message('openhmis.inventory.general.of')} {{totalNumOfResults}}
				${ui.message('openhmis.inventory.general.entries')}</b></span>
		</div>
	</span>
	<span style="float:right;">
		<div class="entities-pagination">
			<dir-pagination-controls on-page-change="paginate(currentPage)"></dir-pagination-controls>
		</div>
	</span>
	<br/>
	<span style="float:left;">
		<div id="includeVoided-entities">
			${ui.message('openhmis.inventory.general.show')}
			<select id="pageSize" ng-model="limit" ng-change="updateContent()">
				<option value="2">2</option>
				<option value="5">5</option>
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			${ui.message('openhmis.inventory.general.entries')}
			<span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp <input type="checkbox" ng-checked="includeRetired"
			                                                      ng-model="includeRetired"
			                                                      ng-change="updateContent()"></span>
			<span>${ui.message('openhmis.inventory.general.includeRetired')}</span>
		</div>
	</span>
</div>
