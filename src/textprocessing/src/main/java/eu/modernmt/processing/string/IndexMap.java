package eu.modernmt.processing.string;

/**
 * created by andrea on 25/02/17
 * <p>
 * An IndexMap object maps the position of each character in the current Editor local String
 * to the positions of the corresponding character in the original string version
 * kept in the SentenceBuilder.
 * <p>
 * In other words, given a position (character) in the local string of the current Editor
 * the IndexMap knows the corresponding position (character) in the original string version.
 */
public class IndexMap {
    /*array that actually represents the mapping:
    for each int i representing a position in the local string version of the current Editor,
    positions[i] represents the corresponding position in the original string version*/
    private int[] positions;
    /*Length of the relevant portion in the positions array;
    * (positions is managed as a buffer, so its constant length as a data structure
    * may not be the same as the length of its relevant portion)*/
    private int arrayLength;

    /**
     * This constructor initializes an IndexMap object
     * by creating and populating the positions array, on the basis of its required length.
     * <p>
     * The length is initially equal to the length of the original string + 1,
     * because the original string is in fact the first version
     * of local string that the first Editor stores;
     * In other words, a newly generated IndexMap
     * maps each position in the original string with itself.
     *
     * @param length is the length of the original string + 1.
     *               (The length is added 1 because this way it is possible
     *               to call the substring method on the last part of the string too).
     */
    public IndexMap(int length) {
        this.arrayLength = length;
        this.positions = new int[length + 1];

        /*positions is initialized so that in each position i it contains the value i itself:
        * therefore, at the very beginning it maps the original string with itself*/
        for (int i = 0; i < length + 1; i++) {
            positions[i] = i;
        }
    }

    /**
     * This method transforms the current positions array
     * by taking its portion between start (included) and end (not included)
     * and making it become as long as newLength says.
     * It is only invoked when a transformation is required that involves a replacement.
     *
     * @param start     the starting position of the array portion to transform
     * @param end       the position that follows the end of the portion to transform
     * @param newLength the length that the portion under analysis must assume
     * @return the new version of the positions array
     */
    public int[] update(int start, int end, int newLength) {

        /*the length of the text to replace*/
        int oldLength = end - start;
        /*the new length that the positions array must assume*/
        this.arrayLength = this.arrayLength - oldLength + newLength;

        /*if the replacement is longer than the text to replace*/
        if (newLength > oldLength) {
            /*if necessary, replace the whole array with a new bigger one*/
            if (this.arrayLength > this.positions.length) {
                int[] newArray = new int[arrayLength];
                System.arraycopy(this.positions, 0, newArray, 0, start);
                this.positions = newArray;
            }

            /*shift rightwards the array portion that follows the end of the text to replace*/
            shiftPortion(end, this.arrayLength, newLength - oldLength);
            /*update the positions between start and the new end
            * with values that distribute proportionally in relation to
            * the values at start and at the end of the new portion*/
            double ratio = ((double) newLength) / oldLength;
            for (int i = 1; i < newLength; i++) {
                this.positions[start + i] = (int) Math.round(this.positions[start] + ((this.positions[start + i] - positions[start]) * ratio));
            }
        }

        /*else, the replacement is shorter than the text to replace*/
        else {
            /*first update the portion between start and the new end
            * with values that distribute proportionally in relation to
            * the values at start and at the end of the new portion*/
            double ratio = ((double) newLength) / oldLength;
            for (int i = 1; i < newLength; i++) {
                this.positions[start + i] = (int) Math.round(this.positions[start] + ((this.positions[start + i] - positions[start]) * ratio));
            }
            /*after that shift leftwards the whole portion of the array
            * that comes after the end of the text to replace*/
            shiftPortion(end, this.positions.length, newLength - oldLength);
        }
        return this.positions;
    }

    /**
     * This method selects a portion of the array and
     * and shifts it backwards or forwards for a certain amount of positions.
     *
     * @param start     the index of the first position of the portion to shift
     * @param end       the index of the position that follows the end of the portion to shift
     * @param shiftStep the amount of positions that the portion must be shifted of.
     *                  if it is greater than 0, the shift must be shifted forwards;
     *                  if it is smaller than 0 the portion must be shifted backwards.
     */
    private void shiftPortion(int start, int end, int shiftStep) {
        int portionSize = end - start;
        int newStart = start + shiftStep;
        System.arraycopy(this.positions, start, this.positions, newStart, portionSize);
    }

    /**
     * Method that returns the value stored in a certain position of the indexMap array
     *
     * @param index the position where the target value is stored
     * @return the target value
     */
    public int get(int index) {
        return this.positions[index];
    }

    /**
     * Method that overwrites the value stored in a certain position of the indexMap array
     *
     * @param index the position the value that must be overwritten is stored
     * @param value the new value to write in position index
     */
    public void put(int index, int value) {
        this.positions[index] = value;
    }


    /*getters and setters*/

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public int getArrayLength() {
        return arrayLength;
    }

    public void setArrayLength(int arrayLength) {
        this.arrayLength = arrayLength;
    }
}