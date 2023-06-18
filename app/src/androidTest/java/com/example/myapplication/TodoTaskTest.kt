package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.core.AppDataBase
import com.example.myapplication.core.DatabaseService
import com.example.myapplication.model.TaskDataModel
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class LanguageDatabaseTest : TestCase() {
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: AppDataBase
    private lateinit var dao: DatabaseService

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.dataBaseService()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun writeAndReadToDos() = runBlocking {
        val calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

        var minute: Int = calendar.get(Calendar.MINUTE)
        if (minute<50) minute=minute+5 else hour+1
        val timeFormat ="PM"
        val taskDataModel = TaskDataModel("Task 1", "in_progress",getTimeStamp(hour,minute,timeFormat),timeFormat,"$hour:$minute",System.currentTimeMillis())
        launch(Dispatchers.IO) {
            dao.insertToDoAll(taskDataModel)
            val taskDataModels = dao.getAllTodos()
            assertThat(taskDataModels.value?.contains(taskDataModel)).isTrue()

        }
    }

    private fun getTimeStamp(sHour:Int, selectedMinute:Int, format:String): Long {
        val selectedHour= if (format=="PM"){
            sHour+12
        } else sHour
        val mcurrentTime: Calendar = Calendar.getInstance()
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = mcurrentTime.get(Calendar.YEAR)
        calendar[Calendar.MONTH] = mcurrentTime.get(Calendar.MONTH)
        calendar[Calendar.DAY_OF_MONTH] = mcurrentTime.get(Calendar.DAY_OF_MONTH)
        calendar[Calendar.HOUR_OF_DAY] = selectedHour
        calendar[Calendar.MINUTE] = selectedMinute
        calendar[Calendar.SECOND] = 0
        return calendar.time.time
    }
}