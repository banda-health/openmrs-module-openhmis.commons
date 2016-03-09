<!-- search and autocomplete fragment -->
<div class="btn-group">
    <input type="text"
            <% if(config.model){ %>
                ng-model = ${config.model}
            <% } %>

            <% if(config.onChangeEvent){ %>
                ng-change = ${config.onChangeEvent}
            <% } %>

            <% if(config.class){ %>
                class =
                    <% config.class.each{ %>
                        "${ it } "
                    <% } %>
            <% } %>

            <% if(config.placeholder) { %>
                placeholder =
                    <% config.placeholder.each{ %>
                        "${ it } "
                    <% } %>
            <% } %>

            <% if(config.typeahead) { %>
                typeahead =
                    <% config.typeahead.each{ %>
                        "${ it } "
                    <% } %>
                typeahead-append-to-body = "true"
                typeahead-min-length="3"
            <% } else { %>
                ng-minlength="3"
            <% } %>

            <% if(config.typeaheadOnSelect) { %>
                typeahead-on-select = ${config.typeaheadOnSelect}
            <% } %>

            <% if(config.typeaheadEditable) { %>
                typeahead-editable = ${config.typeaheadEditable}
            <% } %>
            ng-model-options="{ debounce: 500 }"
            autofocus />

    <% if(!config.typeahead) {%>
        <span id="searchclear" class="searchclear icon-remove-circle"></span>
    <% } %>
</div>