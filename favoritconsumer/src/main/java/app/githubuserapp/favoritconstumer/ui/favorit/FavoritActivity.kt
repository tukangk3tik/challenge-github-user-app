package app.githubuserapp.favoritconstumer.ui.favorit

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.githubuserapp.favoritconstumer.databinding.ActivityFavoritBinding
import app.githubuserapp.favoritconstumer.db.DatabaseContract.FavoritColumns.Companion.CONTENT_URI
import app.githubuserapp.favoritconstumer.db.FavoritEntity
import app.githubuserapp.favoritconstumer.helper.FavoritMappingHelper
import com.dicoding.picodiploma.githubuserapp.ui.favorit.CardViewFavoritAdapter
import com.dicoding.picodiploma.githubuserapp.ui.favorit.FavoritListClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoritActivity : AppCompatActivity(), FavoritListClickListener {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var adapter: CardViewFavoritAdapter
    private lateinit var uriWithId: Uri

    companion object {
        const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorit User's Consumer"

        adapter = CardViewFavoritAdapter()
        adapter.listener = this
        binding.rvFavorit.layoutManager = LinearLayoutManager(this)
        binding.rvFavorit.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavoritAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavoritAsync()
        } else {
            savedInstanceState.getParcelableArrayList<FavoritEntity>(EXTRA_STATE)?.also { adapter.setData(it) }
        }
    }

    private fun loadFavoritAsync() {

        GlobalScope.launch(Dispatchers.Main) {
            isDataAvailable("loading")

            val deferredFavorit = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                FavoritMappingHelper.mapCursorToArrayList(cursor)
            }
            val users = deferredFavorit.await()
            if (users.size > 0) {
                isDataAvailable("available")
                adapter.setData(users)
            } else {
                isDataAvailable("nodata")
                adapter.setData(ArrayList())
            }
        }
    }

    private fun isDataAvailable(status: String) {
        when (status) {
            "available" -> {
                binding.txtNoData.visibility = View.GONE
                binding.rvFavorit.visibility = View.VISIBLE
            }
            "nodata" -> {
                binding.txtNoData.visibility = View.VISIBLE
                binding.rvFavorit.visibility = View.GONE
            }
            "loading" -> {
                binding.rvFavorit.visibility = View.GONE
                binding.txtNoData.visibility = View.GONE
            }
        }
    }

    override fun onDeleteClicked(view: View, favorit: FavoritEntity) {
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorit.id)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Delete User")
        alertDialogBuilder
            .setMessage("Are you sure delete ${favorit.username} from favorit?")
            .setCancelable(false)
            .setPositiveButton("Confirm") { dialog, id ->
                contentResolver.delete(uriWithId, null, null)

                Toast.makeText(this,
                    "${favorit.username} has been removed from favorit by consumer app",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("Cancel") { dialog, id -> dialog.cancel() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}