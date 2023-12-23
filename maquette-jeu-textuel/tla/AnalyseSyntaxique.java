package tla;

import exception_tla.IncompleteParsingException;
import exception_tla.UnexpectedTokenException;

import java.util.List;

/*
 * MULARD Andreas 
 * BERTHEL Gabriel
 * LAGASSE Adrian 
 * YOUSRI Célia
 */

public class AnalyseSyntaxique {
    private int pos;
    private List<Token> tokens;

    public Noeud analyse(List<Token> tokens) throws Exception {
        pos = 0;
        this.tokens = tokens;
        Noeud expr = First();
        if (pos != tokens.size()) {
            System.out.println("L'analyse syntaxique s'est terminé avant l'examen de tous les tokens");
            throw new IncompleteParsingException();
        }
        return expr;
    }

    /*
     * S -> LIEU ## S'
     */
    private Noeud S() throws UnexpectedTokenException {
        Noeud nvLieu = new Noeud(TypeDeNoeud.lieuContainer);
        System.out.println(getTypeDeToken());

        if (getTypeDeToken() == TypeDeToken.intVal) {
            nvLieu.ajout(Lieu());
        } else {
            throw new UnexpectedTokenException("'intVal' attendu");
        }

        /*
         * Lire ##
         */
        if (getTypeDeToken() == TypeDeToken.finLieu) {
            Token t = lireToken();
            Noeud n = S_prime();
            if (n != null) {
                nvLieu.ajout(n);
            }
            return nvLieu;
        }
        throw new UnexpectedTokenException("'##' attendu");
    }

    /*
     * S' -> S | ε
     */
    private Noeud S_prime() throws UnexpectedTokenException {
        if (finAtteinte()) {
            return null;
        } else {
            return S();
        }
    }

    /*
     * LIEU -> intVal string § PROPOSITION
     */
    private Noeud Lieu() throws UnexpectedTokenException {
        Noeud noeud = new Noeud(TypeDeNoeud.lieu);
        Token t = lireToken();
        /* On lit intval */
        if (t.getTypeDeToken() == TypeDeToken.intVal) {
            /* On lit le token suivant */
            Token t1 = lireToken();
            noeud.ajout(new Noeud(TypeDeNoeud.intVal, "" + t.getValeur()));
            /* On lit String */
            if (t1.getTypeDeToken() == TypeDeToken.stringVal) {
                /* On lit le token suivant */
                Token t2 = lireToken();
                noeud.ajout(new Noeud(TypeDeNoeud.string, t1.getValeur()));
                /* On lit § */
                if (t2.getTypeDeToken() == TypeDeToken.separateurLigne) {
                    noeud.ajout(Proposition());
                    return noeud;
                }
                throw new UnexpectedTokenException("§ attendu");
            }
            throw new UnexpectedTokenException("String attendu");
        }
        throw new UnexpectedTokenException("intVal attendu");
    }
    /*
     * PROPOSITION -> - intVal string § F P’
     */

    private Noeud Proposition() throws UnexpectedTokenException {
        Token t = lireToken();
        Noeud noeud = new Noeud(TypeDeNoeud.proposition);
        if (t.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                noeud.ajout(new Noeud(TypeDeNoeud.intVal, "" + t1.getValeur()));
                Token t2 = lireToken();
                if (t2.getTypeDeToken() == TypeDeToken.stringVal) {
                    Token t3 = lireToken();
                    noeud.ajout(new Noeud(TypeDeNoeud.string, "" + t2.getValeur()));
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        Noeud f = F();
                        Noeud pp = P_prime();
                        if (f != null) {
                            noeud.ajout(f);
                        }
                        if (pp != null) {
                            noeud.ajout(pp);
                        }
                        return noeud;
                    }
                    throw new UnexpectedTokenException("§ attendu");
                }
                throw new UnexpectedTokenException("stringVal attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        }
        if (!t.getValeur().equals("\n")) {
            throw new UnexpectedTokenException("- attendu");
        }
        return null;
    }

    /*
     * P' -> PROPOSITION | ε
     */
    private Noeud P_prime() throws UnexpectedTokenException {
        if (finAtteinte()) {
            return null;
        }
        if (getTypeDeToken() == TypeDeToken.finLieu) {
            return null;
        }
        return Proposition();
    }

    /*
     * F -> ε
     */
    // pour rajouter des conditions
    private Noeud F() {
        return null;
    }

    /*
     * FIRST-> string § PARAM S
     */
    private Noeud First() throws UnexpectedTokenException {
        Noeud noeudFirst = new Noeud(TypeDeNoeud.first);
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.stringVal) {
            noeudFirst.ajout(new Noeud(TypeDeNoeud.string, t0.getValeur()));

            Token t1 = lireToken();
            /* On lit § */
            if (t1.getTypeDeToken() == TypeDeToken.separateurLigne) {
                Noeud param = Param();
                Noeud s = S();
                if (param != null) {
                    noeudFirst.ajout(param);
                }
                if (s != null) {
                    noeudFirst.ajout(s);
                }
                return noeudFirst;
            }
            throw new UnexpectedTokenException("§ attendu");
        }
        throw new UnexpectedTokenException("String attendu");
    }

    /*
     * PARAM-> #stat § STAT #objet OBJET § #flag FLAG
     */
    private Noeud Param() throws UnexpectedTokenException {
        Noeud noeudParam = new Noeud(TypeDeNoeud.param);
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.stat) {
            noeudParam.ajout(new Noeud(TypeDeNoeud.stat, t0.getValeur()));

            Token t1 = lireToken();
            /* On lit § */
            if (t1.getTypeDeToken() == TypeDeToken.separateurLigne) {
                Noeud stat = Stat();
                if (stat != null)
                    noeudParam.ajout(stat);

                Token t2 = lireToken();
                /* On lit #objet */
                if (t2.getTypeDeToken() == TypeDeToken.objet) {
                    noeudParam.ajout(new Noeud(TypeDeNoeud.objet, t2.getValeur()));

                    Noeud objet = Objet();
                    if (objet != null)
                        noeudParam.ajout(objet);

                    Token t3 = lireToken();
                    /* On lit § */
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        Token t4 = lireToken();
                        if (t4.getTypeDeToken() == TypeDeToken.flag) {
                            Noeud flag = Flag();
                            if (flag != null)
                                noeudParam.ajout(flag);

                            return noeudParam;
                        }
                        throw new UnexpectedTokenException("#flag attendu");
                    }
                    throw new UnexpectedTokenException("§ attendu");
                }
                throw new UnexpectedTokenException("#objet attendu");
            }
            throw new UnexpectedTokenException("§ attendu");
        }
        throw new UnexpectedTokenException("#stat attendu");
    }

    /*
     * STAT-> - intval intval intval string § STAT | epsilon
     */
    private Noeud Stat() throws UnexpectedTokenException {
        Noeud noeudStat = new Noeud(TypeDeNoeud.stat);
        Token t0 = lireToken();
        // System.out.println("v " + t0.getTypeDeToken());
        if (t0.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            /* On lit intval */
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                noeudStat.ajout(new Noeud(TypeDeNoeud.intVal, "" + t1.getValeur()));
                /* On lit le token suivant */
                Token t2 = lireToken();
                /* On lit intVal */
                if (t2.getTypeDeToken() == TypeDeToken.intVal) {
                    noeudStat.ajout(new Noeud(TypeDeNoeud.intVal, "" + t2.getValeur()));
                    /* On lit le token suivant */
                    Token t3 = lireToken();
                    /* On lit intVal */
                    if (t3.getTypeDeToken() == TypeDeToken.intVal) {
                        noeudStat.ajout(new Noeud(TypeDeNoeud.intVal, "" + t3.getValeur()));
                        /* On lit le token suivant */
                        Token t4 = lireToken();
                        /* On lit String */
                        if (t4.getTypeDeToken() == TypeDeToken.stringVal) {
                            noeudStat.ajout(new Noeud(TypeDeNoeud.string, t4.getValeur()));
                            /* On lit le token suivant */
                            Token t5 = lireToken();
                            /* On lit § */
                            if (t5.getTypeDeToken() == TypeDeToken.separateurLigne) {
                                noeudStat.ajout(Stat());
                                return noeudStat;
                            }
                            throw new UnexpectedTokenException("§ attendu");
                        }
                        throw new UnexpectedTokenException("String attendu");
                    }
                    throw new UnexpectedTokenException("intVal attendu");
                }
                throw new UnexpectedTokenException("intVal attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        } else if (t0.getTypeDeToken() == TypeDeToken.objet)
            return noeudStat;
        throw new UnexpectedTokenException("- ou #objet attendu");
    }

    /*
     * OBJET-> - intval string § OBJET | epsilon
     */
    private Noeud Objet() throws UnexpectedTokenException {
        Noeud noeudObjet = new Noeud(TypeDeNoeud.objet);
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            /* On lit intval */
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                noeudObjet.ajout(new Noeud(TypeDeNoeud.intVal, "" + t1.getValeur()));
                /* On lit le token suivant */
                Token t2 = lireToken();
                /* On lit String */
                if (t2.getTypeDeToken() == TypeDeToken.stringVal) {
                    noeudObjet.ajout(new Noeud(TypeDeNoeud.string, t2.getValeur()));
                    /* On lit le token suivant */
                    Token t3 = lireToken();
                    /* On lit § */
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        noeudObjet.ajout(Objet());
                        return noeudObjet;
                    }
                    throw new UnexpectedTokenException("§ attendu");
                }
                throw new UnexpectedTokenException("String attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        } else if (t0.getTypeDeToken() == TypeDeToken.flag)
            return noeudObjet;
        throw new UnexpectedTokenException("- ou #flag attendu");
    }

    /*
     * FLAG-> - intval string § FLAG | epsilon
     */
    private Noeud Flag() throws UnexpectedTokenException {
        if (getTypeDeToken() == TypeDeToken.intVal)
            return null;

        Noeud noeudFlag = new Noeud(TypeDeNoeud.flag);
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            /* On lit intval */
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                noeudFlag.ajout(new Noeud(TypeDeNoeud.intVal, "" + t1.getValeur()));
                /* On lit le token suivant */
                Token t2 = lireToken();
                /* On lit String */
                if (t2.getTypeDeToken() == TypeDeToken.stringVal) {
                    noeudFlag.ajout(new Noeud(TypeDeNoeud.string, t2.getValeur()));
                    /* On lit le token suivant */
                    Token t3 = lireToken();
                    /* On lit § */
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        noeudFlag.ajout(Flag());
                        return noeudFlag;
                    }
                    throw new UnexpectedTokenException("§ attendu");
                }
                throw new UnexpectedTokenException("String attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        }
        throw new UnexpectedTokenException("- ou intVal attendu");
    }

    /*
     * 
     * méthodes utilitaires
     * 
     */

    private boolean finAtteinte() {
        return pos >= tokens.size();
    }

    /*
     * Retourne la classe du prochain token à lire
     * SANS AVANCER au token suivant
     */
    private TypeDeToken getTypeDeToken() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            return tokens.get(pos).getTypeDeToken();
        }
    }

    /*
     * Retourne le prochain token à lire
     * ET AVANCE au token suivant
     */
    private Token lireToken() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            Token t = tokens.get(pos);
            pos++;
            return t;
        }
    }
}
