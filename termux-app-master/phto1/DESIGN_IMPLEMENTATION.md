# Luxury UI redesign

## Visual system
- Deep midnight background with indigo/cyan/purple accents.
- Rounded 14–36dp surfaces and thin blue-violet strokes.
- Premium session cards with an active-state gradient.
- Branded drawer header with app icon, status and settings action.
- Refined session spacing and bottom action bar.
- Extra-key toolbar palette moved from flat black/red to midnight/indigo/cyan.
- Existing Termux terminal functionality and IDs are preserved.

## Main source files changed
- `app/src/main/res/layout/activity_termux.xml`
- `app/src/main/res/layout/item_terminal_sessions_list.xml`
- `app/src/main/res/values/colors.xml`
- `app/src/main/res/values/styles.xml`
- `app/src/main/res/values/themes.xml`
- `app/src/main/res/values-night/themes.xml`
- `app/src/main/res/values/strings.xml`
- New luxury drawable resources under `app/src/main/res/drawable/`

## Validation
The project was structurally edited, but a local Gradle build could not be completed in this environment because Gradle attempted to download its distribution and outbound network access was unavailable.
