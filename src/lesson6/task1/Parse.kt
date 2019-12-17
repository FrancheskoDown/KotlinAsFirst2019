@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.NumberFormatException
import java.lang.StringBuilder

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")

    if (parts.size != 3) return ""

    try {

        val day = parts[0].toInt()
        if (day !in 1..31) return ""

        val month = when (parts[1]) {
            "января" -> 1
            "февраля" -> 2
            "марта" -> 3
            "апреля" -> 4
            "мая" -> 5
            "июня" -> 6
            "июля" -> 7
            "августа" -> 8
            "сентября" -> 9
            "октября" -> 10
            "ноября" -> 11
            "декабря" -> 12
            else -> return ""
        }

        val year = parts[2].toInt()
        if (year < 0 || day > lesson2.task2.daysInMonth(month, year)) return ""

        return String.format("%02d.%02d.%d", day, month, year)
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")

    try {

        if (parts.size != 3) return ""

        val dayDigit = parts[0].toInt()
        if (dayDigit !in 1..31) return ""

        val day = dayDigit.toString()

        val monthDigit = parts[1].toInt()

        val month = when (monthDigit) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> return ""
        }

        val yearDigit = parts[2].toInt()
        if (yearDigit < 0 || dayDigit > lesson2.task2.daysInMonth(monthDigit, yearDigit)) return ""

        val year = parts[2]

        return "$day $month $year"
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val allowedChars = listOf('+', '-', '(', ')', ' ')
    val result = StringBuilder()
    var flagLeftParenthesis = false
    var flagRightParenthesis = false
    var flagCountryCode = false

    if (phone.any { it !in allowedChars && !it.isDigit() }) return ""

    for (i in phone) {
        if (i == '+' || i.isDigit()) result.append(i)

        if (i == '(') {
            if (!flagLeftParenthesis && !flagRightParenthesis) {
                flagLeftParenthesis = true
            } else return ""
        }

        if (i == ')') {
            if (!flagRightParenthesis && flagLeftParenthesis) flagRightParenthesis = true
            else return ""
        }

        if (i.isDigit() && !flagRightParenthesis && flagLeftParenthesis) flagCountryCode = true

        if (flagRightParenthesis && !flagCountryCode) return ""
    }

    if (result.toString() == "+") return ""

    return result.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val allowedChars = listOf(' ', '%', '-')
    var maxLength = -1
    val parts = jumps.split(" ")



    if (jumps.all { !it.isDigit() } || jumps.any { it !in allowedChars && !it.isDigit() }) return maxLength



    for (part in parts) {
        if (part.toIntOrNull() != null && part.toInt() > maxLength) maxLength = part.toInt()
    }
    return maxLength
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val allowedChars = listOf('+', '-', ' ', '%')
    val parts = jumps.split(" ")
    var maxHigh = -1

    if (jumps.all { !it.isDigit() } || jumps.any { it !in allowedChars && !it.isDigit() }) return maxHigh

    for (i in parts.indices) {
        if (i + 1 in parts.indices && parts[i + 1].contains('+')) {
            if (parts[i].toIntOrNull() != null && parts[i].toInt() > maxHigh)
                maxHigh = parts[i].toInt()
        }
    }

    return maxHigh
}


/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val allowedChars = listOf('+', '-', ' ')
    val parts = expression.split(" ")

    require(!(expression.all { !it.isDigit() } || expression.any { it !in allowedChars && !it.isDigit() }))
    requireNotNull(parts[0].toIntOrNull())

    var result = parts[0].toInt()

    for (i in parts.indices) {
        require(!(parts[i].toList().size > 1 && parts[i].toList().any { !it.isDigit() }))
        require(!(parts[i].toIntOrNull() == null && i + 1 in parts.indices && parts[i + 1].toIntOrNull() == null))
        require(!(parts[i].toIntOrNull() != null && i + 1 in parts.indices && parts[i + 1].toIntOrNull() != null))

        if (parts[i].toIntOrNull() != null && i - 1 in parts.indices && parts[i - 1].contains('+')) result += parts[i].toInt()
        else if (parts[i].toIntOrNull() != null && i - 1 in parts.indices && parts[i - 1].contains('-')) result -= parts[i].toInt()
    }

    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.toLowerCase().split(" ")
    var index = 0

    if (str.isEmpty()) return -1

    for (i in parts.indices) {
        if (i + 1 in parts.indices && parts[i] == parts[i + 1]) return index + i
        index += parts[i].length
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val parts = description.split(";").joinToString("").split(" ")
    var maxPrice = 0.0
    var maxPriceIndex = 0

    if (description.isEmpty()) return ""

    for (i in 1..parts.size step 2) {
        if (parts[i].toDoubleOrNull() == null) return ""

        if (parts[i].toDouble() >= maxPrice) {
            maxPrice = parts[i].toDouble()
            maxPriceIndex = i - 1
        }

    }
    return parts[maxPriceIndex]
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    val allowedChars = listOf('I', 'V', 'X', 'L', 'C', 'D', 'M')
    var result = 0
    var i = 1
    var flag = true

    if (roman.isEmpty() || roman.any { it !in allowedChars }) return -1

    // просмотр 1 знака
    if (roman[0] == 'I' && ((i in roman.indices && roman[i] != 'V' && roman[i] != 'X') || roman.length == 1))
        result += 1
    if (roman[0] == 'V') result += 5
    if (roman[0] == 'X' && ((i in roman.indices && roman[i] != 'L' && roman[i] != 'C') || roman.length == 1))
        result += 10
    if (roman[0] == 'L') result += 50
    if (roman[0] == 'C' && ((i in roman.indices && roman[i] != 'D' && roman[i] != 'M') || roman.length == 1))
        result += 100
    if (roman[0] == 'D') result += 500
    if (roman[0] == 'M') result += 1000

    //Просмотр основной части
    while (i in roman.indices) {
        if (roman[i] == 'I' && i + 1 in roman.indices && roman[i + 1] != 'V' && roman[i + 1] != 'X') result += 1
        if (roman[i] == 'I' && i + 1 in roman.indices && roman[i + 1] == 'V') {
            flag = false
            result += 4
        }
        if (roman[i] == 'I' && i + 1 in roman.indices && roman[i + 1] == 'X') {
            flag = false
            result += 9
        }

        if (roman[i] == 'V' && roman[i - 1] != 'I') result += 5
        if (roman[i] == 'V' && roman[i - 1] == 'I' && i - 1 == 0) result += 4

        if (roman[i] == 'X' && i + 1 in roman.indices && roman[i + 1] != 'L' && roman[i + 1] != 'C' && roman[i - 1] != 'I')
            result += 10
        if (roman[i] == 'X' && i + 1 in roman.indices && roman[i + 1] == 'L') {
            flag = false
            result += 40
        }
        if (roman[i] == 'X' && i + 1 in roman.indices && roman[i + 1] == 'C') {
            flag = false
            result += 90
        }

        if (roman[i] == 'L' && roman[i - 1] != 'X') result += 50
        if (roman[i] == 'L' && roman[i - 1] == 'X' && i - 1 == 0) result += 40

        if (roman[i] == 'C' && i + 1 in roman.indices && roman[i + 1] != 'D' && roman[i + 1] != 'M' && roman[i - 1] != 'X')
            result += 100
        if (roman[i] == 'C' && i + 1 in roman.indices && roman[i + 1] == 'D') {
            flag = false
            result += 400
        }
        if (roman[i] == 'C' && i + 1 in roman.indices && roman[i + 1] == 'M') {
            flag = false
            result += 900
        }

        if (roman[i] == 'D' && i - 1 in roman.indices && roman[i - 1] != 'C') result += 500
        if (roman[i] == 'D' && roman[i - 1] == 'C' && i - 1 == 0) result += 400

        if (roman[i] == 'M' && i - 1 in roman.indices && roman[i - 1] != 'C') result += 1000
        if (roman[i] == 'M' && roman[i - 1] == 'C' && i - 1 == 0) result += 900

        if (!flag) {
            if (i + 2 !in roman.indices) break
            i += 2
        } else i++
        flag = true
    }

    //Просмотр последнего знака
    if (i == roman.length && roman.length != 1) {
        if (roman[i - 1] == 'I') result += 1
        if (roman[i - 1] == 'X') result += 10
        if (roman[i - 1] == 'C') result += 100
    }

    return result

    // Можно ли решить задачу оптимальнее, это худший способ решить задачу, используя кучу костылей, помоему,
    // но, к сожалению,  я не смог придумать решения оптимальнее
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */

fun cycle(command: Char, j: Int, currentCell: Int, storage: MutableList<Int>, commands: String): Int {
    var counter = 1
    var position = j

    if (commands[position] == '[' && storage[currentCell] == 0) {
        while (counter > 0) {
            position++
            if (commands[position] == '[') counter++
            if (commands[position] == ']') counter--
        }
        return position
    } else if (commands[position] == ']' && storage[currentCell] != 0) {
        while (counter > 0) {
            position--
            if (commands[position] == ']') counter++
            if (commands[position] == '[') counter--
        }
        return position
    }
    return position
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val allowedChar = listOf('+', '-', '>', '<', ']', '[', ' ')
    val storage = MutableList(cells) { 0 }
    var currentCell = cells / 2
    var counterParenthesis = 0
    var validPair = true

    require(!commands.any { it !in allowedChar })

    // проверка  правильного построения скобок в строке
    for (i in commands) {
        if (i == '[') counterParenthesis++
        if (i == ']') counterParenthesis--
        if (counterParenthesis < 0) validPair = false
    }
    require(counterParenthesis == 0)
    require(validPair)

    //Конвеер
    var j = 0
    var l = 0
    while (j in commands.indices && l in 0 until limit) {
        if (commands[j] == '+') storage[currentCell]++
        if (commands[j] == '-') storage[currentCell]--

        if (commands[j] == '>' && currentCell + 1 in storage.indices) currentCell++
        else check(!(commands[j] == '>' && currentCell + 1 !in storage.indices))

        if (commands[j] == '<' && currentCell - 1 in storage.indices) currentCell--
        else check(!(commands[j] == '<' && currentCell - 1 !in storage.indices))

        if (commands[j] == '[' || commands[j] == ']') j = cycle(commands[j], j, currentCell, storage, commands)

        l++
        j++
    }
    return storage
}

fun myFun(text: String): Map<String, String> {
    if (text.isEmpty() || text.all { it == ' ' }) return mutableMapOf("Error" to "0")

    if (text.split(Regex("""[^А-Яа-яёЁ :0-9]+""")).size > 1) return mutableMapOf("Error" to "0")

    val names = text.split(Regex("""[^А-Яа-яёЁ]+"""))
    val results = text.split(Regex("""[^0-9:]+"""))

    if (names.size != results.size) return mutableMapOf("Error" to "0")

    val result = mutableMapOf<String, String>()
    for (i in names.indices) result[names[i]] = results[i]

    return result

}
