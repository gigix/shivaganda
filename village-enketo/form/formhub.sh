#!/bin/sh

curl -X POST -ssl3 -d "server_url=https://formhub.org/gigix&form_id=Patient" https://enketo.formhub.org/transform/get_html_form