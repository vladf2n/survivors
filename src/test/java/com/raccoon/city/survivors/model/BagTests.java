package com.raccoon.city.survivors.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.raccoon.city.survivors.TestUtils;

public class BagTests {

  @Test
  void shouldCalculateAllSuppliesPoints() {
    Bag bag = TestUtils.getResource("first_survivor_bag", Bag.class);

    int expectedPoints = 332;

    assertEquals(expectedPoints, bag.totalSupplyPoints());
  }

  @Test
  void shouldConfirmContainsAllItemsFrom() {
    Bag bag = TestUtils.getResource("first_survivor_bag", Bag.class);
    Bag otherBag = TestUtils.getResource("first_proposal_bag", Bag.class);

    assertTrue(bag.containsAllItemsFrom(otherBag));
  }

  @Test
  void shouldReceiveAllItems() {
    Bag bag = TestUtils.getResource("first_survivor_bag", Bag.class);
    Bag otherBag = TestUtils.getResource("first_proposal_bag", Bag.class);

    bag.receiveItemsFrom(otherBag);

    Map<Supply, Integer> inventory = bag.inventory();

    int expectedFijiWaters = 15;
    int expectedCampbellSoups = 16;
    int expectedAk47s = 6;

    assertEquals(expectedFijiWaters, inventory.get(new Supply().withId(1)));
    assertEquals(expectedCampbellSoups, inventory.get(new Supply().withId(2)));
    assertEquals(expectedAk47s, inventory.get(new Supply().withId(4)));
  }

  @Test
  void shouldRemoveItems() {
    Bag bag = TestUtils.getResource("first_survivor_bag", Bag.class);
    Bag otherBag = TestUtils.getResource("first_proposal_bag", Bag.class);

    bag.removeItemsFrom(otherBag);

    Map<Supply, Integer> inventory = bag.inventory();

    int expectedFijiWaters = 5;
    int expectedCampbellSoups = 12;

    assertEquals(expectedFijiWaters, inventory.get(new Supply().withId(1)));
    assertEquals(expectedCampbellSoups, inventory.get(new Supply().withId(2)));
    assertNull(inventory.get(new Supply().withId(4)));
  }

  @Test
  void shouldAddNewItemsFromOtherBags() {
    Bag bag = TestUtils.getResource("first_survivor_bag", Bag.class);

    List<SupplyBag> supplyBags = Collections.singletonList(SupplyBag.of(new Supply().withId(3), 3));

    Bag otherBag = Bag.builder().supplyBags(supplyBags).build();

    bag.receiveItemsFrom(otherBag);

    Map<Supply, Integer> inventory = bag.inventory();
    int expectedFirstAidPouches = 3;

    assertEquals(expectedFirstAidPouches, inventory.get(new Supply().withId(3)));
  }
}
