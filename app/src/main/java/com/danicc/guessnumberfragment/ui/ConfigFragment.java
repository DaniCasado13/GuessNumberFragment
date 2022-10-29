package com.danicc.guessnumberfragment.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.danicc.guessnumberfragment.GuessNumberApplication;
import com.danicc.guessnumberfragment.R;
import com.danicc.guessnumberfragment.data.Juego;
import com.danicc.guessnumberfragment.databinding.FragmentConfigBinding;

public class ConfigFragment extends Fragment {
    FragmentConfigBinding binding;
    private GuessNumberApplication application;
    public ConfigFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region dataBinding
        binding = FragmentConfigBinding.inflate(getLayoutInflater());
        application= (GuessNumberApplication) getActivity().getApplication();
        binding.setJuego(new Juego(application.getJuego().getNombre(),application.getJuego().getNumeroIntentos()));
    //endregion
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnJugar.setOnClickListener(v -> {
            Play();
        });
    }

    //metodo que se llama desde el onClickListener del boton
    private void Play() {
        //controlar que los editext no estan vacios
        if (Campovacio()) {
            Toast.makeText(getContext(), R.string.ErrorEmpty, Toast.LENGTH_LONG).show();
        } else
            //controlar que el numero de intentos es al menos 1
            if (IntentosValidos()) {
                Toast.makeText(getContext(), R.string.ErrorZero, Toast.LENGTH_LONG).show();
            } else {
                //creo un nuevo juego
                Juego juego = new Juego();
                //seteo los valores con los valores recibidos de los editext
                juego.setNombre(binding.etNombreUser.getText().toString());
                juego.setNumeroIntentos(Integer.parseInt(binding.etIntentos.getText().toString()));
                //creamos el bundle
                Bundle bundle = new Bundle();
                bundle.putSerializable("juego", juego);
                //navegamos de un fragment a otro con el navhostfragment pasando el bundle como argumento
                NavHostFragment.findNavController(this).navigate(R.id.action_ConfigFragment_to_PlayFragment, bundle);
            }
    }

    private boolean Campovacio() {
        return TextUtils.isEmpty(binding.etNombreUser.getText())
                || TextUtils.isEmpty(binding.etIntentos.getText());
    }

    private boolean IntentosValidos() {
        return Integer.parseInt(binding.etIntentos.getText().toString()) < 1;
    }
}