ace.define('ace/mode/asterisk', function(require, exports, module) {
"use strict";

var oop = require("ace/lib/oop");
var TextMode = require("ace/mode/text").Mode;
var ExampleHighlightRules = require("ace/mode/example_highlight_rules").ExampleHighlightRules;

var Mode = function() {
    this.HighlightRules = ExampleHighlightRules;
};
oop.inherits(Mode, TextMode);

(function() {
    // Extra logic goes here. (see below)
}).call(Mode.prototype);

exports.Mode = Mode;
});

ace.define('ace/mode/example_highlight_rules', function(require, exports, module) {

var oop = require("ace/lib/oop");
var TextHighlightRules = require("ace/mode/text_highlight_rules").TextHighlightRules;

var ExampleHighlightRules = function() {

    this.$rules = {
    		start: [{
                token: 'comment',
                regex: ';.*'
            },{
            	token: ['entity.name.function','text'],
            	regex: '^([^=]+)(\s*=)'
            },{
            	token: ['text','constant','text'],
            	regex: /(=?)([_\.!\d\w]+)(,\d+,|,hint,)/
            },{
            	token: 'variable',
            	regex: /\${.*}/
            },{
            	token: 'string',
            	regex: /"[^"]*"/
            },{
            	token: 'keyword.operator',
            	regex: /\$\[/,
            	next: 'eval'
            },{
            	token: ['text','constant','text'],
            	regex: /(n|[0-9]+)(\(.*\))(,)/
            },{
            	token: 'entity.name.section',
            	regex: /\[.*\]/
            }],
        eval: [
            {
                token: 'keyword.operator',
                regex: /\]/,
                next: 'start'
            },{
            	token: 'variable',
            	regex: /\${.*}/
            },{
            	token: 'string',
            	regex: /"[^"]*"/
            }]
    };

}

oop.inherits(ExampleHighlightRules, TextHighlightRules);

exports.ExampleHighlightRules = ExampleHighlightRules;
});