package com.claytoneduard.requisicoeshttp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button botaoRecuperar;
    private TextView textoResultado;
    private TextInputEditText textoCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoRecuperar = findViewById(R.id.buttonRecuperar);
        textoResultado = findViewById(R.id.textResultado);
        //textoCep = findViewById(R.id.textoCep);

        botaoRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyTask task = new MyTask();
                String urlAPI = "https://blockchain.info/ticker";
                //String dolar  = "dolar";
                //String cep = "75535210";
                //String moeda = "USD";
                //String urlApi = "https://blockchain.info/tobtc?currency="+moeda+"&value=500";
                //String urlCep = "https://viacep.com.br/ws/" + textoCep.getText() + "/json";
                //task.execute(urlCep);
                task.execute(urlAPI);

            }
        });
    }

    // class para consultao do blokchain
    //https://blockchain.info/ticker

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringURL = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringURL); // requisição HTTP
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection(); // reponsavel por fazer a conexao
                //recuperar dados em bytes
                inputStream = conexao.getInputStream();
                // lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);
                // objeto utilizado para leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";
                //lendo cada linha enquanto estiver linhas a serem exibidas
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
         /*   String logradouro = null;
            String cep = null;
            String complemento = null;
            String bairro = null;
            String localidade = null;
            String uf = null;
            */

            JSONObject jsonObject = null;
            String objetoValor = null;
            JSONObject jsonObjectReal = null;
            String valorMoeda = null;
            String simbolo = null;

            try {
               /* jsonObject = new JSONObject(resultado);
                logradouro = jsonObject.getString("logradouro");
                cep = jsonObject.getString("cep");
                complemento = jsonObject.getString("complemento");
                bairro = jsonObject.getString("bairro");
                localidade = jsonObject.getString("localidade");
                uf = jsonObject.getString("uf");*/

                jsonObject = new JSONObject(resultado);
                objetoValor = jsonObject.getString("BRL");

                jsonObjectReal = new JSONObject(objetoValor);
                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            //textoResultado.setText(resultado);
            //textoResultado.setText(logradouro + " / " + cep + " / " + complemento + " / " + bairro + " / " + localidade + " / " + uf);
            textoResultado.setText(simbolo + " " + valorMoeda);
        }
    }

}
