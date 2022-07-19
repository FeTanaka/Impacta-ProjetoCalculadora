package br.com.impacta.projetocalculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.impacta.projetocalculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var addDecimal = false

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
    }

    fun digitaNumero(opcao: String) {
        var textoLinha = binding.textLinhaInferior.text
        if (opcao == "-1") {
            if (textoLinha.length > 0) {
                textoLinha = textoLinha.dropLast(1)
            }
            if (textoLinha == "") {
                textoLinha = "0"
            }
        } else {
            if (textoLinha == "0" && opcao != ".") {
                textoLinha = textoLinha.drop(1)
            }
            if (opcao != "." || !textoLinha.contains(".")) {
                textoLinha  = textoLinha.toString() + opcao
            }
        }
        binding.textLinhaInferior.text = textoLinha
    }
}