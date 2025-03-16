package iticbcn.m7.provavalobike

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var rvBicisValorades: RecyclerView
    private lateinit var biciAdapter: BiciAdapter
    private var listaBicis = mutableListOf<Bici>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupNavigation()

        rvBicisValorades = findViewById(R.id.rvBicisValorades)
        rvBicisValorades.layoutManager = LinearLayoutManager(this)

        biciAdapter = BiciAdapter(this, listaBicis)
        rvBicisValorades.adapter = biciAdapter

        cargarBicisDesdeAPI()
    }

    private fun setupNavigation() {
        val btnSettings = findViewById<ImageView>(R.id.btnSettings)
        val btnQR = findViewById<ImageView>(R.id.btnQR)
        val btnProfile = findViewById<ImageView>(R.id.btnProfile)

        btnSettings?.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        btnQR?.setOnClickListener {
            val intent = Intent(this, EscaneixActivity::class.java)
            startActivity(intent)
        }

        btnProfile?.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarBicisDesdeAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.getBicisValorades()
                withContext(Dispatchers.Main) {
                    Log.d("ProfileActivity", "Datos recibidos: $response")
                    listaBicis.clear()
                    listaBicis.addAll(response)
                    biciAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error al cargar bicis: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}