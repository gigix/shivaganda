#!/usr/bin/env node

var express = require('express');
var app = express();

app.use(express.static(__dirname + '/..'));

app.listen(8003);
console.log('Listening on port 8003');
