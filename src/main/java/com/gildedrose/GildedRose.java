package com.gildedrose;

import java.util.Arrays;
import java.util.Scanner;

class GildedRose {
  // However, do not alter the Item class or Items property (...)
  Item[] items;

  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
  private static final String CONJURED = "Conjured Mana Cake";

  public GildedRose(final Item[] items) {
    this.items = items;
  }

  public void setItems(final Item[] items){
    this.items = items;
  }

  // Simulates 1 day passing and returns a new list with updated Items.
  // (Does not mutate original list)
  public static Item[] updateQuality(final Item[] items) {
    return Arrays.stream(items)
        .map(GildedRose::update)
        .toArray(Item[]::new);
  }

  //// Update helper methods

  private static Item update(Item item) {
    Item updatedQuality = updateQualityByName(item);
    Item updatedQualityAndSellIn = updateSellIn(updatedQuality);
    return updatedQualityAndSellIn;
  }

  private static Item updateQualityByName(Item item){
    switch(item.name) {
      case AGED_BRIE: return updateBrie(item);
      case BACKSTAGE_PASS: return updateBackstagePasses(item);
      case CONJURED: return updateRegularOrConjuredItem(item, true);
      case SULFURAS: return updateSulfuras(item);
      default: return updateRegularOrConjuredItem(item, false);
    }
  }

  private static Item updateItemQuality(final Item item, int rate) {
    if (item.sellIn < 0) rate *= 2; // expired items degrade twice as fast
    final int newQuality = (item.quality + rate);
    final int positive = newQuality > 0 ? newQuality : 0;
    final int validQuality = positive < 50 ? positive : 50;
    return new Item(item.name, item.sellIn, validQuality);

  }

  private static Item updateSellIn(final Item item) {
    if (item.name.equals(SULFURAS)) return item; // legendary items do not change
    Item updatedItem = new Item (item.name, item.sellIn - 1, item.quality);
    return updatedItem;
  }

  //// Item specific update methods

  private static Item updateBrie(final Item brie) {
    Item updatedBrie = updateItemQuality(brie, 1);
    return updatedBrie;
  }

  private static Item updateBackstagePasses(final Item passes) {
    if (passes.sellIn <= 0) {
      return new Item (passes.name, passes.sellIn, 0);
    }
    else if (passes.sellIn <= 5) {
      return updateItemQuality(passes, +3);
    }
    else if (passes.sellIn <= 10) {
      return updateItemQuality(passes, +2);
    }
    else {
      return updateItemQuality(passes, +1);
    }
  }

  private static Item updateSulfuras(final Item sulfuras) {
    return new Item(SULFURAS, sulfuras.sellIn, sulfuras.quality);
  }

  private static Item updateRegularOrConjuredItem(final Item item, final Boolean conjured) {
    if (conjured) {
      // conjured items degrade at twice the normal rate
      return updateItemQuality(item, -2);
    } else {
      return updateItemQuality(item, -1);
    }
  }

  //// An interactive main that prints all the items

  public static void main(final String[] args) {
    final Item[] items = new Item[] {
      new Item("+5 Dexterity Vest", 10, 20),
      new Item("Aged Brie", 2, 0),
      new Item("Elixir of the Mongoose", 5, 7),
      new Item("Sulfuras, Hand of Ragnaros", 0, 80),
      new Item("Sulfuras, Hand of Ragnaros", -1, 80),
      new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
      new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
      new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49),
      new Item("Conjured Mana Cake", 3, 6) };
    final GildedRose app = new GildedRose(items);

    System.out.println("Welcome to the Gilded Rose inventory management system");
    try (Scanner scanner = new Scanner(System.in)){
      int day = 0;
      while(true) {
        System.out.println("Press Enter key to move to the next day.");
        System.out.println("Or q to quit.");
        final String userInput = scanner.nextLine();
        if (userInput.equals("q")) break;
        System.out.println("-------- day " + day + " --------");
        System.out.println("name, sellIn, quality");
        for (final Item item : app.items) {
          System.out.println(item);
        }
        System.out.println();
        app.setItems(GildedRose.updateQuality(app.items));
        day += 1;
      }
    }
  }
}
