// tu package

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.upm.etsiinf.gib.myapplication.model.IPs
import es.upm.etsiinf.gib.myapplication.model.descargaIPThread

class MainActivity : AppCompatActivity(), descargaIPThread.DescargaListener {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editText: EditText
    private lateinit var button: Button

    private val urlBase = "http://api.ipstack.com/"
    private val urlExtra = "?access_key="
    private val key = "d941929e52bcdb184d33d11e73eb2425"
    private val listaIPs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.main_list_view)
        button = findViewById<Button>(R.id.button)
        editText = findViewById<EditText>(R.id.editTextText)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaIPs)
        listView.adapter = adapter

        button.setOnClickListener {
            startDownload()
        }

    }

    private fun startDownload(){

        val ip = editText.text.toString().trim()

        if (ip.isEmpty()) {
            Toast.makeText(this, "Introduce un texto primero", Toast.LENGTH_SHORT).show()
            return
        }

        val URLFinal = urlBase + ip + urlExtra + key

        val downloadThread = descargaIPThread(URLFinal, this)
        Thread (downloadThread).start()

    }
    override fun onDescargaCompletada(ipsList: List<IPs>){
        runOnUiThread {
            listaIPs.clear()
            ipsList.forEach { ip ->
                val ipInfo = """
                IP: ${ip.ip}
                País: ${ip.country_name}
                Ciudad: ${ip.city}
                """.trimIndent()
                listaIPs.add(ipInfo)

            }
            adapter.notifyDataSetChanged()

            Toast.makeText(this, "Descarga Completada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDescargaError(e: Exception) {
        // Esto se ejecuta en el background thread, necesitamos ir al UI thread
        runOnUiThread {
            Toast.makeText(this, "Error en la descarga: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
