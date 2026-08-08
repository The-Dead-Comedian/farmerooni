# Termites

  ## About
  Termites are extremely intelligent, social and territorial(racist) insects that live in nests, in groups of up to 8 termites/nest. These nests can be commonly found in any type of forest.
They really like to eat wood. Which can be used in the player's favor to create an automatic wood farm.

  ## Nest
  Termites can be found naturally in a structure called the **Termite Nest**. These spawn in all forest biomes:
  - Cherry Grove
  - Windswept Forest
  - Forest
  - Flower Forest
  - Taiga
  - Old Growth Pine Taiga
  - Old Growth Spruce Taiga
  - Birch Forest
  - Old Growth Birch Forest
  - Dark Oak
  - Jungle  
  - Wooded Badlands

    When a **Termite Nest** spawns there is a 25% chance it spawns as an **abandoned Termite Nest**. These ones are the nests that spawn with **Termite Eggs** in them. The more common type, however,
    is the **wild Termite Nest**, essentially the same structure, without termite eggs, but with 6 termites already spawned. A very obvious visual cue for the presence of **Termite Nests** are pillars
    of **Putrid Wood** around said **Nest**

    The **Termite Nest** **STRUCTURE** always spawns with a **Termite Nest** **BLOCK ENTITY**. This block entity holds up to 18 stacks worth of wood, collected from the **Termites**.
    It can also house up to 8 **Termites**, which is indicated with one **Termite** head for each **Termite** in the ui. The **Termite Nest** block serves as the home for the **Termites**
    and is unobtainable.
  ## Behavior 

  **Termites** eat wood, no matter who placed it. They begin by mapping out the wood structure, and then return to the nest. And then return either alone or with a group, depending on the 
  custom gamerule, TermiteWorkInGroup. And break the wood, which they then pick up and return it to their nest. This scouting behavior works for any shape as long as the wooden blocks are connected,
  **Termites** can walk up walls, similar to spiders, but only when outside their nest.

  **Termites** have a rest mechanic, whenever they are outside they look for trees. If there is a tree they start scouting and detecting the wood as described above. Other wise there is a random chance
  for the **Termite** to get tired and go in the nest and rest for a bit.
      
    Technical note:
      What is defined as wood? There are 2 datapack files, prefix_wood.json , where you add a key word representative of the wood type.
      Eg: "oak" for oak planks, logs, etc.
      There is also the extra_wood.json block tag, where you can add individual blocks, for example crafting tables.

  **Termites** are very territorial, they will attack any termite which has no nest(unless said termite can be housed in their nest), or comes from another nest. They will also not get out of a certain
  range from their nest, this range increases with regional difficulty. This increases both the chance they find other termites, along with the area in which they break blocks.
  Now this also means the can affect player builds. There are 3 solutions for it, either don't use wood, use ignored wood types, like Crimson, Warped or Putrid wood, or just build out of their range.

  **Termites** grow hungry with time, so make sure not to take all the wood away from them. It takes them about 2 Minecraft days to become hungry after eating, and another one to slowly start taking 
  damage from starvation. The way termites eat is by consuming one item from their **Inventory**, if said **Inventory** is empty, they will go to the **Termite Nest** to obtain food, if it is empty they 
  will check on it every 15 minutes for food. While hungry the **Termite**'s chance to get tired increases by a lot, decreasing it's efficiency

  At night **Termites** return to their nest.

  **Termites** will also break leaves in their way as to allow them to properly scout out all trees. Along with this, they can pick up some of the saplings to replant trees.
  There is however a small chance this sapling is infested because of their disregard for Health Regulations, and will turn to mushrooms, upon contact with which they will take some withering damage,
  as they are allergic mushrooms. They are also allergic to water.

    Technical note:
      Termites operate of a schedule mechanic, similar to villagers. This establishes a routine for their behavior. EPXZZY PLEASE LIST THE BEHAVIOR ROUTINE/SEQUENCE HERE
  
  
