
Rabbits spawn next to player made farmland.
They like to eat crops from said farms. This returns the farm to their earliest stage, if a crop is broken when the mob isn't hungry, the loot is dropped.
This helps automate [[Farming]] especially with the help of [[Allay]] to store the dropped harvest.


Rabbits are a passive mob.
(This is a partial overhaul of the behaviour)

### Main Gimmick


If fully grown crops are near the rabbit, they will go toward said crop and de-age it. A cooldown representing hunger will dictate whether the crop drops the loot or not.

They will also avoid eating [[Pest]] infected crops.

### Hunger
Hunger is a hidden mechanic of any animal we re-do. This means there is a secret meter that goes up to 100 (for obvious reasons). That will tick down every time the animal does any activity besides resting. Hunger can be gained back by eating. In this case eating crops.

Rabbits also eat [[Clovers]]. Which can spawn next to their burrows. When being eaten the clover doesnt actually break.

Hunger modifiers:

- Walking: -1 hunger/minute
- Running away: -5 hunger/minute
- Burrowing: -20 hunger, once
- Sex: -30 hunger, once
- Eating crops: +10 hunger
- Eating clovers: +5 hunger/ time eaten


### Sex life

If rabbits witness another rabbit get murdered, they will look to breed. They will birth 1-4 babies. And then go on cool down for a long time.

### Predators

Rabbits are small scared animals. Which makes them ideal prey for **Wolves**, **Foxes**, ***Falcons***(when they're added), and of course **Players**.

Rabbits will run away from these mobs if they get too close, (in the case of players this can be bypassed by holding food in hand, as part of vanilla behaviour).
While running, if they happen to be close enough to their [[Burrow]]. They will run inside and hide for a certain period of time.

### Spawning

Rabbits spawn in the biomes they do usually. Or nearby player placed  farm land (has to be above a certain why level, and probably requires some sort of check to make sure theres direct access to the sky)


(Technical note)
If a rabbit is spawned because of a farm. The rabbit will spawn with the "Farm" flag enabled.


### Home

When a rabbit gets spawned, their first priority will be to make a [[Burrow]] in the nearest **burrowable** block. This will be determined by a block tag. Which by default will include:

- Grass
- Dirt
- Coarse Dirt

Said block will then be broken and replaced with a [[Burrow]] that will look essentially the same but with a hole displayed.

##### My mom kinda homeless

If a rabbit is homeless, they will look for nearby [[Burrow]]s. If there is an available [[Burrow]], they will be housed there. If not they will create their own as explained above.
