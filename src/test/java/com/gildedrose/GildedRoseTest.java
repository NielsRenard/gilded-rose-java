package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

  // Initial test
  @Test
  void foo() {
    Item[] items = new Item[] { new Item("foo", 0, 0) };
    GildedRose app = new GildedRose(items);
    app.updateQuality(items);
    assertEquals("foo", app.items[0].name);
  }

  @Test
  void ExpiredDegradesTwiceAsFast() {
    Item[] items = new Item[] { new Item("foo", 1, 3) };
    GildedRose app = new GildedRose(new Item[]{});
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(0, itemsOneTick[0].sellIn);
    assertEquals(2, itemsOneTick[0].quality);

    Item[] itemsTwoTick = app.updateQuality(itemsOneTick);
    assertEquals(-1, itemsTwoTick[0].sellIn);
    assertEquals(1, itemsTwoTick[0].quality);

    Item[] itemsThreeTick = app.updateQuality(itemsTwoTick);
    // the item has now expired, and degrades twice as fast
    assertEquals(-2, itemsThreeTick[0].sellIn);
    assertEquals(-1, itemsThreeTick[0].quality);
  }

  @Test
  void ItemQualityNeverNegative() {
    Item[] items = new Item[] { new Item("foo", 2, 0) };
    GildedRose app = new GildedRose(items);

    // sellIn positive -> positive
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(1, itemsOneTick[0].sellIn);
    assertEquals(0, itemsOneTick[0].quality);

    // sellIn positive -> zero
    Item[] itemsTwoTick = app.updateQuality(itemsOneTick);
    assertEquals(0, itemsOneTick[0].sellIn);
    assertEquals(0, itemsOneTick[0].quality);

    // sellIn zero -> negative
    Item[] itemsThreeTick = app.updateQuality(itemsTwoTick);
    assertEquals(-1, itemsThreeTick[0].sellIn);
    assertEquals(0, itemsThreeTick[0].quality);

    // sellIn negative -> negative
    Item[] itemsFourTick = app.updateQuality(itemsThreeTick);
    assertEquals(-2, itemsFourTick[0].sellIn);
    assertEquals(0, itemsFourTick[0].quality);
  }

  @Test
  void BrieAndPassesQualityIncreasesWithAge() {
    //TODO: Make backstage passes logic generic, i.e. not just for TAFKAL80ETC concerts
    int initialQuality = 0;
    Item[] items = new Item[] {
      new Item("Aged Brie", 1, initialQuality),
      new Item("Backstage passes to a TAFKAL80ETC concert", 1, initialQuality),
    };
    GildedRose app = new GildedRose(items);
    Item[] itemsOneTick = app.updateQuality(items);
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

    GildedRose app = new GildedRose(items);
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(initialQuality + regularIncrease, itemsOneTick[0].quality);
    assertEquals(initialQuality + tenDaysOutIncrease, itemsOneTick[1].quality);
    assertEquals(initialQuality + fiveDaysOutIncrease, itemsOneTick[2].quality);
  }

  @Test
  void PassesWorthlessAfterConcert() {
    Item[] items = new Item[] {
      new Item("Backstage passes to a TAFKAL80ETC concert", 0, 50),
    };
    GildedRose app = new GildedRose(items);
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(0, itemsOneTick[0].quality);
  }


  @Test
  void QualityNeverAboveFifty() {
    // TODO: Deal with items initialized with a quality of 51 or higher,
    // as this is currently possible and breaks the requirement.
    Item[] items = new Item[] { new Item("Aged Brie", 1, 50) };
    GildedRose app = new GildedRose(items);
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(50, itemsOneTick[0].quality);
  }

  @Test
  void SulfurasDoesNotDegrade() {
    Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
    GildedRose app = new GildedRose(items);
    Item[] itemsOneTick = app.updateQuality(items);
    assertEquals(80, itemsOneTick[0].quality);
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

  // - "Conjured" items degrade in Quality twice as fast as normal items

  // Feel free to make any changes to the UpdateQuality method and add any new
  // code as long as everything
  // still works correctly. However, do not alter the Item class or Items property
  // as those belong to the
  // goblin in the corner who will insta-rage and one-shot you as he doesn't
  // believe in shared code
  // ownership (you can make the UpdateQuality method and Items property static if
  // you like, we'll cover
  // for you).

  // Just for clarification, an item can never have its Quality increase above 50,
  // however "Sulfuras" is a
  // legendary item and as such its Quality is 80 and it never alters.

}
