package com.iop.listprovider.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.iop.listprovider.R;

/**
 * Created by Rafael Iop
 *
 * Classe utilizada para auxiliar em métodos que envolvem alterações
 * de elementos (como Views) da UI.
 */

public class UIHelper {

    private Context context;

    public UIHelper(Context context) {
        this.context = context;
    }

    /**
     * Troca a cor de um ImageView passado por parâmetro, baseado na prioridade.
     * O metodo foi criado para evitar a duplicação de código,
     * pois o trecho é utilizado 3 vezes
     */
    public void setPrioridades(ImageView iv, int idPrioridade){
        Resources res = context.getResources();

        switch (idPrioridade){
            case 0:
                iv.setBackgroundColor(res.getColor(R.color.prioridade_baixa));
                break;
            case 1:
                iv.setBackgroundColor(res.getColor(R.color.prioridade_media));
                break;
            case 2:
                iv.setBackgroundColor(res.getColor(R.color.prioridade_alta));
                break;
        }
    }
}
