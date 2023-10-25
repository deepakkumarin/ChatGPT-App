package com.deepakkumarinc.chatgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepakkumarinc.chatgpt.Adapters.MessageAdapter
import com.deepakkumarinc.chatgpt.Api.Apiutilities
import com.deepakkumarinc.chatgpt.Models.Request.ChatRequest
import com.deepakkumarinc.chatgpt.databinding.ActivityChatBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.Exception

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    var list = ArrayList<MessageModel>()
    private lateinit var mLayoutManager : LinearLayoutManager
    private lateinit var madapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{finish()}

        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.stackFromEnd =true
        madapter = MessageAdapter(list)
        binding.recycleView.adapter = madapter

        binding.recycleView.layoutManager = mLayoutManager

        binding.sendbtn.setOnClickListener{
            if (binding.userMsg.text!!.isEmpty()){
                Toast.makeText(this,"Please ask your question",Toast.LENGTH_SHORT).show()
            }
            else{
                callApi()
            }
        }
    }

    private fun callApi() {
        list.add(MessageModel(true,false,binding.userMsg.text.toString()))

        madapter.notifyItemInserted(list.size-1)

        binding.recycleView.recycledViewPool.clear()
        binding.recycleView.smoothScrollToPosition(list.size-1)

        val apiInterface = Apiutilities.getApiInterface()

        val  requestBody = RequestBody.create(MediaType.parse("application/json"),
            Gson().toJson(
                ChatRequest(
                    250,
                     "gpt-3.5-turbo-instruct",
                    binding.userMsg.text.toString(),

         0.7
                )
            )

            )
        val contentType = "application/json"
        val authorization = "Bearer ${Utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO){

            try {
                val response = apiInterface.getChat(
                    contentType,authorization,requestBody
                )
                val textresponse = response.choices.first().text
                list.add(MessageModel(false,false,textresponse))

                withContext(Dispatchers.Main){
                    madapter.notifyItemInserted(list.size-1)

                    binding.recycleView.recycledViewPool.clear()
                    binding.recycleView.smoothScrollToPosition(list.size-1)
                }

                binding.userMsg.text!!.clear()


            }
            catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ChatActivity,e.message,Toast.LENGTH_SHORT).show() 
                }
            }



        }

    }
}