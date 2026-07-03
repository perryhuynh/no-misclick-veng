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

| Option | Default | Description |
|--------|---------|-------------|
| Show blocked message | on | Print a chat message when a recast is blocked |

## Development

All development tools are managed by [mise](https://mise.jdx.dev). Run
`mise install` to install the locked tool versions.

```
mise run build   # build + tests
mise run test    # unit tests
mise run lint    # checkstyle + PMD
mise run runelite  # launch a RuneLite dev client with the plugin loaded
```

## License

[WTFPL](LICENSE)
