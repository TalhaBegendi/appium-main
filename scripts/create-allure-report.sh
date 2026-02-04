#!/bin/bash

cd "$(dirname "$0")/.." || exit 0

export MAVEN_OPTS="--add-opens=java.base/sun.misc=ALL-UNNAMED -Djava.awt.headless=true"

echo "📊 Allure raporu oluşturuluyor..."

mvn -q allure:report 2>&1 | grep -v "WARNING:" || exit 0
echo "✅ Allure raporu hazır (IDE için)."

allure generate \
  --single-file \
  --clean \
  -o report/allure-report-html \
  report/allure-result

echo "✅ Allure HTML hazır."