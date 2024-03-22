package com.decoded.imagepoem

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.ArrayList

class LimerickGenerator {
    private val rhymeDictionary = mapOf(
        "person" to listOf("version", "tension", "pension"),
        "bicycle" to listOf("cycle", "psychic", "mystic"),
        "car" to listOf("far", "guitar", "bizarre"),
        "motorcycle" to listOf("bottle", "throttle", "little"),
        "airplane" to listOf("complain", "explain", "campaign"),
        "bus" to listOf("fuss", "discuss", "thus"),
        "train" to listOf("rain", "pain", "again"),
        "truck" to listOf("luck", "stuck", "muck"),
        "boat" to listOf("coat", "float", "goat"),
        "traffic light" to listOf("might", "bright", "fright"),
        "fire hydrant" to listOf("giant", "defiant", "reliant"),
        "stop sign" to listOf("define", "align", "shine"),
        "parking meter" to  listOf("greeter", "cheater", "heater"),
        "bench" to listOf("clench", "wrench", "length"),
        "bird" to listOf("word", "heard", "abhorred"),
        "cat" to listOf("hat", "sat", "mat"),
        "dog" to listOf("fog", "bog", "log"),
        "horse" to listOf("course", "source", "force"),
        "sheep" to listOf("sleep", "deep", "weep"),
        "cow" to listOf("now", "vow", "how"),
        "elephant" to listOf("elegant", "pregnant", "fragment"),
        "bear" to listOf("there", "where", "care"),
        "zebra" to listOf("zebra", "fever", "cleaver"), // Handle same word as noun
        "giraffe" to listOf("laugh", "half", "calf"),
        "backpack" to listOf("attack", "smack", "crack"),
        "umbrella" to listOf("remember", "member", "December"),
        "handbag" to listOf("rag", "wag", "stag"),
        "tie" to listOf("sky", "high", "cry"),
        "suitcase" to listOf("face", "embrace", "space"),
        "frisbee" to listOf("maybe", "baby", "crazy"),
        "skis" to listOf("bliss", "miss", "kiss"),
        "snowboard" to listOf("hoard", "sword", "afford"),
        "sports ball" to listOf("sprawl", "yawn", "dawn"),
        "kite" to listOf("flight", "sight", "bright"),
        "baseball bat" to listOf("hat", "sat", "cat"), // Reuse existing rhyme
        "baseball glove" to listOf("love", "above", "shove"),
        "skateboard" to listOf("hoard", "sword", "afford"), // Reuse existing rhyme
        "surfboard" to listOf("afford", "sword", "hoard"), // Reuse existing rhyme
        "tennis racket" to listOf("packet", "jacket", "exact"),
        "bottle" to listOf("throttle", "little", "mumble"),  // Reuse existing rhyme
        "wine glass" to listOf("surpass", "class", "mass"),
        "cup" to listOf("sup", "up", "pup"),
        "fork" to listOf("cork", "york", "pork"),
        "knife" to listOf("life", "wife", "strife"),
        "spoon" to listOf("moon", "boon", "croon"),
        "bowl" to listOf("whole", "soul", "stroll"),
        "banana" to listOf("Pyjama", "drama", "karma"),  // Use a more creative rhyme
        "apple" to listOf("dapple", "saddle", "cattle"),
        "sandwich" to listOf("vanish", "finish", "diminish"),
        "orange" to listOf("change", "range", "strange"),
        "broccoli" to listOf("holly", "lollipop", "poppy"),
        "carrot" to listOf("parrot", "harlot", "scarlet"),
        "hot dog" to listOf("frog", "bog", "log"), // Reuse existing rhyme
        "pizza" to listOf("riddle", "fiddle", "middle"),
        "donut" to listOf("nut", "strut", "cut"),
        "cake" to listOf("ache", "break", "mistake"),
        "chair" to listOf("stare", "dare", "care"),
        "couch" to listOf("vouch", "ouch", "slouch"),
        "potted plant" to listOf("slant", "chant", "grant"),
        "bed" to listOf("shed", "ahead", "read"),
        "dining table" to listOf("fable", "stable", "label"),
        "toilet" to listOf("exploit", "admit", "knit"),
        "tv" to listOf("me", "see", "glee"),
        "laptop" to listOf("hop", "stop", "pop"),
        "mouse" to listOf("house", "blouse", "douse"),
        "remote" to listOf("coat", "float", "boat"), // Reuse existing rhyme
        "keyboard" to listOf("bored", "assured", "secured"),
        "cell phone" to listOf("alone", "known", "moan"),
        "microwave" to listOf("wave", "brave", "rave"),
        "oven" to listOf("gloven", "proven", "shoven"),
        "toaster" to listOf("coaster", "boaster", "roster"),
        "sink" to listOf("think", "blink", "chink"),
        "refrigerator" to listOf("regulator", "navigator", "eliminator"),
        "book" to listOf("look", "brook", "cook"),
        "clock" to listOf("stock", "jock", "rock"),
        "vase" to listOf("embrace", "face", "space"), // Reuse existing rhyme
        "scissors" to listOf("victorious", "glorious", "notorious"),
        "teddy bear" to listOf("care", "there", "where"), // Reuse existing rhyme
        "hair drier" to listOf("higher", "fire", "liar"),
        "toothbrush" to listOf("rush", "gush", "crush")
    )

    private val limerickTemplates = listOf(
        "There once was a(n) %s, \nWho loved to %s all day. \n(For a single word, adjust the rest)",  // Template for 1 word
        "A curious %s, \nMet a(n) %s that was %s and new. \nThey %s together, \nIn all sorts of weather, \nA %s adventure, it's true." // Existing template for multiple words
    )

    private fun getRhyme(word: String): String {
        return rhymeDictionary[word]?.random() ?: ""
    }

    private fun getLine1Noun(word: String): String {
        return "There once a $word from far away."
    }

    private fun getLine2Verb(word1: String, word2: String): String {
        val rhyme = getRhyme(word2)
        if (word1 == word2) {
            val rhyme = getRhyme(word1)
            return "Which loves to $rhyme all day."  // Handle same word as noun and verb
        } else {
            return "Which loves to $word1 $rhyme."
        }
    }

    private fun getLine3Object(words: List<String>): String {
        val rhyme = getRhyme(words[0])
        return "With a flip of a $rhyme,"
//        if (words.size > 1) {
//            return "with a ${words[1]}"
//        }
//        return ""
    }

    private fun getLine4Action(word: String): String {
        val rhyme = getRhyme(word)
        return "Making quite a $rhyme."
    }

    private fun getLine5Outcome(word: String): String {
        val rhyme = getRhyme(word)
        return "Sound with a $rhyme."
    }

    private fun isVowel(char: Char): Boolean {
        val vowels = listOf('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U')
        return vowels.contains(char)
    }

    private fun loadLabelsFromAssets(context: Context): List<String> {
        val labels = mutableListOf<String>()
        try {
            val inputStream = context.assets.open("labels.txt")  // Use 'this' for context
            val reader = BufferedReader(InputStreamReader(inputStream))

            var line: String? = reader.readLine()
            while (line != null) {
                labels.add(line)
                line = reader.readLine()
            }

            reader.close()
        } catch (e: Exception) {
            Log.e("LimerickActivity", "Error loading labels from assets: ${e.message}")
        }

        return labels
    }

    public fun generateLimerick(words: ArrayList<String>): String {
        val line1 = getLine1Noun(words[0])
        val line2: String
        val line3: String
        if (words.size == 1) {
            line2 = getLine2Verb(words[0], words[0])
            line3 = getLine3Object(words)
        } else {
            line2 = getLine2Verb(words[0], words[1])
            line3 = getLine3Object(words)
        }
        val line4 = getLine4Action(words[0])
        val line5 = getLine5Outcome(words[0])

        return "$line1\n$line2\n$line3\n$line4\n$line5"
    }

}