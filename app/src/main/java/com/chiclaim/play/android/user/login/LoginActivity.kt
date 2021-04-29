package com.chiclaim.play.android.user.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chiclaim.play.android.MainActivity
import com.chiclaim.play.android.R
import com.chiclaim.play.android.base.BaseActivity
import com.chiclaim.play.android.bean.ArticleBO
import com.chiclaim.play.android.bean.PageBO
import com.chiclaim.play.android.bean.RespBO
import com.chiclaim.play.android.source.Api
import com.chiclaim.play.android.source.WanApi
import retrofit2.Call
import retrofit2.Response

/**
 * 登录页面
 *
 * Created by kumu@2dfire.com
 */
class LoginActivity : BaseActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText

    private val wanApi = Api.create(WanApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        editUsername = findViewById(R.id.edit_username)
        editPassword = findViewById(R.id.edit_password)


        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(LoginViewModel::class.java)


        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginViewModel.getLoginLiveData(
                editUsername.text.toString(),
                editPassword.text.toString()
            )
                .observe(this) {
                    if (it.isSuccess()) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "${it.errorMsg}", Toast.LENGTH_SHORT).show()
                    }
                }

        }


        findViewById<Button>(R.id.btn_test).setOnClickListener {
            wanApi.getArticleList(0).enqueue(
                object : retrofit2.Callback<RespBO<PageBO<ArticleBO>>> {
                    override fun onResponse(
                        call: Call<RespBO<PageBO<ArticleBO>>>,
                        response: Response<RespBO<PageBO<ArticleBO>>>
                    ) {
                        val resp = response.body()
                        if (resp?.isSuccess() == true) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                resp?.errorMsg,
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                    override fun onFailure(call: Call<RespBO<PageBO<ArticleBO>>>, t: Throwable) {
                        t.printStackTrace()
                    }
                }
            )

        }

    }


}