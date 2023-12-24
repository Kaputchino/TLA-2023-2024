/*

Projet TLA 2023-24

Réalisé par :
 * MULARD Andreas 
 * BERTHEL Gabriel
 * LAGASSE Adrian 
 * YOUSRI Célia

*/

package tla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/*
 * Classe principale.
 * 
 * Gère l'IHM.
 * Affiche les lieux et propositions suivant les décisions du joueur.
 */

public class App implements ActionListener {

    // Nombre de lignes dans la zone de texte
    final int nbLignes = 20;

    Map<Integer, Lieu> lieux;
    Lieu lieuActuel;

    JFrame frame;
    JPanel mainPanel;

    // Labels composant la zone de texte
    JLabel[] labels;

    // Boutons de proposition
    ArrayList<JButton> btns;

    public static void main(String[] args) {
        App app = new App();
        SwingUtilities.invokeLater(app::init);
    }

    private void init() {
        final JFileChooser fc = new JFileChooser();
        boolean fileSelected = false;
        fc.setDialogTitle("Selectioner votre aventure");
        fc.setCurrentDirectory(new File("scenario"));
        fc.setApproveButtonText("En avant pour l'aventure");
        while(!fileSelected){
            int returnVal = fc.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                ContenuAventure.setFile(file);
                fileSelected = true;
                //This is where a real application would open the file.
            }
        }


        // Charge le contenu de l'aventure
        lieux = ContenuAventure.init();

        // Prépare l'IHM
        labels = new JLabel[nbLignes];
        btns = new ArrayList<>();

        frame = new JFrame(ContenuAventure.titre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        frame.add(mainPanel);

        for(int i=0;i<nbLignes;i++) {
            labels[i] = new JLabel(" ");
            mainPanel.add(labels[i], new GridBagConstraints() {{
                this.gridwidth = GridBagConstraints.REMAINDER;
                this.anchor = GridBagConstraints.WEST;
                this.insets = new Insets(0,20,0,20);
            }});
            labels[i].setMinimumSize(new Dimension(750, 20));
            labels[i].setPreferredSize(new Dimension(750, 20));
        }

        // Démarre l'aventure au lieu n° 1
        lieuActuel = lieux.get(1);

        initLieu();


        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Affichage du lieu lieuActuel et créations des boutons de propositions correspondantes
     * à ce lieu
     */
    void initLieu(){
        for(JButton btn: btns) {
            mainPanel.remove(btn);
        }
        btns.clear();
        affiche(lieuActuel.description.split("\n"));
        frame.pack();
        for(int i=0; i<lieuActuel.propositions.size(); i++) {
            JButton btn = new JButton("<html><p>" + lieuActuel.propositions.get(i).texte + "</p></html>");
            btn.setActionCommand(String.valueOf(i));
            btn.addActionListener(this);

                try {
                    btn.setEnabled(lieuActuel.propositions.get(i).getValueOfCondition());
                } catch (Exception e) {
                    showMessageDialog(null, e,
                            "Condition pour proposition du lieu n" +i + lieuActuel.propositions.get(i).condition +" non valide", ERROR_MESSAGE);
                }
            
            mainPanel.add(btn, new GridBagConstraints() {{
                this.gridwidth = GridBagConstraints.REMAINDER;
                this.fill = GridBagConstraints.HORIZONTAL;
                this.insets = new Insets(3,20,3,20);
            }});
            btns.add(btn);
        }
        frame.pack();
    }

    /*
     * Gère les clics sur les boutons de propostion
     */
    public void actionPerformed(ActionEvent event) {

        // Retrouve l'index de la proposition
        int index = Integer.valueOf(event.getActionCommand());

        // Retrouve la propostion
        Proposition proposition = lieuActuel.propositions.get(index);

        // Recherche le lieu désigné par la proposition
        Lieu lieu = lieux.get(proposition.numeroLieu);
        if (lieu != null) {

            // Affiche la proposition qui vient d'être choisie par le joueur
            affiche(new String[]{"> " + proposition.texte});

            // Affichage du nouveau lieu et création des boutons des nouvelles propositions
            lieuActuel = lieu;
            try {
                initLieu();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Cas particulier : le lieu est déclarée dans une proposition mais pas encore décrit
            // (lors de l'élaboration de l'aventure par exemple)
            showMessageDialog(null,"Lieu n° " + proposition.numeroLieu + " à implémenter");
        }
    }

    /*
     * Gère l'affichage dans la zone de texte, avec un effet de défilement
     * (comme dans un terminal)
     */
    private void affiche(String[] contenu) {
        int n = contenu.length;
        for (int i = 0; i < nbLignes-(n+1); i++) {
            labels[i].setText(labels[i + n + 1].getText());
        }
        labels[nbLignes-(n+1)].setText(" ");
        for(int i = 0; i<n; i++) {
            labels[nbLignes-n+i].setText(contenu[i]);
        }
    }

}
