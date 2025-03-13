val colors = listOf("red", "blue", "green", "yellow", "purple", "orange", "black", "white", "brown", "pink")
val animals = listOf("lion", "tiger", "elephant", "giraffe", "zebra", "kangaroo", "panda", "monkey", "rabbit", "cheetah")

fun main() {
    println("Welcome to Hangman!")
    println("Choose a category: (1) Colors (2) Animals")
    val choice = readln()
    val wordToGuess = when (choice) {
        "1" -> colors.random()
        "2" -> animals.random()
        else -> {
            println("Invalid choice. Defaulting to Colors.")
            colors.random()
        }
    }

    playHangman(wordToGuess)
}

fun playHangman(word: String) {
    val wordChars = word.toCharArray().toSet()
    val guessedChars = mutableSetOf<Char>()
    var attempts = 6
    var humanParts = 0

    val humanStages = listOf(
        "",
        " O",
        " O\n |",
        " O\n/|",
        " O\n/|\\",
        " O\n/|\\\n/",
        " O\n/|\\\n/ \\",
    )

    while (attempts > 0) {
        val displayWord = word.map { if (it in guessedChars) it else '_' }.joinToString(" ")
        println("\nWord: $displayWord")
        println("Attempts left: $attempts")
        println("Enter a letter:")

        val input = readln().lowercase()
        if (input.isEmpty() || input.length != 1 || !input[0].isLetter()) {
            println("Invalid input. Please enter a single letter.")
            continue
        }

        val guessedLetter = input[0]
        if (guessedLetter in guessedChars) {
            println("You already guessed that letter!")
            continue
        }

        guessedChars.add(guessedLetter)
        if (guessedLetter in wordChars) {
            humanParts = (humanParts + 1).coerceAtMost(humanStages.size - 1)
            println("Correct guess! Building the human:")
        } else {
            attempts--
            println("Wrong guess!")
        }

        println(humanStages[humanParts])

        if (wordChars.all { it in guessedChars }) {
            println("\nCongratulations! You guessed the word: $word")
            return
        }
    }
    println("\nGame Over! The word was: $word")
}
