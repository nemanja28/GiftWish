package rs.rbt.giftwishlist.util

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone

/**
 * Created by Nemanja Stosic on 12/3/18.
 */
object DateHelper: AnkoLogger {


    const val BIRTHDAY_FORMAT = "dd, MMM, YYYY"

    fun formatNextBirtday(birthdayDate: Date): String{
        val sdf = SimpleDateFormat(BIRTHDAY_FORMAT, Locale.getDefault())
        val calendarNow = Calendar.getInstance()
        val yearOfNextBirthday = calendarNow.get(Calendar.YEAR)
        val calendarNextBirthday = Calendar.getInstance()
        calendarNextBirthday.time = birthdayDate
        calendarNextBirthday.set(Calendar.YEAR, yearOfNextBirthday)
        if(calendarNextBirthday.before(calendarNow)){
            calendarNextBirthday.add(Calendar.YEAR, 1)
        }
        warn { "date = ${sdf.format(calendarNextBirthday.time)}" }
        return sdf.format(calendarNextBirthday.time)
    }


}