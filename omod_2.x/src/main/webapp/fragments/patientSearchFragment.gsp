<%
    def showPatientDetails = config.showPatientDetails ? config.showPatientDetails : true;
    def showPatientSearchBox = config.showPatientSearchBox ? config.showPatientSearchBox : true;
    def changePatient = config.changePatient ? config.changePatient : true;
%>
<fieldset class="nested patient-details"
          ng-show="${showPatientDetails}">
    <legend style="margin-bottom:-15px;">${ui.message('openhmis.commons.general.patientDetails')}</legend>
    <span>
        <ul class="patient-details-layout">
            <li>
                <b>${ui.message('general.name')}:</b>
                <a href="/${ ui.contextPath() }/coreapps/clinicianfacing/patient.page?patientId={{selectedPatient.uuid}}"
                   target="_blank">
                    {{selectedPatient.person.personName.display}}
                </a>
            </li>
            <li>
                <b>${ui.message('Id')}:</b> {{selectedPatient.patientIdentifier.identifier}}
            </li>
            <li>
                <span ng-show="visit !== undefined">
                    <b>${ui.message('openhmis.commons.general.activeVisit')}:</b>
                    {{visit.display}}
                </span>
            </li>
        </ul>
        <span ng-show="${changePatient}">
            <input type="button" class="btn gray-button"
                   value="${ui.message('openhmis.commons.general.changePatient')}"
                   ng-click="changePatient()" />
            <span ng-show="visit !== undefined"> | </span>
        </span>
        <input type="button" class="btn gray-button"
               value="${ui.message('openhmis.commons.general.endVisit')}"
               ng-show="visit !== undefined" ng-click="endVisit()" />
    </span>
</fieldset>

<fieldset class="nested" ng-show="${showPatientSearchBox}">
    <legend>${ui.message('openhmis.commons.general.findPatient')}</legend>
    <div ng-show="selectedPatient === ''">
        <ul class="table-layout">
            <li>${ ui.includeFragment("openhmis.commons", "searchFragment", [
                    model: "patient",
                    onChangeEvent: "searchPatients()",
                    class: ["field-display ui-autocomplete-input form-control searchinput"],
                    placeholder: [ui.message("openhmis.commons.general.searchPatientIdentifier")],
                    required: true
            ])}
            </li>
        </ul>
    </div>
    <br />

    <div id="patient-table" ng-show="selectedPatient === '' && patient !== undefined">
        <span style="margin:150px;" ng-show="patients.length == 0 && patient !== undefined && patient !== ''">
            ${ui.message('openhmis.commons.general.preSearchMessage')}
            - <b> {{patient}} </b> -
        {{postSearchMessage}}
        </span>
        <table ng-show="patients.length !== 0"
               style="margin-bottom:5px;"
               class="manage-entities-table">
            <thead>
            <tr>
                <th style="width: 40%">${ui.message('openhmis.commons.general.identifier')}</th>
                <th>${ui.message('openhmis.commons.general.given')}</th>
                <th>${ui.message('openhmis.commons.general.middle')}</th>
                <th>${ui.message('openhmis.commons.general.familyName')}</th>
                <th style="width: 20%">${ui.message('openhmis.commons.general.age')}</th>
                <th style="width: 30%">${ui.message('openhmis.commons.general.gender')}</th>
                <th style="width: 40%">${ui.message('openhmis.commons.general.birthDate')}</th>
            </tr>
            </thead>
            <tbody>
            <tr class="clickable-tr" pagination-id="__patients"
                dir-paginate="patient in patients | itemsPerPage: limit"
                total-items="totalNumOfResults" current-page="currentPage"
                ng-click="selectPatient(patient)" ng-enter="selectPatient(patient)"
                tabindex="0">
                <td>{{patient.patientIdentifier.identifier}}</td>
                <td>{{patient.person.personName.givenName}}</td>
                <td>{{patient.person.personName.middleName}}</td>
                <td>{{patient.person.personName.familyName}}</td>
                <td>{{patient.person.age}}</td>
                <td>{{patient.person.gender}}</td>
                <td>{{patient.person.birthdate | date: 'dd-MM-yyyy'}}</td>
            </tr>
            </tbody>
        </table>
        ${ui.includeFragment("openhmis.commons", "paginationFragment", [
                hide                : "patients.length === 0",
                paginationId        : "__patients",
                onPageChange        : "searchPatients(currentPage)",
                onChange            : "searchPatients(currentPage)",
                showRetiredSection  : "false"
        ])}
    </div>
</fieldset>
