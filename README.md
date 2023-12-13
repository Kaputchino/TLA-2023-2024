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
