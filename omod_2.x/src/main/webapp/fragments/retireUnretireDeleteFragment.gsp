<div id="retireUnretireDeleteSection" class="dialog" style="display:none;">
    <div class="dialog-header">
        <span>
            <i class="icon-trash" ng-show="retireOrUnretire === messageLabels['general.retire']"></i>
            <i class="icon-reply edit-action" ng-show="retireOrUnretire === messageLabels['general.unretire']" ></i>
            <h3>{{retireOrUnretire}}</h3>
        </span>
        <i class="icon-remove cancel" style="float:right; cursor: pointer;" ng-click="closeThisDialog()"></i>
    </div>
    <div class="dialog-content form">
        <div>
            <span ng-show="entity.retired">
                ${ui.message('openhmis.commons.general.retired.reason')}
                <b>{{entity.retireReason}}</b><br />
            </span>
            <span ng-hide="entity.retired">
                <input type="text" placeholder="{{messageLabels['general.retireReason']}}"
                       ng-model="entity.retireReason" ng-disabled="entity.retired" />
            </span>
            <br />
            <button
                   <% if(config.retireUnretireCall) { %>
                        ng-click=${config.retireUnretireCall}
                   <% } else { %>
                        ng-click="retireOrUnretireCall()"
                   <% } %> >{{retireOrUnretire}}</button>
        </div>
        <br />

        <% if(config.showDeleteSection != "false"){ %>
            <div class="detail-section-border-top detail-section-border-bottom">
                <p class="checkRequired" ng-hide="entity.retireReason != '' || retireReasonIsRequiredMsg == '' || retireReasonIsRequiredMsg == undefined">{{retireReasonIsRequiredMsg}}</p>
                <h3 ng-hide="entity.uuid == ''">
                    ${ui.message("general.delete")} {{entity_name}}
                </h3>
                <p>
                    <button ng-hide="entity.uuid == ''" ng-click="purge()">{{messageLabels['general.purge']}}</button>
                </p>
            </div>
            <br />
        <% } %>
        <div>
        <input type="button" class="cancel" value="{{messageLabels['general.cancel']}}" ng-click="closeThisDialog('Cancel')" />
        </div>
    </div>
</div>
