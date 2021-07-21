tell application "Finder"
    activate
    end tell
tell application "System Events"
    key down {command}
    keystroke "n"
    key up {command}
    end tell
