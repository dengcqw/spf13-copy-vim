#!/bin/sh

jazzy \
  --clean \
  --author ReactiveCocoa \
  --github_url https://github.com/realm/realm-cocoa \
  --github-file-prefix https://github.com/ReactiveCocoa/ReactiveCocoa/tree/master \
  --module-version 5.0.3 \
  --xcodebuild-arguments -scheme,ReactiveCocoa-iOS \
  --root-url http://127.0.0.1/ \
  --output docs/swift_output \
  --theme apple
