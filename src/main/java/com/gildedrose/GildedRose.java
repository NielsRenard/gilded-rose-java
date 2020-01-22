package com.gildedrose;

import java.util.Scanner;
import java.util.Stack;

final class GildedRose {
  // However, do not alter the Item class or Items property (...)
  Item[] items;

  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
  private static final String CONJURED = "Conjured Mana Cake";

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public void setItems(Item[] items){
    this.items = items;
  }

  // Simulates 1 day passing and returns a new list with updated Items.
  // (Does not mutate original list)
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
      item.sellIn = updateSellIn(item);
      updatedItems.push(new Item(item.name, item.sellIn, item.quality));
    }
    return updatedItems.toArray(new Item[items.length]);
  }

  //// Generic update methods
  
  private static int updateItemQuality(Item item, int rate) {
    if (item.quality + rate >= 50) {
      return 50;
    }
    if (item.sellIn < 0) {
      // expired items degrade twice as fast
      rate *= 2;
    }
    int newQuality = (item.quality + rate);
    return newQuality > 0 ? newQuality : 0;
  }

  private static int updateSellIn(Item item) {
    return item.sellIn - 1;
  }

  //// Item specific update methods
  
  private static Item updateBrie(Item brie) {
    brie.quality = updateItemQuality(brie, 1);
    return brie;
  }

  private static Item updateBackstagePasses(Item passes) {
    if (passes.sellIn <= 0) {
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
    return passes;
  }

  private static Item updateRegularOrConjuredItem(Item item, Boolean conjured) {
    if (conjured) {
      // conjured items degrade at twice the normal rate
      item.quality = updateItemQuality(item, -2);
    } else {
      item.quality = updateItemQuality(item, -1);
    }
    return item;
  }

  //// An interactive main that prints all the items
  
  public static void main(String[] args) {
    Item[] items = new Item[] {
      new Item("+5 Dexterity Vest", 10, 20),
      new Item("Aged Brie", 2, 0),
      new Item("Elixir of the Mongoose", 5, 7),
      new Item("Sulfuras, Hand of Ragnaros", 0, 80),
      new Item("Sulfuras, Hand of Ragnaros", -1, 80),
      new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
      new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
      new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49),
      new Item("Conjured Mana Cake", 3, 6) };
    GildedRose app = new GildedRose(items);

    System.out.println("Welcome to the Gilded Rose inventory management system");
    try (Scanner scanner = new Scanner(System.in)){
      int day = 0;
      while(true) {
        System.out.println("Press Enter key to move to the next day.");
        System.out.println("Or q to quit.");
        String userInput = scanner.nextLine();
        if (userInput.equals("q")) break;
        System.out.println("-------- day " + day + " --------");
        System.out.println("name, sellIn, quality");
        for (Item item : items) {
          System.out.println(item);
        }
        System.out.println();
        app.setItems(GildedRose.updateQuality(items));
        day += 1;
      }
    }
  }
}
