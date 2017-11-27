
/* Float Label Pattern Plugin for Bootstrap 3.1.0 by Travis Wilson
 ******************************************************************/
(function($) {
	$.fn.floatLabels = function(options) {

		// Settings
		var self = this;
		var settings = $.extend({}, options);


		// Event Handlers
		function registerEventHandlers() {
			self.on('input keyup change', 'input, textarea', function() {
				actions.swapLabels(this);
			});
		}


		// Actions
		var actions = {
			initialize: function() {
				self.each(function() {
					var $this = $(this);
					var $label = $this.children('label');
					var $field = $this.find('input,textarea').first();

					if ($this.children().first().is('label')) {
						$this.children().first().remove();
						$this.append($label);
					}

					var placeholderText = ($field.attr('placeholder') && $field.attr('placeholder') != $label.text()) ? $field.attr('placeholder') : $label.text();

					$label.data('placeholder-text', placeholderText);
					$label.data('original-text', $label.text());

					if ($field.val() == '') {
						$field.addClass('empty')
					}
				});
			},
			swapLabels: function(field) {
				var $field = $(field);
				var $label = $(field).siblings('label').first();
				var isEmpty = Boolean($field.val());

				if (isEmpty) {
					$field.removeClass('empty');
					$label.text($label.data('original-text'));
				} else {
					$field.addClass('empty');
					$label.text($label.data('placeholder-text'));
				}
			}
		}


		// Initialization
		function init() {
			registerEventHandlers();

			actions.initialize();
			self.each(function() {
				actions.swapLabels($(this).find('input,textarea').first());
			});
		}
		init();


		return this;
	};

	$(function() {
		$('.float-label-control').floatLabels();
	});
})(jQuery);



(function($) {

	$.fn.FloatLabel = function(options) {

		var defaults = {
				populatedClass: 'populated',
				focusedClass: 'focused'
			},
			settings = $.extend({}, defaults, options);

		return this.each(function() {

			var element = $(this),
				label = element.find('label'),
				input = element.find('textarea, input');

			if (input.val() == '') {
				input.val(label.text());
			} else {
				element.addClass(settings.populatedClass);
			}

			input.on('focus', function() {
				element.addClass(settings.focusedClass);

				if (input.val() === label.text()) {
					input.val('');
				} else {
					element.addClass(settings.populatedClass);
				}
			});

			input.on('blur', function() {
				element.removeClass(settings.focusedClass);

				if (!input.val()) {
					input.val(label.text());
					element.removeClass(settings.populatedClass);
				}
			});

			input.on('keyup', function() {
				element.addClass(settings.populatedClass);
			});
		});
	};

})(jQuery);


function mmfInit()
{
	
}

function checkboxToSwitch(){
	
}

function radioToTick(){
	
}

function alphaOnly(){
	
}

function alphaSpace(){
	
}

function alphaNumeric(){
	
}

function alphaNumericSpace(){
	
}

function numeric(){
	
}

function numericSpace(){
	
}


$(document).keydown(function(event) {
	if (event.ctrlKey==true && (event.which == '61' || event.which == '107' || event.which == '173' || event.which == '109'  || event.which == '187'  || event.which == '189'  ) ) {
	        event.preventDefault();
	     }
	    // 107 Num Key  +
	    // 109 Num Key  -
	    // 173 Min Key  hyphen/underscor Hey
	    // 61 Plus key  +/= key
	});

	$(window).bind('mousewheel DOMMouseScroll', function (event) {
	       if (event.ctrlKey == true) {
	       event.preventDefault();
	       }
	});

