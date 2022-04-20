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
import ru.mmn.myfirsttests.*

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
        val editText = uiDevice.findObject(By.res(packageName, TEST_SEARCH_EDIT_TEXT_ID))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, TEST_SEARCH_EDIT_TEXT_ID))
        editText.text = TEST_UIAUTOMATOR_EDITTEXT_TEXT
        val searchButton = uiDevice.findObject(By.res(packageName, TEST_SEARCH_BUTTON_ID))
        searchButton.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID)),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, TEST_UIAUTOMATOR_NUMBER_OF_SEARCH_RESULTS)
        Assert.assertNotEquals(changedText.text, TEST_NUMBER_OF_RESULTS_ZERO)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findObject(By.res(packageName,
            TEST_TO_DETAILS_ACTIVITY_BUTTON_ID))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID)),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, TEST_NUMBER_OF_RESULTS_ZERO)
        Assert.assertNotEquals(changedText.text, TEST_UIAUTOMATOR_NUMBER_OF_SEARCH_RESULTS)
    }

    @Test
    fun test_OpenDetailsScreenWithCorrectSearchResults() {
        val editText = uiDevice.findObject(By.res(packageName, TEST_SEARCH_EDIT_TEXT_ID))
        editText.text = TEST_UIAUTOMATOR_EDITTEXT_TEXT
        val searchButton = uiDevice.findObject(By.res(packageName, TEST_SEARCH_BUTTON_ID))
        searchButton.click()
        val mainActivityChangedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID)),
                TIMEOUT
            )
        val mainActivityTextResult = mainActivityChangedText.text
        Assert.assertEquals(mainActivityChangedText.text, mainActivityTextResult)
        val toDetails = uiDevice.findObject(By.res(packageName, TEST_TO_DETAILS_ACTIVITY_BUTTON_ID))
        toDetails.click()

        //Не работает
        val detailsActivityChangedText =
            uiDevice.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID))
        val detailsActivityTextResult = detailsActivityChangedText.text
        Assert.assertEquals(detailsActivityTextResult, mainActivityTextResult)
    }

    @Test
    fun test_DetailsActivityDecrementButtonIsWorking() {
        val toDetails = uiDevice.findObject(By.res(packageName,
            TEST_TO_DETAILS_ACTIVITY_BUTTON_ID))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID)),
                TIMEOUT
            )
        val decrementButton = uiDevice.findObject(By.res(packageName,
            TEST_DECREMENT_BUTTON_ID))
        decrementButton.click()
        Assert.assertEquals(changedText.text, TEST_NUMBER_OF_RESULTS_MINUS_1)
        Assert.assertNotEquals(changedText.text, TEST_NUMBER_OF_RESULTS_PLUS_1)
    }

    @Test
    fun test_DetailsActivityIncrementButtonIsWorking() {
        val toDetails = uiDevice.findObject(By.res(packageName,
            TEST_TO_DETAILS_ACTIVITY_BUTTON_ID))
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, TEST_TOTAL_COUNT_TEXTVIEW_ID)),
                TIMEOUT
            )
        val incrementButton = uiDevice.findObject(By.res(packageName,
            TEST_INCREMENT_BUTTON_ID))
        incrementButton.click()
        Assert.assertEquals(changedText.text, TEST_NUMBER_OF_RESULTS_PLUS_1)
        Assert.assertNotEquals(changedText.text, TEST_NUMBER_OF_RESULTS_MINUS_1)

    }

    companion object {
        private const val TIMEOUT = 5000L
    }
}