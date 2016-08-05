<!-- search and autocomplete fragment -->
<%
	def model = config.model;
	def onChangeEvent = config.onChangeEvent;
	def cssClass = config.class;
	def placeholder = config.placeholder;
	def typeahead = config.typeahead;
	def typeaheadOnSelect = config.typeaheadOnSelect;
	def typeaheadEditable = config.typeaheadEditable;
    def typeaheadClearEvent = config.typeaheadClearEvent ? config.typeaheadClearEvent : "selectConcept('')";
    def ngEnterEvent = config.ngEnterEvent ? config.ngEnterEvent : '';
    def required = config.required ? config.required : false;
    def showRemoveIcon = config.showRemoveIcon ? config.showRemoveIcon : true;
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
              typeahead-on-select = "${typeaheadOnSelect}; ${ngEnterEvent}"
          <% } %>

          <% if(typeaheadEditable) { %>
              typeahead-editable = ${typeaheadEditable}
          <% } %>
          ng-model-options="{ debounce: 500 }"

          <% if(ngEnterEvent != '') {%>
              ng-enter = ${ngEnterEvent}
          <% } %>

          <% if(required == 'true') { %>
              required
          <% } %>
          autofocus
    />

    <span ng-show="${showRemoveIcon}" id="searchclear" href=""
          <% if(typeahead) { %>
                ng-click = "${model} = ''; ${typeaheadClearEvent}; ${ngEnterEvent};"
                class="autocompleteclear icon-remove-circle"
          <% } else{ %>
                ng-click = "${model} = ''; ${onChangeEvent}"
                class="searchclear icon-remove-circle"
          <% } %>>
    </span>
</div>
