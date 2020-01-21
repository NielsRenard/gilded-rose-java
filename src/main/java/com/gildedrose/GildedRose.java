package com.gildedrose;

import java.util.Arrays;

class GildedRose {
  // However, do not alter the Item class or Items property (...)
  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public static void main(String[] args) {
    System.out.println("Welcome to the Gilded Rose inventory management system");
  }

  public void updateQuality() {
    // TODO: pass items in as a parameter
    Item[] updatedItems = Arrays.copyOf(items, items.length);

    for (Item item : updatedItems) {
      if (item.name.equals("Aged Brie")) {
        item = updateBrie(item);
      } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        item = updateBackstagePasses(item);
      } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
        // no op for legendary items
      } else {
        item = updateRegularItem(item);
      }
    }
    items = updatedItems;
  }

  public static int updateItemQuality(Item item, int rate) {
    if (item.quality >= 50) {
      return item.quality;
    }
    if (item.sellIn < 0) {
      // expired items degrade twice as fast
      rate *= 2;
    }
    return (item.quality + rate);
  }

  public static int updateExpiration(Item item) {
    return item.sellIn - 1;
  }

  public static Item updateBrie(Item brie) {
    brie.quality = updateItemQuality(brie, 1);
    brie.sellIn = updateExpiration(brie);
    return brie;
  }

  public static Item updateBackstagePasses(Item passes) {
    // TODO: Get rid of updateExpiration duplication
    if (passes.sellIn == 0) {
      passes.quality = 0;
      passes.sellIn = updateExpiration(passes);
      return passes;
    }
    if (passes.sellIn <= 5) {
      passes.quality = updateItemQuality(passes, +3);
      passes.sellIn = updateExpiration(passes);
      return passes;
    }
    if (passes.sellIn <= 10) {
      passes.quality = updateItemQuality(passes, +2);
      passes.sellIn = updateExpiration(passes);
      return passes;
    } else {
      passes.quality = updateItemQuality(passes, +1);
      passes.sellIn = updateExpiration(passes);
      return passes;
    }
  }

  public static Item updateRegularItem(Item item) {
    if (item.quality > 0) {
      item.quality = updateItemQuality(item, -1);
    }
    item.sellIn = updateExpiration(item);
    return item;
  }
}
