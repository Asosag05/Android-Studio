//tu package

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
import com.example.miproyecto.model.IPs
import com.example.miproyecto.model.descargaIPThread

class MainActivity : AppCompatActivity(), descargaIPThread.DescargaListener {

    // declaramos variables del xml
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var editText: EditText
    private lateinit var button: Button

    //creamos la url a la que vamos a dirigir nuestro codigo

    private val urlBase = "http://api.ipstack.com/"
    private val urlExtra = "?access_key="
    private val key = "d941929e52bcdb184d33d11e73eb2425" // llave unica dirigida a cuenta de ipStack
    private val listaIPs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) { //en la creacion de la app:
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.main_list_view) // nombramos nuestros objetos
        button = findViewById<Button>(R.id.button)
        editText = findViewById<EditText>(R.id.textoDeIP)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets -> // metodo creado automaticamente por android studios
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaIPs) // creamos el adapter, sirve como puente entre la listview y lo que quieres mostrar
        listView.adapter = adapter

        button.setOnClickListener { // definimos funcionalidad de boton
            startDownload()
        }
    }

    private fun startDownload() { //descarga de datos json
        val ip = editText.text.toString().trim() // le damos a una variable ip el valor que añada el usuario a la caja de texto

        if (ip.isEmpty()) { // si no añadimos texto, salta un pop up pidiendonos que añadamos texto
            Toast.makeText(this, "Introduce un texto primero", Toast.LENGTH_SHORT).show()
            return
        }

        val URLFinal = urlBase + ip + urlExtra + key // generamos la url final

        val downloadThread = descargaIPThread(URLFinal, this)
        Thread(downloadThread).start() // empezamos el thread con descargaIPThread
    }

    override fun onDescargaCompletada(ipsList: List<IPs>) { // caso de descarga completada: tenemos todos los datos extraidos
        runOnUiThread {

            if (listaIPs.isEmpty()) { // le damos formato
                listaIPs.add("---------------------------------------------------------------------")
            }

            ipsList.forEach { ip -> // añadimos los valores importantes
                val ipInfo = """
                IP: ${ip.ip}
                Tipo: ${ip.type}
                País: ${ip.country_name}
                Ciudad: ${ip.city}
                Código Postal: ${ip.zip}
                """.trimIndent()
                listaIPs.add(ipInfo)
                listaIPs.add("---------------------------------------------------------------------")
            }
            adapter.notifyDataSetChanged() // notificamos al adapter que se ha cambiado los valores de list view


            editText.text.clear() // limpiamos la caja de texto

            Toast.makeText(this, "Descarga Completada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDescargaError(e: Exception) { // en caso de que el codigo nos de error, creamos un pop up con el codigo de error
        runOnUiThread {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
