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

  public static int updateQuality(Item item, int rate) {
    return (item.quality + rate);
  }

  public static int updateExpiration(Item item) {
    return item.sellIn - 1;
  }

  public static Item updateBrie(Item brie) {
    brie.quality = updateQuality(brie, 1);
    brie.sellIn = updateExpiration(brie);
    return brie;
  }

  public static Item updateBackstagePasses(Item passes) {
    if (passes.sellIn <= 5) {
      passes.quality = updateQuality(passes, +3);
      return passes;
    }
    if (passes.sellIn <= 10) {
      passes.quality = updateQuality(passes, +2);
      return passes;
    } else {
      passes.quality = updateQuality(passes, +1);
      return passes;
    }
  }

  public static Item updateRegularItem(Item item) {
    if(item.quality > 0){
      item.quality = updateQuality(item, -1);
    }
    return item;
  }

  public void updateQuality() {
    // TODO: pass items in as a parameter
    Item[] updatedItems = Arrays.copyOf(items, items.length);

    for (Item item : updatedItems) {
      if (!item.name.equals("Aged Brie") && !item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        if (item.quality > 0) {
          if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item = updateRegularItem(item);
          }
        }
      } else {
        // quality of brie and passes increases over time
        if (item.quality < 50) {
          if (item.name.equals("Aged Brie")) {
            item = updateBrie(item);
          }
          if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            item = updateBackstagePasses(item);
          }
        }
      }

      if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
        item.sellIn = updateExpiration(item);
      }

      if (item.sellIn < 0) {
        if (!item.name.equals("Aged Brie")) {
          if (!item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
            if (item.quality > 0) {
              if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                item.quality = item.quality - 1;
              }
            }
          } else {
            item.quality = item.quality - item.quality;
          }
        } else {
          if (item.quality < 50) {
            item.quality = item.quality + 1;
          }
        }
      }
    }
    items = updatedItems;
  }
}
