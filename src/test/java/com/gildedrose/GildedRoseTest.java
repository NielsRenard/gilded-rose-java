package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

  @Test
  void ExpiredDegradesTwiceAsFast() {
    Item[] items = new Item[] { new Item("foo", 1, 3) };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(0, itemsOneTick[0].sellIn);
    assertEquals(2, itemsOneTick[0].quality);

    Item[] itemsTwoTick = GildedRose.updateQuality(itemsOneTick);
    assertEquals(-1, itemsTwoTick[0].sellIn);
    assertEquals(1, itemsTwoTick[0].quality);

    Item[] itemsThreeTick = GildedRose.updateQuality(itemsTwoTick);
    // the item has now expired, and degrades twice as fast
    assertEquals(-2, itemsThreeTick[0].sellIn);
    assertEquals(0, itemsThreeTick[0].quality);
  }

  @Test
  void ItemQualityNeverNegative() {
    Item[] items = new Item[] { new Item("foo", 2, 1),
                                new Item("Conjured Mana Cake", 2, 2)};
    // sellIn positive -> positive
    Item[] itemsOneTick = GildedRose.updateQuality(items);

    assertEquals(1, itemsOneTick[0].sellIn);
    assertEquals(0, itemsOneTick[0].quality);
    // conjured
    assertEquals(1, itemsOneTick[1].sellIn);
    assertEquals(0, itemsOneTick[1].quality);

    // sellIn positive -> zero
    Item[] itemsTwoTick = GildedRose.updateQuality(itemsOneTick);
    assertEquals(0, itemsTwoTick[0].sellIn);
    assertEquals(0, itemsTwoTick[0].quality);

    // conjured
    assertEquals(0, itemsTwoTick[1].sellIn);
    assertEquals(0, itemsTwoTick[1].quality);

    // sellIn zero -> negative
    Item[] itemsThreeTick = GildedRose.updateQuality(itemsTwoTick);
    assertEquals(-1, itemsThreeTick[0].sellIn);
    assertEquals(0, itemsThreeTick[0].quality);
    // conjured
    assertEquals(-1, itemsThreeTick[1].sellIn);
    assertEquals(0, itemsThreeTick[1].quality);

    // sellIn negative -> negative
    Item[] itemsFourTick = GildedRose.updateQuality(itemsThreeTick);
    assertEquals(-2, itemsFourTick[0].sellIn);
    assertEquals(0, itemsFourTick[0].quality);

    // conjured
    assertEquals(-2, itemsFourTick[1].sellIn);
    assertEquals(0, itemsFourTick[1].quality);
  }

  @Test
  void BrieAndPassesQualityIncreasesWithAge() {
    int initialQuality = 0;
    Item[] items = new Item[] {
      new Item("Aged Brie", 1, initialQuality),
      new Item("Backstage passes to a TAFKAL80ETC concert", 1, initialQuality),
    };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertTrue(itemsOneTick[0].quality > initialQuality);
    assertTrue(itemsOneTick[1].quality > initialQuality);
  }

  @Test
  void PassesQualityIncreasesFasterNearConcert() {
    int initialQuality = 0;
    String backstagePasses = "Backstage passes to a TAFKAL80ETC concert";
    Item[] items = new Item[] {
      new Item(backstagePasses, 12, initialQuality),
      new Item(backstagePasses, 10, initialQuality),
      new Item(backstagePasses, 5, initialQuality),
    };

    int regularIncrease = 1;
    int tenDaysOutIncrease = 2;
    int fiveDaysOutIncrease = 3;

    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(initialQuality + regularIncrease, itemsOneTick[0].quality);
    assertEquals(initialQuality + tenDaysOutIncrease, itemsOneTick[1].quality);
    assertEquals(initialQuality + fiveDaysOutIncrease, itemsOneTick[2].quality);
  }

  @Test
  void PassesWorthlessAfterConcert() {
    Item[] items = new Item[] {
      new Item("Backstage passes to a TAFKAL80ETC concert", 0, 50),
    };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(0, itemsOneTick[0].quality);
  }


  @Test
  void QualityNeverAboveFifty() {
    Item[] items = new Item[] { new Item("Aged Brie", 1, 50) };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(50, itemsOneTick[0].quality);
  }

  @Test
  void SulfurasDoesNotDegrade() {
    Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(80, itemsOneTick[0].quality);
  }

  @Test
  void ConjuredDegradeTwiceAsFast() {
    Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 6) };
    Item[] itemsOneTick = GildedRose.updateQuality(items);
    assertEquals(-1, itemsOneTick[0].sellIn);
    assertEquals(4, itemsOneTick[0].quality);

    // the item has now expired, and degrades twice as fast
    Item[] itemsTwoTick = GildedRose.updateQuality(itemsOneTick);
    assertEquals(-2, itemsTwoTick[0].sellIn);
    assertEquals(0, itemsTwoTick[0].quality);
  }

  // ======================================
  // Gilded Rose Requirements Specification
  // ======================================

  // ✓- Once the sell by date has passed, Quality degrades twice as fast
  // ✓- The Quality of an item is never negative
  // ✓- "Aged Brie" actually increases in Quality the older it gets
  // ✓- The Quality of an item is never more than 50
  // ✓- Once the sell by date has passed, Quality degrades twice as fast
  // ✓- The Quality of an item is never negative
  // ✓- "Aged Brie" actually increases in Quality the older it gets
  // ✓- The Quality of an item is never more than 50
  // ✓- "Sulfuras", being a legendary item, never has to be sold or decreases in
  // Quality
  // ✓- "Backstage passes", like aged brie, increases in Quality as its SellIn
  // value approaches;
  // ✓-Quality increases by 2 when there are 10 days or less and by 3 when there are
  // 5 days or less but
  // ✓-Quality drops to 0 after the concert

  // We have recently signed a supplier of conjured items. This requires an update
  // to our system:

  // ✓ "Conjured" items degrade in Quality twice as fast as normal items

  // Just for clarification, an item can never have its Quality increase above 50,
  // however "Sulfuras" is a
  // legendary item and as such its Quality is 80 and it never alters.
}
