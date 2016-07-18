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
<ul class="table-layout" ng-repeat="paymentModeAttribute in paymentModeAttributes">
    <li class="required" ng-if="paymentModeAttribute.required">
        <span>{{paymentModeAttribute.name}}</span>
    </li>
    <li class="not-required" ng-if="!paymentModeAttribute.required">
        <span>{{paymentModeAttribute.name}}</span>
    </li>
    <li>
        <span ng-if="paymentModeAttribute.format === 'java.lang.Boolean'">
            <table class="bool">
                <tr>
                    <td>${ui.message('general.yes')}:</td>
                    <td>
                        <input type="radio" name="response"
                               ng-model="attributes[paymentModeAttribute.uuid].value"
                               data-ng-value="true"
                               ng-checked="attributes[paymentModeAttribute.uuid].value"/>
                    </td>
                    <td>${ui.message('general.no')}:</td>
                    <td>
                        <input type="radio" name="response"
                               ng-model="attributes[paymentModeAttribute.uuid].value"
                               data-ng-value="false"
                               ng-checked="!attributes[paymentModeAttribute.uuid].value"/>
                    </td>
                </tr>
            </table>
        </span>

        <span ng-if="paymentModeAttribute.format === 'java.lang.Float' || paymentModeAttribute.format === 'java.lang.Integer'">
            <input type="number" ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input type="number" ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'java.lang.Character' || paymentModeAttribute.format === 'java.lang.String'">
            <input ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Concept'">
            <!-- show concepts drop-down -->
            <select ng-model="attributes[paymentModeAttribute.uuid].value" class="form-control"
                    ng-options="concept.name for concept in paymentModeAttributesData[paymentModeAttribute.foreignKey]">
            </select>
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.User'">
            <!-- show users dropdown -->
            <select ng-model="attributes[paymentModeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in paymentModeAttributesData['users']">
            </select>
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Location'">
            <!-- show locations dropdown -->
            <select ng-model="attributes[paymentModeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in paymentModeAttributesData['locations']">
            </select>
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Drug'">
            <!-- show drugs dropdown -->
            <select ng-model="attributes[paymentModeAttribute.uuid].value" class="form-control"
                    ng-options="user.name for user in paymentModeAttributesData['drugs']">
            </select>

        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Encounter'">
            <!-- show encounter fragment -->
            <input ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Patient'">
            <!-- show patient fragment -->
            <input ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Person'">
            <!-- show person fragment -->
            <input ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.ProgramWorkflow'">
            <!-- show workflow fragment -->
            <input ng-if="paymentModeAttribute.required" required class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
            <input ng-if="!paymentModeAttribute.required" class="form-control"
                   ng-model="attributes[paymentModeAttribute.uuid].value" />
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.Provider'">
            <!-- show provider dropdown -->
            <select ng-model="attributes[paymentModeAttribute.uuid].value" class="form-control"
                    ng-options="provider.name for provider in paymentModeAttributesData['providers']">
            </select>
        </span>

        <span ng-if="paymentModeAttribute.format === 'org.openmrs.util.AttributableDate'">
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
