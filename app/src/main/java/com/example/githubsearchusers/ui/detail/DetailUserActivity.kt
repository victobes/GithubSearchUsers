package com.example.githubsearchusers.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubsearchusers.databinding.ActivityDetailUserBinding
import com.example.githubsearchusers.ui.main.RepositoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    private lateinit var repositoryAdapter : RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        repositoryAdapter =  RepositoryAdapter()
        repositoryAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        if (username != null) {
            viewModel.setUserDetail(username)
            viewModel.setListRepositories(username)
        }

        viewModel.getUserDetail().observe(this, {
            if (it != null){
                binding.apply{
                    rvRepositories.layoutManager = LinearLayoutManager(this@DetailUserActivity)
                    rvRepositories.setHasFixedSize(true)
                    rvRepositories.adapter =  repositoryAdapter

                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }

            }
        })

        viewModel.getListRepositories().observe(this, {
            if (it != null){
                repositoryAdapter.setRepositoryList(it)
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if (count > 0){
                        binding.toggleFavourite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavourite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFavourite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addToFavourite(username.toString(), id, avatarUrl.toString())
            } else {
                viewModel.removeFromFavourite(id)
            }
            binding.toggleFavourite.isChecked = _isChecked
        }
    }
}