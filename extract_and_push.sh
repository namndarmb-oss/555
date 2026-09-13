#!/usr/bin/env bash
set -euo pipefail

REPO_URL="https://github.com/namndarmb-oss/555.git"
ZIP_NAME="یک_اپلیکیشن_حسابداری_حرفه_ای_1789294678765.zip"
BRANCH="unpack-zip-$(date +%s)"
WORKDIR=$(mktemp -d)

echo "Cloning repo into $WORKDIR"
git clone "$REPO_URL" "$WORKDIR"
cd "$WORKDIR"

echo "Creating branch $BRANCH"
git checkout -b "$BRANCH"

if [ ! -f "$ZIP_NAME" ]; then
  echo "ERROR: Zip file '$ZIP_NAME' not found in repo root. Listing files:"
  ls -la
  exit 1
fi

echo "Unzipping $ZIP_NAME into repo root"
unzip -o "$ZIP_NAME" -d .

echo "Staging changes"
git add .

if git diff --staged --quiet; then
  echo "No changes to commit"
else
  git config user.name "your-name"
  git config user.email "you@example.com"
  git commit -m "Unpack zip: $ZIP_NAME"
  echo "Pushing branch $BRANCH"
  git push origin "$BRANCH"
  echo "Branch pushed. Create a PR from branch $BRANCH."
fi
