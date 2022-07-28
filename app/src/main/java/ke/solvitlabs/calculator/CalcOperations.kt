package ke.solvitlabs.calculator


object CalcOperations {
    val operations = HashMap<String, CategoryAndPriority>()

    init {
        initializeOperatorCategories()
    }

    private fun initializeOperatorCategories() {
        operations["兀"] = CategoryAndPriority(STANDALONE, PRIORITY_1)
        operations["e"] = CategoryAndPriority(STANDALONE, PRIORITY_1)
        operations["√"] = CategoryAndPriority(PREFIX, PRIORITY_1)
        operations["!"] = CategoryAndPriority(POSTFIX, PRIORITY_1)
        operations["⌃"] = CategoryAndPriority(INFIX, PRIORITY_1)
        operations["%"] = CategoryAndPriority(POSTFIX, PRIORITY_1)
        operations["÷"] = CategoryAndPriority(INFIX, PRIORITY_2)
        operations["×"] = CategoryAndPriority(INFIX, PRIORITY_3)
        operations["+"] = CategoryAndPriority(INFIX, PRIORITY_4)
        operations["−"] = CategoryAndPriority(INFIX, PRIORITY_5)
        operations["sin"] = CategoryAndPriority(PREFIX, PRIORITY_1)
        operations["cos"] = CategoryAndPriority(PREFIX, PRIORITY_1)
        operations["tan"] = CategoryAndPriority(PREFIX, PRIORITY_1)
        operations["ln"] = CategoryAndPriority(PREFIX, PRIORITY_1)
        operations["log"] = CategoryAndPriority(PREFIX, PRIORITY_1)
    }
}