# GildedRose code refactoring kata
Had some fun with Java doing the [GildedRose code refactoring kata](https://github.com/emilybache/GildedRose-Refactoring-Kata).

Learned a bunch about java.util.Arrays and bumped my head a couple times forgetting that nearly every copy operation does a shallow copy.

### Requirements

Find the requirements (and restrictions!) for this kata here: <a href="./GildedRoseRequirements.txt">Gilded Rose Requirements</a>

### Before/After & Tests

For the "before" code, click [here](https://gitlab.com/NielsRenard/gilded-rose-java/blob/1af0aaf9cd5c3780de33e55059aff36c934a96d9/src/main/java/com/gildedrose/GildedRose.java)

Refactored code is [here](./src/main/java/com/gildedrose/GildedRose.java)

For the tests I wrote click [here](./src/test/java/com/gildedrose/GildedRoseTest.java)


### How to run

If you have (gnu) Make available, in a terminal, run:  

`make run`

If you're on a system without Make, run it as such:  

`mvn package`

`java -jar ./target/gilded-rose-kata-0.0.1-SNAPSHOT-jar-with-dependencies.jar`

You should see something like this:  

    Welcome to the Gilded Rose inventory management system
    -------- day 0 --------
    name, sellIn, quality
    +5 Dexterity Vest, 10, 20
    Aged Brie, 2, 0
    Elixir of the Mongoose, 5, 7
    Sulfuras, Hand of Ragnaros, 0, 80
    Sulfuras, Hand of Ragnaros, -1, 80
    Backstage passes to a TAFKAL80ETC concert, 15, 20
    Backstage passes to a TAFKAL80ETC concert, 10, 49
    Backstage passes to a TAFKAL80ETC concert, 5, 49
    Conjured Mana Cake, 3, 6
	
	-------- day 1 --------
    name, sellIn, quality
    +5 Dexterity Vest, 9, 19
    Aged Brie, 1, 1
    Elixir of the Mongoose, 4, 6
    Sulfuras, Hand of Ragnaros, 0, 80
    Sulfuras, Hand of Ragnaros, -1, 80
    Backstage passes to a TAFKAL80ETC concert, 14, 21
    Backstage passes to a TAFKAL80ETC concert, 9, 51
    Backstage passes to a TAFKAL80ETC concert, 4, 52
    Conjured Mana Cake, 2, 5
