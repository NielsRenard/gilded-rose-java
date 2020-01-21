package com.gildedrose;

import java.util.Stack;

final class GildedRose {
  // However, do not alter the Item class or Items property (...)
  Item[] items;

  public static final String AGED_BRIE = "Aged Brie";
  public static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public void setItems(Item[] items){
    this.items = items;
  }

  public static void main(String[] args) {
    System.out.println("Welcome to the Gilded Rose inventory management system");
  }

  // Takes a list of Items, returns a new list of updated items.
  public static Item[] updateQuality(Item[] items) {
    Stack<Item> updatedItems = new Stack<Item>();
    for (Item item : items) {
      switch(item.name) {
        case AGED_BRIE: item = updateBrie(item); break;
        case BACKSTAGE_PASS: item = updateBackstagePasses(item); break;
        case SULFURAS: break;
        default: item = updateRegularItem(item);
      }
      updatedItems.push(new Item(item.name, item.sellIn, item.quality));
    }
    return updatedItems.toArray(new Item[items.length]);
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
    if (passes.sellIn == 0) {
      passes.quality = 0;
    }
    else if (passes.sellIn <= 5) {
      passes.quality = updateItemQuality(passes, +3);
    }
    else if (passes.sellIn <= 10) {
      passes.quality = updateItemQuality(passes, +2);
    } else {
      passes.quality = updateItemQuality(passes, +1);
    }
    passes.sellIn = updateExpiration(passes);
    return passes;
  }

  public static Item updateRegularItem(Item item) {
    if (item.quality > 0) { // TODO: would be nice to have this 'if' in updateItemQuality
      item.quality = updateItemQuality(item, -1);
    }
    item.sellIn = updateExpiration(item);
    return item;
  }
}
