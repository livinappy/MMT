package eu.modernmt.processing.string2;

/**
 * An IndexMap...
 */
public class IndexMap {

    /*l'array e la sua lunghezza "utile" sono variabili dell'indexMap!*/
    private int arrayLength;
    private int[] positions;

    public IndexMap(int[] positions) {
        this.arrayLength = positions.length;
        this.positions = positions;
    }

    /*semantica: da start a end lo devo far diventare lungo length*/
    public int[] updateArray(int start, int end, int newLength) {
        //int transformationStartIndex = transformation.getStart();
        //int transformationEndIndex = transformation.getEnd();
        //int replacedTextLength = transformationEndIndex - transformationStartIndex;
        //int placeholderLength = transformation.getReplacement().length();

        int oldLength = end-start;

        this.arrayLength = this.arrayLength - oldLength + newLength;

        if (oldLength < newLength) {
            int a = this.arrayLength;
            int b = this.positions.length;
            if(this.arrayLength > this.positions.length) {
                int[] newArray = new int[arrayLength];
                System.arraycopy(this.positions, 0, newArray, 0, start);
                this.positions = newArray;
            }

            // else: è il caso in cui l'array iniziale è comunque abbastanza grande da
            // poter sopportare la nuova arrayLength senza dover essere ricreato da capo.


            double ratio = ((double) newLength) / oldLength;

            for (int i = 1; i < newLength; i++) {

                positions[start + i] = (int)Math.round(positions[start] + ((positions[start + i] - positions[start]) * ratio));
                //positions[start + i] += (int) (i * ratio);

            }

            shiftArrayPortion(end, this.arrayLength, newLength - oldLength);


        } else if (oldLength > newLength) {

            double ratio = ((double) newLength) / oldLength;
            int i;

            for (i = start+1; i < start + newLength; i++) {
                positions[start + i] = (int)Math.round(positions[start] + ((positions[start + i] - positions[start]) * ratio));
            }

            shiftArrayPortion(end, positions.length, newLength - oldLength);
        }

        return positions;
    }


    /**
     * This method takes the IndexMap array, selects a portion
     * and shifts it backwards or forwards for a certain amount of positions.
     *
     * @param portionStart the index corresponding to the start of the portion to shift
     * @param portionEnd the index corresponding to the start of the portion to shift
     * @param shiftStep the amount of positions that the portion must be shifted of.
     *                  if it is greater than 0, the shift must be shifted forwards;
     *                  if it is smaller than 0 the portion must be shifted backwards.
     */
    public void shiftArrayPortion(int portionStart, int portionEnd, int shiftStep) {

        int portionSize = portionEnd - portionStart;
        int newStart = portionStart + shiftStep;

        System.arraycopy(this.positions, portionStart, this.positions, newStart, portionSize);

    }

    public int getArrayLength() {
        return arrayLength;
    }

    public void setArrayLength(int arrayLength) {
        this.arrayLength = arrayLength;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

}