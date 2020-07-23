package com.projeto.generico;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.generico.domain.Categoria;
import com.projeto.generico.domain.Cidade;
import com.projeto.generico.domain.Cliente;
import com.projeto.generico.domain.Endereco;
import com.projeto.generico.domain.Estado;
import com.projeto.generico.domain.ItemPedido;
import com.projeto.generico.domain.Pagamento;
import com.projeto.generico.domain.PagamentoComBoleto;
import com.projeto.generico.domain.PagamentoComCartao;
import com.projeto.generico.domain.Pedido;
import com.projeto.generico.domain.Produto;
import com.projeto.generico.domain.enums.EstadoPagamento;
import com.projeto.generico.domain.enums.TipoCliente;
import com.projeto.generico.repositories.CategoriaRepository;
import com.projeto.generico.repositories.CidadeRepository;
import com.projeto.generico.repositories.ClienteRepository;
import com.projeto.generico.repositories.EnderecoRepository;
import com.projeto.generico.repositories.EstadoRepository;
import com.projeto.generico.repositories.ItemPedidoRepository;
import com.projeto.generico.repositories.PagamentoRepository;
import com.projeto.generico.repositories.PedidoRepository;
import com.projeto.generico.repositories.ProdutoRepository;

//CommandLineRunner para permitir executar uma ação quando a aplicação iniciar

@SpringBootApplication
public class GenericoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GenericoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}