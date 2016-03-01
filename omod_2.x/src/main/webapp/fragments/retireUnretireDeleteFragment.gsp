<div id="retireUnretireDeleteSection" class="dialog" style="display:none;">
    <div class="dialog-header">
        <span>
            <i class="icon-trash"></i>
            <h3>{{retireOrUnretire}}</h3>
        </span>
    </div>
    <div class="dialog-content form">
        <div>
            <span ng-show="entity.retired">
                {{messageLabels['openhmis.inventory.general.retired.reason']}}
                <b>{{entity.retireReason}}</b><br />
            </span>
            <span ng-hide="entity.retired">
                <input type="text" placeholder="{{messageLabels['general.retireReason']}}"
                       ng-model="entity.retireReason" ng-disabled="entity.retired" />
            </span>
            <input type="button" class="confirm" style="background: #ff3d3d; background-color: #ff3d3d; border:#ff3d3d 1px solid" value="{{retireOrUnretire}}" ng-click="retireOrUnretireCall()" />
        </div>
        <br /><br />
        <div class="detail-section-border-top detail-section-border-bottom">
            <p class="checkRequired" ng-hide="entity.retireReason != '' || retireReasonIsRequiredMsg == '' || retireReasonIsRequiredMsg == undefined">{{retireReasonIsRequiredMsg}}</p>
            <h3 ng-hide="entity.uuid == ''">
                {{messageLabels['delete.forever']}}
            </h3>
            <p>
                <input type="button" ng-hide="entity.uuid == ''" class="confirm" style="background: #ff3d3d; background-color: #ff3d3d; border:#ff3d3d 1px solid" value="{{messageLabels['general.purge']}}" ng-click="purge()"/>
            </p>
        </div>
    </div>
</div>
