package br.dev.daniel.clientesapp.screens

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.dev.daniel.clientesapp.model.Cliente
import br.dev.daniel.clientesapp.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@Composable
fun FormCliente(navController: NavHostController?) {

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

    //Variável que vai exibir a caixa de diálogo
    var mostrarTelaSucesso by remember {
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "ícone do cadastro"
            )
            Text(
                text = "Novo Cliente",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
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
                        mostrarTelaSucesso = true
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

        if(mostrarTelaSucesso){

            AlertDialog(
                onDismissRequest = {},
                title = { Text(text = "Sucesso")},
                text = { Text(text = "Cliente gravado coms sucesso!")},
                confirmButton = {
                    Button(
                        onClick = {
                            navController!!.navigate("Home")
                        }
                    ) {
                        Text( text = "OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormClientePreview(){
    FormCliente(null)
}
