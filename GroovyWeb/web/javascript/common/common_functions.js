/*
    Date: Thu Feb 18 07:05:34 SAST 2010
    File: common_functions.js
    Author: admin
*/


/**
 * This method changes the action to the one specified and submits the form.
 */
function changeActionSubmitform(pageForm, pageAction)
{
   pageForm.action = pageAction;
   pageForm.submit();
}
