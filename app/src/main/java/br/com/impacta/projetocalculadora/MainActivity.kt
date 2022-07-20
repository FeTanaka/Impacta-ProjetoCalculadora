package br.com.impacta.projetocalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.com.impacta.projetocalculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    // Declaração das funções Lambdas
    // Funções Lambda: funções anônimas que guardamos a referência através de variavéis
    private val adicao: (Double, Double) -> Double = {a, b -> a + b} // Forma "longa" de declarar uma função lamba
    private val subtracao = {a: Double, b: Double -> a - b} // Forma "reduzida" de declarar uma função lamba
    private val multiplicacao = {a: Double, b: Double -> a * b}
    private val divisao : (Double, Double) -> Double = {a, b -> a / b}
    private val porcentagem = {a: Double, b: Double -> a * (divisao(b, 100.0))}

    // Declaração da nossa função de ordem superior
    // Função de ordem superior: é uma função que recebe outra função como parametro
    fun calculaValorFinal(numero1: Double, numero2: Double, operacao: (Double, Double) -> Double): Double {
        return operacao(numero1, numero2)
    }

    private var operacaoSelecionada:String? = null
    private var ultimoValor:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializador()
    }

    fun inicializador() {
        binding.btnNum0.setOnClickListener { digitaNumero("0") }
        binding.btnNum1.setOnClickListener { digitaNumero("1") }
        binding.btnNum2.setOnClickListener { digitaNumero("2") }
        binding.btnNum3.setOnClickListener { digitaNumero("3") }
        binding.btnNum4.setOnClickListener { digitaNumero("4") }
        binding.btnNum5.setOnClickListener { digitaNumero("5") }
        binding.btnNum6.setOnClickListener { digitaNumero("6") }
        binding.btnNum7.setOnClickListener { digitaNumero("7") }
        binding.btnNum8.setOnClickListener { digitaNumero("8") }
        binding.btnNum9.setOnClickListener { digitaNumero("9") }
        binding.btnNumPonto.setOnClickListener { digitaNumero(".") }
        binding.btnNumApagar.setOnClickListener { digitaNumero("-1") }

        binding.btnOprAdicao.setOnClickListener { digitaOperacao("+") }
        binding.btnOprSubtracao.setOnClickListener { digitaOperacao("-") }
        binding.btnOprMultiplicacao.setOnClickListener { digitaOperacao("*") }
        binding.btnOprDivisao.setOnClickListener { digitaOperacao("/") }
        binding.btnOprPorcetagem.setOnClickListener { digitaOperacao("%") }

        binding.btnOprIgual.setOnClickListener { mostraResultado() }
    }

    fun digitaNumero(opcao: String) {
        var textoLinha = binding.textLinhaInferior.text
        // Usuário clicou em apagar
        if (opcao == "-1") {
            // Verificamos se existe algum caracter na string
            if (textoLinha.length > 0) {
                textoLinha = textoLinha.dropLast(1)
            }
            // Verificamos se após a exclusão a string ficou vazia
            // Caso tenha ficado, adicionamos o número 0
            if (textoLinha == "") {
                textoLinha = "0"
            }
        } else { // Usuário clicou em um número ou ponto
            // Aqui verificamos se o usuário apertou algo diferente de .(ponto)
            // e caso o valor na linha ainda seja 0, retiramos o zero.
            if (textoLinha == "0" && opcao != ".") {
                textoLinha = textoLinha.drop(1)
            }
            // Aqui verificamos se o usuário apertou algo diferente de .(ponto)
            // ou o valor na linha ainda não contenha nenhum .(ponto).
            // Caso alguma das condições sejam válidas adicionamos o conteúdo da opção
            // ao final da linha
            if (opcao != "." || !textoLinha.contains(".")) {
                textoLinha  = textoLinha.toString() + opcao

            }
        }
        binding.textLinhaInferior.text = textoLinha
    }

    fun digitaOperacao(opcao: String) {
        operacaoSelecionada = opcao

        // Pegamos o número digitado pelo usuário
        var linhaInferior = binding.textLinhaInferior.text.toString()
        // Neste if tratamos o caso de o usuário apertar duas operações seguidas.
        var linhaSuperior = if (linhaInferior != "0") {
            // Só reatribuimos um novo valor para a variavel ultimoValor caso o número digitado
            // seja diferente de 0
            ultimoValor = linhaInferior.toDouble()
            "$linhaInferior $opcao"
        } else {
            // Aqui, não houve uma entrada de número, então podemos simplesmente trocar a operação
            "$ultimoValor $opcao"
        }

        // Mostramos as novas informações nos campos de texto
        binding.textLinhaSuperior.text = linhaSuperior
        binding.textLinhaInferior.text = "0"
    }


    fun mostraResultado() {
        // Pego o novo número digitado após a entrada da operação
        var numero2 = binding.textLinhaInferior.text.toString().toDouble()
        // Faço o cálculo da operação
        // pegando o valor da variavel ultimoValor e verificando se é diferente de null
        // caso seja diferente de null, executamos o cálculo
        // Funcionamento do LET: O let verifica uma condição a esquerda, caso se verdadeira,
        // pega o valor da condição e executa o trecho de código entre { }
        // e retorna o valor do mesmo tipo da condição, o último valor que ele encontrar dentro
        // do código que foi executado.
        var resultado = ultimoValor?.let { numero1 ->
            when(operacaoSelecionada) {
                "+" -> calculaValorFinal(numero1, numero2, adicao)
                "-" -> calculaValorFinal(numero1, numero2, subtracao)
                "*" -> calculaValorFinal(numero1, numero2, multiplicacao)
                "/" -> calculaValorFinal(numero1, numero2, divisao)
                else -> calculaValorFinal(numero1, numero2, porcentagem)
            }
        }
        // atribuimos os novos valores aos campos de texto.
        binding.textLinhaSuperior.text = ""
        binding.textLinhaInferior.text = resultado.toString()
    }

}