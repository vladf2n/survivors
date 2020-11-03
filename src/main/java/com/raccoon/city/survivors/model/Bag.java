package com.raccoon.city.survivors.model;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Bag {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Boolean blocked;

	@OneToMany(mappedBy = "bag", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SupplyBag> supplyBags;

	@PrePersist
	@PreUpdate
	public void prePersist() {
		supplyBags.forEach(supplyBag -> supplyBag.setBag(this));
	}

	public Map<Supply, Integer> inventory() {
		return supplyBags.stream().collect(Collectors.toMap(SupplyBag::getSupply, SupplyBag::getQuantity));
	}

	public Integer totalSupplyPoints() {
		return supplyBags.stream().mapToInt(SupplyBag::totalPoints).reduce(0, Integer::sum);
	}

	public Boolean containsAllItemsFrom(Bag otherBag) {
		final Map<Supply, Integer> inventory = inventory();

		return otherBag.getSupplyBags().stream().allMatch(supplyBag -> {
			Integer ownedAmount = inventory.getOrDefault(supplyBag.getSupply(), 0);

			return ownedAmount >= supplyBag.getQuantity();
		});
	}

	public void receiveItemsFrom(Bag otherBag) {
		updateInventory(otherBag.getSupplyBags(), (supplyBag, quantity) -> supplyBag.add(quantity));
	}

	public void removeItemsFrom(Bag otherBag) {
		updateInventory(otherBag.getSupplyBags() , (supplyBag, quantity) -> supplyBag.remove(quantity));
	}

	private void updateInventory(List<SupplyBag> updatedItems, BiConsumer<SupplyBag, Integer> action) {
		final Map<Supply, SupplyBag> supplyBagMap = supplyBags.stream().collect(Collectors.toMap(SupplyBag::getSupply, it -> it));

		updatedItems.forEach(item -> {
			SupplyBag supplyBag = supplyBagMap.get(item.getSupply());

			if (supplyBag != null) {
				action.accept(supplyBag, item.getQuantity());

				if (supplyBag.getQuantity() <= 0) {
					supplyBagMap.remove(item.getSupply());
					this.supplyBags.remove(supplyBag);
				}
			} else {
				SupplyBag newSupplyBag = SupplyBag.of(item.getSupply(), 0);
				action.accept(newSupplyBag, item.getQuantity());

				if (newSupplyBag.getQuantity() > 0) {
					newSupplyBag.setBag(this);

					supplyBagMap.put(item.getSupply(), newSupplyBag);
					this.supplyBags.add(newSupplyBag);
				}
			}
		});
	}

}
