# PumpkinBomber Projectile Assets

Drop production projectile sprites in this folder using the exact filenames below so runtime picks them up automatically:

- pumpkin_straight_projectile.png
- pumpkin_lob_projectile.png
- pumpkin_beam_core_segment.png

Current behavior:
- If these files exist, JavaFX projectile rendering uses them.
- If a file is missing, JavaFX falls back to generated placeholder visuals.

Path in classpath:
- /main/game/maze/projectiles/pumpkinbomber/
