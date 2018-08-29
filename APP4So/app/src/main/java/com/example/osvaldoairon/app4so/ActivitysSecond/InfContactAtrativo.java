package com.example.osvaldoairon.app4so.ActivitysSecond;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.osvaldoairon.app4so.Modelo.AtrativosTuristicos;
import com.example.osvaldoairon.app4so.R;
import com.example.osvaldoairon.app4so.Sqlite.HelperSQLAtrativos;
import com.example.osvaldoairon.app4so.Util.FontesTerceiras;

import org.w3c.dom.Text;

public class InfContactAtrativo extends AppCompatActivity {

    private TextView nomeAtrativoC;
    private TextView nomeResponsavelAt;
    private TextView emailAtrativoC;
    private TextView contatoAtrativoC;
    private TextView nome_preenchimento_AtrativoC;
    private TextView email_preenchimento_AtrativoC;
    private TextView contato_preenchimento_AtrativoC;

    private HelperSQLAtrativos helperSQLAtrativos;
    private AtrativosTuristicos primeiro;
    private AtrativosTuristicos secundarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_contact_atrativo);
        helperSQLAtrativos = new HelperSQLAtrativos(this);
        helperSQLAtrativos.recoverDataSQLAtrativos();

        View decorView = getWindow().getDecorView();

        int uiopt = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        String index = getIntent().getStringExtra("position");
        decorView.setSystemUiVisibility(uiopt);


        nomeAtrativoC = (TextView)findViewById(R.id.nomedetailAtC);
        nomeResponsavelAt=(TextView)findViewById(R.id.nomeResponsavelAtrativo);
        emailAtrativoC=(TextView)findViewById(R.id.email_atrativo);
        contatoAtrativoC=(TextView)findViewById(R.id.contato_atrativo);
        nome_preenchimento_AtrativoC=(TextView)findViewById(R.id.nome_preenchimento);
        email_preenchimento_AtrativoC=(TextView)findViewById(R.id.email_preenchimento);
        contato_preenchimento_AtrativoC=(TextView)findViewById(R.id.contato_preenchimento);

        if(index.equals("0")){
            primeiro = helperSQLAtrativos.getReturnList().get(0);
            nomeAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
            nomeAtrativoC.setText(primeiro.getNome());


            if(primeiro.getNome_responsavel_atrativo().equals("")){

                nomeResponsavelAt.setText("Nome do responsavel pelo atrativo não informado!");
            }else{

                nomeResponsavelAt.setText("Nome: " +primeiro.getNome_responsavel_atrativo());
            }

            if(primeiro.getEmail_responsavel_atrativo().equals("")){
                emailAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                emailAtrativoC.setText("Email não informado!");
            }else{
                emailAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                emailAtrativoC.setText("Email: " + primeiro.getEmail_responsavel_atrativo());
            }

            if(primeiro.getInfoContato().equals("")){
                contatoAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contatoAtrativoC.setText("Contato não informado");
            }else{
                contatoAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contatoAtrativoC.setText("Contato: "+ primeiro.getInfoContato());
            }

            if(primeiro.getNome_responsavel_preenchimento().equals("")){
                nome_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nome_preenchimento_AtrativoC.setText("Nome não informado!");
            }else{
                nome_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nome_preenchimento_AtrativoC.setText("Nome: "+primeiro.getNome_responsavel_preenchimento());
            }
            if(primeiro.getEmail_responsavel_preenchimento().equals("")){
                email_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                email_preenchimento_AtrativoC.setText("Email do responsavel pelo preenchimento, não informado!");
            }else{
                email_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                email_preenchimento_AtrativoC.setText("Email:" +primeiro.getEmail_responsavel_preenchimento());
            }
            if(primeiro.getContato_responsavel_preenchimento().equals("")){
                contato_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contato_preenchimento_AtrativoC.setText("Contato do responsavel pelo preenchimento não informado");
            }else{
                contato_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contato_preenchimento_AtrativoC.setText("Contato: " + primeiro.getContato_responsavel_preenchimento());
            }


        }else{
            int index_int = Integer.parseInt(index);
            secundarios = helperSQLAtrativos.getReturnList().get(index_int);
            nomeAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
            nomeAtrativoC.setText(secundarios.getNome());


            if(secundarios.getNome_responsavel_atrativo().equals("")){
                nomeResponsavelAt.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nomeResponsavelAt.setText("Responsavel pelo atrativo não informado!");
            }else{
                nomeResponsavelAt.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nomeResponsavelAt.setText("Nome: " +secundarios.getNome_responsavel_atrativo());
            }

            if(secundarios.getEmail_responsavel_atrativo().equals("")){
                emailAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                emailAtrativoC.setText("Email não informado!");
            }else{
                emailAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                emailAtrativoC.setText("Email: " + secundarios.getEmail_responsavel_atrativo());
            }

            if(secundarios.getInfoContato().equals("")){
                contatoAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contatoAtrativoC.setText("Contato não informado");
            }else{
                contatoAtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contatoAtrativoC.setText("Contato: "+ secundarios.getInfoContato());
            }

            if(secundarios.getNome_responsavel_preenchimento().equals("")){
                nome_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nome_preenchimento_AtrativoC.setText("Responsavel pelo preenchimento, não informado!");
            }else{
                nome_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                nome_preenchimento_AtrativoC.setText("Nome: "+secundarios.getNome_responsavel_preenchimento());
            }
            if(secundarios.getEmail_responsavel_preenchimento().equals("")){
                email_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                email_preenchimento_AtrativoC.setText("Email responsavel pelo preenchimento, não informado!");
            }else{
                email_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                email_preenchimento_AtrativoC.setText("Email:" +secundarios.getEmail_responsavel_preenchimento());
            }
            if(secundarios.getContato_responsavel_preenchimento().equals("")){
                contato_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contato_preenchimento_AtrativoC.setText("Responsavel pelo preenchimento não informado");
            }else{
                contato_preenchimento_AtrativoC.setTypeface(FontesTerceiras.setRobotoRegular(getBaseContext()));
                contato_preenchimento_AtrativoC.setText("Contato:" + secundarios.getContato_responsavel_preenchimento());
            }

        }

    }
}
