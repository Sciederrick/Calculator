package ke.solvitlabs.calculator


object CalcOperations {
    val operations = HashMap<String, CategoryAndPriority>()

    init {
        initializeOperatorCategories()
    }

    private fun initializeOperatorCategories() {
        operations["兀"] = CategoryAndPriority(STANDALONE, PRIORITY_2)
        operations["e"] = CategoryAndPriority(STANDALONE, PRIORITY_2)
        operations["sin"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["cos"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["tan"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["ln"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["log"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["√"] = CategoryAndPriority(PREFIX, PRIORITY_2)
        operations["!"] = CategoryAndPriority(POSTFIX, PRIORITY_2)
        operations["⌃"] = CategoryAndPriority(INFIX, PRIORITY_2)
        operations["+"] = CategoryAndPriority(INFIX, PRIORITY_3)
        operations["−"] = CategoryAndPriority(INFIX, PRIORITY_4)
        operations["×"] = CategoryAndPriority(INFIX, PRIORITY_2)
        operations["%"] = CategoryAndPriority(POSTFIX, PRIORITY_2)
        operations["÷"] = CategoryAndPriority(INFIX, PRIORITY_1)
    }
}