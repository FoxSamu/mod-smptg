package net.foxboi.summon.api.recipe;

import java.util.*;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

@NullMarked
public class StonecuttingGraph {
    private final Map<Item, Node> nodes = new HashMap<>();
    private final Map<ItemPair, Edge> edges = new HashMap<>();

    private static Identifier idOf(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public void add(ItemLike from, ItemLike to) {
        add(from, to, 1, false);
    }

    public void add(ItemLike from, ItemLike to, int amount) {
        add(from, to, amount, false);
    }

    public void add(ItemLike from, ItemLike to, boolean ignoreInOutput) {
        add(from, to, 1, ignoreInOutput);
    }

    public void add(ItemLike from, ItemLike to, int amount, boolean ignoreInOutput) {
        var fromItem = from.asItem();
        var toItem = to.asItem();

        if (fromItem == toItem) {
            throw new IllegalArgumentException("Edge from %s to itself is not allowed".formatted(idOf(fromItem)));
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount should be positive");
        }


        var edgeKey = new ItemPair(fromItem, toItem);

        // Check if edge exists
        var existingEdge = edges.get(edgeKey);
        if (existingEdge != null) {
            if (existingEdge.amount == amount) {
                return; // The edge exists already and is consistent, we don't need to do anything
            }

            // Edge exists but amount is different, this is an inconsistency.
            var fromName = idOf(fromItem);
            var toName = idOf(toItem);
            throw new IllegalArgumentException(
                    "Edge from %s to %s already exists with a different amount %d (registered amount was %d)"
                            .formatted(fromName, toName, existingEdge.amount, amount)
            );
        }

        // No edge exists, register new one
        var fromNode = node(fromItem);
        var toNode = node(toItem);

        var edge = new Edge(fromNode, toNode, amount, ignoreInOutput);
        edges.put(edgeKey, edge);

        fromNode.outgoing.add(edge);
    }

    private Node node(Item item) {
        return nodes.computeIfAbsent(item, Node::new);
    }

    public List<TableEntry> buildTable() {
        record Deduction(Item from, Node node, int amount, boolean ignore, @Nullable Deduction previous) {
            String printChain() {
                var chain = new ArrayDeque<Deduction>();
                var cur = this;
                while (cur != null) {
                    chain.add(cur);
                    cur = cur.previous;
                }

                var amt = 1;
                var builder = new StringBuilder();
                for (var element : chain) {
                    builder.append(amt).append(" x ");
                    builder.append(idOf(element.from));
                    builder.append(" -> ");

                    amt = element.amount;
                }

                builder.append(amt).append(" x ");
                builder.append(idOf(node.item));

                return builder.toString();
            }
        }

        var closure = new HashMap<ItemPair, Deduction>();
        var queue = new ArrayDeque<Deduction>();

        // Add all outgoing edges from all known nodes
        for (var root : nodes.values()) {
            for (var outgoing : root.outgoing) {
                queue.add(new Deduction(root.item, outgoing.to, outgoing.amount, outgoing.ignore, null));
            }
        }

        // Find transitive closure
        Deduction current;
        while ((current = queue.poll()) != null) {
            var node = current.node;
            var from = current.from;
            var to = node.item;
            var amount = current.amount;
            var ignore = current.ignore;
            var pair = new ItemPair(from, to);

            // Check if we deduced this edge previously
            var previous = closure.get(pair);
            if (previous != null) {
                if (previous.amount != amount) {
                    // Inconsistency!
                    throw new IllegalStateException(
                            ("""
                                    Graph is inconsistent: an edge from %s to %s was deduced to have amount %d, but an earlier deduction found an amount of %d.
                                        Previous deduction: %s
                                        Current deduction: %s
                                    The cause might be a cycle!!""")
                                    .formatted(
                                            idOf(from), idOf(to), amount, previous.amount,
                                            previous.printChain(),
                                            current.printChain()
                                    )
                    );
                } else {
                    // Consistent, and since this edge has been deducted before, we know that further deductions
                    // resulting from this edge are already enqueued or processed, so we don't need to do anything more.

                    // Note that we may reach here because of a cycle. In stonecutter terms, that would mean an item can
                    // ultimately be crafted into itself through that cycle without duplicating the item. That is fine.
                    continue;
                }
            }

            // Add to closure if not present
            closure.put(pair, current);

            // Add transitive deductions to queue
            for (var outgoing : node.outgoing) {
                var next = new Deduction(from, outgoing.to, outgoing.amount * amount, outgoing.ignore && ignore, current);
                queue.add(next);
            }
        }

        // Compute result by unwrapping amounts
        var result = new ArrayList<TableEntry>();
        for (var entry : closure.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();
            result.add(new TableEntry(key.from, key.to, value.amount));
        }

        return result;
    }

    private static class Node {
        final Item item;

        final Set<Edge> outgoing = new HashSet<>();

        private Node(Item item) {
            this.item = item;
        }
    }

    private record Edge(Node from, Node to, int amount, boolean ignore) {
    }

    private record ItemPair(Item from, Item to) {
    }

    public record TableEntry(Item from, Item to, int amount) {
    }
}
