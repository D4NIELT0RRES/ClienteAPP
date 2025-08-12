package br.dev.daniel.clientesapp.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.daniel.clientesapp.model.Cliente
import br.dev.daniel.clientesapp.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import java.util.regex.Pattern

@Composable
fun FormCliente(modifier: Modifier = Modifier){

    // variáveis de estado para utilizar no Outlined
    var nomeCliente by remember {
        mutableStateOf("")
    }

    var emailCliente by remember {
        mutableStateOf("")
    }

    // Variáveis de estado para validar a entrada do usuário
    var isNomeError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }

    // Criar uma instância do RetrofitFactory
    val clienteApi = RetrofitFactory().getClienteService()

    fun validar():Boolean{
        isNomeError = nomeCliente.length < 1
        isEmailError = !Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()
        return !isNomeError && !isEmailError
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = nomeCliente,
            onValueChange = { nome ->
                nomeCliente = nome
            },
            label = {
                Text(text = "Nome do cliente")
            },
            supportingText = {
                if (isNomeError) {
                    Text(text = "Nome do usuário é obrigatório")
                }
            },
            trailingIcon = {
                if (isNomeError){
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "")
                }
            },
            isError = isNomeError,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = emailCliente,
            onValueChange = { email ->
                emailCliente = email
            },
            label = {
                Text(text = "E-mail do cliente")
            },

            supportingText = {
                if (isEmailError) {
                    Text(text = "E-mail do cliente é obrigatório")
                }
            },
            trailingIcon = {
                if (isEmailError){
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = ""
                    )
                }
            },
            isError = isEmailError,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {

                if (validar()){
                    //Cria um cliente com os dados informados
                    val cliente = Cliente(
                        nome = nomeCliente,
                        email = emailCliente
                    )

                    //Requisição POST para a API
                    GlobalScope.launch(Dispatchers.IO) {
                        val novoCliente = clienteApi.gravar(cliente).await()
                        println(novoCliente)
                    }
                }else{
                    println("***************** NOME DOS DADOS INCORRETOS ***************** ")
                }

            },
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Gravar Cliente")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormClientePreview(){
    FormCliente()
}
