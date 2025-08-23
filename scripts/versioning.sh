#!/bin/bash

VERSION_FILE="buildSrc/src/main/java/AppVersion.kt"

function getVersionName() {
  VERSION_NAME=$(grep 'const val NAME' $VERSION_FILE | sed -E 's/.*"([^"]+)".*/\1/')
  echo "$VERSION_NAME"
}

function getVersionCode() {
  VERSION_CODE=$(grep 'const val CODE' $VERSION_FILE | sed -E 's/.* ([0-9]+).*/\1/')
  echo "$VERSION_CODE"
}

function bumpVersionCode() {
  CURRENT_CODE=$(getVersionCode)
  NEW_CODE=$((CURRENT_CODE + 1))
  sed -i.bak -E "s/(const val CODE = )([0-9]+)/\1$NEW_CODE/" $VERSION_FILE
  rm "$VERSION_FILE.bak"
  echo "$NEW_CODE"
}

function bumpMajorVersion() {
  CURRENT_NAME=$(getVersionName)
  IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_NAME"
  MAJOR=$((MAJOR + 1))
  MINOR=0
  PATCH=0
  NEW_NAME="$MAJOR.$MINOR.$PATCH"
  sed -i.bak -E "s/(const val NAME = \")([0-9]+\.[0-9]+\.[0-9]+)(\")/\1$NEW_NAME\3/" $VERSION_FILE
  rm "$VERSION_FILE.bak"
  echo "$NEW_NAME"
}

function bumpMinorVersion() {
  CURRENT_NAME=$(getVersionName)
  IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_NAME"
  MINOR=$((MINOR + 1))
  PATCH=0
  NEW_NAME="$MAJOR.$MINOR.$PATCH"
  sed -i.bak -E "s/(const val NAME = \")([0-9]+\.[0-9]+\.[0-9]+)(\")/\1$NEW_NAME\3/" $VERSION_FILE
  rm "$VERSION_FILE.bak"
  echo "$NEW_NAME"
}

function bumpPatchVersion() {
  CURRENT_NAME=$(getVersionName)
  IFS='.' read -r MAJOR MINOR PATCH <<< "$CURRENT_NAME"
  PATCH=$((PATCH + 1))
  NEW_NAME="$MAJOR.$MINOR.$PATCH"
  sed -i.bak -E "s/(const val NAME = \")([0-9]+\.[0-9]+\.[0-9]+)(\")/\1$NEW_NAME\3/" $VERSION_FILE
  rm "$VERSION_FILE.bak"
  echo "$NEW_NAME"
}
