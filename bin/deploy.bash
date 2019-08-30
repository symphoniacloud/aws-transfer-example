#!/bin/bash

set -e

mvn clean package

sam package \
  --s3-bucket ${1} \
  --output-template-file target/packaged-template.yaml

sam deploy \
  --capabilities CAPABILITY_IAM \
  --template-file target/packaged-template.yaml \
  --stack-name aws-transfer-example
