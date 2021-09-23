sealed class Result<T>

class Success<T>(val data: T) : Result<T>()

class Error<T>(val errorString: String = "Error!") : Result<T>()

enum class Operation{
    SORT_ASC {
        override fun <T : Comparable<T>> invoke(list: List<T>): List<T>
        {
            return list.sorted()
        }
    },

    SORT_DESC {
        override fun <T : Comparable<T>> invoke(list: List<T>): List<T>
        {
            return list.sortedDescending()
        }
    },

    SHUFFLE {
        override fun <T : Comparable<T>> invoke(list: List<T>): List<T>
        {
            return list.shuffled()
        }
    };

    abstract operator fun <T : Comparable<T>> invoke(list: List<T>): List<T>
}

fun main() {
    var listStrings = generateStrings(4,5)
    println("Original list: $listStrings")
    println("Sort ASC")
    listStrings.operate(Operation.SORT_ASC).print()
    println("Sort DESC")
    listStrings.operate(Operation.SORT_DESC).print()
    println("SHUFFLE")
    listStrings.operate(Operation.SHUFFLE).print()

    var listIntegers = generateIntegers(5)
    println("Original list: $listIntegers")
    println("Sort ASC")
    listIntegers.operate(Operation.SORT_ASC).print()
    println("Sort DESC")
    listIntegers.operate(Operation.SORT_DESC).print()
    println("SHUFFLE")
    listIntegers.operate(Operation.SHUFFLE).print()
}

fun <T : Comparable<T>> List<T>.operate(operation: Operation): Result<List<T>> {
    if (this.isEmpty()) return Error("Empty")

    when (operation){
        Operation.SORT_ASC -> return Success(Operation.SORT_ASC(this))
        Operation.SORT_DESC -> return Success(Operation.SORT_DESC(this))
        Operation.SHUFFLE -> return Success(Operation.SHUFFLE(this))
    }
}

fun generateStrings(stringsLenght: Int, length : Int) : List<String> {
    val chars = ('A'..'Z') + ('a'..'z')
    var listStrings = mutableListOf<String>()
    for (i in 1..length) listStrings.add((1..stringsLenght).map { chars.random() }.joinToString(""))
    return listStrings
}

fun generateIntegers(length : Int) : List<Int>
{
    return (-100..100).shuffled().take(length)
}

fun <T : Comparable<T>> Result<List<T>>.print(): Unit
{
    if (this is Error) println(this.errorString)
    else if (this is Success) println(this.data)
}
