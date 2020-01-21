package com.gildedrose;

// However, do not alter the Item class or Items property as those belong to the
// goblin in the corner who will insta-rage and one-shot you as he doesn't believe in shared code
// ownership.
public class Item {

  public String name;

  public int sellIn;

  public int quality;

  public Item(String name, int sellIn, int quality) {
    this.name = name;
    this.sellIn = sellIn;
    this.quality = quality;
  }

  @Override
  public String toString() {
    return this.name + ", " + this.sellIn + ", " + this.quality;
  }
}
