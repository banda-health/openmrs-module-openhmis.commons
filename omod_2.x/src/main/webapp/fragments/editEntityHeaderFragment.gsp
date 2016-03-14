<table class="header-title">
    <tr>
        <td>
            <h1>{{messageLabels['h2SubString']}}</h1>
        </td>
        <td>
            <span style="float:right;">
                <h1>
                    <i class="icon-trash show-cursor" style="width:200px; height: 200px;" ng-click="retireUnretireDeletePopup('retireUnretireDeleteSection')" ng-hide="entity.uuid === '' || retireOrUnretire === messageLabels['general.unretire']" title="{{messageLabels['general.purge']}} / {{retireOrUnretire}}"></i>
                    <i class="icon-reply edit-action" style="width:200px; height: 200px;" ng-click="retireUnretireDeletePopup('retireUnretireDeleteSection')" ng-hide="entity.uuid === '' || retireOrUnretire === messageLabels['general.retire']" title="{{messageLabels['general.purge']}} / {{retireOrUnretire}}"></i>
                </h1>
            </span>
        </td>
    </tr>
</table>