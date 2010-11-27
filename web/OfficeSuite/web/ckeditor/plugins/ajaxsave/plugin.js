/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

/**
 * @fileSave plugin.
 */


(function()
{
	var saveCmd =
	{
		modes : { wysiwyg:1, source:1 },

		exec : function( editor )
		{
			var $form = editor.element.$.form;
            //alert('Hello World ' + $form.filename.value)
            var count = 0
            try {
                if ( $form )
                {
                    var success       = function(t){
                        $('ddiv').innerHTML = t.responseText + 'supposed to be here';
                    }
                    var failure         = function(t){ alert('update failed'); }
                    var url = '/OfficeSuite/CKFileEditor?action=save';

                    var pars = new Hash();
                    pars.set('fileName',$form.filename.value);
                    pars.set('fileContents',editor.getData());

                    var myAjax = new Ajax.Request(
                    url,
                    {
                        method: 'post',
                        parameters: pars
                    }          );
                    $count = 2
                }
            } catch (error) {
                alert('Failed to save : ' + error)
            }
            

		}
	};

	var pluginName = 'ajaxsave';

	// Register a plugin named "save".
	CKEDITOR.plugins.add( pluginName,
	{
		init : function( editor )
		{
			var command = editor.addCommand( pluginName, saveCmd );
			command.modes = { wysiwyg : !!( editor.element.$.form ) };

			editor.ui.addButton( 'AjaxSave',
				{
					label : editor.lang.save,
					command : pluginName,
                    icon: "/OfficeSuite/ckeditor/images/save.png"
				});
		}
	});
})();
