<%
    def attributeTypeAttributes = config.attributeTypeAttributes;
    def fieldAttributesData = config.fieldAttributesData;
    def selectedAttributes = config.selectedAttributes;
%>
<script type="text/javascript">
    jQuery(function() {
        jQuery('body').on('focus', ".date", function(){
            jQuery(this).datetimepicker({
                minView : 2,
                autoclose : true,
                pickerPosition : "bottom-left",
                todayHighlight : false,
                format: "dd/mm/yyyy",
                startDate : new Date(),
            });
        });
    });
</script>
<ul class="table-layout" ng-repeat="attributeTypeAttribute in attributeTypeAttributes">
    <li class="required" ng-if="attributeTypeAttribute.required">
        <span>{{attributeTypeAttribute.name}}</span>
    </li>
    <li class="not-required" ng-if="!attributeTypeAttribute.required">
        <span>{{attributeTypeAttribute.name}}</span>
    </li>
    <li>
        <span ng-if="attributeTypeAttribute.format === 'java.lang.Boolean'">
            <table class="bool" ng-init="attributes[attributeTypeAttribute.uuid].value = true">
                <tr>
                    <td>${ui.message('general.yes')}:</td>
                    <td>
                        <input type="radio" name="response"
                               ng-model="attributes[attributeTypeAttribute.uuid].value"
                               data-ng-value="true"
                               ng-checked="attributes[attributeTypeAttribute.uuid].value"/>
                    </td>
                    <td>${ui.message('general.no')}:</td>
                    <td>
                        <input type="radio" name="response"
                               ng-model="attributes[attributeTypeAttribute.uuid].value"
                               data-ng-value="false"
                               ng-checked="!attributes[attributeTypeAttribute.uuid].value"/>
                    </td>
                </tr>
            </table>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'java.lang.Float' || attributeTypeAttribute.format === 'java.lang.Integer'">
            <input type="number" ng-if="attributeTypeAttribute.required" required class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
            <input type="number" ng-if="!attributeTypeAttribute.required" class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
        </span>

        <span ng-if="attributeTypeAttribute.format === 'java.lang.Character' || attributeTypeAttribute.format === 'java.lang.String'">
            <input ng-if="attributeTypeAttribute.required" required class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
            <input ng-if="!attributeTypeAttribute.required" class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Concept'">
            <!-- show concepts drop-down -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="concept.name for concept in fieldAttributesData[attributeTypeAttribute.foreignKey]">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.User'">
            <!-- show users dropdown -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in fieldAttributesData['users']">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Location'">
            <!-- show locations dropdown -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in fieldAttributesData['locations']">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Drug'">
            <!-- show drugs dropdown -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in fieldAttributesData['drugs']">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Encounter'">
            <!-- show encounter fragment -->
            <input ng-if="attributeTypeAttribute.required" required class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
            <input ng-if="!attributeTypeAttribute.required" class="form-control"
                   ng-model="attributes[attributeTypeAttribute.uuid].value" />
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Patient'">
            <!-- show patient fragment -->
            ${ ui.includeFragment("openhmis.commons", "searchFragment", [
                    ngEnterEvent: "false",
                    typeahead: ["patient.person.personName.display for patient in searchFieldAttributePatients(\$viewValue)"],
                    model: "attributes[attributeTypeAttribute.uuid].value",
                    typeaheadEditable: "true",
                    class: ["form-control autocomplete-search"],
                    showRemoveIcon: "false",
                    required: "paymentModeAttribute.required",
                    placeholder: [ui.message("openhmis.commons.general.searchPatientIdentifier")],
            ])}
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Person'">
            <!-- show person fragment -->
            ${ ui.includeFragment("openhmis.commons", "searchFragment", [
                    ngEnterEvent: "false",
                    typeahead: ["person.display for person in searchFieldAttributePerson(\$viewValue)"],
                    model: "attributes[attributeTypeAttribute.uuid].value",
                    typeaheadEditable: "true",
                    class: ["form-control autocomplete-search"],
                    showRemoveIcon: "false",
                    required: "paymentModeAttribute.required",
                    placeholder: [ui.message("openhmis.commons.general.searchPersonName")],
            ])}
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.ProgramWorkflow'">
            <!-- show workflow fragment -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="programworkflow for programworkflow in fieldAttributesData['programworkflow']">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.Provider'">
            <!-- show provider dropdown -->
            <select ng-model="attributes[attributeTypeAttribute.uuid].value" class="form-control"
                    ng-options="provider.name for provider in fieldAttributesData['providers']">
            </select>
        </span>

        <span ng-if="attributeTypeAttribute.format === 'org.openmrs.util.AttributableDate'">
            <!-- show attributable date fragment -->
            ${ ui.includeFragment("uicommons", "field/datetimepicker", [
                    formFieldName: "attributableDate",
                    label: "",
                    useTime: false,
                    startDate : new Date(),
                    class: "form-control",
            ])}
        </span>
    </li>
</ul>
