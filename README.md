<<<<<<< HEAD
# Jalon 1 :

## Grammaire
| | | | |
|-|-|-|-|
|Grammaire| Transitions |First|Follow|
|S|LIEU ## S’|{intVal}|{intVal, $}|
|S’|S \| ε|{intVal, $}|{intVal, $}|
|LIEU|intVal string § PROPOSITION|{intVal}|{##}|
|PROPOSITION|- intVal string § F P’|{-}|{##, -}|
|P’|PROPOSITION \| ε|{$}|{-, $}|
|F|ε|{$}|{-, $}|

## Symboles terminaux
{intVal , string , $ , ε , -, < , ##}

## Symboles non terminaux
{S, S', LIEU, PROPOSITION, P', F}


## Table LL1 
| | | | | | | |
|-|-|-|-|-|-|-|
| |intVal|string|-|##|§|$|
|S|LIEU ## S’| | | | | |
|S’|S| | | | |ε|
|LIEU|intVal string § PROPOSITION| | | | | |
|PROPOSITION| | |- intVal string § F P'| | | |
|P’| | |PROPOSITION|ε| | |
|F| | |ε|ε| | |

## Explication
- F Signifie facultatif, nous porrons l'utuliser au prochain jalon pour intégrer des conditions ou des évènements
- un lieu est resprésenté par un identifiant (intVal) et est représenter par une description (string) à l'utlisateur
- une lieu n'ayant qu'une proposition, qui renvoie vers ce même lieu est consideré comme terminal
- "§" permet de détecter la fin d'un élément
=======

Permet de recuperer l'aventure avec et sans les modifs au cas ou
>>>>>>> histoire_avec_condition
