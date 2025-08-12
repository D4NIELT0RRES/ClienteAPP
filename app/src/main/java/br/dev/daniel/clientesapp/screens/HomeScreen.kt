package br.dev.daniel.clientesapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.dev.daniel.clientesapp.R
import br.dev.daniel.clientesapp.model.Cliente
import br.dev.daniel.clientesapp.service.RetrofitFactory
import br.dev.daniel.clientesapp.ui.theme.ClientesappTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await


@Composable
fun HomeScreen(modifier: Modifier = Modifier){

    // Criar uma instância do RetrofitFactory
    val clienteApi = RetrofitFactory().getClienteService()

    //Criar uma variavel de estado para armazenar a lista de clientes da API
    var clientes by remember {
        mutableStateOf(listOf<Cliente>())
    }

    LaunchedEffect(Dispatchers.IO) {
        clientes = clienteApi.exibirTodos().await()
        println(clientes)
    }


    Scaffold(
        topBar = {
            BarraDeTitulo()
        },
        bottomBar = {
            BarraDeNavegacao()
        },
        floatingActionButton = {
            BotaoFlutuante()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "icone da lista de clientes",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Lista de Clientes"
                )
            }
            LazyColumn {
                items(clientes){ cliente ->
                    ClientCard(cliente)
                }
            }
        }
    }
}

@Composable
fun ClientCard(cliente: Cliente){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 4.dp
            ),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = cliente.nome, fontWeight = FontWeight.Bold)
                Text(text = cliente.email, fontSize = 12.sp)
            }
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Deletar"
            )
        }
    }
}

@Preview
@Composable
private fun ClientCardPreview() {
    ClientesappTheme {
        ClientCard(Cliente())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDeTitulo(modifier: Modifier = Modifier){
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Column {
                    Text(
                        text = "Daniel Alves",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "alvesdaniel@gmail.com",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Card (
                    modifier = Modifier
                        .size(60.dp),
                    shape = CircleShape
                ){
                    Image(
                        painter = painterResource(R.drawable.danielalves),
                        contentDescription = "Foto perfil"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun BarraDeTituloPreview(){
    ClientesappTheme {
        BarraDeTitulo()
    }
}

@Composable
fun BarraDeNavegacao(modifier: Modifier = Modifier){
    NavigationBar(
        containerColor = MaterialTheme
            .colorScheme.primary,
        contentColor = MaterialTheme
            .colorScheme.onPrimary
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Home", color = MaterialTheme.colorScheme.onPrimary)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Favorite", color = MaterialTheme.colorScheme.onPrimary)
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Menu", color = MaterialTheme.colorScheme.onPrimary)
            }
        )
    }
}

@Preview
@Composable
private fun BarraDeNavegacaoPreview(){
    ClientesappTheme {
        BarraDeNavegacao()
    }
}

@Composable
fun BotaoFlutuante(modifier: Modifier = Modifier){
    FloatingActionButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Botão adicionar"
        )
    }
}

@Preview
@Composable
private fun BotaoFlutuantePreview(){
    ClientesappTheme {
        BotaoFlutuante()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview(){
    ClientesappTheme {
        HomeScreen()
    }
}