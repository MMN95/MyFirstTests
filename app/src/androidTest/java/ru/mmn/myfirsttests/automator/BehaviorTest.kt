package ru.mmn.myfirsttests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 701")
        Assert.assertNotEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findObject(By.res(packageName,
            "toDetailsActivityButton"))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
        Assert.assertNotEquals(changedText.text, "Number of results: 100")
    }

    @Test
    fun test_OpenDetailsScreenWithCorrectSearchResults() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val searchButton = uiDevice.findObject(By.res(packageName, "searchButton"))
        searchButton.click()
        val mainActivityChangedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val mainActivityTextResult = mainActivityChangedText.text
        Assert.assertEquals(mainActivityChangedText.text, mainActivityTextResult)
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()

        //Не работает
        val detailsActivityChangedText =
            uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        val detailsActivityTextResult = detailsActivityChangedText.text
        Assert.assertEquals(detailsActivityTextResult, mainActivityTextResult)
    }

    @Test
    fun test_DetailsActivityDecrementButtonIsWorking(){
        val toDetails = uiDevice.findObject(By.res(packageName,
            "toDetailsActivityButton"))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val decrementButton = uiDevice.findObject(By.res(packageName,
            "decrementButton"))
        decrementButton.click()
        Assert.assertEquals(changedText.text, "Number of results: -1")
        Assert.assertNotEquals(changedText.text, "Number of results: 1")
    }

    @Test
    fun test_DetailsActivityIncrementButtonIsWorking(){
        val toDetails = uiDevice.findObject(By.res(packageName,
            "toDetailsActivityButton"))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val incrementButton = uiDevice.findObject(By.res(packageName,
            "incrementButton"))
        incrementButton.click()
        Assert.assertEquals(changedText.text, "Number of results: 1")
        Assert.assertNotEquals(changedText.text, "Number of results: -1")

    }


    companion object {
        private const val TIMEOUT = 5000L
    }
}