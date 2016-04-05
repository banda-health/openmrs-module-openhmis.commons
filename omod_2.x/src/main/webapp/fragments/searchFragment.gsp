<!-- search and autocomplete fragment -->
<%
	def model = config.model;
	def onChangeEvent = config.onChangeEvent;
	def cssClass = config.class;
	def placeholder = config.placeholder;
	def typeahead = config.typeahead;
	def typeaheadOnSelect = config.typeaheadOnSelect;
	def typeaheadEditable = config.typeaheadEditable;
%>
<div class="btn-group">
    <input type="text" id="searchBox"
            <% if(model){ %>
                ng-model = ${model}
            <% } %>

            <% if(onChangeEvent){ %>
                ng-change = ${onChangeEvent}
            <% } %>

            <% if(cssClass){ %>
                class =
                    <% cssClass.each{ %>
                        "${ it } "
                    <% } %>
            <% } %>

            <% if(placeholder) { %>
                placeholder =
                    <% placeholder.each{ %>
                        "${ it } "
                    <% } %>
            <% } %>

            <% if(typeahead) { %>
                typeahead =
                    <% typeahead.each{ %>
                        "${ it } "
                    <% } %>
                typeahead-append-to-body = "true"
                typeahead-min-length="3"
            <% } else { %>
                ng-minlength="3"
            <% } %>

            <% if(typeaheadOnSelect) { %>
                typeahead-on-select = ${typeaheadOnSelect}
            <% } %>

            <% if(typeaheadEditable) { %>
                typeahead-editable = ${typeaheadEditable}
            <% } %>
            ng-model-options="{ debounce: 500 }"
            autofocus />

    <% if(!typeahead) {%>
        <span id="searchclear" class="searchclear icon-remove-circle" href=""  ng-click=" ${model} = ''; ${onChangeEvent}" > </span>
    <% } %>
</div>
