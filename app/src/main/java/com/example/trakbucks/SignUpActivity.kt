package com.example.trakbucks

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.trakbucks.data.TransactionViewModel
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.trakbucks.data.*
import com.example.trakbucks.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private val myTransactionViewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(
            (this.application as TransactionApplication).database
                .transactionDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.goButton.setOnClickListener{
            if(binding.name.editText?.text.toString() == "" )
            {
                binding.name.error = "Name can't be empty."
                Toast.makeText(this,"Enter your Name to proceed.",LENGTH_SHORT).show()
            }
            else
            {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this /* Activity context */)
                with (sharedPreferences.edit()) {
                    putString("signOut","true")
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                intent.putExtra("Name", binding.name.editText?.text.toString())

                val name = binding.name.editText?.text.toString()


                val uri = ("android.resource://com.example.trakbucks/" + R.drawable.ic_baseline_person_24)
                val user= User(0,uri, name, 0,0,0)
                myTransactionViewModel.addUser(user)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.name.editText?.setText(savedInstanceState.getString("SIGN_UP_NAME"))
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("SIGN_UP_NAME", binding.name.editText?.text.toString())
        super.onSaveInstanceState(outState)
    }
}