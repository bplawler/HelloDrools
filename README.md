HelloDrools
===========

Various experiments working with Scala and the Drools Expert rules engine.  The goal here was to gain some familiarity with how the rules engine actually works without the added complexity of working with it inside of my larger application.  I am proving out some concepts generically, mostly around logicalInserts into the WorkingMemory and tracking that inserts are then retracted when the rules that initially "justified" those facts are no longer true.

This was also a good opportunity to re-familiarize myself with specs tests.  Weird API, but once you get it it's pretty cool...