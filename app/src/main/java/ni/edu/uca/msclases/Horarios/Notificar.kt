package ni.edu.uca.msclases.Horarios

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ni.edu.uca.msclases.R




class Notificar: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notificar=NotificationCompat.Builder(context, channelsID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messagueExtra))
            .build()

        val manager= context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        manager.notify(notificacionID,notificar)


    }


}

const val notificacionID=1
const val channelsID="Channel"
const val titleExtra="TitleExtra"
const val messagueExtra="MessagueExtra"