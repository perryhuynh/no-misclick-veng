# No Misclick Veng

RuneLite plugin that blocks recasting Vengeance while your current Vengeance
is still active.

The game stops you from recasting during the 30-second cooldown, but once the
cooldown expires, it will let you recast while your existing Vengeance
is still active, leading to wasted runes or even missed damage.

## Features

- **Blocks wasted recasts** - clicking Vengeance while it is active
  is consumed client-side. Tracks the real server-synced state (varbit), so it
  works across spellbook swaps, etc.
- **Greys out the spell icon** - while Vengeance is active, and restores it the
  moment it procs.
- Optional chat message when a recast is blocked.

## Configuration

- **Show blocked message** (on by default) - print a chat message when a
  recast is blocked.

## Support

Found a bug or have a feature request? Open an issue on
[GitHub](https://github.com/perryhuynh/no-misclick-veng/issues).

## License

[WTFPL](LICENSE)
