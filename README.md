### Tâches :
+ [ ] Implémenter analyser lexicale
+ [ ] Implémenter analyse syntaxique
+ [ ] Transformer en noeud
+ [ ] Transformer les noeuds en lieu
+ [ ] Ecrire l'histoire dans notre grammaire

## Grammaire
| | | | |
|-|-|-|-|
|Grammaire| Transitions |First|Follow|
|S|LIEU ## S’|{intVal}|{intVal, $}|
|S’|S "| ε|{intVal, $}|{intVal, $}|
|LIEU|intVal string < PROPOSITION|{intVal}|{##}|
|PROPOSITION|- intVal string < F P’|{-}|{##, -}|
|P’|PROPOSITION "| ε|{$}|{-, $}|
|F|ε|{$}|{-, $}|

## Symboles terminaux
intVal , string , $ , ε , -, < , ##

## Symboles non terminaux
S, S', LIEU, PROPOSITION, P', F


## Table LL1 
| | | | | | | |
|-|-|-|-|-|-|-|
| |intVal|string|-|##|<|$|
|S|LIEU ## S’| | | | | |
|S’|S| | | | |ε|
|LIEU|intVal string < PROPOSITION| | | | | |
|PROPOSITION| | |- intVal string < F P'| | | |
|P’| | |PROPOSITION|ε| | |
|F| | |PROPOSITION|ε| | |
