package dev.akash.learnkt.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.runBlocking

object FirestoreQuizCollection{

    fun writeAllQuizzesToFirestore() = runBlocking {
        val firestore = FirebaseFirestore.getInstance()
        try {
            writeOverviewQuiz(firestore)
            writeFunctionsQuiz(firestore)
            writeClassesQuiz(firestore)
            writeLambdasQuiz(firestore)
            writeCollectionsQuiz(firestore)
            writeNullablesQuiz(firestore)
            writeBasicTypesQuiz(firestore)
            writeOperatorsQuiz(firestore)
            writeHigherOrderQuiz(firestore)
            writeGenericsQuiz(firestore)
            writeCoroutinesQuiz(firestore)
            writeConcurrencyQuiz(firestore)
            writeFlowsQuiz(firestore)
            writeFlowOperatorsQuiz(firestore)
            println("All quizzes successfully written to Firestore")
        } catch (e: Exception) {
            println("Error writing quizzes to Firestore: ${e.message}")
        }
    }

    private suspend fun writeOverviewQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Overview of Kotlin",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is Kotlin primarily used for?",
                    "options" to listOf("Web development", "Android development", "Desktop applications", "All of the above"),
                    "correctAnswer" to 3
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "Which company developed Kotlin?",
                    "options" to listOf("Oracle", "Microsoft", "JetBrains", "Google"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "Kotlin is a dynamically typed language.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "What feature helps Kotlin avoid null pointer exceptions?",
                    "options" to listOf("Null safety", "Coroutines", "Lambdas", "Extensions"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "Which platforms does Kotlin support?",
                    "options" to listOf("JVM", "JavaScript", "Native", "All of the above"),
                    "correctAnswer" to 3
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "true_false",
                    "text" to "Kotlin supports functional programming.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Kotlin became an official Android language in 2017.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "Kotlin is interoperable with which language?",
                    "options" to listOf("C#", "Java", "Python", "Swift"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "multiple_choice",
                    "text" to "Which feature allows asynchronous code in Kotlin?",
                    "options" to listOf("Threads", "Async", "Coroutines", "Lambdas"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val x: Int = 5; println(x) }",
                    "options" to listOf("5", "null", "Error", "Hello"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("01overview_quiz").set(quizData).await()
    }

    private suspend fun writeFunctionsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Defining and calling functions",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "How do you declare a function in Kotlin?",
                    "options" to listOf("def functionName()", "fun functionName()", "function functionName()", "val functionName()"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "What is the return type of a function with no return value?",
                    "options" to listOf("Void", "None", "Unit", "Null"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "Kotlin functions can have default arguments.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "What is the syntax for a single-expression function?",
                    "options" to listOf("fun functionName() { expression }", "fun functionName() = expression", "fun functionName(): expression", "functionName() = expression"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "true_false",
                    "text" to "Kotlin supports named arguments for functions.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "true_false",
                    "text" to "Kotlin supports tail recursion optimization.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Local functions can be defined inside other functions.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "How do you call an infix function in Kotlin?",
                    "options" to listOf("functionName(a, b)", "a.functionName(b)", "a functionName b", "functionName a b"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "multiple_choice",
                    "text" to "What happens when you divide an Int by zero?",
                    "options" to listOf("Returns 0", "Returns Infinity", "Throws an exception", "Compiles but doesn't run"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { fun add(a: Int, b: Int) = a + b; println(add(3, 4)) }",
                    "options" to listOf("7", "Error", "null", "34"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("02functions_quiz").set(quizData).await()
    }

    private suspend fun writeClassesQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Classes, objects, and interfaces",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "How do you define a class in Kotlin?",
                    "options" to listOf("class ClassName { }", "def ClassName { }", "object ClassName { }", "interface ClassName { }"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "What is the primary constructor in Kotlin?",
                    "options" to listOf("A constructor inside the class body", "A constructor in the class header", "A constructor with no arguments", "A private constructor"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "All classes in Kotlin are final by default.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "How do you make a class inheritable?",
                    "options" to listOf("Use 'open' keyword", "Use 'abstract' keyword", "Classes are inheritable by default", "Use 'sealed' keyword"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "true_false",
                    "text" to "Abstract classes can have constructors.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "true_false",
                    "text" to "Properties can be defined in the primary constructor.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "multiple_choice",
                    "text" to "How do you define a singleton in Kotlin?",
                    "options" to listOf("Use 'singleton' keyword", "Use 'object' keyword", "Use 'class' with 'instance'", "Use 'interface'"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What is a companion object in Kotlin?",
                    "options" to listOf("Provides class-level functions", "A friend of the class", "Used for inheritance", "Used for composition"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Object declarations can be local.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "How do you define an interface in Kotlin?",
                    "options" to listOf("interface InterfaceName { }", "abstract class InterfaceName { }", "class InterfaceName { }", "object InterfaceName { }"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("03classes_quiz").set(quizData).await()
    }

    private suspend fun writeLambdasQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Programming with lambdas",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is a lambda in Kotlin?",
                    "options" to listOf("A named function", "An anonymous function", "A class method", "A loop"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "What is the syntax for a lambda in Kotlin?",
                    "options" to listOf("{ parameters -> body }", "(parameters) -> body", "lambda parameters -> body", "fun parameters -> body"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "Lambdas can be used as function arguments.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "What is the type of a lambda taking Int and returning String?",
                    "options" to listOf("(Int) -> String", "Int -> String", "(Int, String)", "String(Int)"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "true_false",
                    "text" to "Lambdas can capture surrounding scope variables.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val numbers = listOf(1, 2, 3); numbers.forEach { print(it) } }",
                    "options" to listOf("123", "[1, 2, 3]", "Error", "1 2 3"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Function references can replace lambdas in some cases.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "How do anonymous functions differ from lambdas?",
                    "options" to listOf("No difference", "Lambdas use fun keyword", "Anonymous functions use fun keyword", "Lambdas are not expressions"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val sum = { a: Int, b: Int -> a + b }; println(sum(3, 4)) }",
                    "options" to listOf("7", "Error", "null", "34"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "true_false",
                    "text" to "Lambdas always require explicit parameter types.",
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("04lambdas_quiz").set(quizData).await()
    }

    private suspend fun writeCollectionsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Working with collections and sequences",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "Which is a read-only collection interface in Kotlin?",
                    "options" to listOf("MutableList", "List", "ArrayList", "MutableSet"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "What is the default implementation for MutableList?",
                    "options" to listOf("LinkedList", "ArrayList", "HashSet", "HashMap"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "Sets in Kotlin can contain duplicate elements.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "Which collection type is used for key-value pairs?",
                    "options" to listOf("List", "Set", "Map", "Array"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "true_false",
                    "text" to "Read-only collections are covariant.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the starting index for lists in Kotlin?",
                    "options" to listOf("1", "0", "-1", "Not indexed"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val list = listOf(1, 2, 3); println(list[1]) }",
                    "options" to listOf("1", "2", "3", "Error"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "true_false",
                    "text" to "You can add elements to a List in Kotlin.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "multiple_choice",
                    "text" to "Which function iterates over collection elements?",
                    "options" to listOf("forEach", "map", "filter", "reduce"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What does this code print?\nfun main() { val set = setOf(1, 2, 2, 3); println(set.size) }",
                    "options" to listOf("4", "3", "2", "1"),
                    "correctAnswer" to 1
                )
            )
        )
        firestore.collection("quizzes").document("05collections_quiz").set(quizData).await()
    }

    private suspend fun writeNullablesQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Working with nullable values",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is the type of a variable that can hold null?",
                    "options" to listOf("Any", "Nothing", "Null", "T?"),
                    "correctAnswer" to 3
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "multiple_choice",
                    "text" to "How do you check if a variable is null?",
                    "options" to listOf("if (variable == null)", "if (variable.isNull())", "if (variable.isEmpty())", "if (variable == NULL)"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "true_false",
                    "text" to "The safe call operator (?.) returns null if the receiver is null.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "multiple_choice",
                    "text" to "What does the Elvis operator (?:) do?",
                    "options" to listOf("Throws an exception", "Provides a default value", "Checks for null", "Calls a function"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "true_false",
                    "text" to "Non-null assertions (!!) should be used frequently.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val str: String? = null; println(str?.length) }",
                    "options" to listOf("0", "null", "Error", "Nothing"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "The let function can be used to execute a block if a value is not null.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What does this code print?\nfun main() { val x: Int? = null; println(x ?: 0) }",
                    "options" to listOf("null", "0", "Error", "Nothing"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "A variable of type String can hold null.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is wrong with this code?\nfun main() { val str: String? = null; println(str!!.length) }",
                    "options" to listOf("Nothing, it prints null", "It throws NullPointerException", "It doesn't compile", "It prints 0"),
                    "correctAnswer" to 1
                )
            )
        )
        firestore.collection("quizzes").document("06nullables_quiz").set(quizData).await()
    }

    private suspend fun writeBasicTypesQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Basic types, collections, and arrays",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "Which is a basic numeric type in Kotlin?",
                    "options" to listOf("String", "Int", "Array", "List"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Kotlin has a separate type for unsigned integers.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "What is the default type for a floating-point number?",
                    "options" to listOf("Float", "Double", "Long", "Int"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Arrays in Kotlin are invariant.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "How do you create an array in Kotlin?",
                    "options" to listOf("arrayOf()", "listOf()", "setOf()", "mapOf()"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val arr = arrayOf(1, 2, 3); println(arr[0]) }",
                    "options" to listOf("1", "2", "3", "Error"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Kotlin's Char type represents a 16-bit Unicode character.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "Which type represents a boolean value?",
                    "options" to listOf("Int", "Boolean", "Char", "String"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Kotlin automatically converts between numeric types.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What does this code print?\nfun main() { val chars = charArrayOf('a', 'b'); println(chars.joinToString()) }",
                    "options" to listOf("ab", "[a, b]", "a, b", "Error"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("07basic_types_quiz").set(quizData).await()
    }

    private suspend fun writeOperatorsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Operator overloading and other conventions",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "Which keyword is used to overload operators in Kotlin?",
                    "options" to listOf("operator", "overload", "override", "infix"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "All operators in Kotlin can be overloaded.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "Which function is used to overload the '+' operator?",
                    "options" to listOf("plus", "add", "sum", "increment"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Operator overloading requires the 'operator' modifier in the function declaration.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nclass Point(val x: Int) { operator fun plus(other: Point) = Point(x + other.x) }\nfun main() { val p1 = Point(1); val p2 = Point(2); println((p1 + p2).x) }",
                    "options" to listOf("3", "12", "Error", "1"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "Which operator is overloaded by the 'invoke' function?",
                    "options" to listOf("()", "[]", "++", "=="),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "The 'in' operator can be overloaded using the 'contains' function.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nclass Counter(var count: Int) { operator fun inc() = Counter(count + 1) }\nfun main() { var c = Counter(5); c++; println(c.count) }",
                    "options" to listOf("5", "6", "Error", "7"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Kotlin allows overloading of comparison operators like '<'.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "Which function is used to overload the '[]' operator?",
                    "options" to listOf("get", "set", "index", "access"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("08operators_quiz").set(quizData).await()
    }

    private suspend fun writeHigherOrderQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Higher-order functions",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is a higher-order function in Kotlin?",
                    "options" to listOf("A function that returns Unit", "A function that takes or returns a function", "A recursive function", "An infix function"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Higher-order functions can accept lambdas as parameters.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun operate(x: Int, op: (Int) -> Int): Int = op(x)\nfun main() { println(operate(5) { it * 2 }) }",
                    "options" to listOf("5", "10", "Error", "2"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Higher-order functions always increase runtime overhead significantly.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "Which standard function applies a transformation to each element?",
                    "options" to listOf("forEach", "map", "filter", "reduce"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val list = listOf(1, 2, 3); val doubled = list.map { it * 2 }; println(doubled) }",
                    "options" to listOf("[2, 4, 6]", "[1, 2, 3]", "Error", "6"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Function types in Kotlin can include parameter and return types.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "Which keyword makes a lambda parameter explicit?",
                    "options" to listOf("it", "this", "that", "param"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Inline functions can reduce the overhead of higher-order functions.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nfun main() { val filter: (Int) -> Boolean = { it > 2 }; println(listOf(1, 2, 3, 4).filter(filter)) }",
                    "options" to listOf("[1, 2]", "[3, 4]", "[1, 2, 3, 4]", "Error"),
                    "correctAnswer" to 1
                )
            )
        )
        firestore.collection("quizzes").document("09higher_order_quiz").set(quizData).await()
    }

    private suspend fun writeGenericsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Generics",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "How do you declare a generic class in Kotlin?",
                    "options" to listOf("class MyClass(T) { }", "class MyClass<T> { }", "generic class MyClass { }", "class MyClass: T { }"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Kotlin supports variance annotations like 'out' and 'in'.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "What does the 'out' variance annotation indicate?",
                    "options" to listOf("Covariance", "Contravariance", "Invariance", "No variance"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Generic type parameters can have upper bounds.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nclass Box<T>(val value: T)\nfun main() { val box = Box(42); println(box.value) }",
                    "options" to listOf("42", "Box(42)", "Error", "null"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "Which keyword restricts a generic type to a specific type or its subtypes?",
                    "options" to listOf("where", "extends", "super", "is"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "Kotlin's List<T> is invariant by default.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What does 'in' variance allow?",
                    "options" to listOf("Producer types", "Consumer types", "Immutable types", "No variance"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Type erasure applies to generics at runtime in Kotlin.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is wrong with this code?\nfun <T> printValue(value: T) where T : Number, T : String { println(value) }",
                    "options" to listOf("Nothing, it works", "Multiple upper bounds are not allowed", "String is not a Number", "Requires variance"),
                    "correctAnswer" to 2
                )
            )
        )
        firestore.collection("quizzes").document("10generics_quiz").set(quizData).await()
    }

    private suspend fun writeCoroutinesQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Coroutines",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is a coroutine in Kotlin?",
                    "options" to listOf("A thread", "A lightweight thread", "A function", "A class"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Coroutines are always executed on the main thread.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "Which keyword suspends a coroutine?",
                    "options" to listOf("async", "suspend", "await", "yield"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Coroutines can be launched in a specific dispatcher.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "What does 'launch' do in a coroutine scope?",
                    "options" to listOf("Returns a Deferred", "Starts a coroutine without result", "Cancels a coroutine", "Waits for completion"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.*\nfun main() = runBlocking { launch { delay(100); println(\"Hello\") }; println(\"World\") }",
                    "options" to listOf("Hello World", "World Hello", "Error", "Nothing"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "The 'async' builder returns a Deferred value.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "Which scope is used for UI-related coroutines in Android?",
                    "options" to listOf("GlobalScope", "MainScope", "IOScope", "DefaultScope"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Coroutines can be cancelled using a Job.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What does 'runBlocking' do?",
                    "options" to listOf("Runs a coroutine asynchronously", "Blocks the current thread until completion", "Cancels a coroutine", "Creates a new scope"),
                    "correctAnswer" to 1
                )
            )
        )
        firestore.collection("quizzes").document("11coroutines_quiz").set(quizData).await()
    }

    private suspend fun writeConcurrencyQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Structured concurrency",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is structured concurrency in Kotlin?",
                    "options" to listOf("Running coroutines in parallel", "Managing coroutines hierarchically", "Using threads", "Avoiding coroutines"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Child coroutines are cancelled when their parent is cancelled.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "Which scope ensures structured concurrency?",
                    "options" to listOf("GlobalScope", "CoroutineScope", "MainScope", "UnconfinedScope"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "GlobalScope is recommended for structured concurrency.",
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.*\nfun main() = runBlocking { launch { delay(100); println(\"Child\") }; println(\"Parent\") }",
                    "options" to listOf("Child Parent", "Parent Child", "Error", "Nothing"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What happens if a child coroutine throws an exception?",
                    "options" to listOf("Parent continues", "Parent is cancelled", "Nothing happens", "Child restarts"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "SupervisorScope prevents parent cancellation on child failure.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "Which function creates a new coroutine scope?",
                    "options" to listOf("launch", "async", "coroutineScope", "runBlocking"),
                    "correctAnswer" to 2
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "Structured concurrency ensures all coroutines complete before the scope ends.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.*\nfun main() = runBlocking { coroutineScope { launch { println(\"Inner\") } }; println(\"Outer\") }",
                    "options" to listOf("Inner Outer", "Outer Inner", "Error", "Nothing"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("12concurrency_quiz").set(quizData).await()
    }

    private suspend fun writeFlowsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Flows",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "What is a Flow in Kotlin?",
                    "options" to listOf("A synchronous sequence", "An asynchronous stream of values", "A thread pool", "A coroutine scope"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "Flows are cold by default.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "Which function creates a Flow?",
                    "options" to listOf("flowOf()", "launch", "async", "runBlocking"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "Flows can emit values from different coroutines.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.flow.*\nfun main() = runBlocking { flowOf(1, 2, 3).collect { println(it) } }",
                    "options" to listOf("123", "[1, 2, 3]", "Error", "Nothing"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "Which function collects values from a Flow?",
                    "options" to listOf("emit", "collect", "flow", "onEach"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "StateFlow is a hot Flow that holds a single value.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What is the difference between Flow and StateFlow?",
                    "options" to listOf("Flow is hot, StateFlow is cold", "StateFlow holds a state, Flow does not", "No difference", "Flow is synchronous"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "SharedFlow can emit values to multiple collectors.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What does this code print?\nimport kotlinx.coroutines.flow.*\nfun main() = runBlocking { flow { emit(1); emit(2) }.collect { println(it) } }",
                    "options" to listOf("12", "[1, 2]", "Error", "Nothing"),
                    "correctAnswer" to 0
                )
            )
        )
        firestore.collection("quizzes").document("13flows_quiz").set(quizData).await()
    }

    private suspend fun writeFlowOperatorsQuiz(firestore: FirebaseFirestore) {
        val quizData = mapOf(
            "topic" to "Flow operators",
            "questions" to listOf(
                mapOf(
                    "id" to "q1",
                    "type" to "multiple_choice",
                    "text" to "Which Flow operator transforms emitted values?",
                    "options" to listOf("filter", "map", "collect", "reduce"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q2",
                    "type" to "true_false",
                    "text" to "The 'filter' operator only emits values that satisfy a predicate.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q3",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.flow.*\nfun main() = runBlocking { flowOf(1, 2, 3).map { it * 2 }.collect { println(it) } }",
                    "options" to listOf("246", "[2, 4, 6]", "123", "Error"),
                    "correctAnswer" to 0
                ),
                mapOf(
                    "id" to "q4",
                    "type" to "true_false",
                    "text" to "The 'take' operator limits the number of emitted values.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q5",
                    "type" to "multiple_choice",
                    "text" to "Which operator combines values from multiple Flows?",
                    "options" to listOf("zip", "merge", "combine", "All of the above"),
                    "correctAnswer" to 3
                ),
                mapOf(
                    "id" to "q6",
                    "type" to "multiple_choice",
                    "text" to "What does the 'debounce' operator do?",
                    "options" to listOf("Emits the first value", "Emits values after a timeout", "Emits all values", "Cancels the Flow"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q7",
                    "type" to "true_false",
                    "text" to "The 'onEach' operator performs a side effect for each emission.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q8",
                    "type" to "multiple_choice",
                    "text" to "What is the output of this code?\nimport kotlinx.coroutines.flow.*\nfun main() = runBlocking { flowOf(1, 2, 3, 4).filter { it > 2 }.collect { println(it) } }",
                    "options" to listOf("12", "34", "1234", "Error"),
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q9",
                    "type" to "true_false",
                    "text" to "The 'reduce' operator accumulates values into a single result.",
                    "correctAnswer" to 1
                ),
                mapOf(
                    "id" to "q10",
                    "type" to "multiple_choice",
                    "text" to "What does this code print?\nimport kotlinx.coroutines.flow.*\nfun main() = runBlocking { flowOf(1, 2, 3).take(2).collect { println(it) } }",
                    "options" to listOf("123", "12", "23", "Error"),
                    "correctAnswer" to 1
                )
            )
        )
        firestore.collection("quizzes").document("14flow_operators_quiz").set(quizData).await()
    }
}