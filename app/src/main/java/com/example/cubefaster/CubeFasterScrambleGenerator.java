//The MIT License (MIT)
//
//Copyright (c) 2016 Francisco Jesús Jiménez Hidalgo
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package com.example.cubefaster;
import java.util.Random;

/**
 * Creates a random algorithm to scramble the Rubik's Cube (3x3) with a number of moves
 * (25 recommended for a good scramble) defined by the user.
 *
 * Created by mrxuso on 13/07/16.
 */

public class CubeFasterScrambleGenerator {

        /**
         * MOVE -> 1 - U, 2 - L, 3 - F, 4 - D, 5 - R, 6 - B
         * ROTATION -> 1 - X, 2 - 2X, 3 - X', 4 - 2X
         */
        int mLastFace = 0, mTwoFacesAgo = 0;
        private String mScramble = "";

        /**
         * Constructor: create a scramble algorithm with "algorithmLength" number of moves
         * @param algorithmLength Define the length of the algorithm
         */
        public CubeFasterScrambleGenerator(int algorithmLength) {
            for (int i = 1; i <= algorithmLength; i++) {
                mScramble += getNextMove();
            }
        }

        /**
         * Next face we move in the algorithm, it is different from the last move and from the face moved
         * two times ago if the opposite face was moved in the last move (i.e. R L 2R is incorrect, it should be R' L instead).
         * @return random A number between 1 and 6 (both included) --> 1 - U, 2 - L, 3 - F, 4 - D, 5 - R, 6 - B <--
         */
        private int getNextFace(){
            int random = (new Random().nextInt(6) + 1);

            /*We generate a face different from the one two moves ago if we moved
             * the opposite face in the previous move and different from the last face we moved
             */
            while (random == mTwoFacesAgo && random%3 == mLastFace%3 || random == mLastFace) {
                random = (new Random().nextInt(6) + 1);
            }

            //We save the last face moved and the one moved two times ago
            mTwoFacesAgo = mLastFace;
            mLastFace = random;

            //We return the random number of the face
            return random;
        }
        /**
         * Next rotation of the face (there are no constraints) --> 1 - X, 2 - 2X, 3 - X', 4 - 2X <--
         */
        private int getNextRotation(){

            //We return a random number between 1 and 4 indicating the rotation of the face
            return (new Random().nextInt(4) + 1);
        }

        /**
         * Concatenates the rotation and the movement (X, X', 2X)
         */
        private String getNextMove(){
            String nextMove = "";

            // --> 1 - U, 2 - L, 3 - F, 4 - D, 5 - R, 6 - B <--
            switch (getNextFace()){
                case 1:
                    nextMove = "U";
                    break;
                case 2:
                    nextMove = "L";
                    break;
                case 3:
                    nextMove = "F";
                    break;
                case 4:
                    nextMove = "D";
                    break;
                case 5:
                    nextMove = "R";
                    break;
                case 6:
                    nextMove = "B";
                    break;
            }

            // --> 1 - X, 2 - 2X, 3 - X', 4 - 2X <--
            switch (getNextRotation()){
                case 2:
                    nextMove = nextMove + "2";
                    break;
                case 3:
                    nextMove = nextMove + "'";
                    break;
                case 4:
                    nextMove = nextMove + "2";
                    break;
            }

            //We add a space between the moves
            return nextMove + "   ";
        }

        /**
         * @return mScramble The full algorithm for scrambling the cube
         */
        public String getScramble(){
            return mScramble;
        }
}
