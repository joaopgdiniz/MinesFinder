import java.io.Serializable;
import java.util.ArrayList;

public class TabelaRecordes implements Serializable {
    private String nomeDoJogador;
    private long tempoDeJogo;

    private transient ArrayList<TabelaRecordesListener> listeners;

    public TabelaRecordes() {
        this.nomeDoJogador = "Anónimo";
        this.tempoDeJogo = 999999999;
        listeners = new ArrayList<>();
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(list);
    }
    public void removeTabelaRecordesListener(TabelaRecordesListener list) {
        if (listeners != null)
            listeners.remove(list);
    }

    private void notifyRecordesActualizados() {
        if (listeners == null)
            return;
        for (TabelaRecordesListener list:listeners)
            list.recordesActualizados(this);
    }

    public String getNomeDoJogador() {
        return nomeDoJogador;
    }

    public long getTempoDeJogo() {
        return tempoDeJogo;
    }

    public void setRecorde(String nomeDoJogador, long tempoDeJogo) {
        this.nomeDoJogador = nomeDoJogador;
        this.tempoDeJogo = tempoDeJogo;
        notifyRecordesActualizados();
    }
}
