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

        if (getTypeDeToken() == TypeDeToken.intVal) {
            Noeud lieu = Lieu();

            if (lieu != null) {
                nvLieu.ajout(lieu);
            }
        } else {
            throw new UnexpectedTokenException("'intVal' attendu a la fin d'un lieu.");
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
        throw new UnexpectedTokenException("'##' attendu a la fin d'un lieu.");
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
                throw new UnexpectedTokenException("§ attendu dans un lieu");
            }
            throw new UnexpectedTokenException("String attendu dans un lieu");
        }
        throw new UnexpectedTokenException("intVal attendu dans un lieu");
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
                    throw new UnexpectedTokenException("§ attendu dans une proposition");
                }
                throw new UnexpectedTokenException("stringVal attendu dans une proposition");
            }
            throw new UnexpectedTokenException("intVal attendu dans une proposition");
        }
        if (!t.getValeur().equals("\n")) {
            throw new UnexpectedTokenException("- attendu en debut de proposition");
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
     * F -> COND F'
     */
    // pour rajouter des conditions
    private Noeud F() throws UnexpectedTokenException {
        Noeud noeud = new Noeud(TypeDeNoeud.facultatif);
        if (getTypeDeToken() == TypeDeToken.condition) {
            Noeud cond = Cond();
            noeud.ajout(cond);

            Noeud ff = F_prime();
            if (ff != null) {
                noeud.ajout(ff);
            }

            return  noeud;
        }

        Noeud ff = F_prime();
        if (ff != null) {
            noeud.ajout(ff);
            return noeud;
        } else {
            return null;
        }
    }



    /*
     * F' -> EFFET F' | ε
     */
    private Noeud F_prime() throws UnexpectedTokenException {
        if (getTypeDeToken() == TypeDeToken.effet) {
            Noeud noeud = new Noeud(TypeDeNoeud.facultatif);
            Noeud effet = Effet();
            Noeud ff = F_prime();

            if (effet != null) {
                noeud.ajout(effet);
            }
            if (ff != null){
                noeud.ajout(ff);
            }
            return noeud;
        }

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
            throw new UnexpectedTokenException("§ attendu apres votre titre.");
        }
        throw new UnexpectedTokenException("Titre attendu");
    }

    /*
     * PARAM-> #stat § STAT #objet OBJET § #flag FLAG
     */
    private Noeud Param() throws UnexpectedTokenException {
        Noeud noeudParam = new Noeud(TypeDeNoeud.param);
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.stat) {
            Token t1 = lireToken();
            /* On lit § */
            if (t1.getTypeDeToken() == TypeDeToken.separateurLigne) {
                Noeud stat = Stat();
                if (stat != null) {
                    noeudParam.ajout(stat);
                } else {
                    noeudParam.ajout(new Noeud(TypeDeNoeud.stat));
                }

                Token t2 = lireToken();
                /* On lit § (#objet lu dans Stat()) */
                if (t2.getTypeDeToken() == TypeDeToken.separateurLigne) {

                    Noeud objet = Objet();
                    if (stat != null) {
                        noeudParam.ajout(objet);
                    } else {
                        noeudParam.ajout(new Noeud(TypeDeNoeud.objet));
                    }

                    Token t3 = lireToken();
                    /* On lit § (#flag lu dans Objet()) */
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        Noeud flag = Flag();
                        if (stat != null) {
                            noeudParam.ajout(flag);
                        } else {
                            noeudParam.ajout(new Noeud(TypeDeNoeud.flag));
                        }

                        return noeudParam;
                    }
                    throw new UnexpectedTokenException("§ attendu apres un identifiant #object.");
                }
                throw new UnexpectedTokenException("§ attendu apres un identifiant #stats.");
            }
            throw new UnexpectedTokenException("§ attendu dans les settings apres un idenfiant #flags");
        }
        throw new UnexpectedTokenException("#stat attendu dans vos settings");
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
                                Noeud stat = Stat();

                                if (stat != null) {
                                    noeudStat.ajout(stat);
                                }

                                return noeudStat;
                            }
                            throw new UnexpectedTokenException("§ attendu dans les settings stats");
                        }
                        throw new UnexpectedTokenException("String attendu dans les settings stats");
                    }
                    throw new UnexpectedTokenException("intVal attendu dans les settings stats");
                }
                throw new UnexpectedTokenException("intVal attendu dans les settings stats");
            }
            throw new UnexpectedTokenException("intVal attendu dans les settings stats");
        } else if (t0.getTypeDeToken() == TypeDeToken.objet)
            return null;
        throw new UnexpectedTokenException("- ou #objet attendu dans les settings");
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
                        Noeud obj = Objet();

                        if (obj != null) {
                            noeudObjet.ajout(obj);
                        }

                        return noeudObjet;
                    }
                    throw new UnexpectedTokenException("§ attendu dans les settings objets");
                }
                throw new UnexpectedTokenException("String attendu dans les settings objets");
            }
            throw new UnexpectedTokenException("intVal attendu dans les settings objets");
        } else if (t0.getTypeDeToken() == TypeDeToken.flag)
            return null;
        throw new UnexpectedTokenException("- ou #flag attendu dans le settings");
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
                        Noeud flag = Flag();

                        if (flag != null) {
                            noeudFlag.ajout(flag);
                        }

                        return noeudFlag;
                    }
                    throw new UnexpectedTokenException("§ attendu dans les settings flag");
                }
                throw new UnexpectedTokenException("String attendu dans les settings flag");
            }
            throw new UnexpectedTokenException("intVal attendu dans les settings flag");
        }
        throw new UnexpectedTokenException("- ou intVal attendu dans les settings flag");
    }

    /*
     * COND-> condition: STATEMENT § | epsilon
     */
    private Noeud Cond() throws UnexpectedTokenException {
        Noeud noeud = new Noeud(TypeDeNoeud.condition);
        Token t0 = lireToken();
        /* On lit condition: */
        if (t0.getTypeDeToken() == TypeDeToken.condition) {

                Noeud statement = Statement();
                Token t1 = lireToken();
                if (t1.getTypeDeToken() == TypeDeToken.separateurLigne) {
                    noeud.ajout(statement);
                    return noeud;
                }
                throw new UnexpectedTokenException("§ attendu apres un STATEMENT.");
        }

        return noeud;
    }

    private Noeud Statement() throws UnexpectedTokenException {
        Token t0 = lireToken();
        if (t0.getTypeDeToken() == TypeDeToken.statement) {
            return new Noeud(TypeDeNoeud.statement, t0.getValeur());
        }

        throw new UnexpectedTokenException("'Statement' attendu apres un identifiant condition.");
    }

    /*
     * EFFET-> effect: string SYMBOL intval § | epsilon
     */
    private Noeud Effet() throws UnexpectedTokenException {
        Noeud noeud = new Noeud(TypeDeNoeud.effet);
        Token t0 = lireToken();

        /*On lit effect: */
        if (t0.getTypeDeToken() == TypeDeToken.effet) {
            Token t1 = lireToken();
            /* On lit string */
            if (t1.getTypeDeToken() == TypeDeToken.stringVal) {
                noeud.ajout(new Noeud(TypeDeNoeud.string, t1.getValeur()));

                Noeud symbol = Symbol();
                noeud.ajout(symbol);
                /* On lit le token suivant */
                Token t2 = lireToken();
                /* On lit intval */
                if (t2.getTypeDeToken() == TypeDeToken.intVal) {
                    noeud.ajout(new Noeud(TypeDeNoeud.intVal, t2.getValeur()));

                    /* On lit le token suivant */
                    Token t3 = lireToken();
                    /* On lit § */
                    if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        return noeud;
                    }
                    throw new UnexpectedTokenException("§ attendu a la fin d'un effet.");
                }
                throw new UnexpectedTokenException("Intval attendu dans un effet.");
            }
            throw new UnexpectedTokenException("Stringval attendu dans un effet.");
        }

        return noeud;
    }

    /*
     * SYMBOL-> + | - | set
     */
    private Noeud Symbol() throws UnexpectedTokenException {

        Token t = lireToken();

        if (t.getTypeDeToken() == TypeDeToken.condAdd) {
            return new Noeud(TypeDeNoeud.symbolAdd, "add");
        } else if (t.getTypeDeToken() == TypeDeToken.condSub) {
            return new Noeud(TypeDeNoeud.symbolSub, "sub");
        } else if (t.getTypeDeToken() == TypeDeToken.condSet) {
            return new Noeud(TypeDeNoeud.symbolSet, "set");
        }

        throw new UnexpectedTokenException("#add ou #add ou #set attendu dans votre effet.");
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
