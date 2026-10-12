/**
 * This file defines a child class named HandBag.
 * We have provided most of the code for this class, however,
 * you should implement the provided TODOs.
 *
 * You may find the readings in chapter 3. Relationships between Classes
 * helpful while working through this exercise.
 */
public class HandBag extends Bag{

    /**
     * Creates a new HandBag with the given color and
     * capacity.
     *
     * @param color the color of the bag
     * @param capacity the number of items the bag can store
     */
    public HandBag(String color, int capacity) {
        /*
         * This is how we call the parent's constructor
         * The Python equivalent is super().__init__(...)
         */
        super(color, capacity);
    }

    /**
     * Increase the capacity of this bag by 1.
     */
    @Override
    public void enhance() {
        /* Implement this method.
         *       You may want to use the increaseCapacity() method that was
         *       implemented in Bag.
         *
         * To call a method defined in a parent, you use super.method_name(...)
         */
        super.increaseCapacity(1);
    }
}