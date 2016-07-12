<div id="attribute-types-dialog" class="dialog" style="display:none;">
	<div class="dialog-header">
		<span ng-show="addAttributeTypeTitle != ''">
			<i class="icon-plus-sign"></i>
			<h3>{{addAttributeTypeTitle}}</h3>
		</span>
		<span ng-show="editAttributeTypeTitle != ''">
			<i class="icon-edit"></i>
			<h3>{{editAttributeTypeTitle}}</h3>
		</span>
		<i class="icon-remove cancel show-cursor"  style="float:right;" ng-click="closeThisDialog()"></i>
	</div>

	<div class="dialog-content form" id="dialog-bottom">
		<ul class="table-layout dialog-table-layout">
			<li class="required">
				<span>${ui.message('general.name')}</span>
			</li>
			<li>
				<input type="text" style="min-width: 100%;"
				       placeholder="" required ng-model="attributeType.name"/>
			</li>
		</ul>
		<ul class="table-layout dialog-table-layout">
			<li class="required">
				<span>${ui.message('PersonAttributeType.format')}</span>
			</li>
			<li>
				<select id="formatSelect" class="form-control" style="font-size: 14px" ng-model="attributeType.format"
				        ng-options="field for field in formatFields track by field">
					<option value="0"></option>
					<option ng-selected="attributeType.format == field"></option>
				</select>
			</li>
		</ul>
		<ul class="table-layout dialog-table-layout">
			<li class="not-required">
				<span>${ui.message('PersonAttributeType.foreignKey')}</span>
			</li>
			<li>
				<input type="number" ng-model="attributeType.foreignKey"/>
			</li>
		</ul>
		<ul class="table-layout dialog-table-layout">
			<li class="not-required">
				<span>${ui.message('PatientIdentifierType.format')}</span>
			</li>
			<li>
				<input type="text" ng-model="attributeType.regExp"/>
			</li>
		</ul>
		<ul class="table-layout dialog-table-layout">
			<li class="not-required">
				<span>${ui.message('FormField.required')}</span>
			</li>
			<li>
				<input type="checkbox" ng-model="attributeType.required"/>
			</li>
		</ul>
		<ul class="table-layout dialog-table-layout">
			<li class="required">
				<span>${ui.message('Field.attributeName')} ${ui.message('Obs.order')}</span>
			</li>
			<li>
				<input type="number" required ng-model="attributeType.attributeOrder"/>
			</li>
		</ul>
		<br/>

		<div class="ngdialog-buttons detail-section-border-top">
			<br/>
			<input type="button" class="cancel" value="${ui.message('general.cancel')}"
			       ng-click="cancel()"/>
			<span ng-show="addAttributeTypeTitle != ''">
				<input type="button" class="confirm right"
				       ng-disabled="attributeType.name == '' || attributeType.name == undefined || attributeType.attributeOrder == undefined
										       || attributeType.format == undefined || attributeType.format == ''"
				       value="{{messageLabels['general.save']}}"
				       ng-click="saveOrUpdate()"/>
			</span>
			<span ng-show="editAttributeTypeTitle != ''">
				<input type="button" class="confirm right"
				       ng-disabled="attributeType.name == '' || attributeType.name == undefined
										        || attributeType.attributeOrder == undefined
										       || attributeType.format == undefined || attributeType.format == ''"
				       value="${ui.message('openhmis.commons.general.confirm')}"
				       ng-click="saveOrUpdate()"/>
			</span>
		</div>
	</div>
</div>
