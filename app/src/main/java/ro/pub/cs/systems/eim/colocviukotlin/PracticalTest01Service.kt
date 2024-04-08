package ro.pub.cs.systems.eim.colocviukotlin


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class PracticalTest01Service : Service() {

    private var processingThread: ProcessThread? = null

    override fun onCreate() {
        super.onCreate()

        val channelId = "my_channel_01"
        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("")
            .setContentText("").build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("PracticalTest01Service", "onStartCommand() callback method was invoked")

        processingThread = ProcessThread(
            this,
            intent?.getIntExtra(Constants.INPUT1, 0) ?: 0,
            intent?.getIntExtra(Constants.INPUT2, 0) ?: 0
        )
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        processingThread?.stopThread()
    }
}
