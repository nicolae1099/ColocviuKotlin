package ro.pub.cs.systems.eim.colocviukotlin

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Date
import kotlin.random.Random

class ProcessThread(
    private val context: Context,
    firstNumber: Int,
    secondNumber: Int
) : Thread() {

    private var isRunning = true
    private val arithmeticMean = (firstNumber + secondNumber) / 2.0
    private val geometricMean = kotlin.math.sqrt(firstNumber.toDouble() * secondNumber)

    override fun run() {
        while (isRunning) {
            sleep()
            sendMessage()
        }
    }

    private fun sleep() {
        try {
            sleep(Constants.SLEEP_TIME)
        } catch (interruptedException: InterruptedException) {
            Log.d("ProcessThread", "Thread has stopped!")
        }
    }

    private fun sendMessage() {
        val intent = Intent()
        intent.action = Constants.actionTypes[Random.nextInt(Constants.actionTypes.size)]
        intent.putExtra(
            Constants.BROADCAST_RECEIVER_EXTRA,
            "${Date(System.currentTimeMillis())} $arithmeticMean $geometricMean"
        )
        context.sendBroadcast(intent)
    }

    fun stopThread() {
        isRunning = false
    }
}
