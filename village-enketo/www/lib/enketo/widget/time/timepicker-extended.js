/**
 * @preserve Copyright 2012 Martijn van de Rijdt & Modi Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

define( [ 'js/Widget', 'modernizr', 'jquery', 'widget/time/bootstrap-timepicker/js/bootstrap-timepicker' ],
    function( Widget, modernizr, $ ) {
        "use strict";

        var pluginName = 'timepickerExtended';

        /**
         * Extends jdewit's bootstrap-timepicker without changing the original
         * https://github.com/jdewit/bootstrap-timepicker
         * TODO: I'd like to find a replacement for jdewit's widget during the move to bootstrap 3.
         *
         * @constructor
         * @param {Element}                       element   Element to apply widget to.
         * @param {(boolean|{touch: boolean})}    options   options
         * @param {*=}                            event     event
         */

        function TimepickerExtended( element, options, event ) {
            //call the Super constructor
            Widget.call( this, element, options );
            this._init();
        }

        //copy the prototype functions from the Widget super class
        TimepickerExtended.prototype = Object.create( Widget.prototype );

        //ensure the constructor is the new one
        TimepickerExtended.prototype.constructor = TimepickerExtended;

        /**
         * Initialize timepicker widget
         */
        TimepickerExtended.prototype._init = function() {
            var $timeI = $( this.element ),
                $p = $( this ).parent( 'label' ),
                timeVal = $( this.element ).val(),
                $fakeTime = $( '<div class="widget bootstrap-timepicker">' +
                    '<input class="ignore timepicker-default input-small" readonly="readonly" type="text" value="' + timeVal + '" placeholder="hh:mm" />' +
                    '<button class="btn-reset"><i class="icon icon-trash"></i></button></div>' ),
                $fakeTimeReset = $fakeTime.find( '.btn-reset' ),
                $fakeTimeI = $fakeTime.find( 'input' );

            $timeI.next( '.widget.bootstrap-timepicker-component' ).remove();
            $timeI.hide().after( $fakeTime );

            $fakeTimeI.timepicker( {
                defaultTime: ( timeVal.length > 0 ) ? timeVal : 'current',
                showMeridian: false
            } ).val( timeVal )
            //the time picker itself has input elements
            .closest( '.widget' ).find( 'input' ).addClass( 'ignore' );

            $fakeTimeI.on( 'change', function() {
                $timeI.val( $( this ).val() ).trigger( 'change' ).blur();
                return false;
            } );

            //reset button
            $fakeTimeReset.on( 'click', function( event ) {
                $fakeTimeI.val( '' ).trigger( 'change' );
            } );

            $fakeTimeI.on( 'focus blur', function( event ) {
                $timeI.trigger( event.type );
            } );
        };

        $.fn[ pluginName ] = function( options, event ) {

            options = options || {};

            return this.each( function() {
                var $this = $( this ),
                    data = $this.data( pluginName );

                if ( !data && typeof options === 'object' && ( !options.touch || !modernizr.inputtypes.time ) ) {
                    $this.data( pluginName, ( data = new TimepickerExtended( this, options, event ) ) );
                }
                //only call method if widget was instantiated before
                else if ( data && typeof options == 'string' ) {
                    //pass the element as a parameter as this is used in fix()
                    data[ options ]( this );
                }
            } );
        };

    } );
