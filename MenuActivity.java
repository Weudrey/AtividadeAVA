package com.example.labdesenvolvimento.atividadeava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends Activity {

    TextView textJSON;
    private String facebookID;
    private TextView textNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        facebookID = getIntent().getStringExtra("FB_ID");
        loadUsername();
        textNome =(TextView) findViewById(R.id.textNome);

        ProfilePictureView FotoUsuario;
        FotoUsuario = (ProfilePictureView) findViewById(R.id.profile);
        AccessToken token = AccessToken.getCurrentAccessToken();
        FotoUsuario.setProfileId(token.getUserId());
        //FotoUsuario.setProfileId(facebookID);jgjhgj



        textJSON = (TextView) findViewById(R.id.textJSON);

        String url = "https://swapi.co/api/films";
        String urlCharacters = "https://swapi.co/api/people";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    textJSON.setText(response.get("title").toString() + " " + response.get("characters").toString());
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                //JSONObject title = response.getJSONObject("title");
                //textJSON.setText("" + title.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    public void loadUsername(){
        new GraphRequest(AccessToken.getCurrentAccessToken(),"me", null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                JSONObject obj = response.getJSONObject();
                try{
                    String nome = obj.getString("name");
                    textNome.setText("Olá" + nome);

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }).executeAsync();
    }
}
