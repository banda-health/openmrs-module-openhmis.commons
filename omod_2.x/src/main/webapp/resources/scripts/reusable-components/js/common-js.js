(function() {
  'use strict';

  var jq = jQuery;
  jq(document).ready(function() {// supports reseting search phrase to blank
    jq(".searchinput").keyup(function() {
      jq(this).next().toggle(Boolean(jq(this).val()));
    });
    jq(".searchclear").toggle(Boolean(jq(".searchinput").val()));
    jq(".searchclear").click(function() {
      jq(this).prev().val('').focus();
      jq(this).prev().trigger('input');
      jq(this).hide();
    });
  });
});