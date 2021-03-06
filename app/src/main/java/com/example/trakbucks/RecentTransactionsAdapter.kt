package com.example.trakbucks

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trakbucks.data.Transaction
import com.example.trakbucks.data.TransactionViewModel
import com.google.android.material.card.MaterialCardView
import com.mikhaellopez.circularimageview.CircularImageView
import java.text.NumberFormat
import java.util.*

class RecentTransactionsAdapter() :RecyclerView.Adapter<RecentTransactionsAdapter.RecentTransactionsViewHolder>() {

    private lateinit var myTransactionViewModel: TransactionViewModel
    var recentTransactionList = emptyList<Transaction>()
    lateinit var mContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTransactionsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction,parent,false)
        return RecentTransactionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecentTransactionsViewHolder, position: Int) {
        val currentTransaction = recentTransactionList[position]
        holder.profileImage_tran.setImageURI(Uri.parse(currentTransaction.personImage))
        holder.name_tran.text= currentTransaction.name
        holder.date_tran.text=currentTransaction.date
        holder.time_tran.text=currentTransaction.time

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences( mContext/* Activity context */)
        val currency = sharedPreferences.getString("Currency", "INR")

        when(currency)
        {
            "INR"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","IN")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
            "EUR"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","IE")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
            "DOL"->{
                val str = NumberFormat.getCurrencyInstance(Locale("en","US")).format(currentTransaction.amount.toLong())
                holder.amount_tran.text=str
            }
        }


        when(currentTransaction.type)
        {
            1-> holder.type_tran.text="Received From"
            2-> holder.type_tran.text="Credited To"
            else -> holder.type_tran.text="Invalid Transaction"
        }
    }

    override fun getItemCount(): Int {
        return recentTransactionList.size
    }

    class RecentTransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val profileImage_tran: CircularImageView = itemView.findViewById(R.id.profile_image_transaction)
        val name_tran: TextView = itemView.findViewById(R.id.name_transaction)
        val amount_tran: TextView = itemView.findViewById(R.id.amount_transaction)
        val date_tran: TextView = itemView.findViewById(R.id.date_transaction)
        val time_tran: TextView = itemView.findViewById(R.id.time_transaction)
        val type_tran: TextView = itemView.findViewById(R.id.type_transaction)
    }

    fun setData(transactionList: List<Transaction>){
        this.recentTransactionList = transactionList
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mContext = recyclerView.context
    }

}