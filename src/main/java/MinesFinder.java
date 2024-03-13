import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinesFinder extends JFrame {
    private JPanel painelPrincipal;
    private JButton jogoFacilButton;
    private JButton jogoDificilButton;
    private JButton jogoMedioButton;
    private JButton sairButton;
    private JLabel lblFacil;
    private JLabel lblMedio;
    private JLabel lblDificil;

    private TabelaRecordes recordesFacil;
    private TabelaRecordes recordesMedio;
    private TabelaRecordes recordesDificil;


    public MinesFinder(String title){
        super(title);
        recordesFacil = new TabelaRecordes();
        recordesMedio = new TabelaRecordes();
        recordesDificil = new TabelaRecordes();
        lerRecordesDoDisco();

        lblFacil.setText(recordesFacil.getNomeDoJogador() + " " + Long.toString(recordesFacil.getTempoDeJogo()/1000) + "s");
        lblMedio.setText(recordesMedio.getNomeDoJogador() + " " + Long.toString(recordesMedio.getTempoDeJogo()/1000) + "s");
        lblDificil.setText(recordesDificil.getNomeDoJogador() + " " + Long.toString(recordesDificil.getTempoDeJogo()/1000) + "s");

        recordesFacil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesFacilActualizado(recordes);
                guardarRecordesDisco();
            }
        });

        recordesMedio.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesMedioActualizado(recordes);
                guardarRecordesDisco();
            }
        });

        recordesDificil.addTabelaRecordesListener(new TabelaRecordesListener() {
            @Override
            public void recordesActualizados(TabelaRecordes recordes) {
                recordesDificilActualizado(recordes);
                guardarRecordesDisco();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(painelPrincipal);
        pack();

        sairButton.addActionListener(this::sairButtonActionPerformed);
        jogoFacilButton.addActionListener(this::jogoFacilButtonActionPerformed);
        jogoMedioButton.addActionListener(this::jogoMedioButtonActionPerformed);
        jogoDificilButton.addActionListener(this::jogoDificilButtonActionPerformed);
    }

    private void recordesFacilActualizado(TabelaRecordes recordes) {
        lblFacil.setText(recordes.getNomeDoJogador() + " " + Long.toString(recordes.getTempoDeJogo()/1000) + "s");
    }
    private void recordesMedioActualizado(TabelaRecordes recordes) {
        lblMedio.setText(recordes.getNomeDoJogador() + " " + Long.toString(recordes.getTempoDeJogo()/1000) + "s");
    }
    private void recordesDificilActualizado(TabelaRecordes recordes) {
        lblDificil.setText(recordes.getNomeDoJogador() + " " + Long.toString(recordes.getTempoDeJogo()/1000) + "s");
    }
    public void jogoDificilButtonActionPerformed(ActionEvent e) {
        var janelaDeJogo = new JanelaDeJogo(new CampoMinado(16,30,90), recordesDificil);
        janelaDeJogo.setVisible(true);
    }

    public void jogoMedioButtonActionPerformed(ActionEvent e) {
        var janelaDeJogo = new JanelaDeJogo(new CampoMinado(16,16,40), recordesMedio);
        janelaDeJogo.setVisible(true);
    }

    public void jogoFacilButtonActionPerformed(ActionEvent e) {
        var janelaDeJogo = new JanelaDeJogo(new CampoMinado(9,9,10), recordesFacil);
        janelaDeJogo.setVisible(true);
    }
    private void sairButtonActionPerformed(ActionEvent e){
        System.exit(0);
    }

    private void guardarRecordesDisco() {
        ObjectOutputStream oos = null;
        try {
            File f  =new File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(recordesFacil);
            oos.writeObject(recordesMedio);
            oos.writeObject(recordesDificil);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void lerRecordesDoDisco() {
        ObjectInputStream ois = null;
        File f = new
                File(System.getProperty("user.home")+File.separator+"minesfinder.recordes");
        if (f.canRead()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(f));
                recordesFacil=(TabelaRecordes) ois.readObject();
                recordesMedio=(TabelaRecordes) ois.readObject();
                recordesDificil=(TabelaRecordes) ois.readObject();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MinesFinder.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

    public static void main(String[] args) {
        new MinesFinder("MinesFinder").setVisible(true);
    }
}
