package ch.nocego.runeWritings.model

class Alphabet {
    companion object {
        private val ltr = LetterToRunes()
        private val runesAlphabeticallyOrdered = arrayListOf<Rune>(
            ltr.getRuneInstanceOfOneChar('A')!!,
            ltr.getRuneInstanceOfOneChar('B')!!,
            ltr.getRuneInstanceOfOneChar('C')!!,
            ltr.getRuneInstanceOfOneChar('D')!!,
            ltr.getRuneInstanceOfOneChar('E')!!,
            ltr.getRuneInstanceOfOneChar('F')!!,
            ltr.getRuneInstanceOfOneChar('G')!!,
            ltr.getRuneInstanceOfOneChar('H')!!,
            ltr.getRuneInstanceOfOneChar('I')!!,
            ltr.getRuneInstanceOfOneChar('J')!!,
            ltr.getRuneInstanceOfOneChar('K')!!,
            ltr.getRuneInstanceOfOneChar('L')!!,
            ltr.getRuneInstanceOfOneChar('M')!!,
            ltr.getRuneInstanceOfOneChar('N')!!,
            ltr.getRuneInstanceOfOneChar('O')!!,
            ltr.getRuneInstanceOfOneChar('P')!!,
            ltr.getRuneInstanceOfOneChar('Q')!!,
            ltr.getRuneInstanceOfOneChar('R')!!,
            ltr.getRuneInstanceOfOneChar('S')!!,
            ltr.getRuneInstanceOfOneChar('T')!!,
            ltr.getRuneInstanceOfOneChar('U')!!,
            ltr.getRuneInstanceOfOneChar('V')!!,
            ltr.getRuneInstanceOfOneChar('W')!!,
            ltr.getRuneInstanceOfOneChar('X')!!,
            ltr.getRuneInstanceOfOneChar('Y')!!,
            ltr.getRuneInstanceOfOneChar('Z')!!
        )

        private val alphabetUpperCase = arrayListOf<String>(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )

        fun runesAlphabeticallyOrdered(): ArrayList<Rune> {
            return runesAlphabeticallyOrdered
        }

        fun runeObjectAtIndex(index: Int): Rune {
            return runesAlphabeticallyOrdered[index]
        }

        fun alphabetUpperCase(): ArrayList<String> {
            return alphabetUpperCase
        }

        fun indexOfLetter(letter: String): Int {
            return alphabetUpperCase.indexOfFirst { alphabetLetter -> alphabetLetter == letter }
        }
    }
}