# Rabbits

  ## About
  While **rabbits** are a vanilla mob, like many vanilla animals they have a lot of lost potential.
So they will be some of the animals to benefit from at least partial revamp.

  ## Behavior
  In order to create an ecosystem around the rabbits, they must have interactions. Both with the player and the shared environment.

  The first major change will be their interactions with player crops. Rabbits will now have a chance to spawn around every player made crop. This chance starts off incredibly low. But increases with the number of crops in the area.
  
    Technical note:
        rabbits dont spawn infinitely, there would probably be a mob cap for them, hell maybe it also increases with regional difficulty
.

    Just an idea:
        Maybe chances would be calculated per "tiled soil patch", aka how many of the soil blocks are touching, 
        and the chance increases exponentially.

        And then the chances of each patch would get added together.
        This would probably incentivise interesting designs for farms, where fewer blocks would be placed together. And have patches slightly separated.
        Maybe this would also increase the speed of crop growing. 
        Since on larger touching surfaces the crops would consume nutrients from that area of soil.
        Therefore by making smaller patches less nutrients would be consumed from the same area of soil or something.
    

        TL DR if im not coheerent. We discourage large surface farms by making them have crops grow slower and rabbits spawn more often.
        By doing so, the player would at least be incentivised to think about their farm layout, so its smaller, more productive overall and rabbits spawn less.

  When rabbits are near soil, they will go and harvest/ eat it (return it to their youngest stage). They will then eat some of the items dropped, to satiate their hunger, which will be a value we monitor.
  (They do not eat melons and other gourds id say)
  The rest of the items will just exist there. Therefore:
  - generally it will make their items go to waste, by having them despawn
  - allow players to use allays to harvest the crops essentially automatically