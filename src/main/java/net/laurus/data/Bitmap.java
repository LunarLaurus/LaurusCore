package net.laurus.data;

import java.io.Serializable;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The Bitmap class represents a fixed-size bitmap structure to manage bits for network operations.
 * It uses a BitSet internally to efficiently store and manipulate individual bits, typically used
 * for marking IPv4 addresses or any other binary data. The class allows setting, clearing, flipping,
 * and retrieving bits within a specified size.
 * <p>
 * This class is thread-safe.
 * </p>
 */
public class Bitmap implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Internal BitSet that stores the bits of the bitmap.
     */
    private final BitSet bitSet;

    /**
     * The size of the bitmap, representing the total number of bits.
     */
    private final int size;

    /**
     * Constructs a bitmap of a specific size.
     *
     * @param size The size of the bitmap, i.e., the number of bits it can hold.
     * @throws IllegalArgumentException If the size is non-positive.
     */
    public Bitmap(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Bitmap size must be positive.");
        }
        this.size = size;
        this.bitSet = new BitSet(size);
    }

    /**
     * Retrieves the size of the bitmap.
     *
     * @return The total number of bits in the bitmap.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the bit at the given index in the bitmap.
     *
     * @param index The index of the bit to set. This should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public synchronized void setBit(int index) {
        validateIndex(index);
        bitSet.set(index);
    }

    /**
     * Clears the bit at the given index in the bitmap.
     *
     * @param index The index of the bit to clear. This should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public synchronized void clearBit(int index) {
        validateIndex(index);
        bitSet.clear(index);
    }

    /**
     * Flips the bit at the given index in the bitmap.
     *
     * @param index The index of the bit to flip. This should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public synchronized void flipBit(int index) {
        validateIndex(index);
        bitSet.flip(index);
    }

    /**
     * Retrieves the value of the bit at the given index.
     *
     * @param index The index of the bit to retrieve. This should be in the range [0, size-1].
     * @return The current value of the bit at the specified index (true if set, false if not).
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public synchronized boolean getBit(int index) {
        validateIndex(index);
        return bitSet.get(index);
    }

    /**
     * Finds all the indexes of the active (set) bits in the bitmap.
     *
     * @return A list of indexes where the bit is set (true).
     */
    public synchronized List<Integer> getActiveIndexes() {
        return IntStream.range(0, size)
                .filter(bitSet::get)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of set bits (1s) in the bitmap.
     *
     * @return The number of set bits in the bitmap.
     */
    public synchronized int countSetBits() {
        return bitSet.cardinality();
    }

    /**
     * Clears all bits in the bitmap (sets all bits to 0).
     */
    public synchronized void clearAll() {
        bitSet.clear();
    }

    /**
     * Sets multiple bits in the bitmap.
     *
     * @param indexes The indexes of the bits to set. Each index should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If any index is out of bounds.
     */
    public synchronized void setBits(List<Integer> indexes) {
        indexes.forEach(this::setBit);
    }

    /**
     * Clears multiple bits in the bitmap.
     *
     * @param indexes The indexes of the bits to clear. Each index should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If any index is out of bounds.
     */
    public synchronized void clearBits(List<Integer> indexes) {
        indexes.forEach(this::clearBit);
    }

    /**
     * Flips multiple bits in the bitmap.
     *
     * @param indexes The indexes of the bits to flip. Each index should be in the range [0, size-1].
     * @throws IndexOutOfBoundsException If any index is out of bounds.
     */
    public synchronized void flipBits(List<Integer> indexes) {
        indexes.forEach(this::flipBit);
    }

    /**
     * Converts the bitmap to a binary string representation.
     *
     * @return A string representing the bitmap (e.g., "101010").
     */
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            sb.append(bitSet.get(i) ? '1' : '0');
        }
        return sb.toString();
    }

    /**
     * Validates that the provided index is within the bounds of the bitmap.
     *
     * @param index The index to validate.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds for the bitmap: " + index);
        }
    }

    /**
     * Checks equality based on the size and bitSet.
     *
     * @param o The object to compare with.
     * @return true if both Bitmap instances have the same size and bitSet; false otherwise.
     */
    @Override
    public synchronized boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bitmap)) return false;
        Bitmap bitmap = (Bitmap) o;
        return size == bitmap.size && Objects.equals(bitSet, bitmap.bitSet);
    }

    /**
     * Generates a hash code based on the size and bitSet.
     *
     * @return The hash code of the bitmap.
     */
    @Override
    public synchronized int hashCode() {
        return Objects.hash(bitSet, size);
    }
}
