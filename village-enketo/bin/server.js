#!/usr/bin/env node

var express = require('express');
var app = express();

app.use(express.static(__dirname + "/../www"));

app.listen(8002);
console.log('Listening on port 8002');
