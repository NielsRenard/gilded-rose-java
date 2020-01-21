package com.gildedrose;

import java.util.Stack;

final class GildedRose {
  // However, do not alter the Item class or Items property (...)
  Item[] items;

  public static final String AGED_BRIE = "Aged Brie";
  public static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
  public static final String CONJURED = "Conjured Mana Cake";

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public void setItems(Item[] items){
    this.items = items;
  }

  public static void main(String[] args) {
    System.out.println("Welcome to the Gilded Rose inventory management system");

    Item[] items = new Item[] {
      new Item("+5 Dexterity Vest", 10, 20),
      new Item("Aged Brie", 2, 0),
      new Item("Elixir of the Mongoose", 5, 7),
      new Item("Sulfuras, Hand of Ragnaros", 0, 80),
      new Item("Sulfuras, Hand of Ragnaros", -1, 80),
      new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
      new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
      new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
      // this conjured item does not work properly yet
      new Item("Conjured Mana Cake", 3, 6) };

    GildedRose app = new GildedRose(items);

    int days = 3;
    if (args.length > 0) {
      days = Integer.parseInt(args[0]) + 1;
    }

    for (int i = 0; i < days; i++) {
      System.out.println("-------- day " + i + " --------");
      System.out.println("name, sellIn, quality");
      for (Item item : items) {
        System.out.println(item);
      }
      System.out.println();
      app.setItems(GildedRose.updateQuality(items));
    }

  }

  // Takes a list of Items, returns a new list of updated items.
  public static Item[] updateQuality(Item[] items) {
    Stack<Item> updatedItems = new Stack<Item>();
    for (Item item : items) {
      switch(item.name) {
        case AGED_BRIE: item = updateBrie(item); break;
        case BACKSTAGE_PASS: item = updateBackstagePasses(item); break;
        case CONJURED: item = updateRegularOrConjuredItem(item, true); break;
        case SULFURAS: break;
        default: item = updateRegularOrConjuredItem(item, false);
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

  private static int updateExpiration(Item item) {
    return item.sellIn - 1;
  }

  private static Item updateBrie(Item brie) {
    brie.quality = updateItemQuality(brie, 1);
    brie.sellIn = updateExpiration(brie);
    return brie;
  }

  private static Item updateBackstagePasses(Item passes) {
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

  private static Item updateRegularOrConjuredItem(Item item, Boolean conjured) {
    if (item.quality > 0) { // TODO: would be nice to have this 'if' in updateItemQuality
      if (conjured) {
        item.quality = updateItemQuality(item, -2);
      } else {
        item.quality = updateItemQuality(item, -1);
      }
    }
    item.sellIn = updateExpiration(item);
    return item;
  }
}
