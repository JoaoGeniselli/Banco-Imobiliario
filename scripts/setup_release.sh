#!/bin/bash

VERSION_TYPE=$1
LOCAL_REPOSITORY_HAS_CHANGES=$(git status --porcelain)

echo "Checking for uncommitted changes in the local repository..."
if [ -n "$LOCAL_REPOSITORY_HAS_CHANGES" ]; then
  echo "Error: You have uncommitted changes in your local repository. Please commit or stash them before proceeding."
  exit 1
fi

if [ $VERSION_TYPE != "major" ] && [ $VERSION_TYPE != "minor" ] && [ $VERSION_TYPE != "patch" ]; then
  echo "Error: Invalid version type. Please specify 'major', 'minor', or 'patch'."
  exit 1
fi

if [ -z "$VERSION_TYPE" ]; then
  echo "Error: No version type specified. Please provide 'major', 'minor', or 'patch' as an argument."
  exit 1
fi

#git switch develop

echo "Pulling latest changes from remote..."
git pull origin develop

echo "Bumping $VERSION_TYPE version..."

if [ $VERSION_TYPE == "major" ]; then
  NEW_VERSION=$(./scripts/versioning.sh bumpMajorVersion)
elif [ $VERSION_TYPE == "minor" ]; then
  NEW_VERSION=$(./scripts/versioning.sh bumpMinorVersion)
elif [ $VERSION_TYPE == "patch" ]; then
  NEW_VERSION=$(./scripts/versioning.sh bumpPatchVersion)
fi

NEW_CODE=$(./scripts/versioning.sh bumpVersionCode)

echo "Bumped version to $NEW_VERSION (code $NEW_CODE)"
echo "Creating and switching to branch release/v$NEW_VERSION"

git switch -c release/v$NEW_VERSION
git add buildSrc/src/main/java/AppVersion.kt
git commit -m "chore: Bump version to $NEW_VERSION (code $NEW_CODE)"
#git push -u origin release/v$NEW_VERSION

echo "Release branch release/v$NEW_VERSION created and pushed to remote."
echo "You can now create a pull request to merge release/v$NEW_VERSION into main."
echo "After merging, don't forget to tag the release."


